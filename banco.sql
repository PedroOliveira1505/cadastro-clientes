CREATE DATABASE IF NOT EXISTS cadastro_clientes;

USE cadastro_clientes;

CREATE TABLE IF NOT EXISTS clientes (
                                        id             INT AUTO_INCREMENT PRIMARY KEY,
                                        nome           VARCHAR(100) NOT NULL,
    email          VARCHAR(100) UNIQUE,
    telefone       VARCHAR(20),
    cpf            VARCHAR(14) UNIQUE,
    rua            VARCHAR(150),
    numero         VARCHAR(10),
    bairro         VARCHAR(80),
    cidade         VARCHAR(80),
    data_nascimento DATE,
    data_cadastro  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS usuarios (
                                        id        INT AUTO_INCREMENT PRIMARY KEY,
                                        usuario   VARCHAR(50) UNIQUE NOT NULL,
    senha     VARCHAR(64) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );