package com.gestion.model;

public class departement {

        private int id;
        private String nom;
        private String description;

        // Constructeur vide (utile pour certaines manipulations)
        public departement() {}

        // Constructeur complet
        public departement(int id, String nom, String description) {
            this.id = id;
            this.nom = nom;
            this.description = description;
        }

        // Getters et Setters (Encapsulation)
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        // Pour afficher le nom du département dans les listes déroulantes (Combo Box)
        @Override
        public String toString() {
            return nom;
        }


}
