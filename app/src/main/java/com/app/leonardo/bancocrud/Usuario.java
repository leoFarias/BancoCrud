package com.app.leonardo.bancocrud;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Objects;

@IgnoreExtraProperties
public class Usuario {


  public   String nome;
  public   String Condominio;
  public String  apartamento;

    public Usuario() {
      }

    public Usuario(String nome, String condominio, String apartamento) {
        this.nome = nome;
        this.Condominio = condominio;
        this.apartamento = apartamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCondominio() {
        return Condominio;
    }

    public void setCondominio(String condominio) {
        Condominio = condominio;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nome, usuario.nome) &&
                Objects.equals(Condominio, usuario.Condominio) &&
                Objects.equals(apartamento, usuario.apartamento);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nome, Condominio, apartamento);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", Condominio='" + Condominio + '\'' +
                ", apartamento='" + apartamento + '\'' +
                '}';
    }
}
