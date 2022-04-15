package com.example.pdm2_avaliacao_01.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Categoria {

    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("dicas")
    @Expose
    private List<String> dicas = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getDicas() {
        return dicas;
    }

    public void setDicas(List<String> dicas) {
        this.dicas = dicas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Categoria.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("nome");
        sb.append('=');
        sb.append(((this.nome == null)?"<null>":this.nome));
        sb.append(',');
        sb.append("dicas");
        sb.append('=');
        sb.append(((this.dicas == null)?"<null>":this.dicas));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}