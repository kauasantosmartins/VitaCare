-- Execute no SQL Server Management Studio (SSMS), na instancia SQLEXPRESS.
-- Cria apenas se ainda nao existir. Nao apaga dados anteriores.
IF DB_ID(N'saude') IS NULL
    CREATE DATABASE saude;
GO
USE saude;
GO

IF OBJECT_ID(N'dbo.paciente', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.paciente (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    nascimento DATE NULL,
    objetivo VARCHAR(100) NULL,
    telefone VARCHAR(30) NULL
);
END;
GO

IF OBJECT_ID(N'dbo.profissional', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.profissional (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(60) NULL,
    registro VARCHAR(30) NULL,
    email VARCHAR(120) NULL
);
END;
GO

IF OBJECT_ID(N'dbo.consulta', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.consulta (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        data DATE NULL,
        motivo VARCHAR(150) NULL,
        paciente_id BIGINT NOT NULL,
        profissional_id BIGINT NOT NULL,
        CONSTRAINT FK_consulta_paciente FOREIGN KEY (paciente_id)
            REFERENCES dbo.paciente(id) ON DELETE CASCADE,
        CONSTRAINT FK_consulta_profissional FOREIGN KEY (profissional_id)
            REFERENCES dbo.profissional(id) ON DELETE CASCADE
    );
END;
GO

IF OBJECT_ID(N'dbo.habito', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.habito (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        tipo VARCHAR(60) NOT NULL,
        minutos INT NULL,
        data DATE NULL,
        paciente_id BIGINT NOT NULL,
        CONSTRAINT FK_habito_paciente FOREIGN KEY (paciente_id)
            REFERENCES dbo.paciente(id) ON DELETE CASCADE
    );
END;
GO
