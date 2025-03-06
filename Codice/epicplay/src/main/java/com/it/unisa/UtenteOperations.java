package com.it.unisa;

import java.sql.*;

public class UtenteOperations {

    // Metodo per registrare un nuovo utente
    public static int registraCliente(String nome, String cognome, String email, String username, String password, Date dataNascita, String telefono) throws SQLException {
        String query = "INSERT INTO Utente (Nome, Cognome, Email, Username, Psw, DataRegistrazione, DataNascita, Telefono) VALUES (?, ?, ?, ?, ?, CURDATE(), ?, ?)";
        try (Connection conn = DBAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Imposta i parametri per l'inserimento
            stmt.setString(1, nome);
            stmt.setString(2, cognome);
            stmt.setString(3, email);
            stmt.setString(4, username);
            stmt.setString(5, password);
            stmt.setDate(6, dataNascita);
            stmt.setString(7, telefono);

            // Esegue l'inserimento
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La registrazione dell'utente non ha avuto successo, nessuna riga interessata.");
            }

            //Recupera l'ID generato
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()){
                if (generatedKeys.next()){
                    int idUtente = generatedKeys.getInt(1);
                    System.out.println("Utente aggiunto con successo! ID: " + idUtente);
                    return idUtente;
                } else {
                    throw new SQLException("La registrazione dell'utente non ha restituito l'ID.");
                }
            }
        }
    }

    // Metodo per registrare l'indirizzo
    public static void registraIndirizzo(String via, String civico, String cap, 
                                         String citta, String provincia, int idUtente) throws SQLException {
        String query = "INSERT INTO Indirizzo (Via, Civico, CAP, Citta, Prov, ID_Utente) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, via);
            stmt.setString(2, civico);
            stmt.setString(3, cap);
            stmt.setString(4, citta);
            stmt.setString(5, provincia);
            stmt.setInt(6, idUtente);

            stmt.executeUpdate();
        }
    }
}
