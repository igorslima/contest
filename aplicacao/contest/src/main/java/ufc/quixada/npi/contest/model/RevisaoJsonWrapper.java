package ufc.quixada.npi.contest.model;

public class RevisaoJsonWrapper {
	private Long revisorId;
	private Long trabalhoId;

	// retorna o Id do Revisor
	public Long getRevisorId() {
		return revisorId;
	}
	// define o id do revisor
	public void setRevisorId(Long revidorId) {
		this.revisorId = revidorId;
	}
	// retorna o id do trabalho
	public Long getTrabalhoId() {
		return trabalhoId;
	}
	// define o id do trabalho
	public void setTrabalhoId(Long trabalhoId) {
		this.trabalhoId = trabalhoId;
	}
}
