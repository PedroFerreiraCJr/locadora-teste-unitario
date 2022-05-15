package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.teste_unitario.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.teste_unitario.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.ce.wcaquino.teste_unitario.entidades.Filme;
import br.ce.wcaquino.teste_unitario.entidades.Locacao;
import br.ce.wcaquino.teste_unitario.entidades.Usuario;
import br.ce.wcaquino.teste_unitario.servicos.LocacaoService;

public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();

	/**
	 * <pre>
	 * 	1º O primeiro passo para tornar este código um teste é torná-lo um método
	 * público e com retorno void.
	 * 	2º O segundo passo é anotá-lo com @Test do pacote org.junit.
	 * 	3º O terceiro passo a ser realizado é fazer uso das assertions disponíveis
	 * para validar o resultado da execução do método que está sendo testado.
	 * </pre>
	 */
	@Test
	public void testeLocacao() {
		// cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// ação
		Locacao locacao = service.alugarFilme(usuario, filme);

		// verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
}
