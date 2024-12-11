package br.ce.wcaquino.teste_unitario.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.teste_unitario.utils.DataUtils;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date> {

	private Integer qtdeDias;

	public DataDiferencaDiasMatcher(Integer dias) {
		this.qtdeDias = dias;
	}

	@Override
	public void describeTo(Description description) {
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(qtdeDias));
	}
}
