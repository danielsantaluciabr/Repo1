# Os modulos foram divididos em tres principais

	1 - domain API, exposicao das interfaces do sistema de parser e report
	2 - core, as implementacoes dos parsers e reports, 
	3 - web, exposicao de uma api de servicos 
	
Desta forma foi possivel esconder as implementacoes no modulo core e expor os parsers e reports somente atraves de suas interfaces.
E para tal foi criado uma factoy que constroi a implementacao correta baseado em um tipo de parser.

Alguns metodos das implementacoes no modulo core foram pensados para serem testados, por isso em alguns casos o a exposicao deles
é (default). Ainda há espaço para se desenvolver diferentes cenários de teste, considero que os cenarios entregues sao o caminho feliz.

Enxerguei uma segunda linha de testes automatizados no servico rest do modulo web, testes de integracao chamando o servico poderiam ser
escritos utilizano junit (testes já com o servidor de aplicacao de pé). 

# A linguagem escolhida para a resolucao do problema se deu por dois fatores principais:
	
	1- atende completamente os requisitos dos problemas apresentados
	2- linguagem de maior afinidade 

# Setup do parser:
O modulo web possui a dependencia dos modulos core e domain. O projeto foi desenvolvido com IDE Eclipse Kepler e foi comitado no 
controle de versao com suas suas configuracoe de projeto. Basta fazer o checkout dos projetos e importar no eclipse.
Para fazer o deployment pode-se usar a propria IDE ou gerar o .war 

O container usado foi Tomcat6