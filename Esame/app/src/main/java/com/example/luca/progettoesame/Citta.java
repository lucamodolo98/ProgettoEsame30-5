package com.example.luca.progettoesame;

public class Citta {
    public int IdCitta;
    public String NomeCitta;
    public int popolazione;
    public int IdStato;
    public int IsCapitale;
    public double Latitudine;
    public double Longitudine;

    public Stato stato;


    public Citta(int idCitta, String nomeCitta, int popolazione, int idStato, int isCapitale, double latitudine, double longitudine) {
        IdCitta = idCitta;
        NomeCitta = nomeCitta;
        this.popolazione = popolazione;
        IdStato = idStato;
        IsCapitale = isCapitale;
        Latitudine = latitudine;
        Longitudine = longitudine;
    }

    public int getIdCitta() {
        return IdCitta;
    }

    public void setIdCitta(int idCitta) {
        IdCitta = idCitta;
    }

    public String getNomeCitta() {
        return NomeCitta;
    }

    public void setNomeCitta(String nomeCitta) {
        NomeCitta = nomeCitta;
    }

    public int getPopolazione() {
        return popolazione;
    }

    public void setPopolazione(int popolazione) {
        this.popolazione = popolazione;
    }

    public int getIdStato() {
        return IdStato;
    }

    public void setIdStato(int idStato) {
        IdStato = idStato;
    }

    public int getIsCapitale() {
        return IsCapitale;
    }

    public void setIsCapitale(int isCapitale) {
        IsCapitale = isCapitale;
    }

    public double getLatitudine() {
        return Latitudine;
    }

    public void setLatitudine(double latitudine) {
        Latitudine = latitudine;
    }

    public double getLongitudine() {
        return Longitudine;
    }

    public void setLongitudine(double longitudine) {
        Longitudine = longitudine;
    }

    @Override
    public String toString() {
        return "Citta{" +
                "IdCitta=" + IdCitta +
                ", NomeCitta='" + NomeCitta + '\'' +
                ", popolazione=" + popolazione +
                ", IdStato=" + IdStato +
                ", IsCapitale=" + IsCapitale +
                ", Latitudine=" + Latitudine +
                ", Longitudine=" + Longitudine +
                ", stato=" + stato +
                '}';
    }
}
