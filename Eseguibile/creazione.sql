DROP SCHEMA IF EXISTS proj;
CREATE SCHEMA proj;
USE proj;

-- Creazione delle tabelle
CREATE TABLE Utente (
    ID_Utente INT auto_increment PRIMARY KEY,
    Nome VARCHAR(100) not null,
    Cognome VARCHAR(100) not null,
    Psw VARCHAR(255) not null,
    DataRegistrazione DATE not null,
    Email VARCHAR(255) unique not null,
    Username VARCHAR(100) unique not null,
    DataNascita DATE not null,
    Telefono VARCHAR(15) not null,
    CHECK ( email LIKE '%_@_%._%' )
);

CREATE TABLE Indirizzo (
    Via VARCHAR(255),
    Civico VARCHAR(10),
    CAP VARCHAR(5),
    Citta VARCHAR(100),
    Prov VARCHAR(2), #sigla della provincia
    ID_Utente INT,
	foreign key (ID_Utente) references Utente(ID_Utente) on update cascade on delete cascade,
    primary key (Via, Civico, CAP, Citta, Prov, ID_Utente) 
);

CREATE TABLE Ordine (
    ID_Ordine INT auto_increment PRIMARY KEY,
    DataAcquisto DATE,
    ID_Utente INT not null,
    FOREIGN KEY (ID_Utente) REFERENCES Utente(ID_Utente)
);

CREATE TABLE Sviluppatore (
    ID_Sviluppatore INT PRIMARY KEY,
    Nome VARCHAR(255) not null,
    Email VARCHAR(255) unique not null,
    PIVA VARCHAR(20) not null,
    Telefono VARCHAR(15) not null,
    Via VARCHAR(255),
    Civico VARCHAR(10),
    CAP VARCHAR(10),
    Citta VARCHAR(100),
    Prov VARCHAR(50)
);

CREATE TABLE Videogioco (
    ID_Videogioco INT auto_increment PRIMARY KEY,
    Nome VARCHAR(255) not null,
    StudioSviluppatore INT not null,
    Prezzo DECIMAL(10, 2) not null,
    DataRilascio DATE,
    MediaRecensioni DECIMAL(3, 2),
    num_recensioni INT not null default '0',
    Genere VARCHAR(100),
    foreign key (StudioSviluppatore) references Sviluppatore(ID_Sviluppatore)
);

CREATE TABLE Abbonamento (
    ID_Abbonamento INT auto_increment PRIMARY KEY,
    Tipologia VARCHAR(50) not null,
    CostoMensile DECIMAL(10, 2) not null
);

CREATE TABLE Recensione (
    ID_Recensione INT auto_increment PRIMARY KEY,
    ID_Utente INT not null,
    ID_Videogioco INT not null,
    Punteggio INT not null CHECK (Punteggio >= 1 AND Punteggio <= 5),
    Commento TEXT,
    DataRecensione DATE not null,
    FOREIGN KEY (ID_Utente) REFERENCES Utente(ID_Utente),
    FOREIGN KEY (ID_Videogioco) REFERENCES Videogioco(ID_Videogioco)
);

CREATE TABLE Sottoscrizione (
    ID_Utente INT not null,
    ID_Abbonamento INT not null,
    DataInizio DATE not null,
    DataFine DATE not null,
    PRIMARY KEY (ID_Utente, ID_Abbonamento),
    FOREIGN KEY (ID_Utente) REFERENCES Utente(ID_Utente),
    FOREIGN KEY (ID_Abbonamento) REFERENCES Abbonamento(ID_Abbonamento),
    UNIQUE (ID_Utente), -- assicura RV1
    CHECK (DATEDIFF(DataFine, DataInizio) >= 30) -- assicura RV6
);

CREATE TABLE Inclusione (
    ID_Abbonamento INT not null,
    ID_Videogioco INT not null,
    PRIMARY KEY (ID_Abbonamento, ID_Videogioco),
    FOREIGN KEY (ID_Abbonamento) REFERENCES Abbonamento(ID_Abbonamento),
    FOREIGN KEY (ID_Videogioco) REFERENCES Videogioco(ID_Videogioco)
);

CREATE TABLE Composizione (
    ID_Ordine INT not null,
    ID_Videogioco INT not null,
    PRIMARY KEY (ID_Ordine, ID_Videogioco),
    FOREIGN KEY (ID_Ordine) REFERENCES Ordine(ID_Ordine),
    FOREIGN KEY (ID_Videogioco) REFERENCES Videogioco(ID_Videogioco)
);

