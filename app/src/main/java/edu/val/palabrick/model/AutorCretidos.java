package edu.val.palabrick.model;

public class AutorCretidos {

    private int foto;
    private String nombre;
    private String url_github;
    private String url_linkedin;

    public AutorCretidos(int foto, String nombre, String url_github, String url_linkedin) {
        this.foto = foto;
        this.nombre = nombre;
        this.url_github = url_github;
        this.url_linkedin = url_linkedin;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl_github() {
        return url_github;
    }

    public void setUrl_github(String url_github) {
        this.url_github = url_github;
    }

    public String getUrl_linkedin() {
        return url_linkedin;
    }

    public void setUrl_linkedin(String url_linkedin) {
        this.url_linkedin = url_linkedin;
    }
}
