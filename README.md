# Projeto Sistema de Vendas de produtos


* Este foi um projeto de estudo realizado durante as aulas de Programação Orientada a Objetos II, na qual o objetivo foi construir um Sistema de Vendas de produtos, que permite o usuário cadastrar clientes, produtos e categorias de produtos, registrar vendas, além de gerar gráficos e relatórios.

## :desktop_computer: Tecnologias e Conceitos utilizados 
- Linguagem **Java** e a tecnologia **JavaFx** para construção das interfaces gráficas. 
- **Padrão MVC** (Model, View e Controller) que divide o projeto em camadas de responsabilidades. 
- **Padrão DAO** (Data Acess Object) que isola as regras de negócio das regras de persistência de dados.
- **PostgreSQL** como SGBD para armazenar e gerenciar os dados da aplicação.
- **Padrão Factory** para gerar a instância de um determinado SGBD para persistência dos dados, permitindo gerar uma instância de outro SGBD como o MySql.  
- **Jasper Reports** para geração de relatórios de estoque da aplicação.

---

## :computer_mouse: Demonstração do Sistema

![DemoSistema](https://user-images.githubusercontent.com/101357910/174904361-645b8801-fb90-4fea-9541-409bd79e9f65.gif)

---

## :man_technologist: Como executar o Sistema

- Instale o Java 8 em seu Computador.
- Faça um clone deste repositório.
- Abra o projeto em sua IDE de preferência (Net Beans, VsCode, etc).
- Certifique-se que as bibliotecas do diretório 'lib' estão sendo utilizados.
- Crie um Banco de Dados no PostgreSQL com nome de 'javafxmvc' e altere suas credenciais de acesso ao SGBD (login, senha) dentro do código fonte (DatabasePostgreSQL.java).
- Execute o código SQL dentro do arquivo 'script_bd.sql' para criar a estrutura do banco de dados da aplicação. 

---

## :wrench: Melhorias
Por ser tratar de um Sistema realizado para estudo, podem ser feitas várias melhorias com objetivo de aperfeiçoar o software e principalmente de criar desafios pessoais para que você evolua e pratique essas tecnologias:
- Tratar detalhadamente todas as entradas de dados.
- Melhorar o design com CSS ou alguma biblioteca gráfica.
- Implementar mais alguns relatórios com outros dados utilizando parâmetros no Jasper Reports.
- Tratar algumas questões visuais, como não deixar repetir os mesmos itens na tabela no Processo de Vendas.
- Entre outras diversas implementações, basta usar a criatividade.

## :book: Estudo do Sistema
Caso queira entender como o Sistema foi desenvolvido desde o início, basta acessar o [Youtube](https://www.youtube.com/watch?v=_Ke7CiTdmiI&list=PL-mvLy2ws8ILNrs8jtEAwaZMxDZvlMj48) do professor da disciplina.
