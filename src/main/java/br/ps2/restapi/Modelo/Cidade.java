package br.ps2.restapi.Modelo;

import javax.persistence.*;

@Entity
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nome, estado, nomeFilial;


    public Cidade() {
        nome = "";
    }

    public Cidade(String nome, String estado, String nomeFilial){
        this.nome = nome;
        this.estado = estado;
        this.nomeFilial = nomeFilial;
    }

    public void setId(long id) {this.id = id;}
    public void setNome(String nome) {this.nome = nome;}
    public void setnomeFilial(String nomeFilial) {this.estado = nomeFilial;}
    public void setestado(String estado) {this.estado = estado;}

    public String getNome() {return nome;}
    public String getnomeFilial() {return nomeFilial;}
    public String getestado() {return estado;}
    public long getId() {return id;}
}