package ufc.quixada.npi.contest.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Secao {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "local")
	private String local;
	@Column(name="data_secao")
	private String dataSecao;
	@Column(name="horario")
	private String horario;
	
	@OneToMany(mappedBy = "secao", targetEntity = Trabalho.class, fetch = FetchType.EAGER)
	private List<Trabalho> trabalhos;
	
	@ManyToOne
	@JoinColumn(name="responsavel_id")
	private Pessoa responsavel;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Evento evento;
	// retorna o id de uma sessao 
	public Long getId() {
		return id;
	}
	// define o id de uma sessao
	public void setId(Long id) {
		this.id = id;
	}
	// retorna o nome de uma sessao
	public String getNome() {
		return nome;
	}
	// define o nome de uma sessao 
	public void setNome(String nome) {
		this.nome = nome;
	}
	// retorna os trabalhos de uma secao
	public List<Trabalho> getTrabalhos() {
		return trabalhos;
	}
	// define os trabalhos de uma secao
	public void setTrabalhos(List<Trabalho> trabalhos) {
		this.trabalhos = trabalhos;
	}
	// retorna o responsavel de uma secao
	public Pessoa getResponsavel() {
		return responsavel;
	}
	// define o responsavel de uma secao
	public void setResponsavel(Pessoa responsavel) {
		this.responsavel = responsavel;
	}
	// retorna o evento da secao
	public Evento getEvento() {
		return evento;
	}
	// define o evento da secao
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	// retorna a data da Secao
	public String getDataSecao() {
		return dataSecao;
	}
	// define a data de secao
	public void setDataSecao(String dataSecao) {
		this.dataSecao = dataSecao;
	}
	// retorna o horario de uma secao
	public String getHorario() {
		return horario;
	}
	// define o horario de uma secao
	public void setHorario(String horario) {
		this.horario = horario;
	}
	// retorna o local de uma secao
	public String getLocal() {
		return local;
	}
	// define o local de uma secao
	public void setLocal(String local) {
		this.local = local;
	}
}