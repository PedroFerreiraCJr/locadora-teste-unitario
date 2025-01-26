package br.ce.wcaquino.teste_unitario.matchers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
		Date dataEsperada = DataUtils.obterDataComDiferencaDias(this.qtdeDias);
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		description.appendText(format.format(dataEsperada));
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(qtdeDias));
	}
}
