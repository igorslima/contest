# language: pt
Funcionalidade: Cadastrar Evento
	
	Eu como administrador posso criar um evento e atribuir um organizador.
	O formulário de cadastro deve conter os seguintes campos: Nome do Evento, Descrição e Organizador do Evento
	sendo nome do evento e organizador campos obrigatórios.
	O organizador deve ser um servidor cadastrado no sistema
	O projeto recém cadastrado deve permanecer com status inativo e visibilidade privado (apenas o admin e o organizador pode visualizar)
	
      Contexto:
        Dado o administrador deseja cadastrar um evento.
	
	  Cenário: Cadastro de evento com sucesso
		Quando informar o organizador lucas@gmail.com e o evento com nome Teste e descricao Legal
		Então o organizador deve ser convidado por email e o evento deve ser cadastrado com visibilidade privada e estado inativo.
		
	  Cenário: Cadastrar evento sem informar o organizador do evento
		Quando informar somente o nome do evento Teste
		Então O evento não deve ser cadastrado
		
	  Cenário: Cadastro de evento sem nome
		Quando informar somente o organizador Matheus
		Então O evento não deve ser cadastrado devido ao nome vazio