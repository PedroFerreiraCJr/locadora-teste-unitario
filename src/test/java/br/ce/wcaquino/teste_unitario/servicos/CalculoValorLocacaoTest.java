package br.ce.wcaquino.teste_unitario.servicos;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.teste_unitario.builders.FilmeBuilder;
import br.ce.wcaquino.teste_unitario.builders.UsuarioBuilder;
import br.ce.wcaquino.teste_unitario.dao.LocacaoDAO;
import br.ce.wcaquino.teste_unitario.entidades.Filme;
import br.ce.wcaquino.teste_unitario.entidades.Locacao;
import br.ce.wcaquino.teste_unitario.entidades.Usuario;
import br.ce.wcaquino.teste_unitario.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.teste_unitario.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	@InjectMocks
	private LocacaoService service;

	@Mock
	private LocacaoDAO dao;

	@Mock
	private SPCService spc;

	@Parameter
	public List<Filme> filmes;

	@Parameter(1)
	public Double valorLocacao;

	@Parameter(2)
	public String cenario;

	private static Filme filme1 = FilmeBuilder.umFilme().agora();
	private static Filme filme2 = FilmeBuilder.umFilme().agora();
	private static Filme filme3 = FilmeBuilder.umFilme().agora();
	private static Filme filme4 = FilmeBuilder.umFilme().agora();
	private static Filme filme5 = FilmeBuilder.umFilme().agora();
	private static Filme filme6 = FilmeBuilder.umFilme().agora();
	private static Filme filme7 = FilmeBuilder.umFilme().agora();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Parameters(name = "{2}")
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object[][] {
			{ Arrays.asList(filme1, filme2), 8.0d, "2 Filmes: Sem Desconto" },
			{ Arrays.asList(filme1, filme2, filme3), 11.0d, "3 Filmes: 25%" },
			{ Arrays.asList(filme1, filme2, filme3, filme4), 13.0d, "4 Filmes: 50%" },
			{ Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0d, "5 Filmes: 75%" },
			{ Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0d, "6 Filmes: 100%" },
			{ Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0d, "7 Filmes: Sem Desconto" }
		});
	}

	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();

		// ação
		Locacao resultado = service.alugarFilmes(usuario, filmes);

		// verificação
		Assert.assertThat(resultado.getValor(), is(valorLocacao));
	}
}
