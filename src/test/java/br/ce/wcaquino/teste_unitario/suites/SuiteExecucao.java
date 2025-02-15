package br.ce.wcaquino.teste_unitario.suites;

import org.junit.runners.Suite.SuiteClasses;

import br.ce.wcaquino.teste_unitario.servicos.CalculoValorLocacaoTest;
import br.ce.wcaquino.teste_unitario.servicos.LocacaoServiceTest;

//@RunWith(Suite.class)
@SuiteClasses({
//	CalculadoraTest.class,
	CalculoValorLocacaoTest.class,
	LocacaoServiceTest.class })
public class SuiteExecucao {

}
