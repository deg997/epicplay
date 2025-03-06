package com.it.unisa;

import java.awt.GridLayout;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SviluppatoreGUI extends JFrame{
    
    public SviluppatoreGUI(){
        // Configurazione finestra
        setTitle("EpicPlay");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        // Pulsanti
        JButton addGameButton = new JButton("Aggiungi Videogioco");
        JButton esci = new JButton("Esci");

        // Listener per la registrazione
        addGameButton.addActionListener(e -> {
            JTextField nome = new JTextField();
            JTextField prezzo = new JTextField();
            JTextField dataRilascio = new JTextField();
            JTextField genere = new JTextField();
        
            Object[] message = {
                "Nome:", nome,
                "Prezzo:", prezzo,
                "Data di rilascio (YYYY-MM-DD):", dataRilascio,
                "Genere:", genere
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Aggiungi videogioco", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    // Usa il metodo addGame per aggiungere l'utente
                    SviluppatoreOperations.addGame(nome.getText(), SviluppatoreLoginGUI.getSviluppatoreId(), prezzo.getText(), Date.valueOf(dataRilascio.getText()), genere.getText());
                    JOptionPane.showMessageDialog(this, "Videogioco aggiunto con successo!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Errore durante l'aggiunta.");
                }
            }
        });

        // Pulsante per uscire
        esci.addActionListener(e -> System.exit(0));

        panel.add(addGameButton);
        panel.add(esci);
        add(panel);
    }

}
