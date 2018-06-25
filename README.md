# BOT_FIPE_2B

# Sobre
Bot para telegram escrito em java, utilizando os conceitos de MVC utilizando o modelo providenciado pelo professor. 
Ele tem como objetivo permitir utilizar um bot de pesquisas para verificar o valor de um veículo, com um histórico de pesquisas 
utilizando um banco de dados db4o.

Desenvolvido pelos alunos Gisele, Jéssica e Jonas, alunos de Análise e Desenvolvimento de Sistemas na FATEC São José dos Campos.

# Funcionamento

>Inicialmente, o usuário tem a opção de escolher uma das três opções ao clicar nos botões:
>Pesquisar o código da marca, pesquisar o valor de um veículo e verificar o histórico de pesquisas realizadas.

![Menu inicial](Images/Tela5b.png)

>Nesta tela há um exemplo de como fazer uma pesquisa de uma marca específica.
Ao digitar a marca, é retornado o código na FIPE da mesma.

![Pesquisa de marcas](Images/Tela1b.png)


>Aqui, está sendo representada uma pesquisa pela marca de um veículo específico.

>Ao digitar "Valor", são solicitados os seguintes dados, um de cada vez: Marca, Modelo e Ano. Após digitados estes valores, é retornado o preço do veículo desejado.(É necessário digitar o nome do veículo de acordo com o que está escrito na FIPE)

![Pesquisa de preço](Images/Tela3b.png)

>É possível ao clicar no botão Histórico de consultas verificar as consultas realizadas anteriormente.

![Histórico de pesquisa](Images/Tela4b.png)
![Histórico de pesquisa2](Images/Tela5b.png)

>Abaixo está o diagrama de classes do projeto.
![Diagrama de classes](Images/ConsultaFipeBotNovo.jpg)
