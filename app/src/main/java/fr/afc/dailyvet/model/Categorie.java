package fr.afc.dailyvet.model;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
    private String name;
    private List<Vetement> vetements;

    public Categorie(){ }

    //Cat without vetements
    public Categorie(String name) {
        this.name = name;
    }

    //Cat with vetements
    public Categorie(String name, List<Vetement> vetements) {
        this.name = name;
        this.vetements = vetements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Vetement> getVetements() {
        if (vetements == null){
            vetements = new ArrayList<>();
        }
        return vetements;
    }

    public void setVetements(List<Vetement> vetements) {
        this.vetements = vetements;
    }


}
