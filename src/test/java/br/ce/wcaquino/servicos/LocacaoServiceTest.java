package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.teste_unitario.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.teste_unitario.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.teste_unitario.entidades.Filme;
import br.ce.wcaquino.teste_unitario.entidades.Locacao;
import br.ce.wcaquino.teste_unitario.entidades.Usuario;
import br.ce.wcaquino.teste_unitario.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.teste_unitario.exceptions.LocadoraException;
import br.ce.wcaquino.teste_unitario.servicos.LocacaoService;

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
	 * Código de exemplo das anotações demonstradas na aula
	@After
	public void tearDown() {
		System.out.println("After");
	}
	
	@BeforeClass
	public static void setUpClass() {
		System.out.println("BeforeClass");
	}
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println("AfterClass");
	}
	*/
	
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
	public void testeLocacao() throws Exception {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// ação
		Locacao locacao = null;
		locacao = service.alugarFilme(usuario, filme);

		// verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}

	/**
	 * Essa é uma abordagem de captura de exceção considerada elegante, pois basta
	 * declarar a cláusula throws e adicionar o metadado na anotação @Test.
	 */
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// ação
		service.alugarFilme(usuario, filme);
	}

	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// cenário
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// ação
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}

	@Test
	public void testLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		// ação
		service.alugarFilme(usuario, null);
	}
}
