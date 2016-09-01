package ufc.quixada.npi.contest.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.contest.model.EstadoEvento;
import ufc.quixada.npi.contest.model.Evento;

@Repository
@Transactional
public interface EventoRepository extends CrudRepository<Evento, Long>{
	public List<Evento> findByEstadoEquals(EstadoEvento estado);
	@Query("select e from Evento e where e.estado = ufc.quixada.npi.contest.model.EstadoEvento.ATIVO and "
			+ "e.visibilidade = ufc.quixada.npi.contest.model.VisibilidadeEvento.PUBLICO")
	public List<Evento> findEventosAtivosEPublicos();
	
	@Query("SELECT  * FROM evento e" + 
	"WHERE e.id NOT in ( SELECT DISTINCT pe.evento_id FROM participacao_evento pe WHERE 2 = pe.pessoa_id) "
	+ "AND  e.visibilidade = ufc.quixada.npi.contest.model.VisibilidadeEvento.PUBLICO"+
	"ORDER BY e.id")
	public List<Evento> eventosParaParticipar();
	
	//TODO Terminar a Query de buscar todos os eventos em que o autor esta participando
//	@Query("select p.evento from ParticipacaoEvento p where p.pessoa.id = 2")
//	public List<Evento> findEventosDoAutor(@Param("idAutor") Long idAutor);
	
	
}