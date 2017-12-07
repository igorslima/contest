package ufc.quixada.npi.contest.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import ufc.quixada.npi.contest.model.Papel.Tipo;

@Entity
@Table(name = "evento")
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotEmpty(message = "{NOME_EVENTO_VAZIO_ERROR}")
	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(name = "visibilidade")
	private VisibilidadeEvento visibilidade;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private EstadoEvento estado;

	@Temporal(TemporalType.DATE)
	@Column(name = "prazo_submissao_inicial")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date prazoSubmissaoInicial;

	@Temporal(TemporalType.DATE)
	@Column(name = "prazo_submissao_final")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date prazoSubmissaoFinal;

	@Temporal(TemporalType.DATE)
	@Column(name = "prazo_revisao_inicial")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date prazoRevisaoInicial;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "prazo_revisao_final")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date prazoRevisaoFinal;

	@OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval=false)
	private List<ParticipacaoEvento> participacoes;
	
	@OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval=false)
	@OrderBy("nome ASC")
	private List<Trilha> trilhas;
	
	@OneToMany(mappedBy="evento",cascade=CascadeType.ALL, orphanRemoval=false)
	private List<Secao> secoes;
	// retorna o id de um evento
	public Long getId() {
		return id;
	}
	// seta o id de um evento
	public void setId(Long id) {
		this.id = id;
	}
	// retorna o nome do evento
	public String getNome() {
		return nome;
	}
	// define o nome de um evento
	public void setNome(String nome) {
		this.nome = nome;
	}
	// retorna a descrição de um evento
	public String getDescricao() {
		return descricao;
	}
	// define a descrição de um evento
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	// retorna a visibilidade de um evento
	public VisibilidadeEvento getVisibilidade() {
		return visibilidade;
	}
	// define a visibilidade de um evento
	public void setVisibilidade(VisibilidadeEvento visibilidade) {
		this.visibilidade = visibilidade;
	}
	// retorna o estado de um evento
	public EstadoEvento getEstado() {
		return estado;
	}
	// define o estado de um evento
	public void setEstado(EstadoEvento estado) {
		this.estado = estado;
	}
	// retorna o prazo de submissão inicial
	public Date getPrazoSubmissaoInicial() {
		return prazoSubmissaoInicial;
	}
	// define o prazo de submissão inicial
	public void setPrazoSubmissaoInicial(Date prazoSubmissaoInicial) {
		this.prazoSubmissaoInicial = prazoSubmissaoInicial;
	}
	// retorna o prazo de submissao final
	public Date getPrazoSubmissaoFinal() {
		return prazoSubmissaoFinal;
	}
	// define o deadline de submissao
	public void setPrazoSubmissaoFinal(Date prazoSubmissaoFinal) {
		this.prazoSubmissaoFinal = prazoSubmissaoFinal;
	}
	// retorna o prazo de revisao inicial
	public Date getPrazoRevisaoInicial() {
		return prazoRevisaoInicial;
	}
	// define o prazo de revisao inicial
	public void setPrazoRevisaoInicial(Date prazoRevisaoInicial) {
		this.prazoRevisaoInicial = prazoRevisaoInicial;
	}
	// retorna o prazo de revisao final
	public Date getPrazoRevisaoFinal() {
		return prazoRevisaoFinal;
	}

	public void setPrazoRevisaoFinal(Date prazoRevisaoFinal) {
		this.prazoRevisaoFinal = prazoRevisaoFinal;
	}
	// retorna as participante de um evento
	public List<ParticipacaoEvento> getParticipacoes() {
		return participacoes;
	}
	// define as participantes em evento
	public void setParticipacoes(List<ParticipacaoEvento> participacoes) {
		this.participacoes = participacoes;
	}
	// retorna o hashCode do evento
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
		if (obj == null || getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// retorna o objeto no fomat String
	@Override
	public String toString() {
		return "Evento [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", visibilidade=" + visibilidade
				+ ", estado=" + estado + ", prazoSubmissaoInicial=" + prazoSubmissaoInicial + ", prazoSubmissaoFinal="
				+ prazoSubmissaoFinal + ", prazoRevisaoInicial=" + prazoRevisaoInicial + ", prazoRevisaoFinal="
				+ prazoRevisaoFinal + ", participacoes=" + participacoes + "]";
	}
	// retorna as trilhas
	public List<Trilha> getTrilhas() {
		return trilhas;
	}
	// define as trilhas
	public void setTrilhas(List<Trilha> trilhas) {
		if(this.trilhas == null){
			this.trilhas = trilhas;
		}
		trilhas(trilhas);
	}
	private void trilhas(List<Trilha> trilhas) {
		this.trilhas.clear();
		this.trilhas.addAll(trilhas);
	}
	
	//Método que diz se está no periodo inicial de submissão
	public boolean isPeriodoInicial(){
		Date dataAtual = new Date();
		Calendar cal = Calendar.getInstance();		
        cal.setTime(prazoRevisaoInicial);
        cal.add(Calendar.SECOND, -1);      
		Date diaAntesDoInicioDaRevisao = cal.getTime();
		
		cal.setTime(prazoSubmissaoInicial);
		Date diaInicioSubmissao = cal.getTime();
		
		return (dataAtual.compareTo(diaInicioSubmissao) >= 0) && (dataAtual.compareTo(diaAntesDoInicioDaRevisao) <= 0);
	}
	
	// Método que diz se está no perídodo de Revisão
	public boolean isPeriodoRevisao(){
		Date dataAtual = new Date();
		Calendar cal = Calendar.getInstance();
        cal.setTime(prazoRevisaoFinal);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.SECOND, -1);
		Date dataFinalRevisao = cal.getTime();		
		boolean comecaNoDiaOuAposInicioRevisao = (dataAtual.compareTo(prazoRevisaoInicial)>= 0);
		boolean terminaNoDiaOuAntesFinalRevisao = (dataAtual.compareTo(dataFinalRevisao)<= 0);
		return (comecaNoDiaOuAposInicioRevisao && terminaNoDiaOuAntesFinalRevisao);
	}
	
	// Método que diz se está no perídodo final de submissão
	public boolean isPeriodoFinal(){
		Date dataAtual = new Date();
		Calendar cal = Calendar.getInstance();
        cal.setTime(prazoRevisaoFinal);
        cal.add(Calendar.DAY_OF_MONTH, 1);
		Date diaAposRevisaoFinal = cal.getTime();
		boolean comecaAposRevisaoFinal = (dataAtual.compareTo(diaAposRevisaoFinal)>= 0);
		
		cal.setTime(prazoSubmissaoFinal);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date diaAposSubmissaoFinal = cal.getTime();
		boolean terminaNoDiaOuAntesSubissaoFinal = (dataAtual.compareTo(diaAposSubmissaoFinal)<= 0);
		return (comecaAposRevisaoFinal && terminaNoDiaOuAntesSubissaoFinal);
	}
	// retora as pessoas por papeis
	private List<Pessoa> getByPapel(Tipo ...papeis){
		List<Pessoa> pessoa = new ArrayList<Pessoa>();
		for (ParticipacaoEvento p : getParticipacoes()) {
			for(Tipo papel : papeis){
				if (p.getPapel() == papel){
					pessoa.add(p.getPessoa());
				}
			}
		}
		return pessoa;
	}
	// retorna as pessoas da organizacao
	public List<Pessoa> getOrganizadores(){
		return getByPapel(Tipo.ORGANIZADOR);
	}
	// retorna os revisores do evento
	public List<Pessoa> getRevisores(){
		return getByPapel(Tipo.REVISOR);
	}
	// retorna os autores do evento
	public List<Pessoa> getAutores(){
		return getByPapel(Tipo.AUTOR);
	}
	// retorna as secoes de um evento
	public List<Secao> getSecoes() {
		return secoes;
	}
	// define as sessões de um evento
	public void setSecoes(List<Secao> secoes) {
		if(this.secoes == null){
			this.secoes = secoes;
		}
		secoes(secoes);
	}
	private void secoes(List<Secao> secoes) {
		this.secoes.clear();
		this.secoes.addAll(secoes);
	}
}