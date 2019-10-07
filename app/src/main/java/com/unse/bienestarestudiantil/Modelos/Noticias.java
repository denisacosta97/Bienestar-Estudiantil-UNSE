package com.unse.bienestarestudiantil.Modelos;

public class Noticias {

    private String titulo, cuerpo, fechahora, urlImagen, urlWeb;
    private int id, idTipoNoticias, idCategoria;
    private boolean isURL = false, isEncuesta = false, isButton = false;

    public Noticias() {
        this.titulo = "";
        this.cuerpo = "";
        this.fechahora = "";
        this.id = -1;
        this.idTipoNoticias = 0;
    }

    public Noticias(String titulo, String cuerpo, String fechahora, String urlImagen, int id, int idTipoNoticias, int cat) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.fechahora = fechahora;
        this.urlImagen = urlImagen;
        this.id = id;
        this.idTipoNoticias = idTipoNoticias;
        this.idCategoria = cat;
    }

    public int getIdTipoNoticias() {
        return idTipoNoticias;
    }

    public void setIdTipoNoticias(int idTipoNoticias) {
        this.idTipoNoticias = idTipoNoticias;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public boolean isButton() {
        return isButton;
    }

    public void setButton(boolean button) {
        isButton = button;
    }

    public String getTitulo() {
        return titulo;
    }

    public Noticias setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public Noticias setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
        return this;
    }

    public String getFechahora() {
        return fechahora;
    }

    public Noticias setFechahora(String fechahora) {
        this.fechahora = fechahora;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;

    }

    public String getUrlWeb() {
        return urlWeb;
    }

    public void setUrlWeb(String urlWeb) {
        this.urlWeb = urlWeb;
        setButton(true);
        setURL(true);

    }

    public int getIdTipo() {
        return idTipoNoticias;
    }

    public void setIdTipo(int idTipoNoticias) {
        this.idTipoNoticias = idTipoNoticias;

    }

    public boolean isURL() {
        return isURL;
    }

    public void setURL(boolean URL) {
        isURL = URL;
    }

    public boolean isEncuesta() {
        return isEncuesta;
    }

    public void setEncuesta(boolean encuesta) {
        isEncuesta = encuesta;
        setButton(true);
    }


}
