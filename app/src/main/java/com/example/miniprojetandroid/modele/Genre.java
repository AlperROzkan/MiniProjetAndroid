package com.example.miniprojetandroid.modele;

/**
 * Un genre de film
 */
public class Genre {
    private String id;
    private String name;

    /**
     * Constructeur
     * @param id Utilisé lors de la requete a l'api
     * @param name Utilisé pour l'affichage lors de la requete
     */
    public Genre(String id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
