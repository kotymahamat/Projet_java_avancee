package com.gestion.model;

public class Employe {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private int idDepartement; // C'est ici la clé étrangère (Relation)

    public Employe() {}

    public Employe(int id, String nom, String prenom, String email, int idDepartement) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.idDepartement = idDepartement;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getIdDepartement() { return idDepartement; }
    public void setIdDepartement(int idDepartement) { this.idDepartement = idDepartement; }

}
