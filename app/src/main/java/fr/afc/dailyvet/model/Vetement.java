package fr.afc.dailyvet.model;

public class Vetement {
    private String couleur;
    private String marque;
    private String url;

    public Vetement() {
    }

    public Vetement( String couleur, String marque,String url) {
        this.couleur = couleur;
        this.marque = marque;
        this.url = url;
    }


    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
