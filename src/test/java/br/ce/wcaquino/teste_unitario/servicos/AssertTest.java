package br.ce.wcaquino.teste_unitario.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.teste_unitario.entidades.Usuario;

public class AssertTest {
	@Test
	public void test() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);

		Assert.assertEquals(1, 1); // lê-se: é experado que o valor seja 1, avaliando o valor do segundo argumento;
		Assert.assertEquals(0.51, 0.51, 0.01); // o terceiro argumento é um delta de variação (margem de erro);
		Assert.assertEquals(Math.PI, 3.14, 0.01); // como não quero precisar colocar o valor com total precisão, é feito
													// a declaração do delta;

		int i = 5;
		Integer i2 = 5;
//		Assert.assertEquals(i, i2);	// não compila, pois é preciso usar tipos compatíveis, primitivo com primitivo, ou o contrário;

		Assert.assertEquals("Bola", "Bola");
		Assert.assertEquals("Bola".toLowerCase(), "bola"); // é preciso comparar usando o valor em caixa baixa com caixa
															// baixa
		Assert.assertNotEquals("Bola", "Casa");
		Assert.assertTrue("Bola".startsWith("Bo"));

		// no caso de objetos de outras classes, quem deve dizer se eles são iguais é a
		// própria classe usando o método equals
		Usuario u1 = new Usuario("Usuário 1");
		Usuario u2 = new Usuario("Usuário 1");
		Usuario u3 = u2;
		Assert.assertEquals(u1, u2);

		// para comparar se dois objetos são a mesma instância, use
		Assert.assertSame(u2, u3);
		Assert.assertNotSame(u1, u2); // valida o contrário

		u3 = null;
		Assert.assertTrue(u3 == null);
		Assert.assertNull(u3);
		u3 = u2;
		Assert.assertNotNull(u3);
	}
}
