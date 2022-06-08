package br.ce.wcaquino.teste_unitario.servicos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Configura que a ordem de execução dos testes deve levar em consideração a
 * ordem léxica
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemTest {

	public static int contador = 0;

	@Test
	public void inicia() {
		contador++;
	}

	@Test
	public void verifica() {
		Assert.assertEquals(1, contador);
	}

	/*
	@Test
	public void testGeral() {
		inicia();
		verifica();
	}
	*/
}
