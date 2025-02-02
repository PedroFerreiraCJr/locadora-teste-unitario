package br.ce.wcaquino.teste_unitario.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CalculadoraMockTest {

	@Mock
	private Calculadora calcMock;

	@Spy
	private Calculadora calcSpy;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void deveriaDemonstrarADiferencaEntreMockESpy() {
		// neste caso, estamos gravando uma expectativa no objeto 'calcMock';
		// quando o método 'somar' do objeto mock 'calcMock' for invocado, ele
		// deverá retornar o valor '8';
		// quando o método 'somar' for invocado com os argumentos '1', e '2', o
		// mock deverá retornar o valor '8';
		Mockito.when(calcMock.somar(1, 2)).thenReturn(8);

		// no momento da gravação da expectativa abaixo, o método 'somar' será
		// executado; mas por quê isso ocorre? porque estamos usando uma forma
		// que primeiro passamos a invocação do método 'somar'; mas podemos fazer
		// de outra maneira, para que o método 'somar' não chegue a ser executado;
		// Mockito.when(calcSpy.somar(1, 2)).thenReturn(8);

		// essa é a forma de não executar o método 'somar', no momento de estabelecer
		// a gravação de expectativas; desta forma, não será impresso o print do método
		// 'somar';
		Mockito.doReturn(5).when(calcSpy).somar(1, 2);
		Mockito.doReturn(5).when(calcSpy).somar(1, 5);

		// para fazer com que, um 'Spy', não execute o método real, basta
		// gravar a seguinte expectativa; neste caso, o método é 'void';
		Mockito.doNothing().when(calcSpy).imprimir();

		// aqui, estamos invocando o método do objeto Mock;
		// caso os argumentos do método 'somar' forem diferentes do que foi
		// estabelecido na expectativa, então o mock retorna o valor padrão
		// de acordo com o tipo de retorno do método, que neste caso, é um 'int';
		System.out.println("Mock: " + calcMock.somar(1, 2));

		// essa é uma invocação em um 'Mock', que não tem uma expectativa gravada
		// para os argumentos do método 'somar', portanto, o mock irá retornar o
		// valor padrão, que para o tipo 'int', é '0';
		System.out.println("Mock: " + calcMock.somar(1, 5));

		// caso um 'Spy' seja invocado com argumentos que não tem alguma expectativa
		// gravada para eles, então, o comportamento padrão do 'Spy' é realizar
		// a invocação do método real;
		// essa linha tem uma expectativa gravada, portanto, o valor de retorno que
		// foi estabelecido, será o resultado desta invocação;
		System.out.println("Spy: " + calcSpy.somar(1, 2));

		// para essa invocação do método 'somar' da instância que é um 'Spy', ele
		// irá invocar o método real, porque esse é o comportamento padrão de um 'Spy';
		System.out.println("Spy: " + calcSpy.somar(1, 5));

		System.out.println("Mock");
		calcMock.imprimir();

		System.out.println("Spy");
		calcSpy.imprimir();
	}

	@Test
	public void teste() {
		Calculadora calc = Mockito.mock(Calculadora.class);

		ArgumentCaptor<Integer> argCaptor = ArgumentCaptor.forClass(Integer.class);
		Mockito.when(calc.somar(argCaptor.capture(), argCaptor.capture())).thenReturn(5);

		Assert.assertEquals(5, calc.somar(1, 2));
		// System.out.print(argCaptor.getAllValues());
	}
}
