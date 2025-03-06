package com.it.unisa;

public class Main {
    public static void main(String[] args) {
        // Mostra il dialogo per i dati di accesso al database
        boolean ok = DBLoginDialog.showDialog();
        if (!ok) {
            System.exit(0); // Esci se l'utente annulla
        }

        // Ora puoi lanciare la LoginGUI, sapendo che DBAccess è configurato
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginGUI().setVisible(true);
        });
    }
}
