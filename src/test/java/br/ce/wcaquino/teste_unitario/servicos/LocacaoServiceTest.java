package br.ce.wcaquino.teste_unitario.servicos;

import static br.ce.wcaquino.teste_unitario.matchers.MatchersProprios.caiNumaSegunda;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.ce.wcaquino.teste_unitario.builders.FilmeBuilder;
import br.ce.wcaquino.teste_unitario.builders.LocacaoBuilder;
import br.ce.wcaquino.teste_unitario.builders.UsuarioBuilder;
import br.ce.wcaquino.teste_unitario.dao.LocacaoDAO;
import br.ce.wcaquino.teste_unitario.entidades.Filme;
import br.ce.wcaquino.teste_unitario.entidades.Locacao;
import br.ce.wcaquino.teste_unitario.entidades.Usuario;
import br.ce.wcaquino.teste_unitario.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.teste_unitario.exceptions.LocadoraException;
import br.ce.wcaquino.teste_unitario.matchers.MatchersProprios;
import br.ce.wcaquino.teste_unitario.utils.DataUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LocacaoService.class })
public class LocacaoServiceTest {

	@InjectMocks
	private LocacaoService service;

	@Mock
	private LocacaoDAO dao;

	@Mock
	private SPCService spc;

	@Mock
	private EmailService email;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = PowerMockito.spy(service);
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
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Collections.singletonList(FilmeBuilder.umFilme().comValor(5.0).agora());

		// PowerMockito.whenNew(Date.class).withNoArguments()
		// .thenReturn(DataUtils.obterData(28, 4, 2017));

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 28);
		calendar.set(Calendar.MONTH, Calendar.APRIL);
		calendar.set(Calendar.YEAR, 2017);

		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);

		// ação
		Locacao locacao = service.alugarFilmes(usuario, filmes);

		// verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
//		error.checkThat(locacao.getDataLocacao(), ehHoje());
//		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28, 4, 2017)), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(29, 4, 2017)), is(true));
	}

	/**
	 * Essa é uma abordagem de captura de exceção considerada Elegante, pois basta
	 * declarar a cláusula throws e adicionar o metadado na anotação @Test.
	 */
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Collections.singletonList(FilmeBuilder.umFilmeSemEstoque().agora());

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
		List<Filme> filmes = Collections.singletonList(FilmeBuilder.umFilme().agora());

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
		Usuario usuario = UsuarioBuilder.umUsuario().agora();

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		// ação
		service.alugarFilmes(usuario, null);
	}

	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

		// PowerMockito.whenNew(Date.class).withNoArguments()
		// .thenReturn(DataUtils.obterData(29, 4, 2017));

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 29);
		calendar.set(Calendar.MONTH, Calendar.APRIL);
		calendar.set(Calendar.YEAR, 2017);

		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);

		// ação
		Locacao resultado = service.alugarFilmes(usuario, filmes);

		// verificação
		Assert.assertThat(resultado.getDataRetorno(), caiNumaSegunda());
		// PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();

		PowerMockito.verifyStatic(Mockito.times(2));
		Calendar.getInstance();
	}

	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws Exception {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

		Mockito.when(spc.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);

		// ação
		try {
			service.alugarFilmes(usuario, filmes);

			// verificação
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuário Negativado"));
		}

		Mockito.verify(spc).possuiNegativacao(usuario);
	}

	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usuario em dia").agora();
		Usuario usuario3 = UsuarioBuilder.umUsuario().comNome("Outro atrasado").agora();
		List<Locacao> locacoes = Arrays.asList(LocacaoBuilder.umLocacao().comUsuario(usuario).atrasado().agora(),
				LocacaoBuilder.umLocacao().comUsuario(usuario2).agora(),
				LocacaoBuilder.umLocacao().comUsuario(usuario3).atrasado().agora(),
				LocacaoBuilder.umLocacao().comUsuario(usuario3).atrasado().agora());

		Mockito.when(dao.obterLocacoesPendentes()).thenReturn(locacoes);

		// acao
		service.notificarAtrasos();

		// verificação
		Mockito.verify(email, Mockito.times(3)).notificarAtraso(Mockito.any(Usuario.class));
		Mockito.verify(email).notificarAtraso(usuario);
		Mockito.verify(email, Mockito.atLeastOnce()).notificarAtraso(usuario3);
		Mockito.verify(email, Mockito.never()).notificarAtraso(usuario2);
		Mockito.verifyNoMoreInteractions(email);
	}

	@Test
	public void deveTratarErroNoSPC() throws Exception {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

		Mockito.when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha catrastófica"));

		exception.expect(LocadoraException.class);
		exception.expectMessage("Problemas com SPC, tente novamente");

		// ação
		service.alugarFilmes(usuario, filmes);
	}

	@Test
	public void deveProrrogarUmaLocacao() {
		// cenário
		Locacao locacao = LocacaoBuilder.umLocacao().agora();

		// ação
		service.prorrogarLocacao(locacao, 3);

		// verificação
		ArgumentCaptor<Locacao> argCaptor = ArgumentCaptor.forClass(Locacao.class);
		Mockito.verify(dao).salvar(argCaptor.capture());
		Locacao locacaoRetornada = argCaptor.getValue();

		error.checkThat(locacaoRetornada.getValor(), is(12.0));
		error.checkThat(locacaoRetornada.getDataLocacao(), MatchersProprios.ehHoje());
		error.checkThat(locacaoRetornada.getDataRetorno(), MatchersProprios.ehHojeComDiferencaDias(3));
	}

	@Test
	public void deveAlugarFilmeSemCalcularValor() throws Exception {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Collections.singletonList(FilmeBuilder.umFilme().agora());

		PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes);

		// ação
		Locacao locacao = service.alugarFilmes(usuario, filmes);

		// verificação
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(1.0));
		PowerMockito.verifyPrivate(service).invoke("calcularValorLocacao", filmes);
	}

	/**
	 * Através da classe Whitebox do PowerMock, é possível invocar diretamente um
	 * método de instância privado;
	 */
	@Test
	public void deveCalcularValorLocacao() throws Exception {
		// cenário
		List<Filme> filmes = Collections.singletonList(FilmeBuilder.umFilme().agora());

		// ação
		// o método 'invokeMethod' retorna um Object, portanto, é preciso do casting
		// para o respectivo valor retornado pela invocação real do método;
		Double valor = (Double) Whitebox
				.invokeMethod(service, "calcularValorLocacao", filmes);

		// verificação
		Assert.assertThat(valor, CoreMatchers.is(4.0));
	}
}
