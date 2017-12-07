package ufc.quixada.npi.contest.model;

public enum VisibilidadeEvento {
	PUBLICO("Publico"), PRIVADO("Privado");

	private String visibilidade;

	VisibilidadeEvento(String visibilidade) {
		this.setVisibilidade(visibilidade);
	}

	//Método que retorna a visibilidade do evento
	public String getVisibilidade() {
		return visibilidade;
	}

	//Método que altera a visibilidade do evento
	public void setVisibilidade(String visibilidade) {
		this.visibilidade = visibilidade;
	}
}