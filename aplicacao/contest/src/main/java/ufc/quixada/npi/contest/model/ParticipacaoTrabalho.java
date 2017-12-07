package ufc.quixada.npi.contest.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import ufc.quixada.npi.contest.model.Papel.Tipo;

@Entity
@Table(name = "participacao_trabalho")
public class ParticipacaoTrabalho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "papel")
	@Enumerated(EnumType.STRING)
	@NotNull
	private Tipo papel;

	@ManyToOne
	private Pessoa pessoa;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private Trabalho trabalho;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tipo getPapel() {
		return papel;
	}

	public void setPapel(Tipo papel) {
		this.papel = papel;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Trabalho getTrabalho() {
		return trabalho;
	}

	public void setTrabalho(Trabalho trabalho) {
		this.trabalho = trabalho;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	// Método compara se o objeto atual é igual à outro objeto 
	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}

	@Override
	public String toString() {
		return "ParticipacaoTrabalho [id=" + id + ", papel=" + papel + ", pessoa=" + pessoa + ", trabalho=" + trabalho
				+ "]";
	}
}