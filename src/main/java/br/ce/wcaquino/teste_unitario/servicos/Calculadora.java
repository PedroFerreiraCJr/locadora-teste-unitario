package br.ce.wcaquino.teste_unitario.servicos;

import br.ce.wcaquino.teste_unitario.exceptions.NaoPodeDividirPorZeroException;

public class Calculadora {

	public int somar(int a, int b) {
		System.out.println("Executando método somar...");
		return a + b;
	}

	public int subtrair(int a, int b) {
		return a - b;
	}

	public int dividir(int a, int b) throws NaoPodeDividirPorZeroException {
		if (b == 0) {
			throw new NaoPodeDividirPorZeroException();
		}
		return a / b;
	}

	public int divide(String a, String b) {
		return Integer.valueOf(a) / Integer.valueOf(b);
	}

	public void imprimir() {
		System.out.println("Passou aqui");
	}
}
