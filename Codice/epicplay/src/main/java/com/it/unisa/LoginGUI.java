package com.it.unisa;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginGUI extends JFrame {

    public LoginGUI() {
        // Configurazione della finestra iniziale
        setTitle("EpicPlay");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creazione del pannello
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        // Pulsanti
        JButton loginButton = new JButton("Accedi");
        JButton registerButton = new JButton("Registrati");
        JButton devSectionButton = new JButton("Sezione Sviluppatori"); 
        JButton exitButton = new JButton("Esci");

        // Listener per il pulsante di login
        loginButton.addActionListener(e -> {
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();

            Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Accedi", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("admin") && password.equals("admin")){
                    JOptionPane.showMessageDialog(this, "Accesso riuscito!");
                        this.dispose(); // Chiude la finestra attuale
                        new AdminGUI().setVisible(true); // Mostra il menu per admin
                } else {
                    // Controlla le credenziali nel database
                    try (Connection conn = DBAccess.getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Utente WHERE Username = ? AND Psw = ?")) {
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        int idUtente = 0;
                        PreparedStatement stmtId = conn.prepareStatement("SELECT ID_Utente FROM Utente WHERE Username = ?");
                        stmtId.setString(1, username);
                        ResultSet rs2 = stmtId.executeQuery();

                        if (rs2.next()){idUtente = rs2.getInt("ID_Utente");}

                        JOptionPane.showMessageDialog(this, "Accesso riuscito!");
                        this.dispose(); // Chiude la finestra attuale
                        new UtenteGUI(idUtente).setVisible(true); // Mostra il menu per utenti registrati
                    } else {
                        JOptionPane.showMessageDialog(this, "Accesso negato. Username o password errati.");
                    }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Errore durante l'accesso al database.");
                    }
                }
            }
        });

        // Listener per la registrazione
        registerButton.addActionListener(e -> {
            JTextField nome = new JTextField();
            JTextField cognome = new JTextField();
            JTextField email = new JTextField();
            JTextField username = new JTextField();
            JPasswordField password = new JPasswordField();
            JTextField dataNascita = new JTextField();
            JTextField telefono = new JTextField();

            //Campi per l'indirizzo
            JTextField via = new JTextField();
            JTextField civico = new JTextField();
            JTextField cap = new JTextField();
            JTextField citta = new JTextField();
            JTextField provincia = new JTextField();

            Object[] message = {
                "Nome:", nome,
                "Cognome:", cognome,
                "Email:", email,
                "Username:", username,
                "Password:", password,
                "Data di nascita (YYYY-MM-DD):", dataNascita,
                "Telefono:", telefono,
                "--- Indirizzo ---",
                "Via:", via,
                "Civico:", civico,
                "CAP:", cap,
                "Città:", citta,
                "Provincia (sigla):", provincia
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Registrazione", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    // Registra il cliente
                    int idUtente = UtenteOperations.registraCliente(
                        nome.getText(), cognome.getText(), email.getText(),
                        username.getText(), new String(password.getPassword()),
                        Date.valueOf(dataNascita.getText()), telefono.getText()
                    );

                    //Registra l'indirizzo associato all'utente
                    UtenteOperations.registraIndirizzo(
                    via.getText(), civico.getText(), cap.getText(), 
                    citta.getText(), provincia.getText(), idUtente
                    );
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Errore durante la registrazione.");
                }
            }
        });

        // Listener per la sezione sviluppatori
        devSectionButton.addActionListener(e -> {
            this.dispose(); // Chiude la finestra attuale
            new SviluppatoreLoginGUI().setVisible(true); // Apre la GUI per sviluppatori
        });

        // Listener per uscire
        exitButton.addActionListener(e -> System.exit(0));

        // Aggiunta dei pulsanti al pannello
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(devSectionButton); 
        panel.add(exitButton);

        // Aggiunta del pannello alla finestra
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginGUI login = new LoginGUI();
            login.setVisible(true);
        });
    }
}
