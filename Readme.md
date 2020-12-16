[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE) [![Build Status](https://travis-ci.org/bcd29008/java-sqlite-mysql-gradle.svg?branch=master)](https://travis-ci.org/bcd29008/java-sqlite-mysql-gradle)

# Exemplos em Java com SQLite e MySQL / MariaDB

Esse projeto é composto de pequenos exemplos em Java para acessar banco de dados SQLite e MySQL / MariaDB usando [JDBC 4](https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html).

Execute a classe [bcd.Principal](src/main/java/bcd/Principal.java) e a partir dessa será possível invocar os exemplos apresentados abaixo.

## Exemplo 01 - SQLite

Código simples para apresentar os conceitos básicos para consulta, inserção, alteração e remoção de linhas em um banco de dados SQLite.

É feito uso do recurso [try-with-resources](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html) do Java para fechar a conexão após o uso.

## Exemplo 02 - SQLite

Código que apresenta uma organização que segue padrões de projeto de software (*Design Patterns*) para acessar banco de dados. Nesse caso, foi criada uma classe abstrata [ConnectionFactory.java](src/main/java/exemplo02/db/ConnectionFactory.java) que é uma fábrica de conexões com o banco.

É feito uso do recurso [try-with-resources](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html) do Java para fechar a conexão após o uso.

## Exemplo 03 - SQLite

Apresenta o uso da classe [PreparedStatement](https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html), preferível em relação ao Statement principalmente por evitar problemas com [*SQL Injection*](https://pt.wikipedia.org/wiki/Inje%C3%A7%C3%A3o_de_SQL).

![SQL Injection](exploits_of_a_mom.png)
https://xkcd.com/327/


## Exemplo 04 - SQLite

Apresenta o uso do padrão de projeto [*Data Access Object* (DAO)](https://pt.wikipedia.org/wiki/Objeto_de_acesso_a_dados).

## Exemplo 05 - MySQL

Apresenta um pequeno exemplo de como conectar em um banco de dados MySQL.

Para conectar em um banco MySQL é necessário informar:
- username - nome do usuário
- password - senha do usuário
- hostname - endereço IP ou hostname do servidor MySQL
- port - porta onde o processo do MySQL está ouvindo (por padrão é 3306)
- dbname - nome do esquema

Essas informações devem ser informadas no arquivo [ConnectionFactory.java](src/main/java/exemplo05mysql/db/ConnectionFactory.java).

Se você tiver uma instalação própria do MySQL, então use o arquivo [lab01-mysql-dml-ddl.sql](src/main/resources/lab01-mysql-dml-ddl.sql) para criar as tabelas, bem como inserir os registros, necessários para esse exemplo 05. Os comandos abaixo devem ser executados, porém, no cliente mysql no terminal:
```SQL
CREATE DATABASE lab01;

CREATE USER IF NOT EXISTS 'aluno'@'%' IDENTIFIED WITH mysql_native_password by '1234';
GRANT ALL ON lab01.* TO 'aluno'@'%';

USE lab01;
SOURCE lab01-mysql-dml-ddl.sql
```

Os comandos acima criaram um esquema chamado `lab01`, um usuário `aluno` com a senha `1234` e com total permissão sobre o esquema `lab01`. Por fim, executou o *script* `lab01-mysql-dml-ddl.sql`.


## Como usar o IntelliJ Ultimate para acessar o banco de dados

O IntelliJ tem um cliente para bancos de dados o qual permite fazer consultas, inserir, alterar e remover registros. Para configurar isso, veja:

- [Documentação oficial para SQLite](https://www.jetbrains.com/help/idea/connecting-to-a-database.html#connect-to-sqlite-database).
- [Documentação oficial para MySQL](https://www.jetbrains.com/help/idea/connecting-to-a-database.html#connect-to-mysql-database)