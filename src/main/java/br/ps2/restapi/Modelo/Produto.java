package br.ps2.restapi.Modelo;

import javax.persistence.*;

@Entity
@Table(name="produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nome, marca;
    private double preco;

    public Produto() {
        nome = "";
    }

    public Produto(String nome, String marca, double preco){
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
    }

    public void setId(long id) {this.id = id;}
    public void setNome(String nome) {this.nome = nome;}
    public void setPreco(double preco) {this.preco = preco;}
    public void setMarca(String marca) {this.marca = marca;}

    public String getNome() {return nome;}
    public double getPreco() {return preco;}
    public String getMarca() {return marca;}
    public long getId() {return id;}
}