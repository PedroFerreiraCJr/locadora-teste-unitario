package br.ce.wcaquino.teste_unitario.servicos;

import br.ce.wcaquino.teste_unitario.entidades.Usuario;

public interface EmailService {
	public void notificarAtraso(Usuario usuario);
}
