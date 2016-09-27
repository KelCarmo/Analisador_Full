package model.uefs.br;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yuri Silva
 */
public class Token {
    
    //private ArrayList<String>lexemas = new ArrayList<String>();
    private String nome;
    private int tipo;
     private int linha;
    private boolean status;
    
    public Token(String nome, int tipo, int linha, String expressaoRegular){
        this.nome = nome;
        this.tipo = tipo;
        this.linha = linha;       
        this.status = nome.matches(expressaoRegular);
    }
    
    public Token(String nome, int tipo, int linha, boolean status){
        this.nome = nome;
        this.tipo = tipo;
        this.linha = linha;       
        this.status = status;
    }
   

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }
    
   
    public boolean verifica(String lexema){
        
        
       
        return true;
    }
    
    public enum Tipos {
	Palavra_Reservada(1), Identificador(2), Numero(3), 
        Operador_Aritmetico(4), Operador_Relacional(5), Operador_Logico(6), 
        Delimitador_Comentario(7), Delimitador(8), Cadeia_de_Caracteres(9), Caracter(10), Desconhecido(11);


	public int valorTipo;
	Tipos(int valor) {
		valorTipo = valor;
	}
        
        public int getValor(){
		return valorTipo;
	}
        
        
}
    
    private String getNomeTipos(int i){
            for(Tipos op : Tipos.values()){	
            if(i == op.getValor()) return op.name();
            }
            return null;
        }
    
    @Override
    public String toString(){
        if(this.status)
        return linha + "|" + nome + "|" + getNomeTipos(tipo);
        else {
            if(tipo == 11) return linha + "|" + nome + "|" + "Contém Caracteres Desconhecido pelo alfabeto da linguagem";
            return linha + "|" + nome + "|" + getNomeTipos(tipo) + " mal formado!";
        }
    }
    
    
}
