CREATE TABLE usuarios (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);