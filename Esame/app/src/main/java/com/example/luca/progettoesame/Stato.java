package com.example.luca.progettoesame;

public class Stato {
    public int IdStato;
    public String NomeStato;
    public String CodiceDue;
    public String CodiceTre;
    public String Bandiera;

    public Stato(int idStato, String nomeStato, String codiceDue, String codiceTre, String bandiera) {
        IdStato = idStato;
        NomeStato = nomeStato;
        CodiceDue = codiceDue;
        CodiceTre = codiceTre;
        Bandiera = bandiera;
    }

    public int getIdStato() {
        return IdStato;
    }

    public void setIdStato(int idStato) {
        IdStato = idStato;
    }

    public String getNomeStato() {
        return NomeStato;
    }

    public void setNomeStato(String nomeStato) {
        NomeStato = nomeStato;
    }

    public String getCodiceDue() {
        return CodiceDue;
    }

    public void setCodiceDue(String codiceDue) {
        CodiceDue = codiceDue;
    }

    public String getCodiceTre() {
        return CodiceTre;
    }

    public void setCodiceTre(String codiceTre) {
        CodiceTre = codiceTre;
    }

    public String getBandiera() {
        return Bandiera;
    }

    public void setBandiera(String bandiera) {
        Bandiera = bandiera;
    }
}
