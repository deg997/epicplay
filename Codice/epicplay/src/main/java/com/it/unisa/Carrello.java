package com.it.unisa;

import java.util.ArrayList;
import java.util.List;

public class Carrello {

    // ID dell'utente che possiede il carrello
    private int idUtente;

    // Classe interna per rappresentare un articolo nel carrello
    static class ArticoloCarrello {
        int idVideogioco;
        String nome;
        double prezzo;

        public ArticoloCarrello(int idVideogioco, String nome, double prezzo) {
            this.idVideogioco = idVideogioco;
            this.nome = nome;
            this.prezzo = prezzo;
        }
    }

    // Lista per memorizzare gli articoli nel carrello
    private List<ArticoloCarrello> carrello;

    // Costruttore
    public Carrello(int idUtente) {
        this.idUtente = idUtente;
        this.carrello = new ArrayList<>();
    }

    // Aggiunge un videogioco al carrello
    public void aggiungiAlCarrello(int id, String nome, double prezzo) {
        carrello.add(new ArticoloCarrello(id, nome, prezzo));
        System.out.println("Aggiunto al carrello: " + nome);
    }

    // Rimuove un videogioco dal carrello
    public void rimuoviDalCarrello(int id) {
        carrello.removeIf(articolo -> articolo.idVideogioco == id);
        System.out.println("Rimosso dal carrello: ID " + id);
    }


    /*
    // Mostra tutti gli articoli nel carrello
    public String mostraCarrello() {
        StringBuilder result = new StringBuilder("Articoli nel carrello:\n");
        double totale = 0.0;

        for (ArticoloCarrello articolo : carrello) {
            result.append("ID: ").append(articolo.idVideogioco)
                  .append(", Nome: ").append(articolo.nome)
                  .append(", Prezzo: ").append(articolo.prezzo).append("€\n");
            totale += articolo.prezzo;
        }
        result.append("Totale: ").append(totale).append("€");
        return result.toString();
    }*/

    // Restituisce la lista degli articoli nel carrello
    public List<ArticoloCarrello> getArticoli() {
        return carrello; // Restituisce direttamente la lista degli articoli
    }


    // Svuota il carrello
    public void svuotaCarrello() {
        carrello.clear();
        System.out.println("Carrello svuotato.");
    }

    // Restituisce il numero totale di articoli
    public int getNumeroArticoli() {
        return carrello.size();
    }

    // Restituisce il totale del carrello
    public double getTotale() {
        double totale = 0.0;
        for (ArticoloCarrello articolo : carrello) {
            totale += articolo.prezzo;
        }
        return totale;
    }

    // Restituisce l'ID dell'utente associato al carrello
    public int getIdUtente() {
        return idUtente;
    }
}
