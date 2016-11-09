	package ufc.quixada.npi.contest.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.contest.model.Papel;
import ufc.quixada.npi.contest.model.PapelLdap;
import ufc.quixada.npi.contest.model.Pessoa;

@Repository
@Transactional
public interface PessoaRepository extends CrudRepository<Pessoa, Long>{
	public Pessoa findByCpf(String cpf);
	public Pessoa findByEmail(String email);
	public Pessoa findByCpfAndPassword(String cpf, String password);
	public List<Pessoa> findPessoaByParticipacoesEventoEventoIdAndParticipacoesEventoPapel(Long id, Papel papel);
	
	@Query("select p from Pessoa p where p.papelLdap = ufc.quixada.npi.contest.model.PapelLdap$Tipo.DOCENTE or "
			+ "p.papelLdap = ufc.quixada.npi.contest.model.PapelLdap$Tipo.STA ")
	public List<Pessoa> getPossiveisOrganizadores();
	
	@Query("SELECT pe FROM Pessoa pe, ParticipacaoEvento pa WHERE pe.papelLdap = :papel AND pa.evento.id = :idEvento")
	public List<Pessoa> pessoasPorPapelNoEvento(@Param("papel")PapelLdap.Tipo papel, @Param("idEvento")Long idEvento);
	
	@Query("select p from Pessoa p where p.id not in "
			+ "(select DISTINCT pe.pessoa.id from ParticipacaoEvento pe where pe.evento.id = :idEvento AND "
			+ " pe.papel = ufc.quixada.npi.contest.model.Papel.ORGANIZADOR) AND "
			+ "(p.papelLdap <> ufc.quixada.npi.contest.model.PapelLdap$Tipo.DISCENTE)")	
	public List<Pessoa> getPossiveisOrganizadoresDoEvento(@Param("idEvento")Long idEvento);
	
	public List<Pessoa> findPessoaByParticipacoesEventoPapelAndParticipacoesEventoEventoIdOrderByParticipacoesEventoPessoaNome(Papel papel, Long id);
}