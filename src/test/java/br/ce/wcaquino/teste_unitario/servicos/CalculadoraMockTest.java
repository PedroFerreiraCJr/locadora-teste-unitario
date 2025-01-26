package br.ce.wcaquino.teste_unitario.servicos;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class CalculadoraMockTest {

	@Test
	public void teste() {
		Calculadora calc = Mockito.mock(Calculadora.class);

		ArgumentCaptor<Integer> argCaptor = ArgumentCaptor.forClass(Integer.class);
		Mockito.when(calc.somar(argCaptor.capture(), argCaptor.capture())).thenReturn(5);

		Assert.assertEquals(5, calc.somar(1, 2));
		System.out.print(argCaptor.getAllValues());
	}
}
