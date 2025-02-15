package br.ce.wcaquino.teste_unitario.servicos;

import static br.ce.wcaquino.teste_unitario.matchers.MatchersProprios.caiNumaSegunda;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.ce.wcaquino.teste_unitario.builders.FilmeBuilder;
import br.ce.wcaquino.teste_unitario.builders.UsuarioBuilder;
import br.ce.wcaquino.teste_unitario.dao.LocacaoDAO;
import br.ce.wcaquino.teste_unitario.entidades.Filme;
import br.ce.wcaquino.teste_unitario.entidades.Locacao;
import br.ce.wcaquino.teste_unitario.entidades.Usuario;
import br.ce.wcaquino.teste_unitario.utils.DataUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LocacaoService.class })
public class LocacaoServiceTest_PowerMock {

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

		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(28, 4, 2017));

		// ação
		Locacao locacao = service.alugarFilmes(usuario, filmes);

		// verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28, 4, 2017)), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(29, 4, 2017)), is(true));
	}

	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

		PowerMockito.whenNew(Date.class).withNoArguments()
			.thenReturn(DataUtils.obterData(29, 4, 2017));

		// ação
		Locacao resultado = service.alugarFilmes(usuario, filmes);

		// verificação
		Assert.assertThat(resultado.getDataRetorno(), caiNumaSegunda());
	}

	@Test
	public void deveAlugarFilmeSemCalcularValor() throws Exception {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Collections.singletonList(FilmeBuilder.umFilme().agora());

		PowerMockito.doReturn(1.0)
			.when(service, "calcularValorLocacao", filmes);

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
		Double valor = (Double) Whitebox.invokeMethod(service, "calcularValorLocacao", filmes);

		// verificação
		Assert.assertThat(valor, CoreMatchers.is(4.0));
	}
}
