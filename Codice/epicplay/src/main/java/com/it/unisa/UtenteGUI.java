package com.it.unisa;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UtenteGUI extends JFrame {
    private Carrello cart;
    
    public UtenteGUI(int idUtente) {
        cart = new Carrello(idUtente);
        
        // Configurazione finestra
        setTitle("EpicPlay");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));
        
        // Recupera il numero di ordini effettuati
        String numeroOrdini = "Errore durante il recupero dei dati.";
        try (Connection conn = DBAccess.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS NumeroOrdini FROM Ordine WHERE ID_Utente = ?");) {
            stmt.setInt(1, idUtente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                numeroOrdini = "Numero totale di ordini effettuati: " + rs.getInt("NumeroOrdini");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Label per mostrare il numero di ordini
        JLabel ordersLabel = new JLabel(numeroOrdini, SwingConstants.CENTER);

        // Pulsanti
        JButton viewGamesButton = new JButton("Mostra Videogiochi disponibili");
        JButton viewCartButton = new JButton("Visualizza carrello");
        JButton viewOwnedGamesButton = new JButton("Visualizza Videogiochi Posseduti");
        JButton viewOrdersButton = new JButton("Visualizza Ordini Effettuati");
        JButton subscriptionButton = new JButton("Abbonamento");
        JButton esci = new JButton("Esci");

        // Listener per visualizzare i videogiochi
        viewGamesButton.addActionListener(e -> {
            try (Connection conn = DBAccess.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT ID_Videogioco, Nome, Genere, Prezzo, DataRilascio FROM Videogioco")) {

                // Creazione di un pannello per i videogiochi
                JPanel gamesPanel = new JPanel();
                panel.setLayout(new GridLayout(0, 1, 10, 10)); // Layout dinamico per i bottoni
                
                while (rs.next()) {
                    int idVideogioco = rs.getInt("ID_Videogioco");
                    String nome = rs.getString("Nome");
                    String genere = rs.getString("Genere");
                    double prezzo = rs.getDouble("Prezzo");
                    Date dataRilascio = rs.getDate("DataRilascio");
        
                    // Crea un pulsante con le informazioni del gioco
                    JButton gameButton = new JButton(
                        "<html><b>" + nome + "</b><br>" +
                        "Genere: " + genere + "<br>" +
                        "Prezzo: " + prezzo + "€<br>" +
                        "Rilasciato: " + dataRilascio + "</html>"
                    );

                    // Aggiungi un listener per aggiungere il gioco al carrello
                    gameButton.addActionListener(ev -> {
                        cart.aggiungiAlCarrello(idVideogioco, nome, prezzo); // Funzione che gestisce il carrello
                        JOptionPane.showMessageDialog(null, "Gioco aggiunto al carrello!");
                    });

                    // Aggiunge il pulsante al pannello
                    gamesPanel.add(gameButton);
                }
                
                // Visualizza i giochi in una finestra scorrevole
                JScrollPane scrollPane = new JScrollPane(gamesPanel);
                scrollPane.setPreferredSize(new Dimension(400, 400));
                JOptionPane.showMessageDialog(null, scrollPane, "Videogiochi Disponibili", JOptionPane.PLAIN_MESSAGE);  
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante il recupero dei videogiochi.");
            }
        });
        
        // Visualizzazione del carrello
        viewCartButton.addActionListener(e -> {
            // Crea un pannello scorrevole per mostrare i contenuti del carrello
            JPanel cartPanel = new JPanel();
            cartPanel.setLayout(new GridLayout(0, 1, 10, 10));

            aggiornaVistaCarrello(cartPanel); //riempie dinamicamente il pannello

            // Mostra il carrello in una finestra scorrevole
            JScrollPane scrollPane = new JScrollPane(cartPanel);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JDialog dialog = new JDialog();
            dialog.setTitle("Carrello");
            dialog.setModal(true);
            dialog.add(scrollPane);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        //visualizzazione dei videogiochi posseduti
        viewOwnedGamesButton.addActionListener(e -> {
            try {
                // Richiama il metodo per visualizzare i videogiochi posseduti
                visualizzaVideogiochiPosseduti(idUtente);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante il caricamento dei videogiochi posseduti.");
            }
        });

        //Listener per visualizzare gli ordini effettuati
        viewOrdersButton.addActionListener(e -> {
            String query = "SELECT O.DataAcquisto, SUM(V.Prezzo) AS Totale FROM Ordine O JOIN Composizione C ON O.ID_Ordine = C.ID_Ordine JOIN Videogioco V ON C.ID_Videogioco = V.ID_Videogioco WHERE O.ID_Utente = ? GROUP BY O.ID_Ordine, O.DataAcquisto ORDER BY O.DataAcquisto DESC;";
        
            try (Connection conn = DBAccess.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
        
                stmt.setInt(1, idUtente); // Imposta l'ID dell'utente loggato
        
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.isBeforeFirst()) { // Controlla se il ResultSet è vuoto
                        JOptionPane.showMessageDialog(null, "Non hai effettuato alcun ordine.");
                        return;
                    }
        
                    StringBuilder result = new StringBuilder("Ordini Effettuati:\n\n");
                    while (rs.next()) {
                        Date dataAcquisto = rs.getDate("DataAcquisto");
                        double totale = rs.getDouble("Totale");
                        result.append("Data Acquisto: ").append(dataAcquisto)
                              .append(" - Totale Pagato: ").append(String.format("%.2f€", totale))
                              .append("\n");
                    }
        
                    JOptionPane.showMessageDialog(null, result.toString(), "Ordini Effettuati", JOptionPane.PLAIN_MESSAGE);
                }
        
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante il recupero degli ordini.");
            }
        });

        //Listener per abbonarsi
        subscriptionButton.addActionListener(e -> {
            String queryCurrentSubscription = "SELECT A.Tipologia, S.DataFine FROM Sottoscrizione S JOIN Abbonamento A ON S.ID_Abbonamento = A.ID_Abbonamento WHERE S.ID_Utente = ? AND S.DataFine > CURDATE();";
        
            try (Connection conn = DBAccess.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(queryCurrentSubscription)) {
        
                stmt.setInt(1, idUtente); // Imposta l'ID dell'utente loggato
                ResultSet rs = stmt.executeQuery();
        
                if (rs.next()) {
                    // L'utente ha già un abbonamento attivo
                    String tipoAbbonamento = rs.getString("Tipologia");
                    Date dataFine = rs.getDate("DataFine");
                    JOptionPane.showMessageDialog(null,
                            "Sei già abbonato a: " + tipoAbbonamento +
                            "\nScadenza: " + dataFine,
                            "Abbonamento Attuale", JOptionPane.INFORMATION_MESSAGE);
                    visualizzaAbbonamenti(tipoAbbonamento, idUtente); // Mostra i tipi di abbonamento con restrizioni
                } else {
                    // Nessun abbonamento attivo
                    visualizzaAbbonamenti(null, idUtente); // Mostra entrambi gli abbonamenti disponibili
                }
        
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante il recupero degli abbonamenti.");
            }
        });

        // Pulsante per uscire
        esci.addActionListener(e -> System.exit(0));
        
        panel.add(viewOwnedGamesButton);
        panel.add(viewGamesButton);
        panel.add(viewCartButton);
        panel.add(ordersLabel);
        panel.add(viewOrdersButton);
        panel.add(subscriptionButton);
        panel.add(esci);
        add(panel);
    }

    //funzione per aggiornare dinamicamente il carrello
    private void aggiornaVistaCarrello(JPanel cartPanel) {
        cartPanel.removeAll(); // Svuota il pannello prima di ricaricarlo
    
        for (Carrello.ArticoloCarrello articolo : cart.getArticoli()) {
            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
            // Mostra i dettagli dell'articolo
            JLabel itemLabel = new JLabel(articolo.nome + " - " + articolo.prezzo + "€");
    
            // Pulsante per rimuovere l'articolo
            JButton removeButton = new JButton("Rimuovi");
            removeButton.addActionListener(ev -> {
                cart.rimuoviDalCarrello(articolo.idVideogioco); // Rimuove l'articolo
                aggiornaVistaCarrello(cartPanel); // Aggiorna la vista
                cartPanel.revalidate(); // Ricostruisce il layout
                cartPanel.repaint();   // Ridisegna il pannello
            });
    
            itemPanel.add(itemLabel);
            itemPanel.add(removeButton);
            cartPanel.add(itemPanel);
        }
    
        // Bottone per il checkout
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setEnabled(cart.getNumeroArticoli()>0);

        //Listener per il bottone del checkout
        checkoutButton.addActionListener(e -> {
            if (cart.getNumeroArticoli() == 0) {
                JOptionPane.showMessageDialog(null, "Il carrello è vuoto!");
                return;
            }
    
            // Connessione al database per salvare l'ordine
            try (Connection conn = DBAccess.getConnection()) {
                conn.setAutoCommit(false); // Avvia una transazione
    
                // Inserisce un nuovo ordine
                String insertOrdine = "INSERT INTO Ordine (DataAcquisto, ID_Utente) VALUES (?, ?)";
                PreparedStatement ordineStmt = conn.prepareStatement(insertOrdine, Statement.RETURN_GENERATED_KEYS);
                ordineStmt.setDate(1, new Date(System.currentTimeMillis())); // Data odierna
                ordineStmt.setInt(2, cart.getIdUtente());
                ordineStmt.executeUpdate();
    
                // Recupera l'ID dell'ordine appena creato
                ResultSet generatedKeys = ordineStmt.getGeneratedKeys();
                int idOrdine = 0;
                if (generatedKeys.next()) {
                    idOrdine = generatedKeys.getInt(1);
                }
    
                // Inserisce ogni articolo nella tabella Composizione
                String insertComposizione = "INSERT INTO Composizione (ID_Ordine, ID_Videogioco) VALUES (?, ?)";
                PreparedStatement compStmt = conn.prepareStatement(insertComposizione);
                for (Carrello.ArticoloCarrello articolo : cart.getArticoli()) {
                    compStmt.setInt(1, idOrdine);
                    compStmt.setInt(2, articolo.idVideogioco);
                    compStmt.addBatch(); // Batch insert per ottimizzare
                }
                compStmt.executeBatch();
    
                conn.commit(); // Conferma la transazione
                cart.svuotaCarrello(); // Svuota il carrello dopo l'acquisto
                JOptionPane.showMessageDialog(null, "Ordine confermato con successo!");
    
                aggiornaVistaCarrello(cartPanel); // Aggiorna dinamicamente la vista dopo il checkout
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante il checkout.");
            }
        });
    
        // Aggiungi il pulsante checkout al pannello
        cartPanel.add(checkoutButton);
    }
    
    //funzione per visualizzare i videogiochi posseduti
    public void visualizzaVideogiochiPosseduti(int idUtente) {
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new GridLayout(0, 1, 10, 10));
    
        String query = "SELECT V.ID_Videogioco, V.Nome, V.Genere, V.Prezzo, V.DataRilascio, \r\n" + //
                        "       IFNULL(R.ID_Videogioco, 0) AS GiaRecensito\r\n" + //
                        "FROM Videogioco V\r\n" + //
                        "JOIN Composizione C ON V.ID_Videogioco = C.ID_Videogioco\r\n" + //
                        "JOIN Ordine O ON C.ID_Ordine = O.ID_Ordine\r\n" + //
                        "LEFT JOIN Recensione R ON R.ID_Videogioco = V.ID_Videogioco AND R.ID_Utente = ?\r\n" + //
                        "WHERE O.ID_Utente = ?;\r\n" + //
                        ""; 
    
        try (Connection conn = DBAccess.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, idUtente);
            stmt.setInt(2, idUtente);
    
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idVideogioco = rs.getInt("ID_Videogioco");
                    String nome = rs.getString("Nome");
                    String genere = rs.getString("Genere");
                    double prezzo = rs.getDouble("Prezzo");
                    Date dataRilascio = rs.getDate("DataRilascio");
                    boolean giaRecensito = rs.getBoolean("GiaRecensito");
    
                    // Crea il pannello per ciascun videogioco
                    JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
                    JLabel itemLabel = new JLabel(
                        nome + " - " + genere + " - " + prezzo + "€ - Rilasciato: " + dataRilascio
                    );
    
                    JButton reviewButton = new JButton("Scrivi Recensione");
                    reviewButton.setEnabled(!giaRecensito); // Attiva solo se non recensito
    
                    reviewButton.addActionListener(e -> {
                        // Crea una finestra di dialogo per scrivere la recensione e il punteggio
                        JPanel reviewPanel = new JPanel();
                        reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
    
                        JTextArea reviewTextArea = new JTextArea(5, 30);
                        reviewTextArea.setWrapStyleWord(true);
                        reviewTextArea.setLineWrap(true);
                        JScrollPane scrollPane = new JScrollPane(reviewTextArea);
                        reviewPanel.add(new JLabel("Scrivi la tua recensione:"));
                        reviewPanel.add(scrollPane);
    
                        // Crea un JSpinner per il punteggio
                        JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1)); // Punteggio da 1 a 5
                        reviewPanel.add(new JLabel("Seleziona il punteggio:"));
                        reviewPanel.add(ratingSpinner);
    
                        // Mostra la finestra di dialogo
                        int option = JOptionPane.showConfirmDialog(null, reviewPanel, 
                            "Scrivi una recensione per " + nome, JOptionPane.OK_CANCEL_OPTION);
    
                        if (option == JOptionPane.OK_OPTION) {
                            String recensione = reviewTextArea.getText();
                            int punteggio = (int) ratingSpinner.getValue(); // Ottieni il punteggio
    
                            if (recensione != null && !recensione.isEmpty()) {
                                try {
                                    salvaRecensione(idUtente, idVideogioco, recensione, punteggio);
                                    JOptionPane.showMessageDialog(null, "Recensione salvata con successo!");
                                    reviewButton.setEnabled(false); // Disabilita il pulsante
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(null, "Errore durante il salvataggio della recensione.");
                                }
                            }
                        }
                    });
    
                    itemPanel.add(itemLabel);
                    itemPanel.add(reviewButton);
                    cartPanel.add(itemPanel);
                }
            }
    
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante il recupero dei videogiochi posseduti.");
        }
    
        JScrollPane scrollPane = new JScrollPane(cartPanel);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        JOptionPane.showMessageDialog(null, scrollPane, "Videogiochi Posseduti", JOptionPane.PLAIN_MESSAGE);
    }
    
    //funzione per salvare la recensione
    public static void salvaRecensione(int idUtente, int idVideogioco, String commento, int punteggio) throws SQLException {
        String queryRecensione = "INSERT INTO Recensione (ID_Utente, ID_Videogioco, Commento, DataRecensione, Punteggio) VALUES (?, ?, ?, CURDATE(), ?)";
        String queryMediaRecensioni = "UPDATE Videogioco V SET V.MediaRecensioni = (SELECT AVG(R.Punteggio) FROM Recensione R WHERE R.ID_Videogioco = V.ID_Videogioco) WHERE V.ID_Videogioco = ?;";
    
        try (Connection conn = DBAccess.getConnection()) {
            // Inizia la transazione
            conn.setAutoCommit(false);
    
            try (PreparedStatement stmtRecensione = conn.prepareStatement(queryRecensione)) {
                // Salva la recensione
                stmtRecensione.setInt(1, idUtente);
                stmtRecensione.setInt(2, idVideogioco);
                stmtRecensione.setString(3, commento);
                stmtRecensione.setInt(4, punteggio);
                stmtRecensione.executeUpdate();
            }
    
            try (PreparedStatement stmtMediaRecensioni = conn.prepareStatement(queryMediaRecensioni)) {
                // Ricalcola e aggiorna la media delle recensioni per il videogioco
                stmtMediaRecensioni.setInt(1, idVideogioco);
                stmtMediaRecensioni.executeUpdate();
            }
    
            // Completa la transazione
            conn.commit();
        } catch (SQLException ex) {
            // Gestisce eventuali errori e annulla la transazione
            ex.printStackTrace();
            throw new SQLException("Errore durante il salvataggio della recensione e l'aggiornamento della media", ex);
        }
    }

    //funzione per visualizzare gli abbonamenti
    private void visualizzaAbbonamenti(String abbonamentoAttuale, int idUtente) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
    
        String[] abbonamenti = {"Base", "Premium"};
        double[] costi = {9.99, 19.99};
    
        for (int i = 0; i < abbonamenti.length; i++) {
            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel label = new JLabel(abbonamenti[i] + " - Costo Mensile: " + costi[i] + "€");
    
            JButton abbonatiButton = new JButton("Abbonati");
            abbonatiButton.setEnabled(!abbonamenti[i].equals(abbonamentoAttuale)); // Disabilita se già abbonato
    
            int index = i; // Variabile necessaria per la lambda
            abbonatiButton.addActionListener(e -> {
                int conferma = JOptionPane.showConfirmDialog(null,
                        "Stai sottoscrivendo l'abbonamento " + abbonamenti[index] + ". Continuare?",
                        "Conferma Sottoscrizione",
                        JOptionPane.YES_NO_OPTION);
    
                if (conferma == JOptionPane.YES_OPTION) {
                    sottoscriviAbbonamento(abbonamenti[index], idUtente);
                }
            });
    
            itemPanel.add(label);
            itemPanel.add(abbonatiButton);
            panel.add(itemPanel);
        }
    
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        JOptionPane.showMessageDialog(null, scrollPane, "Abbonamenti Disponibili", JOptionPane.PLAIN_MESSAGE);
    }
    
    //funzione per registrare la sottoscrizione dell'abbonamento
    private void sottoscriviAbbonamento(String tipoAbbonamento, int idUtente) {
        String queryAbbonamentoID = "SELECT ID_Abbonamento FROM Abbonamento WHERE Tipologia = ?";
        String queryUpdate = "INSERT INTO Sottoscrizione (ID_Utente, ID_Abbonamento, DataInizio, DataFine) VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 1 MONTH)) ON DUPLICATE KEY UPDATE ID_Abbonamento = VALUES(ID_Abbonamento), DataInizio = CURDATE(), DataFine = DATE_ADD(CURDATE(), INTERVAL 1 MONTH);";
    
        try (Connection conn = DBAccess.getConnection()) {
            conn.setAutoCommit(false);
    
            int idAbbonamento = 0;
            try (PreparedStatement stmt = conn.prepareStatement(queryAbbonamentoID)) {
                stmt.setString(1, tipoAbbonamento);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    idAbbonamento = rs.getInt("ID_Abbonamento");
                }
            }
    
            try (PreparedStatement stmt = conn.prepareStatement(queryUpdate)) {
                stmt.setInt(1, idUtente);
                stmt.setInt(2, idAbbonamento);
                stmt.executeUpdate();
            }
    
            conn.commit();
            JOptionPane.showMessageDialog(null, "Sottoscrizione all'abbonamento " + tipoAbbonamento + " completata!");
    
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante la sottoscrizione dell'abbonamento.");
        }
    }
    
}