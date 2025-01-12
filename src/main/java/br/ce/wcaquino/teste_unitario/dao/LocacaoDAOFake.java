package br.ce.wcaquino.teste_unitario.dao;

import java.util.List;

import br.ce.wcaquino.teste_unitario.entidades.Locacao;

public class LocacaoDAOFake implements LocacaoDAO {
	@Override
	public void salvar(Locacao locacao) {
	}

	@Override
	public List<Locacao> obterLocacoesPendentes() {
		return null;
	}
}
