package ufc.quixada.npi.contest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.contest.model.EstadoEvento;
import ufc.quixada.npi.contest.model.Evento;
import ufc.quixada.npi.contest.repository.EventoRepository;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	public void adicionarOuAtualizarEvento(Evento evento){
		eventoRepository.save(evento);
	}
	
	public void removerEvento(Long id){
		if(eventoRepository.findOne(id) != null)
			eventoRepository.delete(id);
	}
	
	public List<Evento> buscarEventos(){
		return (List<Evento>) eventoRepository.findAll();
	}
	
	public Evento buscarEventoPorId(Long id){
		return eventoRepository.findOne(id);
	}
	
	public List<Evento> buscarEventoPorEstado(EstadoEvento estado){
		return eventoRepository.findByEstadoEquals(estado);
	}
	
}