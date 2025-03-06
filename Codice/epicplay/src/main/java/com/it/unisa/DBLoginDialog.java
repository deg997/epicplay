package com.it.unisa;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DBLoginDialog {

    public static boolean showDialog() {
        JTextField urlField = new JTextField(DBAccess.getConnectionURL());
        JTextField userField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
            "Database URL:", urlField,
            "Username:", userField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Dati di accesso al database", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Imposta i nuovi dati di accesso in DBAccess
            DBAccess.setURL(urlField.getText().trim());
            DBAccess.setUser(userField.getText().trim());
            DBAccess.setPassword(new String(passwordField.getPassword()));
            return true;
        }
        return false;
    }

}
