package com.it.unisa;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SviluppatoreOperations {
    
    // Metodo per registrare uno Sviluppatore
    public static void registraSviluppatore(String nome, String email, String piva, String telefono, String via, String civico, String cap, String citta, String prov) throws SQLException {
        String query = "INSERT INTO Utente (Nome, Email, PIVA, Telefono, Via, DataNascita, Civico, CAP, Citta, Prov) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Imposta i parametri per l'inserimento
            stmt.setString(1, nome);
            stmt.setString(3, email);
            stmt.setString(4, piva);
            stmt.setString(5, telefono);
            stmt.setString(6, via);
            stmt.setString(7, civico);
            stmt.setString(8, cap);
            stmt.setString(9, citta);
            stmt.setString(10, prov);

            // Esegue l'inserimento
            stmt.executeUpdate();
            System.out.println("Sviluppatore aggiunto con successo!");
        }
    }

    // Metodo per registrare un nuovo videogioco
    public static void addGame(String nome, int sviluppatoreId, String prezzo, Date dataRilascio, String genere) throws SQLException {
        String query = "INSERT INTO Videogioco (Nome, StudioSviluppatore, Prezzo, DataRilascio, Genere) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Imposta i parametri per l'inserimento
            stmt.setString(1, nome);
            stmt.setInt(2, sviluppatoreId);
            stmt.setString(3, prezzo);
            stmt.setDate(4, dataRilascio);
            stmt.setString(5, genere);

            // Esegue l'inserimento
            stmt.executeUpdate();
            System.out.println("Videogioco aggiunto con successo!");
        }
    }
}
