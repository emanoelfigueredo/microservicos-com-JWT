CREATE TABLE anotacoes (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(50) NOT NULL,
    conteudo VARCHAR(2000) NOT NULL,
    momento DATETIME NOT NULL,
    PRIMARY KEY(id)
);