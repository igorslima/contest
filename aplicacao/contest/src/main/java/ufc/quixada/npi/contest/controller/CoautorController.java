package ufc.quixada.npi.contest.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.contest.model.Evento;
import ufc.quixada.npi.contest.model.Pessoa;
import ufc.quixada.npi.contest.model.Trabalho;
import ufc.quixada.npi.contest.service.EventoService;
import ufc.quixada.npi.contest.service.ParticipacaoEventoService;
import ufc.quixada.npi.contest.service.ParticipacaoTrabalhoService;
import ufc.quixada.npi.contest.service.TrabalhoService;
import ufc.quixada.npi.contest.util.Constants;
import ufc.quixada.npi.contest.util.PessoaLogadaUtil;

@RequestMapping("/coautor")
@Controller
public class CoautorController {

	@Autowired
	private TrabalhoService trabalhoService;
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private ParticipacaoEventoService participacaoEventoService;
	
	@Autowired		
	private ParticipacaoTrabalhoService participacaoTrabalhoService;
	
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("listaTrabalhos", trabalhoService.getTrabalhosDoCoautor(PessoaLogadaUtil.pessoaLogada()));
		return Constants.TEMPLATE_INDEX_COAUTOR;
	}
	
	@RequestMapping(value = "/listarTrabalhos/{id}", method = RequestMethod.GET)
	public String listarTrabalhos(@PathVariable String id, Model model, RedirectAttributes redirect) {

		Long idEvento = Long.parseLong(id);
			if (eventoService.existeEvento(idEvento)) {
				Evento evento = eventoService.buscarEventoPorId(Long.parseLong(id));
				Pessoa pessoa = PessoaLogadaUtil.pessoaLogada();		
								
				List<Trabalho> listaTrabalho = trabalhoService.getTrabalhosDoCoautorNoEvento(pessoa, evento);
				model.addAttribute("evento", evento);
				model.addAttribute("listaTrabalhos", listaTrabalho);
				
				return Constants.TEMPLATE_INDEX_COAUTOR;
			}
			return "redirect:/autor/meusTrabalhos";
	}
	
	@PreAuthorize("isCoautorInTrabalho(#idTrabalho)")
	@RequestMapping(value = "/file/{trabalho}", method = RequestMethod.GET, produces = "application/pdf")
	public void downloadPDFFile(@PathVariable("trabalho") Long idTrabalho, HttpServletResponse response)
			throws IOException {
		Trabalho trabalho = trabalhoService.getTrabalhoById(idTrabalho);
		if (trabalho == null) {
			response.reset();
			response.sendRedirect("/error/500");
			response.getOutputStream().flush();
		} else {
			Pessoa autor = PessoaLogadaUtil.pessoaLogada();
			Long idEvento = trabalho.getEvento().getId();
			if (participacaoEventoService.isOrganizadorDoEvento(autor, idEvento)
					|| participacaoTrabalhoService.isParticipandoDoTrabalho(idTrabalho, autor.getId())) {
				try {
					String path = trabalho.getPath();
					Path file = Paths.get(path);
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition", "attachment; filename=" + path);
					Files.copy(file, response.getOutputStream());
					response.getOutputStream().flush();
				} catch (IOException e) {
					e.printStackTrace();
					response.reset();
					response.sendRedirect("/error/404");
					response.addHeader("Status", "404 Not Found");
					response.getOutputStream().flush();
				}
			} else {
				response.reset();
				response.sendRedirect("/error/500");
				response.getOutputStream().flush();
			}
		}
	}
}
