package br.ce.wcaquino.teste_unitario.servicos;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.teste_unitario.exceptions.NaoPodeDividirPorZeroException;

public class CalculadoraTest {

	private Calculadora calculadora;

	@Before
	public void setUp() {
		calculadora = new Calculadora();
	}

	@Test
	public void deveSomarDoisValores() {
		// cenário
		int a = 5;
		int b = 3;

		// ação
		int resultado = calculadora.somar(a, b);

		// verificação
		assertEquals(8, resultado);
	}

	@Test
	public void deveSubtrairDoisValores() {
		// cenário
		int a = 8;
		int b = 5;

		// ação
		int resultado = calculadora.subtrair(a, b);

		// verificação
		assertEquals(3, resultado);
	}

	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		// cenário
		int a = 6;
		int b = 3;

		// ação
		int resultado = calculadora.dividir(a, b);

		// verificação
		assertEquals(2, resultado);
	}

	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		// cenário
		int a = 10;
		int b = 0;

		// ação
		calculadora.dividir(a, b);
	}

	@Test
	public void deveDividir() {
		String a = "6";
		String b = "3";

		int resultado = calculadora.divide(a, b);

		Assert.assertEquals(2, resultado);
	}
}
