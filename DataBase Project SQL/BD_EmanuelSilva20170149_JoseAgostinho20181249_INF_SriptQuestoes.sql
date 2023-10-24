/*Emanuel Silva 20170149 __ José Agostinho 20181249*/

USE INF_20170149_2223

/*1 – Responda em SQL às seguintes*/
/*i. Faça a listagem geral de todas as tabelas*/
SELECT * FROM funcionario;
SELECT * FROM supervisor;
SELECT * FROM categoria;
SELECT * FROM producao;
SELECT * FROM produtos;
SELECT * FROM ingrediente;
SELECT * FROM distribuicao;
SELECT * FROM viatura;
SELECT * FROM clientes;
SELECT * FROM fornecedor;
SELECT * FROM funcionario_categoria;
SELECT * FROM funcionario_producao;
SELECT * FROM producao_produto;
SELECT * FROM produto_ingrediente;
SELECT * FROM distribuicao_producao;
SELECT * FROM distribuicao_viatura;
SELECT * FROM distribuicao_cliente;


/*ii. Liste toda a informação sobre os funcionários, que tenham ‘Rui’ no nome, 
		e que tenham participado em produções no primeiro trimestre de 2022. Apresente o resultado ordenado por nome de Z para A;*/
SELECT f.id_funcionario, f.nomeFuncionario, p.id_producao, p.dataInicioProd, p.quantidade
FROM Funcionario f
INNER JOIN Funcionario_Producao fp ON f.id_funcionario = fp.id_funcionario
INNER JOIN Producao p ON fp.id_producao = p.id_producao
WHERE f.nomeFuncionario LIKE '%Rui%'
AND YEAR(p.dataInicioProd) = 2022
AND MONTH(p.dataInicioProd) BETWEEN 1 AND 3
ORDER BY f.nomeFuncionario DESC;


/*iii. Liste para cada nome de produto, o nome dos ingredientes e a respetiva quantidade, na sua composição. 
		Inclua no resultado apenas produtos que tenham sido produzidos nos primeiros 15 dias do mês de fevereiro de 2022. 
		Ordene o resultado pelo nome do produto de Z para A;*/
SELECT pr.nomeProduto, i.nomeIngrediente, i.quantidadeIngrediente, p.dataInicioProd
FROM Produtos pr
INNER JOIN Produto_Ingrediente pi ON pr.id_produto = pi.id_produto
INNER JOIN Ingrediente i ON pi.id_ingrediente = i.id_ingrediente
INNER JOIN Producao_Produto pp ON pr.id_produto = pp.id_produto
INNER JOIN Producao p ON pp.id_producao = p.id_producao
WHERE p.dataInicioProd BETWEEN '2022-02-01' AND '2022-02-15'
ORDER BY pr.nomeProduto DESC;


/*iv. Liste para cada nome de funcionário, a quantidade total de vezes que este foi responsável por jornadas. 
		Tenha em consideração que podem existir funcionários que nunca foram responsáveis por qualquer jornada, mas também estes devem constar do resultado. 
		Ordene o resultado pela quantidade começando pela maior quantidade até à menor;*/
SELECT f.nomeFuncionario, COUNT(s.id_funcionario_supervisor) AS quantidade
FROM Funcionario f
LEFT JOIN Supervisor s ON f.id_funcionario = s.id_funcionario_supervisor
GROUP BY f.nomeFuncionario
ORDER BY quantidade DESC;


/*v. Para cada viatura indique a quantidade de entregas em que participou. 
		Apresente o resultado ordenado pela quantidade (da maior para a menor) e diferenciado por semestre (independente do ano em causa);*/
SELECT v.id_viatura, v.modeloViatura,
    COUNT(CASE WHEN MONTH(dv.dataColheita) <= 6 THEN dv.id_distribuicao END) AS '1º Semestre',
    COUNT(CASE WHEN MONTH(dv.dataColheita) > 6 THEN dv.id_distribuicao END) AS '2º Semestre'
FROM Viatura v
INNER JOIN Distribuicao_Viatura dv ON v.id_viatura = dv.id_viatura
GROUP BY v.id_viatura, v.modeloViatura
ORDER BY '1º Semestre' DESC, '2º Semestre' DESC;


/*vi. Liste toda a informação sobre funcionários nascidos antes de 2000 que tendo realizado entregas, nunca tenham sido responsáveis por nenhuma jornada.
		Apresente o resultado ordenado por nome de funcionário de Z para A;*/
SELECT f.id_funcionario, f.nomeFuncionario, f.dataNascimentoFunc
FROM Funcionario f
LEFT JOIN Distribuicao d ON f.id_funcionario = d.id_Funcionario
LEFT JOIN Supervisor s ON f.id_funcionario = s.id_funcionario_supervisionado
WHERE YEAR(f.dataNascimentoFunc) < 2000
    AND d.id_Funcionario IS NOT NULL
    AND s.id_funcionario_supervisionado IS NULL
ORDER BY f.nomeFuncionario DESC;


/*vii. Liste o nome dos 3 produtos produzidos em maior quantidade. Não devem constar do resultado produtos produzidos nos meses pares, 
		nem que o seu nome termine pela letra ‘s’. Apresente o resultado ordenado por data de entrega, começando pela mais recente;*/
SELECT pr.nomeProduto, p.dataInicioProd, SUM(p.quantidade) AS quantidade_total
FROM Produtos pr
INNER JOIN Producao_Produto pp ON pr.id_produto = pp.id_produto
INNER JOIN Producao p ON pp.id_producao = p.id_producao
INNER JOIN Distribuicao_Producao dp ON p.id_producao = dp.id_producao
WHERE MONTH(p.dataInicioProd) % 2 <> 0
AND RIGHT(pr.nomeProduto, 1) <> 's'
GROUP BY pr.nomeProduto, p.dataInicioProd
ORDER BY quantidade_total DESC


/*viii. Liste para cada nome de produto, a data em que este foi produzido pela primeira vez.*/
SELECT pr.nomeProduto, MIN(p.dataInicioProd) AS dataPrimeiraProducao
FROM Produtos pr
INNER JOIN Producao_Produto pp ON pr.id_produto = pp.id_produto
INNER JOIN Producao p ON pp.id_producao = p.id_producao
GROUP BY pr.nomeProduto;


/*ix. Liste o nome dos funcionários que tenham realizado pelo menos duas entregas no mesmo dia. 
		No resultado deverá constar o nome do funcionário, e a data. O resultado apresentado deve ser ordenado por data, 
		iniciando-se a listagem pela mais recente;*/
SELECT f.nomeFuncionario, dc.dataEntrega
FROM Funcionario f
INNER JOIN Distribuicao_Cliente dc ON f.id_funcionario = dc.id_distribuicao
GROUP BY f.nomeFuncionario, dc.dataEntrega
HAVING COUNT(*) >= 2
ORDER BY dc.dataEntrega DESC;


/*x. Recorra ao mecanismo de criação de vista de modo a criar uma vista que para cada categoria dos funcionários, 
		que contabilize o número de vezes que esta é principal ou secundária.*/
CREATE VIEW CategoriaFuncionario AS
SELECT c.nomeCategoria, 
       SUM(CASE WHEN s.numJornadas = 1 THEN 1 ELSE 0 END) AS numPrincipal,
       SUM(CASE WHEN s.numJornadas = 2 THEN 1 ELSE 0 END) AS numSecundaria
FROM Categoria c
LEFT JOIN Funcionario_Categoria fc ON c.id_categoria = fc.id_categoria
LEFT JOIN Supervisor s ON fc.id_funcionario = s.id_funcionario_supervisionado
GROUP BY c.nomeCategoria;

SELECT * FROM CategoriaFuncionario;


--------------------------------//--------------------------------

/*2 - Criar, à escolha, as seguintes consultas (texto da pergunta e resposta em sql):
• 6 consultas que usem: “Like”; “Between”; “In” e os seus opostos “Not Like”; “Not Between” e “Not In (podem usar outros operadores: =; >; <; >=; …);
• 5 consultas que envolvam funções de agregação (“Count”; “Min”; “Max”; “AVG”; “SUM”)
• 2 consultas que envolvam “Group By” e “Having”;
• 2 consultas que envolvam “Subqueries” (uma sem e outra com correlação);
• 2 vistas.*/

----Com uso do LIKE
/*Listagem das viaturas que fizeram mais entregas, incluindo aquelas que não fizeram nenhuma*/
SELECT v.modeloViatura, COUNT(dv.id_distribuicao) AS quantidade_entregas
FROM Viatura v
LEFT JOIN Distribuicao_Viatura dv ON v.id_viatura = dv.id_viatura
GROUP BY v.modeloViatura
ORDER BY quantidade_entregas DESC;


----Com uso do IN
/*Listagem dos ingredientes que foram fornecidos nos primeiros 5 meses do ano de 2022*/
SELECT i.nomeIngrediente, f.nomeFornecedor, f.dataFornecimento
FROM Ingrediente i
JOIN Fornecedor f ON i.id_ingrediente = f.id_ingrediente
WHERE i.id_ingrediente IN (
    SELECT id_ingrediente
    FROM Fornecedor
    WHERE YEAR(dataFornecimento) = 2022
      AND MONTH(dataFornecimento) BETWEEN 1 AND 5);

----Com uso do BETWEEN
/*Listagem dos clientes que receberam uma distribuição e que tenham nascido entre 1980 e 1990*/
SELECT c.nomeCliente, dc.dataEntrega, c.dataNascimento
FROM Clientes c
INNER JOIN Distribuicao_Cliente dc ON c.id_cliente = dc.id_cliente
WHERE c.dataNascimento BETWEEN '1980-01-01' AND '1990-12-31';


----Com uso do NOT LIKE
/*Listagem na qual o nome não seja Rui Silva e quantidade não seja 25000*/
SELECT f.nomeFuncionario, d.quantidade
FROM Funcionario f
JOIN Distribuicao d ON f.id_funcionario = d.id_funcionario
WHERE f.nomeFuncionario NOT LIKE '%Rui Silva%'
  AND d.quantidade NOT LIKE '%25000%';


----Com uso do NOT BETWEEN
/*Listar os ingredientes que não foram fornecidos entre maio e setembro*/
SELECT *
FROM Ingrediente
WHERE id_ingrediente IN (
  SELECT id_ingrediente
  FROM Fornecedor
  WHERE MONTH(dataFornecimento) NOT BETWEEN 5 AND 9);

----Com uso do NOT IN
/*Listar os ingredientes fornecidos em 2022, exceto nos meses pares*/
SELECT f.nomeFornecedor, i.nomeIngrediente, f.dataFornecimento
FROM Fornecedor f
INNER JOIN Ingrediente i ON f.id_ingrediente = i.id_ingrediente
WHERE f.dataFornecimento >= '2022-01-01'
  AND f.dataFornecimento < '2023-01-01'
  AND MONTH(f.dataFornecimento) % 2 <> 0;


----Com o operador =
/*Listagem das viaturas que fizeram uma distribuição com quantidade de entrega igual a 10000*/
SELECT v.modeloViatura, d.quantidade
FROM Viatura v
INNER JOIN Distribuicao_Viatura dv ON v.id_viatura = dv.id_viatura
INNER JOIN Distribuicao d ON dv.id_distribuicao = d.id_distribuicao
WHERE d.quantidade = 10000;


----Com o operador >
/*Listagem das viaturas que fizeram uma distribuição com quantidade de entrega maior que 20000*/
SELECT v.modeloViatura, d.quantidade
FROM Viatura v
INNER JOIN Distribuicao_Viatura dv ON v.id_viatura = dv.id_viatura
INNER JOIN Distribuicao d ON dv.id_distribuicao = d.id_distribuicao
WHERE d.quantidade > 20000;


----Com o operador <
/*Listagem dos ingredientes que a quantidade fornecida foi menor que 900*/
SELECT i.*
FROM ingrediente i
JOIN fornecedor f ON i.id_ingrediente = f.id_ingrediente
WHERE f.quantidadeFornecido < 900;


----Com o operador !=
/*Listagem dos funcionarios em que a categoria é diferente de Gerente de Loja*/
SELECT f.nomeFuncionario, c.nomeCategoria
FROM Funcionario f
JOIN Funcionario_Categoria fc ON f.id_funcionario = fc.id_funcionario
JOIN Categoria c ON fc.id_categoria = c.id_categoria
WHERE c.nomeCategoria != 'Gerente de Loja';


----Com uso do COUNT
/*Listar por cada categoria o número de funcionários*/
SELECT c.nomeCategoria, COUNT(fc.id_funcionario) AS totalFuncionarios
FROM categoria c
JOIN funcionario_categoria fc ON c.id_categoria = fc.id_categoria
GROUP BY c.nomeCategoria;


----Com uso do MIN
/*Listar o cliente mais novo que já recebeu uma distribuição*/
SELECT c.*
FROM clientes c
INNER JOIN distribuicao_cliente dc ON c.id_cliente = dc.id_cliente
WHERE c.dataNascimento = (
    SELECT MIN(dataNascimento)
    FROM clientes
    WHERE id_cliente IN (SELECT id_cliente FROM Distribuicao_Cliente));


----Com uso do MAX
/*Ingrediente com maior quantidade fornecido*/
SELECT i.nomeIngrediente, f.quantidadeFornecido
FROM ingrediente i
JOIN fornecedor f ON i.id_ingrediente = f.id_ingrediente
WHERE f.quantidadeFornecido = (
    SELECT MAX(quantidadeFornecido)
    FROM fornecedor);


----Com uso do AVG
/*Calcular a média de quantidades entregues por cada viatura*/
SELECT v.modeloViatura, AVG(d.quantidade) AS mediaQuantidade
FROM Viatura v
INNER JOIN Distribuicao_Viatura dv ON v.id_viatura = dv.id_viatura
INNER JOIN Distribuicao d ON dv.id_distribuicao = d.id_distribuicao
GROUP BY v.modeloViatura;

----Com uso do SUM
/*Calcular o tempo gasto por cada producao*/
SELECT p.id_producao, SUM(fp.tempoGastoProducao) AS totalTempoGasto
FROM Producao p
INNER JOIN funcionario_Producao fp ON p.id_producao = fp.id_producao
GROUP BY p.id_producao;


----Com uso de GROUP BY 
/*Listar os funcionários e as categorias associadas*/
SELECT f.id_funcionario, f.nomeFuncionario, c.nomeCategoria
FROM funcionario f, funcionario_categoria fc, categoria c
WHERE f.id_funcionario = fc.id_funcionario AND fc.id_categoria = c.id_categoria
GROUP BY f.id_funcionario, f.nomeFuncionario, c.nomeCategoria;


----Com uso de HAVING
/*Listar os funcionários que começaram a trabalhar na empresa a partir 01.01.2020.*/
SELECT nomeFuncionario, dataInicio
FROM funcionario
GROUP BY nomeFuncionario, dataInicio
HAVING dataInicio >= '2020-01-01'


--Com o uso de Subquerie sem correlação
/*Listar o nomes dos empregados que pertencem á categoria Padeiro*/
SELECT nomeFuncionario
FROM funcionario
WHERE id_funcionario IN (
    SELECT id_funcionario
    FROM funcionario_categoria
    WHERE id_categoria = 1);


--Com o uso de subquerie com correlação
/*Listar os nomes dos empregados que ja foram supervisores*/
SELECT nomeFuncionario
FROM funcionario
WHERE id_funcionario IN (
    SELECT id_funcionario_supervisor
    FROM supervisor
    WHERE id_funcionario_supervisor = funcionario.id_funcionario);


---- 2 VISTAS
/*Crie uma vista que mostre os empregados bem como os seus respetivos supervisores*/
CREATE VIEW vw_empregados_supervisores AS
SELECT f.nomeFuncionario AS empregado, s.nomeFuncionario AS supervisor
FROM Funcionario f
JOIN Supervisor sp ON f.id_funcionario = sp.id_funcionario_supervisionado
JOIN Funcionario s ON sp.id_funcionario_supervisor = s.id_funcionario;

SELECT * FROM vw_empregados_supervisores;


/*Criar uma view que liste os produtos e os seus ingredientes*/
CREATE VIEW IngredientesDosProdutos AS
SELECT p.nomeProduto, i.nomeIngrediente
FROM produtos p
INNER JOIN produto_ingrediente ON p.id_produto = produto_ingrediente.id_produto
INNER JOIN ingrediente i ON produto_ingrediente.id_ingrediente = i.id_ingrediente;

SELECT * FROM IngredientesDosProdutos;