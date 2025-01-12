package br.ce.wcaquino.teste_unitario.dao;

import java.util.List;

import br.ce.wcaquino.teste_unitario.entidades.Locacao;

public interface LocacaoDAO {
	public void salvar(Locacao locacao);

	public List<Locacao> obterLocacoesPendentes();
}
