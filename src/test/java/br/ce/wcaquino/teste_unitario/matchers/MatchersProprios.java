package br.ce.wcaquino.teste_unitario.matchers;

import java.util.Calendar;

public class MatchersProprios {
	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}

	public static DiaSemanaMatcher caiNumaSegunda() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}

	public static DataDiferencaDiasMatcher ehHoje() {
		return new DataDiferencaDiasMatcher(0);
	}

	public static DataDiferencaDiasMatcher ehHojeComDiferencaDias(Integer diaFuturo) {
		return new DataDiferencaDiasMatcher(diaFuturo);
	}
}
