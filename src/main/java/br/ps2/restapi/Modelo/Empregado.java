package br.ps2.restapi.Modelo;

import javax.persistence.*;

@Entity
public class Empregado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nome, cargo, cidade;
    private double salario;

    public Empregado() {
        nome = "";
    }

    public Empregado(String nome, String cargo, String cidade , double salario){
        this.nome = nome;
        this.cargo = cargo;
        this.cidade = cidade;
        this.salario = salario;
    }

    public void setId(long id) {this.id = id;}
    public void setNome(String nome) {this.nome = nome;}
    public void setCargo(String cargo) {this.cargo = cargo;}
    public void setCidade(String cidade) {this.cidade = cidade;}
    public void setSalario(double salario) {this.salario = salario;}

    public String getNome() {return nome;}
    public String getCargo() {return cargo;}
    public String getCidade() {return cidade;}
    public double getSalario() {return salario;}
    public long getId() {return id;}
}