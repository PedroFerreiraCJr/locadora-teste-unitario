package br.ce.wcaquino.teste_unitario.builders;

import br.ce.wcaquino.teste_unitario.entidades.Usuario;

public class UsuarioBuilder {
	private Usuario usuario;

	private UsuarioBuilder() {
		super();
	}

	public static UsuarioBuilder umUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Usuario 1");
		return builder;
	}

	public Usuario agora() {
		return usuario;
	}
}
