/*Emanuel Silva 20170149 __ José Agostinho 20181249*/

USE INF_20170149_2223

/*CRIAÇÃO DAS TABELAS*/

CREATE TABLE funcionario (
id_funcionario INT NOT NULL,
nomeFuncionario VARCHAR(30),
dataInicio DATE,
dataNascimentoFunc DATE,
CONSTRAINT tb_funcionario_pk PRIMARY KEY (id_funcionario));

CREATE TABLE supervisor (
id_funcionario_supervisor INT NOT NULL,
id_funcionario_supervisionado INT NOT NULL,
dataInicioSupervisao DATE,
dataFimSupervisao DATE,
numJornadas INT,
CONSTRAINT tb_supervisor_pk PRIMARY KEY (id_funcionario_supervisionado));

CREATE TABLE categoria (
id_categoria INT NOT NULL,
nomeCategoria VARCHAR (40),
CONSTRAINT tb_categoria_pk PRIMARY KEY (id_categoria));

CREATE TABLE producao (
id_producao INT NOT NULL,
dataInicioProd DATE,
duracao INT NOT NULL, 
quantidade INT DEFAULT 0,
CONSTRAINT tb_producao_pk PRIMARY KEY (id_producao));

CREATE TABLE produtos (
id_produto INT NOT NULL,
nomeProduto VARCHAR (40),
pesoProduto INT DEFAULT 0,
CONSTRAINT tb_produtos_pk PRIMARY KEY (id_produto));

CREATE TABLE ingrediente (
id_ingrediente INT NOT NULL,
nomeIngrediente VARCHAR(50),
quantidadeIngrediente INT DEFAULT 0,
CONSTRAINT tb_ingrediente_pk PRIMARY KEY (id_ingrediente));

CREATE TABLE distribuicao (
id_distribuicao INT NOT NULL,
quantidade INT DEFAULT 0,
id_funcionario INT NOT NULL,
CONSTRAINT tb_distribuicao_pk PRIMARY KEY (id_distribuicao));

CREATE TABLE viatura (
id_viatura INT NOT NULL,
modeloViatura VARCHAR(50),
CONSTRAINT tb_viatura_pk PRIMARY KEY (id_viatura));

CREATE TABLE clientes (
id_cliente INT NOT NULL,
nomeCliente VARCHAR(50),
dataNascimento DATE,
moradaCliente VARCHAR(60),
CONSTRAINT tb_clientes_pk PRIMARY KEY (id_cliente));

CREATE TABLE fornecedor (
id_fornecedor INT NOT NULL,
nomeFornecedor VARCHAR(60),
quantidadeFornecido INT DEFAULT 0,
dataFornecimento DATE,
id_ingrediente INT NOT NULL,
CONSTRAINT tb_fornecedor_pk PRIMARY KEY (id_fornecedor));


/*CRIAÇÃO DAS TABELAS RESULTANTES DAS RELAÇÕES*/

CREATE TABLE funcionario_categoria (
id_funcionario INT NOT NULL,
id_categoria INT NOT NULL,
dataRecrutamento DATE,
CONSTRAINT tb_funcionario_categoria_pk PRIMARY KEY (id_funcionario, id_categoria));

CREATE TABLE funcionario_producao (
id_funcionario INT NOT NULL,
id_producao INT NOT NULL,
tempoGastoProducao INT,
CONSTRAINT tb_funcionario_producao_pk PRIMARY KEY (id_funcionario, id_producao));

CREATE TABLE producao_produto (
id_producao INT NOT NULL,
id_produto INT NOT NULL,
CONSTRAINT tb_producao_produto_pk PRIMARY KEY (id_producao, id_produto));

CREATE TABLE produto_ingrediente (
id_produto INT NOT NULL,
id_ingrediente INT NOT NULL,
quantidadeUtilizada INT NOT NULL,
CONSTRAINT tb_produto_ingrediente_pk PRIMARY KEY (id_produto, id_ingrediente));

CREATE TABLE distribuicao_producao (
id_distribuicao INT NOT NULL,
id_producao INT NOT NULL,
quantidadeProducaoDistribuida INT NOT NULL,
CONSTRAINT tb_distribuicao_producao PRIMARY KEY (id_distribuicao, id_producao));

CREATE TABLE distribuicao_viatura (
id_distribuicao INT NOT NULL,
id_viatura INT NOT NULL,
dataColheita DATE,
quantidadesColhido INT DEFAULT 0,
CONSTRAINT tb_distribuicao_viatura_pk PRIMARY KEY (id_distribuicao, id_viatura));

CREATE TABLE distribuicao_cliente (
id_distribuicao INT NOT NULL,
id_cliente INT NOT NULL,
dataEntrega DATE,
quantidadeEntrega INT,
CONSTRAINT tb_distribuicao_cliente_pk PRIMARY KEY (id_distribuicao, id_cliente));



/*ALTERS TABLE*/
ALTER TABLE supervisor
ADD CONSTRAINT tb_responsavel_funcionarioSupervisor_fk
FOREIGN KEY (id_funcionario_supervisor) REFERENCES funcionario (id_funcionario);

ALTER TABLE supervisor
ADD CONSTRAINT tb_responsavel_funcionarioSupervisionado_fk
FOREIGN KEY (id_funcionario_supervisionado) REFERENCES funcionario (id_funcionario);
--------------------------------//--------------------------------
ALTER TABLE funcionario_categoria
ADD CONSTRAINT tb_funcionarioCategoria_funcionario_fk
FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario);

ALTER TABLE funcionario_categoria
ADD CONSTRAINT tb_funcionarioCategoria_categoria_fk
FOREIGN KEY (id_categoria) REFERENCES categoria (id_categoria);

--------------------------------//--------------------------------

ALTER TABLE funcionario_producao
ADD CONSTRAINT tb_funcionarioProducao_funcionario_fk
FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario);

ALTER TABLE funcionario_producao
ADD CONSTRAINT tb_funcionarioProducao_producao_fk
FOREIGN KEY (id_producao) REFERENCES producao (id_producao);

--------------------------------//--------------------------------

ALTER TABLE producao_produto
ADD CONSTRAINT tb_producaoProduto_producao_fk
FOREIGN KEY (id_producao) REFERENCES producao (id_producao);

ALTER TABLE producao_produto
ADD CONSTRAINT tb_producaoProduto_produto_fk
FOREIGN KEY (id_produto) REFERENCES produtos (id_produto);

--------------------------------//--------------------------------
ALTER TABLE produto_ingrediente
ADD CONSTRAINT tb_produto_ingrediente_produto_fk
FOREIGN KEY (id_produto) REFERENCES produtos (id_produto);

ALTER TABLE produto_ingrediente
ADD CONSTRAINT tb_produto_ingrediente_ingrediente_fk
FOREIGN KEY (id_ingrediente) REFERENCES ingrediente (id_ingrediente);

--------------------------------//--------------------------------

ALTER TABLE distribuicao
ADD CONSTRAINT tb_distribuicao_funcionario_fk
FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario);

--------------------------------//--------------------------------

ALTER TABLE distribuicao_producao
ADD CONSTRAINT tb_distribuicaoProducao_distribuicao_fk
FOREIGN KEY (id_distribuicao) REFERENCES distribuicao (id_distribuicao);

ALTER TABLE distribuicao_producao
ADD CONSTRAINT tb_distribuicaoProducao_producao_fk
FOREIGN KEY (id_producao) REFERENCES producao (id_producao);

--------------------------------//--------------------------------

ALTER TABLE distribuicao_viatura
ADD CONSTRAINT tb_distribuicaoViatura_distribuicao_fk
FOREIGN KEY (id_distribuicao) REFERENCES distribuicao (id_distribuicao);

ALTER TABLE distribuicao_viatura
ADD CONSTRAINT tb_distribuicaoViatura_viatura_fk
FOREIGN KEY (id_viatura) REFERENCES viatura (id_viatura);

--------------------------------//--------------------------------

ALTER TABLE distribuicao_cliente
ADD CONSTRAINT tb_distribuicaoCliente_distribuicao_fk
FOREIGN KEY (id_distribuicao) REFERENCES distribuicao (id_distribuicao);

ALTER TABLE distribuicao_cliente
ADD CONSTRAINT tb_distribuicaoCliente_cliente_fk
FOREIGN KEY (id_cliente) REFERENCES clientes (id_cliente);

--------------------------------//--------------------------------

ALTER TABLE fornecedor
ADD CONSTRAINT tb_fornecedor_ingrediente_fk
FOREIGN KEY (id_ingrediente) REFERENCES ingrediente (id_ingrediente);



/*VALIDACAO DOS CAMPOS COM DEFAULT O POR DEFEITO*/
ALTER TABLE supervisor
ADD CONSTRAINT tb_supervisor_CHK_numJornadas
CHECK (numJornadas BETWEEN 0 AND 10000);

ALTER TABLE producao
ADD CONSTRAINT tb_producao_CHK_quantidade
CHECK (quantidade BETWEEN 0 AND 10000);

ALTER TABLE produtos
ADD CONSTRAINT tb_produtos_CHK_pesoProduto
CHECK (pesoProduto BETWEEN 0 AND 1000);

ALTER TABLE ingrediente
ADD CONSTRAINT tb_ingrediente_CHK_quantidadeIngrediente
CHECK (quantidadeIngrediente BETWEEN 0 AND 1000);

ALTER TABLE produto_ingrediente
ADD CONSTRAINT tb_produto_ingrediente_CHK_quantidadeUtilizada
CHECK (quantidadeUtilizada BETWEEN 0 AND 10000);

ALTER TABLE distribuicao_producao
ADD CONSTRAINT tb_distribuicao_producao_CHK_quantidadeProducaoDistribuida
CHECK (quantidadeProducaoDistribuida BETWEEN 0 AND 10000);

ALTER TABLE distribuicao_viatura
ADD CONSTRAINT tb_distribuicao_viatura_CHK_quantidadesColhido
CHECK (quantidadesColhido BETWEEN 0 AND 10000);

ALTER TABLE distribuicao_cliente
ADD CONSTRAINT tb_distribuicao_cliente_CHK_quantidadeEntrega
CHECK (quantidadeEntrega BETWEEN 0 AND 10000);



/*DROP DAS CHAVES FORASTEIRAS*/
AlTER TABLE supervisor
DROP CONSTRAINT tb_responsavel_funcionarioSupervisor_fk;

ALTER TABLE supervisor
DROP CONSTRAINT tb_responsavel_funcionarioSupervisionado_fk;

ALTER TABLE funcionario_categoria
DROP CONSTRAINT tb_funcionarioCategoria_funcionario_fk;

ALTER TABLE funcionario_categoria
DROP CONSTRAINT tb_funcionarioCategoria_categoria_fk;

ALTER TABLE funcionario_producao
DROP CONSTRAINT tb_funcionarioProducao_funcionario_fk;

ALTER TABLE funcionario_producao
DROP CONSTRAINT tb_funcionarioProducao_producao_fk;

ALTER TABLE producao_produto
DROP CONSTRAINT tb_producaoProduto_producao_fk;

ALTER TABLE producao_produto
DROP CONSTRAINT tb_producaoProduto_produto_fk;

ALTER TABLE produto_ingrediente
DROP CONSTRAINT tb_produto_ingrediente_produto_fk;

ALTER TABLE produto_ingrediente
DROP CONSTRAINT tb_produto_ingrediente_ingrediente_fk;

ALTER TABLE distribuicao
DROP CONSTRAINT tb_distribuicao_funcionario_fk;

ALTER TABLE distribuicao_producao
DROP CONSTRAINT tb_distribuicaoProducao_distribuicao_fk;

ALTER TABLE distribuicao_producao
DROP CONSTRAINT tb_distribuicaoProducao_producao_fk;

ALTER TABLE distribuicao_viatura
DROP CONSTRAINT tb_distribuicaoViatura_distribuicao_fk

ALTER TABLE distribuicao_viatura
DROP CONSTRAINT tb_distribuicaoViatura_viatura_fk

ALTER TABLE distribuicao_cliente
DROP CONSTRAINT tb_distribuicaoCliente_distribuicao_fk

ALTER TABLE distribuicao_cliente
DROP CONSTRAINT tb_distribuicaoCliente_cliente_fk

ALTER TABLE fornecedor
DROP CONSTRAINT tb_fornecedor_ingrediente_fk


/*DROP DAS VALIDACOES DE PESO E QUANTIDADE*/
ALTER TABLE supervisor
DROP CONSTRAINT tb_supervisor_CHK_numJornadas;

ALTER TABLE producao
DROP CONSTRAINT tb_producao_CHK_quantidade;

ALTER TABLE produtos
DROP CONSTRAINT tb_produtos_CHK_pesoProduto;

ALTER TABLE ingrediente
DROP CONSTRAINT tb_ingrediente_CHK_quantidadeIngrediente;

ALTER TABLE produto_ingrediente
DROP CONSTRAINT tb_produto_ingrediente_CHK_quantidadeUtilizada

ALTER TABLE distribuicao_producao
DROP CONSTRAINT tb_distribuicao_producao_CHK_quantidadeProducaoDistribuida

ALTER TABLE distribuicao_viatura
DROP CONSTRAINT tb_distribuicao_viatura_CHK_quantidadesColhido

ALTER TABLE distribuicao_cliente
DROP CONSTRAINT tb_distribuicao_cliente_CHK_quantidadeEntrega


/*DROP DAS TABELAS*/
DROP TABLE funcionario;
DROP TABLE supervisor;
DROP TABLE categoria;
DROP TABLE producao;
DROP TABLE produtos;
DROP TABLE ingrediente;
DROP TABLE distribuicao;
DROP TABLE viatura;
DROP TABLE clientes;
DROP TABLE fornecedor;
DROP TABLE funcionario_categoria;
DROP TABLE funcionario_producao;
DROP TABLE producao_produto;
DROP TABLE produto_ingrediente;
DROP TABLE distribuicao_producao;
DROP TABLE distribuicao_viatura;
DROP TABLE distribuicao_cliente;


/*INSERT NAS TABELAS*/

SELECT * FROM funcionario;

INSERT INTO funcionario VALUES (1, 'Rui Silva', '2016-08-14', '1995-04-03');
INSERT INTO funcionario VALUES (2, 'Maria Santos', '2017-05-22', '1998-02-18');
INSERT INTO funcionario VALUES (3, 'António Ferreira', '2019-11-03', '1997-09-10');
INSERT INTO funcionario VALUES (4, 'Sofia Costa', '2021-04-17', '2001-07-21');
INSERT INTO funcionario VALUES (5, 'Luís Oliveira', '2016-10-29', '1992-12-05');
INSERT INTO funcionario VALUES (6, 'Mariana Rodrigues', '2018-02-12', '1999-08-30');
INSERT INTO funcionario VALUES (7, 'Gonçalo Almeida', '2020-09-18', '1996-11-11');
INSERT INTO funcionario VALUES (8, 'Inês Pereira', '2015-12-07', '1993-06-28');
INSERT INTO funcionario VALUES (9, 'Rui Carvalho', '2022-07-02', '2000-02-14');
INSERT INTO funcionario VALUES (10, 'Beatriz Gomes', '2023-01-09', '2002-09-25');
INSERT INTO funcionario VALUES (11, 'Ana Oliveira', '2019-06-18', '2001-03-12');
INSERT INTO funcionario VALUES (12, 'Rui Santos', '2017-09-05', '1995-11-28');
INSERT INTO funcionario VALUES (13, 'Marta Costa', '2022-11-27', '2000-07-15');
INSERT INTO funcionario VALUES (14, 'Tiago Rodrigues', '2016-03-10', '1998-09-02');
INSERT INTO funcionario VALUES (15, 'Carolina Pereira', '2020-08-22', '1999-05-07');
INSERT INTO funcionario VALUES (16, 'Hugo Ferreira', '2018-12-14', '2000-11-02');
INSERT INTO funcionario VALUES (17, 'Isabel Silva', '2023-04-06', '2003-08-18');
INSERT INTO funcionario VALUES (18, 'João Rodrigues', '2017-07-29', '1997-12-25');
INSERT INTO funcionario VALUES (19, 'Sara Almeida', '2021-02-13', '2002-06-10');
INSERT INTO funcionario VALUES (20, 'Ricardo Pereira', '2016-11-05', '2001-09-03');
INSERT INTO funcionario VALUES (21, 'João Palhinha', '2020-01-01', '1995-08-15');
INSERT INTO funcionario VALUES (22, 'Maria Conceição', '2021-02-01', '1998-06-20');

--------------------------------//--------------------------------

SELECT * FROM supervisor;

INSERT INTO supervisor VALUES (1, 2, '2017-05-23', '2017-06-23', 3);
INSERT INTO supervisor VALUES (1, 3, '2019-11-04', '2019-12-04', 4);
INSERT INTO supervisor VALUES (1, 4, '2021-04-18', '2021-05-18', 5);
INSERT INTO supervisor VALUES (1, 5, '2016-10-30', '2016-11-30', 1);
INSERT INTO supervisor VALUES (6, 7, '2020-09-19', '2020-10-19', 8);
INSERT INTO supervisor VALUES (8, 6, '2018-02-13', '2018-03-13', 5);
INSERT INTO supervisor VALUES (8, 9, '2022-07-03', '2022-08-03', 1);
INSERT INTO supervisor VALUES (6, 10, '2023-01-10', '2023-02-10', 3);
INSERT INTO supervisor VALUES (12, 11, '2019-06-19', '2019-07-19', 6);
INSERT INTO supervisor VALUES (11, 13, '2022-11-28', '2022-12-28', 7);
INSERT INTO supervisor VALUES (20, 11, '2019-06-19', '2019-07-19', 1);
INSERT INTO supervisor VALUES (11, 15, '2020-08-23', '2020-09-23', 1);
INSERT INTO supervisor VALUES (16, 17, '2023-04-07', '2023-05-07', 4);
INSERT INTO supervisor VALUES (20, 18, '2017-07-30', '2017-08-30', 6);
INSERT INTO supervisor VALUES (16, 19, '2021-02-14', '2021-03-14', 2);
INSERT INTO supervisor VALUES (16, 22, '2021-02-02', '2021-03-02', 2);

--------------------------------//--------------------------------

SELECT * FROM categoria;

INSERT INTO categoria VALUES (1, 'Padeiro');
INSERT INTO categoria VALUES (2, 'Pizzaiolo');
INSERT INTO categoria VALUES (3, 'Confeiteiro');
INSERT INTO categoria VALUES (4, 'Amassador');
INSERT INTO categoria VALUES (5, 'Forneiro');
INSERT INTO categoria VALUES (6, 'Medidor');
INSERT INTO categoria VALUES (7, 'Pizzaiolo Assistente');
INSERT INTO categoria VALUES (8, 'Atendente de Balconista');
INSERT INTO categoria VALUES (9, 'Auxiliar de Cozinha');
INSERT INTO categoria VALUES (10, 'Padeiro Assistente');
INSERT INTO categoria VALUES (11, 'Padeiro Confeiteiro');
INSERT INTO categoria VALUES (12, 'Gerente de Loja');
INSERT INTO categoria VALUES (13, 'Auxiliar de Limpeza');
INSERT INTO categoria VALUES (14, 'Pizzaiolo Sênior');
INSERT INTO categoria VALUES (15, 'Pasteleiro');
INSERT INTO categoria VALUES (16, 'Diretor de qualidade');


--------------------------------//--------------------------------

SELECT * FROM producao;

-- considerando a duração de produção em minutos
INSERT INTO producao VALUES (1, '2022-02-01', 45, 2500);
INSERT INTO producao VALUES (2, '2022-02-02', 55, 1500);
INSERT INTO producao VALUES (3, '2022-02-05', 30, 1000);
INSERT INTO producao VALUES (4, '2022-03-10', 40, 3000);
INSERT INTO producao VALUES (5, '2022-04-15', 50, 2000);
INSERT INTO producao VALUES (6, '2022-05-20', 35, 1200);
INSERT INTO producao VALUES (7, '2022-06-25', 45, 1800);
INSERT INTO producao VALUES (8, '2022-07-03', 60, 5000);
INSERT INTO producao VALUES (9, '2022-08-12', 30, 10000);
INSERT INTO producao VALUES (10, '2022-09-18', 50, 2500);
INSERT INTO producao VALUES (11, '2022-10-22', 25, 5000);
INSERT INTO producao VALUES (12, '2022-11-27', 40, 1500);
INSERT INTO producao VALUES (13, '2023-01-05', 55, 2000);
INSERT INTO producao VALUES (14, '2023-02-10', 35, 1200);
INSERT INTO producao VALUES (15, '2023-03-15', 50, 1800);

--------------------------------//--------------------------------

SELECT * FROM produtos;

INSERT INTO produtos VALUES (1, 'Pastel de Nata', 50);
INSERT INTO produtos VALUES (2, 'Pão de Deus', 100);
INSERT INTO produtos VALUES (3, 'Bola de Berlim', 120);
INSERT INTO produtos VALUES (4, 'Pão de Queijo', 70);
INSERT INTO produtos VALUES (5, 'Queijadinha', 40);
INSERT INTO produtos VALUES (6, 'Broa de Milho', 200);
INSERT INTO produtos VALUES (7, 'Travesseiro', 90);
INSERT INTO produtos VALUES (8, 'Rabanada', 60);
INSERT INTO produtos VALUES (9, 'Bolo de Arroz', 80);
INSERT INTO produtos VALUES (10, 'Palmier', 30);
INSERT INTO produtos VALUES (11, 'Tarte de Amêndoa', 150);
INSERT INTO produtos VALUES (12, 'Pão de Ló', 250);
INSERT INTO produtos VALUES (13, 'Queque', 45);
INSERT INTO produtos VALUES (14, 'Sonho', 75);
INSERT INTO produtos VALUES (15, 'Bola de Carne', 110);
INSERT INTO produtos VALUES (16, 'Croquete', 130);


--------------------------------//--------------------------------

SELECT * FROM ingrediente; 

INSERT INTO ingrediente VALUES (1, 'Massa Folhada', 500);
INSERT INTO ingrediente VALUES (2, 'Leite Condensado', 300);
INSERT INTO ingrediente VALUES (3, 'Açúcar em Pó', 200);
INSERT INTO ingrediente VALUES (4, 'Gemas de Ovo', 50);
INSERT INTO ingrediente VALUES (5, 'Farinha de Amêndoa', 150);
INSERT INTO ingrediente VALUES (6, 'Natas', 400);
INSERT INTO ingrediente VALUES (7, 'Cerejas', 100);
INSERT INTO ingrediente VALUES (8, 'Fermento Biológico', 20);
INSERT INTO ingrediente VALUES (9, 'Frutas Cristalizadas', 250);
INSERT INTO ingrediente VALUES (10, 'Gelatina', 100);
INSERT INTO ingrediente VALUES (11, 'Açúcar Mascavado', 150);
INSERT INTO ingrediente VALUES (12, 'Café Solúvel', 80);
INSERT INTO ingrediente VALUES (13, 'Levedura de Padeiro', 30);
INSERT INTO ingrediente VALUES (14, 'Caramelo', 100);
INSERT INTO ingrediente VALUES (15, 'Iogurte', 200);
INSERT INTO ingrediente VALUES (16, 'Raspas de Limão', 50);
INSERT INTO ingrediente VALUES (17, 'Canela em Pó', 100);
INSERT INTO ingrediente VALUES (18, 'Amêndoas Laminadas', 150);
INSERT INTO ingrediente VALUES (19, 'Manteiga Derretida', 200);
INSERT INTO ingrediente VALUES (20, 'Chocolate Negro', 300);
INSERT INTO ingrediente VALUES (21, 'Leite de Coco', 250);
INSERT INTO ingrediente VALUES (22, 'Açúcar Mascavado', 150);
INSERT INTO ingrediente VALUES (23, 'Farinha de Milho', 200);
INSERT INTO ingrediente VALUES (24, 'Caramelo Líquido', 100);
INSERT INTO ingrediente VALUES (25, 'Requeijão', 200);
INSERT INTO ingrediente VALUES (26, 'Amêndoas Torradas', 150);
INSERT INTO ingrediente VALUES (27, 'Geleia de Frutas', 100);
INSERT INTO ingrediente VALUES (28, 'Frutas Vermelhas', 200);
INSERT INTO ingrediente VALUES (29, 'Avelãs', 250);
INSERT INTO ingrediente VALUES (30, 'Natas Vegetais', 200);

--------------------------------//--------------------------------

SELECT * FROM distribuicao;

INSERT INTO distribuicao VALUES (1, 25000, 2);
INSERT INTO distribuicao VALUES (2, 15000, 2);
INSERT INTO distribuicao VALUES (3, 10000, 3);
INSERT INTO distribuicao VALUES (4, 30000, 6);
INSERT INTO distribuicao VALUES (5, 20000, 1);
INSERT INTO distribuicao VALUES (6, 12000, 6);
INSERT INTO distribuicao VALUES (7, 18000, 7);
INSERT INTO distribuicao VALUES (8, 5000, 10);
INSERT INTO distribuicao VALUES (9, 10000, 10);
INSERT INTO distribuicao VALUES (10, 25000, 10);
INSERT INTO distribuicao VALUES (11, 50000, 19);
INSERT INTO distribuicao VALUES (12, 15000, 12);
INSERT INTO distribuicao VALUES (13, 20000, 13);
INSERT INTO distribuicao VALUES (14, 12000, 18);
INSERT INTO distribuicao VALUES (15, 18000, 16);
INSERT INTO distribuicao VALUES (16, 12000, 3);
INSERT INTO distribuicao VALUES (17, 18000, 12);
INSERT INTO distribuicao VALUES (18, 10, 21);
INSERT INTO distribuicao VALUES (19, 5, 22);


--------------------------------//--------------------------------

SELECT * FROM viatura;

INSERT INTO viatura VALUES (1, 'Mercedes-Benz Sprinter');
INSERT INTO viatura VALUES (2, 'Volkswagen Transporter');
INSERT INTO viatura VALUES (3, 'Ford Transit');
INSERT INTO viatura VALUES (4, 'Renault Master');
INSERT INTO viatura VALUES (5, 'Peugeot Gama');

--------------------------------//--------------------------------

SELECT * FROM clientes;

INSERT INTO clientes VALUES (1, 'António', '1980-03-10', 'Rua Cândido dos Reis');
INSERT INTO clientes VALUES (2, 'Pedro', '1975-06-25', 'Rua Serpa Pinto');
INSERT INTO clientes VALUES (3, 'Mário', '1992-11-15', 'Rua Francisco de Holanda');
INSERT INTO clientes VALUES (4, 'José', '1988-09-02', 'Rua Alexandre Herculano');
INSERT INTO clientes VALUES (5, 'Aníbal', '1983-04-18', 'Rua de São Vicente');
INSERT INTO clientes VALUES (6, 'Durão', '1995-12-07', 'Rua Cidade de Rio Maior');
INSERT INTO clientes VALUES (7, 'Francisco', '1990-07-31', 'Rua João de Deus');
INSERT INTO clientes VALUES (8, 'Vasco', '1987-02-14', 'Rua Camilo Castelo Branco');
INSERT INTO clientes VALUES (9, 'Marcelo', '1982-10-29', 'Rua Alves Redol');
INSERT INTO clientes VALUES (10, 'Álvaro', '1998-08-13', 'Rua da Liberdade');
INSERT INTO clientes VALUES (11, 'José', '1985-05-27', 'Rua Quinta do Marquês');
INSERT INTO clientes VALUES (12, 'Maria', '1991-01-08', 'Rua da Fonte Nova');
INSERT INTO clientes VALUES (13, 'Cavaco', '1996-03-22', 'Rua dos Combatentes');
INSERT INTO clientes VALUES (14, 'Sá', '1989-09-17', 'Rua Fernando Pessoa');
INSERT INTO clientes VALUES (15, 'António', '1984-07-12', 'Rua Marquês de Pombal');

--------------------------------//--------------------------------

SELECT * FROM fornecedor;

INSERT INTO fornecedor VALUES (1, 'Continente', 500, '2022-01-10', 1);
INSERT INTO fornecedor VALUES (2, 'Pingo Doce', 300, '2022-02-15', 5);
INSERT INTO fornecedor VALUES (3, 'Lidl', 800, '2022-03-20', 10);
INSERT INTO fornecedor VALUES (4, 'Aldi', 400, '2022-04-25', 15);
INSERT INTO fornecedor VALUES (5, 'Minipreço', 600, '2022-05-30', 20);
INSERT INTO fornecedor VALUES (6, 'Intermarché', 200, '2022-06-05', 25);
INSERT INTO fornecedor VALUES (7, 'El Corte Inglés', 900, '2022-07-10', 3);
INSERT INTO fornecedor VALUES (8, 'Apolónia', 350, '2022-08-15', 8);
INSERT INTO fornecedor VALUES (9, 'Jumbo', 700, '2022-09-20', 12);
INSERT INTO fornecedor VALUES (10, 'Amanhecer', 450, '2022-10-25', 18);

--------------------------------//--------------------------------

SELECT * FROM funcionario_categoria;

INSERT INTO funcionario_categoria VALUES (1, 1, '2016-08-14');
INSERT INTO funcionario_categoria VALUES (1, 2, '2016-08-19');
INSERT INTO funcionario_categoria VALUES (2, 3, '2017-05-22');
INSERT INTO funcionario_categoria VALUES (2, 4, '2017-05-29');
INSERT INTO funcionario_categoria VALUES (3, 5, '2019-11-03');
INSERT INTO funcionario_categoria VALUES (4, 6, '2021-04-17');
INSERT INTO funcionario_categoria VALUES (5, 7, '2016-10-29');
INSERT INTO funcionario_categoria VALUES (6, 8, '2018-02-12');
INSERT INTO funcionario_categoria VALUES (7, 9, '2020-09-18');
INSERT INTO funcionario_categoria VALUES (8, 10, '2015-12-07');
INSERT INTO funcionario_categoria VALUES (9, 11, '2022-07-02');
INSERT INTO funcionario_categoria VALUES (10, 12, '2023-01-09');
INSERT INTO funcionario_categoria VALUES (11, 13, '2019-06-18');
INSERT INTO funcionario_categoria VALUES (12, 14, '2017-09-05');
INSERT INTO funcionario_categoria VALUES (13, 15, '2022-11-27');
INSERT INTO funcionario_categoria VALUES (14, 1, '2016-03-10');
INSERT INTO funcionario_categoria VALUES (15, 2, '2020-08-22');
INSERT INTO funcionario_categoria VALUES (16, 3, '2018-12-14');
INSERT INTO funcionario_categoria VALUES (17, 4, '2023-04-06');
INSERT INTO funcionario_categoria VALUES (18, 11, '2017-07-29');
INSERT INTO funcionario_categoria VALUES (19, 5, '2021-02-13');
INSERT INTO funcionario_categoria VALUES (20, 12, '2016-11-05');

--------------------------------//--------------------------------

SELECT * FROM funcionario_producao; 

INSERT INTO funcionario_producao VALUES (2, 11, 25);
INSERT INTO funcionario_producao VALUES (5, 8, 60);
INSERT INTO funcionario_producao VALUES (7, 9, 30);
INSERT INTO funcionario_producao VALUES (10, 6, 35);
INSERT INTO funcionario_producao VALUES (12, 2, 55);
INSERT INTO funcionario_producao VALUES (14, 3, 30);
INSERT INTO funcionario_producao VALUES (16, 15, 45);
INSERT INTO funcionario_producao VALUES (18, 10, 50);
INSERT INTO funcionario_producao VALUES (20, 4, 30);
INSERT INTO funcionario_producao VALUES (1, 7, 45);
INSERT INTO funcionario_producao VALUES (4, 5, 25);
INSERT INTO funcionario_producao VALUES (17, 13, 45);
INSERT INTO funcionario_producao VALUES (19, 1, 20);
INSERT INTO funcionario_producao VALUES (3, 14, 20);
INSERT INTO funcionario_producao VALUES (6, 12, 10);
INSERT INTO funcionario_producao VALUES (21, 12, 40);
INSERT INTO funcionario_producao VALUES (22, 10, 50);


--------------------------------//--------------------------------

SELECT * FROM producao_produto;

INSERT INTO producao_produto VALUES (3, 8);
INSERT INTO producao_produto VALUES (10, 12);
INSERT INTO producao_produto VALUES (7, 2);
INSERT INTO producao_produto VALUES (5, 13);
INSERT INTO producao_produto VALUES (1, 15);
INSERT INTO producao_produto VALUES (8, 7);
INSERT INTO producao_produto VALUES (4, 6);
INSERT INTO producao_produto VALUES (6, 11);
INSERT INTO producao_produto VALUES (2, 1);
INSERT INTO producao_produto VALUES (9, 9);
INSERT INTO producao_produto VALUES (11, 3);
INSERT INTO producao_produto VALUES (14, 5);
INSERT INTO producao_produto VALUES (15, 4);
INSERT INTO producao_produto VALUES (13, 10);
INSERT INTO producao_produto VALUES (12, 14);

--------------------------------//--------------------------------

SELECT * FROM produto_ingrediente;

INSERT INTO produto_ingrediente VALUES (1, 1, 20);
INSERT INTO produto_ingrediente VALUES (1, 2, 10);
INSERT INTO produto_ingrediente VALUES (1, 3, 40);
INSERT INTO produto_ingrediente VALUES (1, 4, 20);
INSERT INTO produto_ingrediente VALUES (2, 13, 5);
INSERT INTO produto_ingrediente VALUES (2, 10, 20);
INSERT INTO produto_ingrediente VALUES (2, 2, 50);
INSERT INTO produto_ingrediente VALUES (3, 9, 25);
INSERT INTO produto_ingrediente VALUES (3, 5, 20);
INSERT INTO produto_ingrediente VALUES (3, 1, 70);
INSERT INTO produto_ingrediente VALUES (4, 14, 15);
INSERT INTO produto_ingrediente VALUES (4, 7, 10);
INSERT INTO produto_ingrediente VALUES (4, 11, 23);
INSERT INTO produto_ingrediente VALUES (4, 20, 49);
INSERT INTO produto_ingrediente VALUES (5, 19, 20);
INSERT INTO produto_ingrediente VALUES (6, 30, 15);
INSERT INTO produto_ingrediente VALUES (6, 18, 23);
INSERT INTO produto_ingrediente VALUES (7, 22, 80);
INSERT INTO produto_ingrediente VALUES (7, 4, 10);
INSERT INTO produto_ingrediente VALUES (8, 11, 24);
INSERT INTO produto_ingrediente VALUES (8, 10, 11);
INSERT INTO produto_ingrediente VALUES (9, 2, 7);
INSERT INTO produto_ingrediente VALUES (9, 12, 9);
INSERT INTO produto_ingrediente VALUES (10, 5, 23);
INSERT INTO produto_ingrediente VALUES (10, 1, 40);
INSERT INTO produto_ingrediente VALUES (11, 14, 5);
INSERT INTO produto_ingrediente VALUES (12, 7, 8);
INSERT INTO produto_ingrediente VALUES (13, 11, 12);
INSERT INTO produto_ingrediente VALUES (14, 4, 2);
INSERT INTO produto_ingrediente VALUES (15, 30, 10);

--------------------------------//--------------------------------

SELECT * FROM distribuicao_producao;

INSERT INTO distribuicao_producao VALUES (1, 1, 100);
INSERT INTO distribuicao_producao VALUES (2, 2, 150);
INSERT INTO distribuicao_producao VALUES (3, 3, 200);
INSERT INTO distribuicao_producao VALUES (4, 4, 120);
INSERT INTO distribuicao_producao VALUES (5, 5, 200);
INSERT INTO distribuicao_producao VALUES (6, 6, 120);
INSERT INTO distribuicao_producao VALUES (7, 7, 170);
INSERT INTO distribuicao_producao VALUES (8, 8, 110);
INSERT INTO distribuicao_producao VALUES (9, 9, 1000);
INSERT INTO distribuicao_producao VALUES (10, 10, 100);
INSERT INTO distribuicao_producao VALUES (11, 11, 200);
INSERT INTO distribuicao_producao VALUES (12, 12, 210);
INSERT INTO distribuicao_producao VALUES (13, 13, 146);
INSERT INTO distribuicao_producao VALUES (14, 14, 280);
INSERT INTO distribuicao_producao VALUES (15, 15, 235);

--------------------------------//--------------------------------

SELECT * FROM distribuicao_viatura;

INSERT INTO distribuicao_viatura VALUES (1, 1, '2022-02-01', 2500);
INSERT INTO distribuicao_viatura VALUES (2, 1, '2022-02-02', 1500);
INSERT INTO distribuicao_viatura VALUES (3, 1, '2022-02-05', 1000);
INSERT INTO distribuicao_viatura VALUES (4, 1, '2022-03-10', 3000);
INSERT INTO distribuicao_viatura VALUES (5, 2, '2022-04-15', 2000);
INSERT INTO distribuicao_viatura VALUES (6, 2, '2022-05-20', 1200);
INSERT INTO distribuicao_viatura VALUES (7, 3, '2022-06-25', 1800);
INSERT INTO distribuicao_viatura VALUES (8, 4, '2022-07-03', 5000);
INSERT INTO distribuicao_viatura VALUES (9, 4, '2022-08-12', 1000);
INSERT INTO distribuicao_viatura VALUES (10, 1, '2022-09-18', 2500);
INSERT INTO distribuicao_viatura VALUES (11, 2, '2022-10-22', 5000);
INSERT INTO distribuicao_viatura VALUES (12, 2, '2022-11-27', 1500);
INSERT INTO distribuicao_viatura VALUES (13, 1, '2023-01-05', 2000);
INSERT INTO distribuicao_viatura VALUES (14, 3, '2023-02-10', 1200);
INSERT INTO distribuicao_viatura VALUES (15, 3, '2023-03-15', 1800);
INSERT INTO distribuicao_viatura VALUES (14, 2, '2023-02-10', 1200);
INSERT INTO distribuicao_viatura VALUES (4, 2, '2022-03-10', 1800);

--------------------------------//--------------------------------

SELECT * FROM distribuicao_cliente;

INSERT INTO distribuicao_cliente VALUES (1, 1, '2022-09-20', 100);
INSERT INTO distribuicao_cliente VALUES (2, 2, '2022-12-23', 200);
INSERT INTO distribuicao_cliente VALUES (3, 3, '2021-04-21', 300);
INSERT INTO distribuicao_cliente VALUES (4, 4, '2023-03-13', 350);
INSERT INTO distribuicao_cliente VALUES (5, 5, '2023-04-10', 400);
INSERT INTO distribuicao_cliente VALUES (6, 6, '2022-12-01', 220);
INSERT INTO distribuicao_cliente VALUES (7, 7, '2022-07-15', 450);
INSERT INTO distribuicao_cliente VALUES (8, 8, '2022-05-10', 290);
INSERT INTO distribuicao_cliente VALUES (9, 9, '2022-06-04', 700);
INSERT INTO distribuicao_cliente VALUES (10, 10, '2022-07-30', 600);
INSERT INTO distribuicao_cliente VALUES (11, 11, '2023-01-15', 610);
INSERT INTO distribuicao_cliente VALUES (12, 12, '2023-04-01', 520);
INSERT INTO distribuicao_cliente VALUES (13, 13, '2022-08-08', 515);
INSERT INTO distribuicao_cliente VALUES (14, 14, '2022-03-10', 890);
INSERT INTO distribuicao_cliente VALUES (15, 15, '2023-02-09', 210);
INSERT INTO distribuicao_cliente VALUES (4, 15, '2023-03-13', 210);

