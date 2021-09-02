package fr.afc.dailyvet.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String ref;
    private String username;
    private String phoneNumber;
    private String email;
    private boolean daf;
    private List<Categorie> categories;

    public User() {
    }
    //User with no Categories
    public User(String ref, String username, String phoneNumber, String email, boolean daf) {
        this.ref = ref;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.daf = daf;
    }
    //USer with categories
    public User(String ref, String username, String phoneNumber, String email, boolean daf,List<Categorie> categories) {
        this.ref = ref;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.daf = daf;
        this.categories = categories;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDaf() {
        return daf;
    }

    public void setDaf(boolean daf) {
        this.daf = daf;
    }

    public List<Categorie> getCategorie() {
        return categories;
    }

    public void setCategorie(List<Categorie> categorie) {
        this.categories = categorie;
    }

    public Categorie getUneCategorie(String nomCat){
        if (categories == null){
            this.categories = new ArrayList<>();

        }
        for(Categorie cat : categories){
            if(cat.getName().equals(nomCat)){
                return cat;
            }
        }
        return null;
    }
    public void modifieUneCategorie( Categorie cat){
        List<Categorie> newCategories = new ArrayList<>();
        for(Categorie categorie : categories){
            if(categorie.getName() == cat.getName()){
                newCategories.add(cat);
            }else{
                newCategories.add(categorie);
            }

        }
        this.categories = newCategories;
    }
}
