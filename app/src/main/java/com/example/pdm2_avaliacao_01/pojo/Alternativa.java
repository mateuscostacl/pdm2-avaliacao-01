package com.example.pdm2_avaliacao_01.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alternativa {

    @SerializedName("texto")
    @Expose
    private String texto;
    @SerializedName("certo")
    @Expose
    private Integer certo;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getCerto() {
        return certo;
    }

    public void setCerto(Integer certo) {
        this.certo = certo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Alternativa.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("texto");
        sb.append('=');
        sb.append(((this.texto == null)?"<null>":this.texto));
        sb.append(',');
        sb.append("certo");
        sb.append('=');
        sb.append(((this.certo == null)?"<null>":this.certo));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}