# Cadastro de Clientes

Sistema de cadastro de clientes desenvolvido em Java + MySQL.

## Tecnologias
- Java 17
- MySQL 8
- Swing (interface gráfica)
- Maven

## Como rodar

### 1. Banco de dados
- Instale o MySQL
- Abra o MySQL Workbench
- Execute o arquivo `banco.sql`

### 2. Configurar a conexão
- Abra `src/main/java/com/meuprojeto/dao/Conexao.java`
- Altere o campo `PASS` com a sua senha do MySQL
- Se não tiver senha, deixe `""`

### 3. Rodar o projeto
- Abra o projeto no IntelliJ IDEA
- Aguarde o Maven baixar as dependências
- Execute `TelaLogin.java`

## Funcionalidades
- Cadastro completo de clientes
- CPF com máscara automática
- Data de nascimento com máscara
- Endereço dividido em rua, número, bairro e cidade
- Capitalização automática
- Busca por nome
- Login e registro de usuários com senha criptografada

## Autor
Pedro Oliveira