package ufc.quixada.npi.contest.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import ufc.quixada.npi.contest.model.Papel.Tipo;

@Entity
@Table(name = "trabalho")
public class Trabalho implements Comparable<Trabalho> {
	// GOD CLASS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotEmpty
	@Column(name = "titulo")
	private String titulo;

	@ManyToOne
	private Evento evento;

	@ManyToOne
	private Trilha trilha;
	
	private String status;
	
	@OneToMany(mappedBy="trabalho", cascade=CascadeType.REMOVE)
	@OrderBy("data_submissao")
	private List<Submissao> submissoes;

	@OneToMany(mappedBy="trabalho", cascade=CascadeType.REMOVE)
	private List<Revisao> revisoes;

	@OneToMany(mappedBy = "trabalho", cascade=CascadeType.ALL)
	@OrderBy("papel")
	private List<ParticipacaoTrabalho> participacoes;

	@Column(name="path")
	private String path;
	
	@Transient
	private String coautoresInString;
	
	@ManyToOne
	private Secao secao;
	
	// Retorna o id do Trabalho
	public Long getId() {
		return id;
	}

	// Altera o tipo do Trabalho
	public void setId(Long id) {
		this.id = id;
	}

	// Retorna a lista de submissões do Trabalho
	public List<Submissao> getSubmissoes() {
		return submissoes;
	}
	
	// Altera a lista de submissões do Trabalho
	public void setSubmissoes(List<Submissao> submissoes) {
		this.submissoes = submissoes;
	}
	
	// Retorna a lista de revisões
	public List<Revisao> getRevisoes() {
		return revisoes;
	}
	
	// Altera a lista de revisões
	public void setRevisoes(List<Revisao> revisoes) {
		this.revisoes = revisoes;
	}
	
	// Retorna o path
	public String getPath() {
		return path;
	}

	// Altera o path
	public void setPath(String path) {
		this.path = path;
	}

	// Retorna o titulo
	public String getTitulo() {
		return titulo;
	}

	// Altera o titulo
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	// Retorna o evento
	public Evento getEvento() {
		return evento;
	}

	// Altera o evento
	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	// Altera a trilha
	public void setTrilha(Trilha trilha) {
		this.trilha = trilha;
	}

	// Retorna a lista de participacoes
	public List<ParticipacaoTrabalho> getParticipacoes() {
		return participacoes;
	}

	// Altera a lista de participacoes
	public void setParticipacoes(List<ParticipacaoTrabalho> participacoes) {
		this.participacoes = participacoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}
	
	// Altera os autores e coautores
	public void setAutores(Pessoa autor, List<Pessoa> coautores){
		ParticipacaoTrabalho participacaoAutor = participacaoAutor(autor);
		participacoes = new ArrayList<ParticipacaoTrabalho>();
		participacoes(coautores, participacaoAutor);
	}

	private ParticipacaoTrabalho participacaoAutor(Pessoa autor) {
		ParticipacaoTrabalho participacaoAutor = new ParticipacaoTrabalho();
		participacaoAutor.setPapel(Tipo.AUTOR);
		participacaoAutor.setTrabalho(this);
		participacaoAutor.setPessoa(autor);
		return participacaoAutor;
	}

	private void participacoes(List<Pessoa> coautores, ParticipacaoTrabalho participacaoAutor) {
		for (Pessoa pessoa : coautores) {
			ParticipacaoTrabalho participacaoCoautor = participacaoCoautor(pessoa);
			participacoes.add(participacaoCoautor);
		}
		participacoes.add(participacaoAutor);
	}

	private ParticipacaoTrabalho participacaoCoautor(Pessoa pessoa) {
		ParticipacaoTrabalho participacaoCoautor = new ParticipacaoTrabalho();
		participacaoCoautor.setPapel(Tipo.COAUTOR);
		participacaoCoautor.setTrabalho(this);
		participacaoCoautor.setPessoa(pessoa);
		return participacaoCoautor;
	}
	
	// Altera os coautores
	public void setCoautores(List<Pessoa> coautores) {
		for (Pessoa pessoa : coautores) {
			_participacoes(pessoa);
		}
	}

	private void _participacoes(Pessoa pessoa) {
		ParticipacaoTrabalho participacaoCoautor = participacao_Coautor(pessoa);
		participacoes.add(participacaoCoautor);
	}

	private ParticipacaoTrabalho participacao_Coautor(Pessoa pessoa) {
		ParticipacaoTrabalho participacaoCoautor = new ParticipacaoTrabalho();
		participacaoCoautor.setPapel(Tipo.COAUTOR);
		participacaoCoautor.setTrabalho(this);
		participacaoCoautor.setPessoa(pessoa);
		return participacaoCoautor;
	}
	
	public List<Pessoa> getParticipacaoPapelTrabalho(Tipo... papeis) {
		List<Pessoa> pessoa = new ArrayList<Pessoa>();
		for (ParticipacaoTrabalho p : getParticipacoes()) {
			for(Tipo papel : papeis){
				if (p.getPapel() == papel){
					pessoa.add(p.getPessoa());
				}
			}
		}
		return pessoa;
	}
	
	public String getCoautoresInString(){
		List<Pessoa> lista = this.getCoAutoresDoTrabalho();
		if(lista!=null){
			StringBuilder nomes = new StringBuilder();
			for (Pessoa p : lista) {
				nomes.append(p.getNome().toUpperCase());
				if(lista.indexOf(p)!=(lista.size()-1)){
					nomes.append(", ");
				}
			}
			return nomes.toString();
		}
		return "";
	}

	public Pessoa getAutor() {
		return getParticipacaoPapelTrabalho(Tipo.AUTOR).get(0);
	}
	
	public List<Pessoa> getAutoresDoTrabalho() {
		return getParticipacaoPapelTrabalho(Tipo.AUTOR, Tipo.COAUTOR);
	}
	
	public List<Pessoa> getCoAutoresDoTrabalho() {
		return getParticipacaoPapelTrabalho(Tipo.COAUTOR);
	}
	
	public List<Pessoa> getRevisores(){
		return getParticipacaoPapelTrabalho(Tipo.REVISOR);
	}
	
	public boolean isRevisado(){
		return (revisoes != null && revisoes.size() > 0);
	}
	
	public boolean isIndicadoMelhoresTrabalhos(){
		if(this.isRevisado()){
			for(Revisao revisao : revisoes){
				if(revisao.getConteudo().contains("indicacao"))
					return true;
			}
		}		
		return false;
	}
	
	public String getStatus(){
		return status;
	}
	
	@Override
	public String toString() {
		return "Trabalho [id=" + id + ", titulo=" + titulo + ", evento=" + evento + ", trilha=" + trilha
				+ ", participacoes=" + participacoes + "]";
	}

	@Override
	public int compareTo(Trabalho o) {
		return this.titulo.toUpperCase().compareTo(o.getTitulo().toUpperCase());
	}

	public Secao getSecao() {
		return secao;
	}

	public void setSecao(Secao secao) {
		this.secao = secao;
	}

	public void setStatus(String resultado) {
		this.status = resultado;
	}

	public void setCoautoresInString(String coautoresInString) {
		this.coautoresInString = coautoresInString;
	}

	public Trilha getTrilha() {
		return trilha;
	}
	
	public boolean isAutorInTrabalho(Pessoa pessoa){
		if(this.getAutor().equals(pessoa) || this.getCoAutoresDoTrabalho().contains(pessoa)){
			return false;
		}
		return true;
	}	
}