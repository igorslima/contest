# language: pt
	Funcionalidade: Organizador cadastra trilhas de submissão
		
		Eu como organizador gostaria de cadastrar trilhas de submissão.
		O organizador deve informar obrigatoriamente o nome da trilha.
	
		
	      	
		
			Cenário: O organizador cadastra uma trilha de submissão
			    Dado que existe um evento EventoNome e DescricaoEvento
			    Quando o organizador cadastra uma trilha de submissão NomeTrilha
			    Então a trilha de submissão é cadastrada
