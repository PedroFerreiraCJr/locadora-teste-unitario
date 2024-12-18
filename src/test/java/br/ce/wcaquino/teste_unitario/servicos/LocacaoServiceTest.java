package br.ce.wcaquino.teste_unitario.servicos;

import static br.ce.wcaquino.teste_unitario.matchers.MatchersProprios.caiNumaSegunda;
import static br.ce.wcaquino.teste_unitario.matchers.MatchersProprios.ehHoje;
import static br.ce.wcaquino.teste_unitario.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.teste_unitario.entidades.Filme;
import br.ce.wcaquino.teste_unitario.entidades.Locacao;
import br.ce.wcaquino.teste_unitario.entidades.Usuario;
import br.ce.wcaquino.teste_unitario.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.teste_unitario.exceptions.LocadoraException;
import br.ce.wcaquino.teste_unitario.utils.DataUtils;

public class LocacaoServiceTest {

	private LocacaoService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		service = new LocacaoService();
	}

	/**
	 * <pre>
	 * 	1º O primeiro passo para tornar este código um teste é torná-lo um método
	 * público e com retorno void.
	 * 	2º O segundo passo é anotá-lo com @Test do pacote org.junit.
	 * 	3º O terceiro passo a ser realizado é fazer uso das assertions disponíveis
	 * para validar o resultado da execução do método que está sendo testado.
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void deveAlugarFilme() throws Exception {
		// o nome desta feature do JUnit é Assumptions;
		// ela permite que o teste somente seja executado sob determinadas condições;
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Collections.singletonList(new Filme("Filme 1", 2, 5.0));

		// ação
		Locacao locacao = service.alugarFilmes(usuario, filmes);

		// verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(locacao.getDataLocacao(), ehHoje());
		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
	}

	/**
	 * Essa é uma abordagem de captura de exceção considerada Elegante, pois basta
	 * declarar a cláusula throws e adicionar o metadado na anotação @Test.
	 */
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Collections.singletonList(new Filme("Filme 1", 0, 4.0));

		// ação
		service.alugarFilmes(usuario, filmes);
	}

	/**
	 * Essa é uma abordagem de captura de exceção considerada Robusta, porque basta
	 * declarar a cláusula throws para possíveis exceptions que não se deseja
	 * capturar, além disso, é preciso adicionar um bloco try/catch para capturar a
	 * exception que se está testando; desta forma, é possível, tanto validar a
	 * exception que se está esperando, assim como, a mensagem enviada na exception.
	 */
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		// cenário
		List<Filme> filmes = Collections.singletonList(new Filme("Filme 1", 1, 5.0));

		// ação
		try {
			service.alugarFilmes(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}

	/**
	 * Essa é uma abordagem de captura de exceção considerada Nova (porque foi uma
	 * funcionalidade adicionada posteriormente no framework JUnit); com ela, também
	 * é possível informar ao JUnit que se esperar uma determinada exception, assim
	 * como, a mensagem que se espera obter da exception.
	 */
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		// ação
		service.alugarFilmes(usuario, null);
	}

	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0));

		// ação
		Locacao resultado = service.alugarFilmes(usuario, filmes);

		// verificação
		// Assert.assertThat(resultado.getDataRetorno(), caiEm(Calendar.MONDAY));
		Assert.assertThat(resultado.getDataRetorno(), caiNumaSegunda());
	}
}
