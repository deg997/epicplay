USE proj;

-- tabella Utente
INSERT INTO Utente (Nome, Cognome, Psw, DataRegistrazione, Email, Username, DataNascita, Telefono) VALUES 
('Marco', 'Rossi', 'Marco2025!', '2025-01-02', 'marco.rossi@hotmail.com', 'marcorossi95', '1995-04-12', '3391234567'),
('Giulia', 'Bianchi', 'Giulia!2025', '2025-01-01', 'giulia.bianchi@gmail.com', 'giuliabianchi', '1992-08-23', '3389876543'),
('Luca', 'Verdi', 'LucaVerdi@123', '2024-12-15', 'luca.verdi@yahoo.com', 'lucaverdi92', '1992-02-19', '3397654321'),
('Sara', 'Neri', 'Sara1234!', '2024-12-30', 'sara.neri@libero.it', 'sara.neri', '1997-11-08', '3476543210'),
('Francesco', 'Gialli', 'Francesco!2019', '2024-11-05', 'francesco.gialli@outlook.com', 'fra_gialli', '1993-03-22', '3361234567'),
('Anna', 'Marini', 'Anna@4567', '2024-10-18', 'anna.marini@hotmail.com', 'anna_marini', '1991-06-11', '3339876543'),
('Matteo', 'Ferri', 'Matteo1234#', '2024-12-10', 'matteo.ferri@gmail.com', 'matteo_ferri', '1994-05-25', '3458765432'),
('Elena', 'Rossi', 'ElenaRossi!92', '2024-09-22', 'elena.rossi@tiscali.it', 'elena_rossi', '1992-07-13', '3441234567'),
('Andrea', 'Conti', 'Andrea#2024', '2024-08-30', 'andrea.conti@libero.it', 'andrea.conti', '1989-01-17', '3365432109'),
('Giovanni', 'Bellucci', 'GiovanniB123!', '2024-07-19', 'giovanni.bellucci@outlook.com', 'giovanni_bellucci', '1990-10-29', '3394321098'),
('Martina', 'Santoro', 'Martina2023#', '2024-12-01', 'martina.santoro@gmail.com', 'martina_santoro', '1996-09-04', '3444321098'),
('Francesca', 'Leone', 'FrancescaLeone2025', '2024-12-28', 'francesca.leone@hotmail.com', 'francesca.leone', '1995-04-30', '3386758492'),
('Paolo', 'Russo', 'Paolo2025!@', '2024-11-15', 'paolo.russo@yahoo.com', 'paolo.russo', '1993-12-05', '3398765431'),
('Laura', 'Ferrari', 'LauraFerrari#1990', '2024-10-07', 'laura.ferrari@libero.it', 'laura_ferrari', '1990-03-18', '3344321098'),
('Stefano', 'Tassi', 'StefanoT@2025', '2024-09-10', 'stefano.tassi@tiscali.it', 'stefano.tassi', '1992-04-25', '3454321098'),
('Michele', 'Bruni', 'MicheleBruni!98', '2024-08-23', 'michele.bruni@gmail.com', 'michele.bruni', '1998-05-03', '3385432109'),
('Valentina', 'Moretti', 'Valentina!_2025', '2024-07-17', 'valentina.moretti@outlook.com', 'valentina_moretti', '1994-09-10', '3478765431'),
('Riccardo', 'Pinto', 'RiccardoP!@2025', '2024-06-05', 'riccardo.pinto@yahoo.com', 'riccardo_pinto', '1990-01-10', '3391230987'),
('Lucia', 'Vitali', 'Lucia2024@#', '2024-12-25', 'lucia.vitali@hotmail.com', 'lucia_vitali', '1996-11-17', '3345678901'),
('Simone', 'Della', 'SimoneDella_2025', '2024-11-22', 'simone.della@libero.it', 'simone_della', '1997-10-15', '3387654321');

-- tabella Indirizzo
INSERT INTO Indirizzo (Via, Civico, CAP, Citta, Prov, ID_Utente) VALUES
('Via Roma', '12', '00100', 'Roma', 'RM', 1),
('Viale Guglielmo Marconi', '5', '20100', 'Milano', 'MI', 2),
('Corso Vittorio Emanuele', '77', '80100', 'Napoli', 'NA', 3),
('Via Dante Alighieri', '22', '10121', 'Torino', 'TO', 4),
('Piazza del Duomo', '1', '50100', 'Firenze', 'FI', 5),
('Via San Giovanni', '34', '90100', 'Palermo', 'PA', 6),
('Viale Umberto I', '3', '70100', 'Bari', 'BA', 7),
('Via Cavour', '10', '40100', 'Bologna', 'BO', 8),
('Piazza San Marco', '15', '30100', 'Venezia', 'VE', 9),
('Via Garibaldi', '25', '16100', 'Genova', 'GE', 10),
('Lungomare Caracciolo', '45', '80121', 'Napoli', 'NA', 11),
('Corso Italia', '89', '80067', 'Sorrento', 'NA', 12),
('Via Nazionale', '56', '07041', 'Alghero', 'SS', 13),
('Via Roma', '101', '09100', 'Cagliari', 'CA', 14),
('Piazza della Repubblica', '3', '53034', 'Colle di Val d\'Elsa', 'SI', 15),
('Viale dei Mille', '28', '56100', 'Pisa', 'PI', 16),
('Via Trieste', '2', '34100', 'Trieste', 'TS', 17),
('Via delle Magnolie', '8', '29010', 'Piacenza', 'PC', 18),
('Corso di Porta Romana', '10', '20122', 'Milano', 'MI', 19),
('Piazza del Popolo', '9', '06034', 'Foligno', 'PG', 20),
('Viale Guglielmo Marconi', '10', '20100', 'Milano', 'MI', 2),
('Via Monti Tiburtini', '23', '00159', 'Roma', 'RM', 1),
('Corso Francia', '50', '10100', 'Torino', 'TO', 4),
('Via Giuseppe Mazzini', '31', '80045', 'Pompei', 'NA', 3),
('Via Garibaldi', '11', '10121', 'Torino', 'TO', 4);

-- tabella Ordine
INSERT INTO Ordine (DataAcquisto, ID_Utente) VALUES
('2025-01-02', 1),
('2024-12-28', 2),
('2024-11-15', 3),
('2024-10-25', 4),
('2024-09-10', 5),
('2024-08-30', 6),
('2024-07-05', 7),
('2024-06-18', 8),
('2024-05-10', 9),
('2024-04-22', 10);

-- tabella Sviluppatore
INSERT INTO Sviluppatore (ID_Sviluppatore, Nome, Email, PIVA, Telefono, Via, Civico, CAP, Citta, Prov) VALUES
(1, 'TechVision Ltd.', 'info@techvision.com', 'IT12345678901', '0234123456', 'Via dei Mille', '10', '10100', 'Torino', 'TO'),
(2, 'Pixel Creators', 'contact@pixelcreators.it', 'IT23456789012', '0245678901', 'Viale Guglielmo Marconi', '15', '20100', 'Milano', 'MI'),
(3, 'GameDev Studios', 'support@gamedevstudios.com', 'IT34567890123', '0334567890', 'Corso Italia', '30', '80100', 'Napoli', 'NA'),
(4, 'CodeWorks', 'hello@codeworks.it', 'IT45678901234', '0345678901', 'Piazza del Duomo', '5', '50100', 'Firenze', 'FI'),
(5, 'DigitalCraft', 'info@digitalcraft.com', 'IT56789012345', '0356789012', 'Via San Giovanni', '20', '90100', 'Palermo', 'PA');

-- tabella Videogioco
INSERT INTO Videogioco (Nome, StudioSviluppatore, Prezzo, DataRilascio, Genere) VALUES 
('Galaxy Adventures', 1, 59.99, '2024-06-15', 'Avventura'),
('Pixel Quest', 1, 49.99, '2024-08-20', 'RPG'),
('Dream Scape', 2, 39.99, '2024-05-10', 'Piattaforme'),
('Skyward Horizon', 2, 64.99, '2024-09-05', 'Azione'),
('Dungeon Masters', 2, 44.99, '2024-04-25', 'GDR'),
('Starship Odyssey', 3, 69.99, '2024-07-30', 'Simulazione'),
('City Builder Tycoon', 3, 29.99, '2024-03-12', 'Strategia'),
('Viking Legacy', 3, 59.99, '2024-11-10', 'Azione'),
('Speed Racer X', 4, 39.99, '2024-10-01', 'Corse'),
('Medieval Battle', 4, 54.99, '2024-02-18', 'Azione');

-- tabella Abbonamento
INSERT INTO Abbonamento (Tipologia, CostoMensile) VALUES
('Base', 9.99),
('Premium', 19.99);

-- tabella Recensioni (con aggiornamento dell'attributo MediaRecensioni all'interno di Videogioco)
INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (1, 1, 4, 'Un gioco molto divertente, ma alcuni livelli sono troppo difficili.', '2024-07-01');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 4)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=1;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (1, 2, 3, 'Nel complesso carino, ma ho trovato la trama un po\' prevedibile.', '2024-08-15');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 3)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=2;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (2, 3, 5, 'Adoro questo gioco! La grafica è fantastica e il gameplay è coinvolgente.', '2024-05-12');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 5)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=3;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (2, 4, 4, 'Ottimo gioco, ma un po\' ripetitivo. Mi piacerebbe più varietà nei nemici.', '2024-09-03');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 4)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=4;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (3, 5, 2, NULL, '2024-06-20');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 2)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=5;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (3, 6, 5, 'Un capolavoro! Ho passato ore a giocarci, davvero un’esperienza unica.', '2024-07-15');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 5)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=6;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (4, 7, 4, NULL, '2024-05-25');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 4)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=7;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (5, 8, 5, 'Fantastico! Storia avvincente e meccaniche di gioco ben bilanciate.', '2024-01-10');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 5)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=8;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (5, 9, 3, NULL, '2024-04-02');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 3)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=9;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (6, 10, 2, NULL, '2024-03-01');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 2)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=10;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (7, 5, 5, 'Un gioco magnifico, ogni dettaglio è curato con attenzione. Lo consiglio a tutti.', '2024-07-18');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 5)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=5;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (8, 8, 3, NULL, '2024-06-25');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 3)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=8;
    
INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (9, 1, 4, 'Molto bello, ma alcune missioni sono un po\' troppo lunghe. Comunque consigliato.', '2024-07-20');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 3)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=1;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (10, 2, 5, 'Assolutamente fantastico! Mi ha preso fin da subito. Una delle migliori esperienze di gioco.', '2024-09-02');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 5)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=2;

INSERT INTO Recensione (ID_Utente, ID_Videogioco, Punteggio, Commento, DataRecensione) VALUES (10, 9, 3, NULL, '2024-02-22');
UPDATE Videogioco SET MediaRecensioni=(MediaRecensioni * num_recensioni + 5)/(num_recensioni + 1),
	num_recensioni=num_recensioni+1 WHERE Videogioco.ID_Videogioco=9;
    
-- Aggiunta di alcuni videogiochi agli abbonamenti
INSERT INTO Inclusione (ID_Abbonamento, ID_Videogioco) VALUES (1, 1), (1, 2), (2, 3), (2, 4), (2, 5);

-- Aggiunta dei videogiochi agli ordini
INSERT INTO Composizione (ID_Ordine, ID_Videogioco) VALUES (1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6), (4, 7), (5, 8), (5, 9), (6, 10), (7, 5), (8, 8), (9, 1), (10, 2), (10, 9);

-- Aggiungiamo alcuni abbonamenti attivi
INSERT INTO Sottoscrizione (ID_Utente, ID_Abbonamento, DataInizio, DataFine) VALUES (1, 2, '2025-01-01', '2025-02-01');
INSERT INTO Sottoscrizione (ID_Utente, ID_Abbonamento, DataInizio, DataFine) VALUES (2, 1, '2025-02-01', '2025-03-03');
INSERT INTO Sottoscrizione (ID_Utente, ID_Abbonamento, DataInizio, DataFine) VALUES (3, 2, '2025-03-01', '2025-04-01');
INSERT INTO Sottoscrizione (ID_Utente, ID_Abbonamento, DataInizio, DataFine) VALUES (4, 1, '2025-04-01', '2025-05-01');
INSERT INTO Sottoscrizione (ID_Utente, ID_Abbonamento, DataInizio, DataFine) VALUES (5, 2, '2025-05-01', '2025-06-01');
INSERT INTO Sottoscrizione (ID_Utente, ID_Abbonamento, DataInizio, DataFine) VALUES (6, 1, '2025-06-01', '2025-07-01');
INSERT INTO Sottoscrizione (ID_Utente, ID_Abbonamento, DataInizio, DataFine) VALUES (7, 2, '2025-07-01', '2025-08-01');
INSERT INTO Sottoscrizione (ID_Utente, ID_Abbonamento, DataInizio, DataFine) VALUES (8, 1, '2025-08-01', '2025-09-01');
INSERT INTO Sottoscrizione (ID_Utente, ID_Abbonamento, DataInizio, DataFine) VALUES (9, 2, '2025-09-01', '2025-10-01');
INSERT INTO Sottoscrizione (ID_Utente, ID_Abbonamento, DataInizio, DataFine) VALUES (10, 1, '2025-10-01', '2025-11-01');


