package ufc.quixada.npi.contest.model;

public enum TipoSubmissao {
	PARCIAL("Parcial"), FINAL("Final");

	private String tipoSubmissao;

	TipoSubmissao(String tipoSubmissao) {
		this.setTipoSubmissao(tipoSubmissao);
	}

	// retorna o tipo de submissao
	public String getTipoSubmissao() {
		return tipoSubmissao;
	}
	// define o tipo de submissao
	public void setTipoSubmissao(String tipoSubmissao) {
		this.tipoSubmissao = tipoSubmissao;
	}
}