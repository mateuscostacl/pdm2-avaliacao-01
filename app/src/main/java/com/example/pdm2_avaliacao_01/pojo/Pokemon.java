package com.example.pdm2_avaliacao_01.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Pokemon implements Serializable {

    private int id;

    @Expose
    @SerializedName("id")
    private String idPoke;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("name")
    private String nome;

    @Expose
    @SerializedName("weight")
    private String peso;

    @Expose
    @SerializedName("height")
    private String altura;

    @Expose
    @SerializedName("base_experience")
    private String expBasica;

    @Expose
    @SerializedName("front_default")
    private String imagem;

    private String habilidade;

    private String tipo;

    public Pokemon() {
    }

    public Pokemon(int id, String idPoke, String url, String nome, String peso, String altura, String expBasica, String imagem, String habilidade, String tipo) {
        this.id = id;
        this.idPoke = idPoke;
        this.url = url;
        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.expBasica = expBasica;
        this.imagem = imagem;
        this.habilidade = habilidade;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdPoke() {
        return idPoke;
    }

    public void setIdPoke(String idPoke) {
        this.idPoke = idPoke;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(String habilidade) {
        this.habilidade = habilidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getExpBasica() {
        return expBasica;
    }

    public void setExpBasica(String exp_basica) {
        this.expBasica = exp_basica;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", idPoke='" + idPoke + '\'' +
                ", url='" + url + '\'' +
                ", nome='" + nome + '\'' +
                ", peso='" + peso + '\'' +
                ", altura='" + altura + '\'' +
                ", exp_basica='" + expBasica + '\'' +
                ", imagem='" + imagem + '\'' +
                ", habilidade='" + habilidade + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return id == pokemon.id && Objects.equals(idPoke, pokemon.idPoke) && Objects.equals(url, pokemon.url) && Objects.equals(nome, pokemon.nome) && Objects.equals(peso, pokemon.peso) && Objects.equals(altura, pokemon.altura) && Objects.equals(expBasica, pokemon.expBasica) && Objects.equals(imagem, pokemon.imagem) && Objects.equals(habilidade, pokemon.habilidade) && Objects.equals(tipo, pokemon.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idPoke, url, nome, peso, altura, expBasica, imagem, habilidade, tipo);
    }
}
