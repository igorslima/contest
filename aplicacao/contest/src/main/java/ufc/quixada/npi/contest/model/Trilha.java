package ufc.quixada.npi.contest.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "trilha")
public class Trilha {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotEmpty
	@Column(name = "nome")
	private String nome;
	
	@OneToMany(mappedBy="trilha", cascade=CascadeType.REMOVE)
	private List<Trabalho> trabalhos;

	@ManyToOne(cascade=CascadeType.PERSIST)
	private Evento evento;
	
	// Método que lista os trabalhos da trilha
	public List<Trabalho> getTrabalhos() {
		return trabalhos;
	}

	// Método que altera os trabalhos da trilha
	public void setTrabalhos(List<Trabalho> trabalhos) {
		this.trabalhos = trabalhos;
	}

	//Método que retorna o identificador da trilha
	public Long getId() {
		return id;
	}

	//Método que altera o identificador da trilha
	public void setId(Long id) {
		this.id = id;
	}

	//Método que retorna o nome da trilha
	public String getNome() {
		return nome;
	}

	//Método que altera o nome da trilha
	public void setNome(String nome) {
		this.nome = nome;
	}

	//Método que retorna o evento
	public Evento getEvento() {
		return evento;
	}

	//Método que altera o evento
	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	//Método que compara o objeto atual com outro
	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}
	
	//Método que transforma em string
	@Override
	public String toString() {
		return "Trilha [id=" + id + ", nome=" + nome + ", evento=" + evento + "]";
	}
	
	//Método que retorna a quantidade de trabalhos 
	public int getNumeroTrabalhos(){
		return this.trabalhos.size();
	}
}