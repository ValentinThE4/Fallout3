package com.example.utilisateur.fallout3;

public class Lieux {

    private int id;
    private String libelle;
    private String descriptif;

    public Lieux(int id, String libelle, String descriptif) {
        this.id = id;
        this.libelle = libelle;
        this.descriptif = descriptif;
    }

    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }
}
