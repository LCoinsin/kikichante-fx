package com.kikichante.server;


public class Music {
    private int id_musique;
    private String titre;
    private String interprete;
    private String url;
    private int annee;
    private String theme;

    public Music(int id_musique, String titre, String interprete, String url, int annee, String theme) {
        this.id_musique = id_musique;
        this.titre = titre;
        this.interprete = interprete;
        this.url = url;
        this.annee = annee;
        this.theme = theme;
    }

    public int getId_musique() {
        return id_musique;
    }

    public void setId_musique(int id_musique) {
        this.id_musique = id_musique;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getInterprete() {
        return interprete;
    }

    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "Musique{" +
                "id_musique='" + id_musique + '\'' +
                ", titre='" + titre + '\'' +
                ", interprete='" + interprete + '\'' +
                ", url='" + url + '\'' +
                ", annee=" + annee +
                ", theme='" + theme + '\'' +
                '}';
    }
}