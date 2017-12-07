package ufc.quixada.npi.contest.model;

public enum EstadoEvento {
	ATIVO("Ativo"), INATIVO("Inativo"), FINALIZADO("Finalizado");

	private String estado;
	// construtor
	EstadoEvento(String estado) {
		this.setEstado(estado);
	}
	// retorna o restado do evento
	public String getEstado() {
		return estado;
	}
	// define o estado de um evento
	public void setEstado(String estado) {
		this.estado = estado;
	}
}