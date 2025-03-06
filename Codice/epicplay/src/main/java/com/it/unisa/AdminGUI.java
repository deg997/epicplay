package com.it.unisa;

import java.awt.*;
import java.sql.*;
import javax.swing.*;


public class AdminGUI extends JFrame{
    
    public AdminGUI() {
        // Configurazione finestra
        setTitle("EpicPlay");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(8, 1, 10, 10));

        // Menu a tendina per selezionare il videogioco
        JComboBox<String> videogiochiComboBox = new JComboBox<>();

        // Pulsanti
        JButton viewBuyersButton = new JButton("Visualizza Acquirenti");
        JButton weeklyReviewButton = new JButton("Report Valutazioni Settimana");
        JButton reportButton = new JButton("Visualizza Report Videogiochi");
        JButton developerReportButton = new JButton("Visualizza Report Sviluppatori");
        JButton revenueReportButton = new JButton("Visualizza Ricavi Sviluppatori");
        JButton esci = new JButton("Esci");

        // Popola il menu a tendina con i videogiochi disponibili   
        try (Connection conn = DBAccess.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_Videogioco, Nome FROM Videogioco")) {

            while (rs.next()) {
                String videogioco = rs.getInt("ID_Videogioco") + " - " + rs.getString("Nome");
                videogiochiComboBox.addItem(videogioco);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante il recupero dei videogiochi.");
        }

        // Listener del pulsante viewBuyers
        viewBuyersButton.addActionListener(e -> {
            String selectedItem = (String) videogiochiComboBox.getSelectedItem();
            if (selectedItem != null) {
                int idVideogioco = Integer.parseInt(selectedItem.split(" - ")[0]);

                // Query per ottenere gli acquirenti
                String query = "SELECT U.Nome, U.Cognome, U.Email FROM Utente U JOIN Ordine O ON U.ID_Utente = O.ID_Utente JOIN Composizione C ON O.ID_Ordine = C.ID_Ordine WHERE C.ID_Videogioco = ?";

                try (Connection conn = DBAccess.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setInt(1, idVideogioco);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (!rs.isBeforeFirst()) { // Controlla se il ResultSet è vuoto
                            JOptionPane.showMessageDialog(null, "Nessun utente ha acquistato questo videogioco.");
                        } else {
                            StringBuilder result = new StringBuilder("Acquirenti:\n\n");
                            while (rs.next()) {
                                result.append(rs.getString("Nome"))
                                    .append(" ")
                                    .append(rs.getString("Cognome"))
                                    .append(" - Email: ")
                                    .append(rs.getString("Email"))
                                    .append("\n");
                            }
                            JOptionPane.showMessageDialog(null, result.toString(), "Acquirenti", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Errore durante il recupero degli acquirenti.");
                    }
            }
        });

        //Listener per il report dei videogiochi
        reportButton.addActionListener(e -> {
            String query = "SELECT V.Nome, V.Genere, V.Prezzo, V.DataRilascio, IFNULL(AVG(R.Punteggio), 0) AS MediaRecensioni FROM Videogioco V LEFT JOIN Recensione R ON V.ID_Videogioco = R.ID_Videogioco GROUP BY V.ID_Videogioco ORDER BY MediaRecensioni DESC;";
        
            try (Connection conn = DBAccess.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
        
                if (!rs.isBeforeFirst()) { // Controlla se ci sono risultati
                    JOptionPane.showMessageDialog(null, "Nessun videogioco trovato.");
                    return;
                }
        
                // Costruisce il report
                StringBuilder report = new StringBuilder("Report Videogiochi:\n\n");
                while (rs.next()) {
                    String nome = rs.getString("Nome");
                    String genere = rs.getString("Genere");
                    double prezzo = rs.getDouble("Prezzo");
                    Date dataRilascio = rs.getDate("DataRilascio");
                    double mediaRecensioni = rs.getDouble("MediaRecensioni");
        
                    report.append(String.format("Nome: %s\nGenere: %s\nPrezzo: %.2f€\nData Rilascio: %s\nMedia Recensioni: %.2f\n\n",
                            nome, genere, prezzo, dataRilascio, mediaRecensioni));
                }
        
                // Mostra il report in una finestra
                JTextArea textArea = new JTextArea(report.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 400));
        
                JOptionPane.showMessageDialog(null, scrollPane, "Report Videogiochi", JOptionPane.PLAIN_MESSAGE);
        
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante il recupero del report.");
            }
        });

        //Listener per il report degli sviluppatori
        developerReportButton.addActionListener(e -> {
            String query = "SELECT S.Nome AS NomeSviluppatore, S.Email, S.Telefono, V.Nome AS NomeVideogioco, COUNT(C.ID_Videogioco) AS VideogiochiVenduti FROM Sviluppatore S LEFT JOIN Videogioco V ON S.ID_Sviluppatore = V.StudioSviluppatore LEFT JOIN Composizione C ON V.ID_Videogioco = C.ID_Videogioco GROUP BY S.ID_Sviluppatore, V.ID_Videogioco ORDER BY S.Nome, VideogiochiVenduti DESC;";
        
            try (Connection conn = DBAccess.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
        
                if (!rs.isBeforeFirst()) { // Controlla se ci sono risultati
                    JOptionPane.showMessageDialog(null, "Nessuno sviluppatore trovato.");
                    return;
                }
        
                // Costruisce il report
                StringBuilder report = new StringBuilder("Report Sviluppatori:\n\n");
                String lastDeveloper = "";
                while (rs.next()) {
                    String nomeSviluppatore = rs.getString("NomeSviluppatore");
                    String email = rs.getString("Email");
                    String telefono = rs.getString("Telefono");
                    String nomeVideogioco = rs.getString("NomeVideogioco");
                    int vendite = rs.getInt("VideogiochiVenduti");
        
                    // Nuovo sviluppatore
                    if (!nomeSviluppatore.equals(lastDeveloper)) {
                        if (!lastDeveloper.isEmpty()) {
                            report.append("\n"); // Separatore tra sviluppatori
                        }
                        report.append("Sviluppatore: ").append(nomeSviluppatore)
                              .append("\nEmail: ").append(email)
                              .append("\nTelefono: ").append(telefono)
                              .append("\nVideogiochi:\n");
                        lastDeveloper = nomeSviluppatore;
                    }
        
                    // Dettagli del videogioco
                    report.append("  - ").append(nomeVideogioco != null ? nomeVideogioco : "Nessun videogioco")
                          .append(" (").append(vendite).append(" vendite)\n");
                }
        
                // Mostra il report in una finestra
                JTextArea textArea = new JTextArea(report.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 400));
        
                JOptionPane.showMessageDialog(null, scrollPane, "Report Sviluppatori", JOptionPane.PLAIN_MESSAGE);
        
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante il recupero del report.");
            }
        });

        //Listener per il report sui ricavi
        revenueReportButton.addActionListener(e -> {
            String query = "SELECT S.Nome AS NomeSviluppatore, S.Email, SUM(V.Prezzo * (SELECT COUNT(*) FROM Composizione C WHERE C.ID_Videogioco = V.ID_Videogioco)) AS Ricavi FROM Sviluppatore S LEFT JOIN Videogioco V ON S.ID_Sviluppatore = V.StudioSviluppatore GROUP BY S.ID_Sviluppatore ORDER BY Ricavi DESC;";
        
            try (Connection conn = DBAccess.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
        
                if (!rs.isBeforeFirst()) { // Controlla se ci sono risultati
                    JOptionPane.showMessageDialog(null, "Nessuno sviluppatore trovato.");
                    return;
                }
        
                // Costruisce il report
                StringBuilder report = new StringBuilder("Report Ricavi Sviluppatori:\n\n");
                while (rs.next()) {
                    String nomeSviluppatore = rs.getString("NomeSviluppatore");
                    String email = rs.getString("Email");
                    double ricavi = rs.getDouble("Ricavi");
        
                    report.append(String.format("Sviluppatore: %s\nEmail: %s\nRicavi Totali: %.2f€\n\n",
                            nomeSviluppatore, email, ricavi));
                }
        
                // Mostra il report in una finestra
                JTextArea textArea = new JTextArea(report.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 400));
        
                JOptionPane.showMessageDialog(null, scrollPane, "Report Ricavi Sviluppatori", JOptionPane.PLAIN_MESSAGE);
        
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante il recupero del report.");
            }
        });

        //Listener per il report delle valutazioni dell'ultima settimana
        weeklyReviewButton.addActionListener(e -> {
            String query = "SELECT DISTINCT U.Nome, U.Cognome, U.Email FROM Utente U JOIN Recensione R ON U.ID_Utente = R.ID_Utente JOIN Videogioco V ON R.ID_Videogioco = V.ID_Videogioco WHERE R.DataRecensione >= CURDATE() - INTERVAL 7 DAY AND R.Punteggio < V.MediaRecensioni AND V.ID_Videogioco = ?;";
        
            // Ottiene il videogioco selezionato dal menu a tendina
            String selectedItem = (String) videogiochiComboBox.getSelectedItem();
            if (selectedItem == null) {
                JOptionPane.showMessageDialog(null, "Seleziona un videogioco dal menu a tendina.");
                return;
            }
        
            int idVideogioco = Integer.parseInt(selectedItem.split(" - ")[0]);
        
            try (Connection conn = DBAccess.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
        
                stmt.setInt(1, idVideogioco); // Imposta l'ID del videogioco
        
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.isBeforeFirst()) { // Controlla se ci sono risultati
                        JOptionPane.showMessageDialog(null, "Nessun utente ha effettuato valutazioni inferiori alla media per questo videogioco nell'ultima settimana.");
                        return;
                    }
        
                    // Costruisce il report
                    StringBuilder report = new StringBuilder("Utenti con Valutazioni Inferiori alla Media (Ultima Settimana):\n\n");
                    while (rs.next()) {
                        String nome = rs.getString("Nome");
                        String cognome = rs.getString("Cognome");
                        String email = rs.getString("Email");
        
                        report.append(String.format("Nome: %s %s\nEmail: %s\n\n", nome, cognome, email));
                    }
        
                    // Mostra il report in una finestra
                    JTextArea textArea = new JTextArea(report.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 400));
        
                    JOptionPane.showMessageDialog(null, scrollPane, "Report Valutazioni Settimana", JOptionPane.PLAIN_MESSAGE);
        
                }
        
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante il recupero del report.");
            }
        });

        // Pulsante per uscire
        esci.addActionListener(e -> System.exit(0));

        adminPanel.add(new JLabel("Seleziona un videogioco:"));
        adminPanel.add(videogiochiComboBox);
        adminPanel.add(viewBuyersButton);
        adminPanel.add(weeklyReviewButton);
        adminPanel.add(reportButton);
        adminPanel.add(developerReportButton);
        adminPanel.add(revenueReportButton);
        adminPanel.add(esci);

        add(adminPanel);
    }

}
