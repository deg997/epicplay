package com.it.unisa;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SviluppatoreLoginGUI extends JFrame{

    private static int sviluppatoreID;

    public SviluppatoreLoginGUI() {
        // Configurazione finestra
        setTitle("EpicPlay");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        // Pulsanti
        JButton loginButton = new JButton("Accedi");
        JButton registerButton = new JButton("Registrati");
        JButton addGameButton = new JButton("Aggiungi Videogioco");
        JButton esci = new JButton("Esci");

        // Listener per il pulsante di login
        loginButton.addActionListener(e -> {
            JTextField nameField = new JTextField();

            Object[] message = {
                "Nome Studio Sviluppatore:", nameField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Accedi", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String username = nameField.getText();

                // Controlla le credenziali nel database
                try (Connection conn = DBAccess.getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Sviluppatore WHERE Nome = ?")) {
                    stmt.setString(1, username);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        PreparedStatement stmtId = conn.prepareStatement("SELECT ID_Sviluppatore FROM Sviluppatore WHERE Nome = ?");
                        stmtId.setString(1, username);
                        ResultSet rs2 = stmtId.executeQuery();

                        if (rs2.next()){
                            int idSviluppatore = rs2.getInt("ID_Sviluppatore");
                            SviluppatoreLoginGUI.setSviluppatoreId(idSviluppatore);
                            System.out.println(SviluppatoreLoginGUI.getSviluppatoreId());
                        }

                        JOptionPane.showMessageDialog(this, "Accesso riuscito!");
                        this.dispose(); // Chiude la finestra attuale
                        new SviluppatoreGUI().setVisible(true); // Mostra il menu per sviluppatori registrati
                    } else {
                        JOptionPane.showMessageDialog(this, "Accesso negato. Studio Sviluppatore non registrato.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Errore durante l'accesso al database.");
                }
            }
        });

        // Listener per la registrazione
        registerButton.addActionListener(e -> {
            JTextField nome = new JTextField();
            JTextField email = new JTextField();
            JTextField piva = new JTextField();
            JTextField telefono = new JTextField();
            JTextField via = new JTextField();
            JTextField civico = new JTextField();
            JTextField cap = new JTextField();
            JTextField citta = new JTextField();
            JTextField prov = new JTextField();

            Object[] message = {
                "Nome:", nome,
                "Email:", email,
                "Partita IVA:", piva,
                "Telefono:", telefono,
                "Via:", via,
                "Civico", civico,
                "CAP:", cap,
                "Città: ", citta,
                "Provincia: ", prov
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Registrazione", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    // Usa il metodo registraSviluppatore per aggiungere l'utente
                    SviluppatoreOperations.registraSviluppatore(
                            nome.getText(), email.getText(), piva.getText(),
                            telefono.getText(), via.getText(), civico.getText(), 
                            cap.getText(), citta.getText(), prov.getText()
                    );
                    JOptionPane.showMessageDialog(this, "Registrazione completata con successo!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Errore durante la registrazione.");
                }
            }
        });

        // Pulsante per uscire
        esci.addActionListener(e -> System.exit(0));

        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(addGameButton);
        panel.add(esci);
        add(panel);
    }

    public static int getSviluppatoreId(){ return sviluppatoreID; }
    public static void setSviluppatoreId(int id){ sviluppatoreID = id; }

}
