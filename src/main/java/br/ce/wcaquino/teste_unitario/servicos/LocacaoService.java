package br.ce.wcaquino.teste_unitario.servicos;

import static br.ce.wcaquino.teste_unitario.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.teste_unitario.dao.LocacaoDAO;
import br.ce.wcaquino.teste_unitario.entidades.Filme;
import br.ce.wcaquino.teste_unitario.entidades.Locacao;
import br.ce.wcaquino.teste_unitario.entidades.Usuario;
import br.ce.wcaquino.teste_unitario.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.teste_unitario.exceptions.LocadoraException;
import br.ce.wcaquino.teste_unitario.utils.DataUtils;

public class LocacaoService {

	private LocacaoDAO dao;
	private SPCService service;
	private EmailService emailService;

	public Locacao alugarFilmes(Usuario usuario, List<Filme> filmes)
			throws FilmeSemEstoqueException, LocadoraException {
		if (usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}

		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}

		for (Filme filme : filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
		}

		boolean negativado = false;
		try {
			negativado = this.service.possuiNegativacao(usuario);
		} catch (Exception e) {
			throw new LocadoraException("Problemas com SPC, tente novamente");
		}

		if (negativado) {
			throw new LocadoraException("Usuário Negativado");
		}

		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(obterData());
		locacao.setValor(calcularValorLocacao(filmes));

		// Entrega no dia seguinte
		Date dataEntrega = obterData();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		dao.salvar(locacao);

		return locacao;
	}

	protected Date obterData() {
		return new Date();
	}

	private double calcularValorLocacao(List<Filme> filmes) {
		double valorTotal = 0.0d;
		for (int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();
			switch (i) {
				case 2:
					valorFilme = valorFilme * 0.75d;
					break;
				case 3:
					valorFilme = valorFilme * 0.5d;
					break;
				case 4:
					valorFilme = valorFilme * 0.25d;
					break;
				case 5:
					valorFilme = 0.0d;
			}

			valorTotal += valorFilme;
		}
		return valorTotal;
	}

	public void notificarAtrasos() {
		List<Locacao> locacoes = this.dao.obterLocacoesPendentes();
		for (Locacao locacao : locacoes) {
			if (locacao.getDataRetorno().before(obterData())) {
				this.emailService.notificarAtraso(locacao.getUsuario());
			}
		}
	}

	public void prorrogarLocacao(Locacao locacao, int dias) {
		Locacao novaLocacao = new Locacao();
		novaLocacao.setUsuario(locacao.getUsuario());
		novaLocacao.setFilmes(locacao.getFilmes());
		novaLocacao.setDataLocacao(obterData());
		novaLocacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(dias));
		novaLocacao.setValor(locacao.getValor() * dias);

		dao.salvar(novaLocacao);
	}
}
