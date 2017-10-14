package ufc.quixada.npi.contest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import ufc.quixada.npi.contest.controller.EventoController;
import ufc.quixada.npi.contest.model.EstadoEvento;
import ufc.quixada.npi.contest.model.Evento;
import ufc.quixada.npi.contest.model.Papel.Tipo;
import ufc.quixada.npi.contest.model.ParticipacaoEvento;
import ufc.quixada.npi.contest.model.Pessoa;
import ufc.quixada.npi.contest.model.Trabalho;
import ufc.quixada.npi.contest.model.VisibilidadeEvento;
import ufc.quixada.npi.contest.service.EventoService;
import ufc.quixada.npi.contest.service.MessageService;
import ufc.quixada.npi.contest.service.ParticipacaoEventoService;
import ufc.quixada.npi.contest.service.PessoaService;
import ufc.quixada.npi.contest.service.TrabalhoService;

public class EditarEventoSteps {

	@InjectMocks
	private EventoController eventoController;

	@Mock
	private PessoaService pessoaService;

	@Mock
	private MessageService messageService;

	@Mock
	private ParticipacaoEventoService participacaoEventoService;
	
	@Mock
	private EventoService eventoService;
	
	@Mock
	private TrabalhoService trabalhoService;
	
	private MockMvc mockMvc;
	private ResultActions action;
	private Evento evento;
	private ParticipacaoEvento participacao;
	private Pessoa org;
	
	private static final String ID_EVENTO = "1";
	private static final String EMAIL = "teste@npi.com";
	private static final String ID_PARTICIPACAO_EVENTO = "1";
	private static final String TEMPLATE_EDITAR_EVENTO = "/evento/editar/{id}";
	private static final String PAGINA_CADASTRAR = "evento/admin_cadastrar";
	private static final String TEMPLATE_LISTAR_EVENTOS_INATIVOS = "/evento/inativos";
	private static final String TEMPLATE_ADICIONAR_EVENTO = "/evento/adicionarEvento";
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(eventoController).build();
		evento = new Evento();
		participacao = new ParticipacaoEvento();
		org = new Pessoa();
		evento.setEstado(EstadoEvento.INATIVO);
		evento.setId(Long.valueOf(ID_EVENTO));
		evento.setNome("teste");
		evento.setDescricao("descricao");
		evento.setVisibilidade(VisibilidadeEvento.PRIVADO);
		
		List<ParticipacaoEvento> participacoes = new ArrayList<>();
		
		org.setCpf("123");
		org.setEmail("a@a");
		org.setNome("Joao");

		participacao.setEvento(evento);
		participacao.setId(Long.valueOf(ID_PARTICIPACAO_EVENTO));
		participacao.setPapel(Tipo.ORGANIZADOR);
		participacao.setPessoa(org);
		participacoes.add(participacao);
		
		evento.setParticipacoes(participacoes);
	}
	
	@Dado("^que o administrador deseja alterar um evento$")
	public void desejaAlterarEvento() throws Throwable{
		when(eventoService.buscarEventoPorId(Long.valueOf(ID_EVENTO))).thenReturn(evento);
		when(trabalhoService.getTrabalhosEvento(evento)).thenReturn(new ArrayList<Trabalho>());
		mockMvc.perform(get(TEMPLATE_EDITAR_EVENTO,Long.valueOf(ID_EVENTO))).andExpect(view().name(PAGINA_CADASTRAR));
	}
	
	@Quando("^altero o nome de um evento para (.*) e descrição para (.*)$")
	public void alteroNomeEDescricao(String nome, String descricao) throws Throwable{
		evento.setNome(nome);
		evento.setDescricao(descricao);
		
		action = mockMvc
				.perform(post(TEMPLATE_ADICIONAR_EVENTO)
				.param("email", EMAIL)
				.param("nome", evento.getNome())
				.param("descricao", evento.getDescricao()));
	}
	
	@Entao("^as configurações do evento são alteradas$")
	public void eventoAlteradoComSucesso() throws Throwable{
//		verify(pessoaService).get(Long.valueOf(ID_PESSOA));
//		ParticipacaoEvento participacao = new ParticipacaoEvento();
//		participacao.setEvento(evento);
//		participacao.setPessoa(org);
//		participacao.setPapel(Tipo.ORGANIZADOR);
//		
//		verify(participacaoEventoService).adicionarOuEditarParticipacaoEvento(participacao);
		
		
		action.andExpect(redirectedUrl(TEMPLATE_LISTAR_EVENTOS_INATIVOS));

	}
	
	@Quando("^o evento escolhido é um evento ativo com nome (.*) decrição (.*) organizador (.*)$")
	public void escolheEventoId(String nome, String descricao, String organizador ) throws Throwable{
		evento.setNome(nome);
		evento.setDescricao(descricao);
		evento.setId(Long.valueOf(ID_EVENTO));
		evento.setEstado(EstadoEvento.ATIVO);
		
		
		org.setNome(organizador);
		org.setCpf("789287454457");
		org.setEmail("a@a.com");
		org.setPapelLdap("DOCENTE");
		
		when(eventoService.existeEvento(Long.valueOf(ID_EVENTO))).thenReturn(true);
		when(eventoService.buscarEventoPorId(Long.valueOf(ID_EVENTO))).thenReturn(evento);
		action = mockMvc
				.perform(get(TEMPLATE_EDITAR_EVENTO,ID_EVENTO));
	}
	
	@Entao("^o evento não deve ser alterado$")
	public void redirecionaFormulario() throws Throwable{
		action.andExpect(view().name(PAGINA_CADASTRAR));
	}
	
	@E("^o usuário é avisado via mensagem o motivo do insucesso do cadastro$")
	public void redirecionado() throws Throwable{
		action.andExpect(view().name(PAGINA_CADASTRAR));
	}
	
	@Quando("^edito o nome do evento para (.*) e descricao para (.*) e não informo o nome do organizador$")
	public void editarEventoNaoInformandoOrganizador(String nomeEvento, String descricao) throws Exception{
		evento.setNome(nomeEvento);
		evento.setDescricao(descricao);
				
		when(messageService.getMessage("ORGANIZADOR_VAZIO_ERROR"))
			.thenReturn("O organizador do evento deve ser informado");
		
		action = mockMvc
				.perform(post("/evento/adicionarEvento")
				.param("nome", nomeEvento)
				.param("email", ""));
	}
	
	@Entao("^o usuário é avisado via mensagem que o organizador do evento deve ser informado$")
	public void informaQueOCampoOrganizadorEObrigatorio() throws Exception{
		action.andExpect(view().name(PAGINA_CADASTRAR)).andExpect(model().attributeHasErrors("evento"));
	}

}
