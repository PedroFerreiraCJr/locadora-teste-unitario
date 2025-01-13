package br.ce.wcaquino.teste_unitario.servicos;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class CalculadoraMockTest {

	@Test
	public void teste() {
		Calculadora calc = Mockito.mock(Calculadora.class);
		
		// se for utilizado um matchers, como o exemplo abaixo, é necessário usar
		// um matcher para cada parâmetro do método;
		Mockito.when(calc.somar(Mockito.eq(1), Mockito.anyInt())).thenReturn(5);
		
		Assert.assertEquals(5, calc.somar(1, 2));
	}
}
