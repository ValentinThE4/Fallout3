package com.example.utilisateur.fallout3;

public class Personnages {

    private int id;
    private String prenom;
    private String nom;
    private int age;
    private String role;
    private String nom_photo;
    private int id_lieu;
    private int id_race;

    public Personnages(int id, String prenom, String nom, int age, String role, String nom_photo, int id_lieu, int id_race) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.age = age;
        this.role = role;
        this.nom_photo = nom_photo;
        this.id_lieu = id_lieu;
        this.id_race = id_race;
    }

    public int getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public int getAge() {
        return age;
    }

    public String getRole() {
        return role;
    }

    public String getNom_photo() {
        return nom_photo;
    }

    public int getId_lieu() {
        return id_lieu;
    }

    public int getId_race() {
        return id_race;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setNom_photo(String nom_photo) {
        this.nom_photo = nom_photo;
    }

    public void setId_lieu(int id_lieu) {
        this.id_lieu = id_lieu;
    }

    public void setId_race(int id_race) {
        this.id_race = id_race;
    }
}
