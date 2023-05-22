package br.ps2.restapi.Modelo;

import javax.persistence.*;

@Entity
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nome, estado;
    private int populacao;
    //private int populacao;

    public Cidade() {
        nome = "";
    }

    public Cidade(String nome, String estado, int populacao){
        this.nome = nome;
        this.estado = estado;
        this.populacao = populacao;
    }

    public void setId(long id) {this.id = id;}
    public void setNome(String nome) {this.nome = nome;}
    public void setPopulacao(int populacao) {this.populacao = populacao;}
    public void setEstado(String estado) {this.estado = estado;}

    public String getNome() {return nome;}
    public int getPopulacao() {return populacao;}
    public String getEstado() {return estado;}
    public long getId() {return id;}
}