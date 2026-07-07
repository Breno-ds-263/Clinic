-- ======================================================
-- BANCO DE DADOS
-- ======================================================

CREATE DATABASE IF NOT EXISTS clinic;
USE clinic;

-- ======================================================
-- PACIENTES
-- ======================================================

CREATE TABLE pacientes (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(255),
    contato VARCHAR(100),
    plano_saude VARCHAR(100)
);

-- ======================================================
-- MÉDICOS
-- ======================================================

CREATE TABLE medicos (
    crm VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(100),
    contato VARCHAR(100)
);

-- ======================================================
-- CONSULTAS
-- ======================================================

CREATE TABLE consultas (

    codigo INT AUTO_INCREMENT PRIMARY KEY,

    data_hora_volta DATETIME,

    observacao VARCHAR(255),

    cpf_paciente VARCHAR(14) NOT NULL,

    crm_medico VARCHAR(20) NOT NULL,

    CONSTRAINT fk_consulta_paciente
        FOREIGN KEY (cpf_paciente)
        REFERENCES pacientes(cpf)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_consulta_medico
        FOREIGN KEY (crm_medico)
        REFERENCES medicos(crm)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ======================================================
-- PRONTUÁRIOS
-- ======================================================

CREATE TABLE prontuarios (

    codigo INT AUTO_INCREMENT PRIMARY KEY,

    descricao VARCHAR(255),

    observacao VARCHAR(255),

    codigo_consulta INT NOT NULL UNIQUE,

    CONSTRAINT fk_prontuario_consulta
        FOREIGN KEY (codigo_consulta)
        REFERENCES consultas(codigo)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ======================================================
-- RECEITUÁRIOS
-- ======================================================

CREATE TABLE receituarios (

    codigo INT AUTO_INCREMENT PRIMARY KEY,

    observacao VARCHAR(255),

    codigo_prontuario INT NOT NULL UNIQUE,

    CONSTRAINT fk_receituario_prontuario
        FOREIGN KEY (codigo_prontuario)
        REFERENCES prontuarios(codigo)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ======================================================
-- MEDICAMENTOS
-- ======================================================

CREATE TABLE medicamentos (

    codigo INT AUTO_INCREMENT PRIMARY KEY,

    nome VARCHAR(100) NOT NULL,

    dosagem INT NOT NULL,

    tipo_dosagem VARCHAR(30),

    descricao VARCHAR(255),

    observacao VARCHAR(255)
);

-- ======================================================
-- ITENS DO RECEITUÁRIO
-- ======================================================

CREATE TABLE itens_receituario (

    codigo INT AUTO_INCREMENT PRIMARY KEY,

    dosagem INT NOT NULL,

    intervalo_entre_doses INT NOT NULL,

    observacao VARCHAR(255),

    codigo_receituario INT NOT NULL,

    codigo_medicamento INT NOT NULL,

    CONSTRAINT fk_item_receituario
        FOREIGN KEY (codigo_receituario)
        REFERENCES receituarios(codigo)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_item_medicamento
        FOREIGN KEY (codigo_medicamento)
        REFERENCES medicamentos(codigo)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ======================================================
-- EXAMES
-- ======================================================

CREATE TABLE exames (

    codigo INT AUTO_INCREMENT PRIMARY KEY,

    observacao VARCHAR(255),

    codigo_prontuario INT NOT NULL,

    CONSTRAINT fk_exame_prontuario
        FOREIGN KEY (codigo_prontuario)
        REFERENCES prontuarios(codigo)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ======================================================
-- INDICADORES DE EXAME
-- ======================================================

CREATE TABLE indicadores_exame (

    codigo INT AUTO_INCREMENT PRIMARY KEY,

    indicador VARCHAR(100) NOT NULL,

    descricao VARCHAR(255),

    min_valor_referencia DOUBLE NOT NULL,

    max_valor_referencia DOUBLE NOT NULL
);

-- ======================================================
-- ITENS DO EXAME
-- ======================================================

CREATE TABLE itens_exame (

    codigo INT AUTO_INCREMENT PRIMARY KEY,

    valor_indicador VARCHAR(100) NOT NULL,

    observacao VARCHAR(255),

    codigo_exame INT NOT NULL,

    codigo_indicador INT NOT NULL,

    CONSTRAINT fk_item_exame_exame
        FOREIGN KEY (codigo_exame)
        REFERENCES exames(codigo)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_item_exame_indicador
        FOREIGN KEY (codigo_indicador)
        REFERENCES indicadores_exame(codigo)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);