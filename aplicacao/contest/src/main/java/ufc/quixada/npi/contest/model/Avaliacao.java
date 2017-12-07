package ufc.quixada.npi.contest.model;

public enum Avaliacao {
	APROVADO("Aprovado"), RESSALVAS("Aprovado com ressalvas"), REPROVADO("Reprovado"), MODERACAO("Moderacao");

	private String tipo;
	// construtor
	Avaliacao(String tipo) {
		this.tipo = tipo;
	}
	// retornar o tipo
	public String getTipo() {
		return this.tipo;
	}
	// define o tipo da a valiação
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
