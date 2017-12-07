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

import ufc.quixada.npi.contest.model.Papel.Tipo;

@Entity
@Table(name = "participacao_evento")
public class ParticipacaoEvento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "papel")
	@Enumerated(EnumType.STRING)
	private Tipo papel;
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	private Pessoa pessoa;
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	private Evento evento;

	// Retorna o id
	public Long getId() {
		return id;
	}
	
	// Altera o id
	public void setId(Long id) {
		this.id = id;
	}

	// Retorna o papel da participacao
	public Tipo getPapel() {
		return papel;
	}
	
	// Altera o papel da participacao
	public void setPapel(Tipo papel) {
		this.papel = papel;
	}
	
	// Retorna a pessoa
	public Pessoa getPessoa() {
		return pessoa;
	}
	
	// Altera a pessoa
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	// Retorna o evento
	public Evento getEvento() {
		return evento;
	}
	
	// Altera o evento
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	// Construtor da participacao
	public ParticipacaoEvento(Tipo papel, Pessoa pessoa, Evento evento) {
		this.papel = papel;
		this.pessoa = pessoa;
		this.evento = evento;
	}
	
	public ParticipacaoEvento() {
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
		return (obj == this);

	}

	@Override
	public String toString() {
		return "ParticipacaoEvento [id=" + id + ", papel=" + papel + ", pessoa=" + pessoa + ", evento=" + evento + "]";
	}
}