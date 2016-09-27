package model.uefs.br;

import java.io.File;
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
public class Analisador {
    static final int PALAVRAS_RESERVADAS = 1;
    static final int IDENTIFICADOR = 2;
    static final int NUMERO = 3;
    static final int ARITMETICOS = 4;
    static final int RELACIONAIS = 5;
    static final int LOGICOS = 6;
    static final int DEL_COMENT = 7;
    static final int DELIMITADORES = 8;
    static final int CADEIA_DE_CARACTERES = 9;
    static final int CARACTER = 10;
    static final int NEGATIVO = 45;
    static final int DESCONHECIDO = 11;
      
    private File diretorio;   
    private ArrayList<Token> tokens = new ArrayList();
      private ArrayList<String> linhas_arq = new ArrayList<String>();
      private int not_alfabeto = -1;
      
      
      
      
      
      
      
    public void analisarArquivosDir(String dir){
        
        diretorio = new File(dir);
        File fList[] = diretorio.listFiles();
        
        for (File fList1 : fList) {
             linhas_arq.clear();
             tokens.clear();
           //System.out.print(fList1.getName());
            analisarCodigo(fList1);
           
        }
    }
    
    private void lerArq(String arquivo){
       
        linhas_arq = ReadWriteArq.ler(arquivo);
    }
    /**
     * Única função que pode ser chamada pelo objeto da classe, que analisa o código do arquivo passado por parâmetro.
     * @param nomeArquivo 
     */
    private void analisarCodigo(File arquivo){
        int tam = arquivo.getAbsolutePath().length();
        
        lerArq(arquivo.getAbsolutePath());
        separaTokens();        
        ReadWriteArq.escreve(tokens,arquivo.getAbsolutePath().substring(0,tam-4) + "Result.txt");

    }
    /**
     * Método responsável por separar todos os lexemas referentes a sua classe-token definidas pela linguagem
     */
    private void separaTokens() {
        
        int ch;
    
   for(int i=0;i < linhas_arq.size();i++){ // Percorre todas as linhas do arquivo
       ch = 0;
       String temp = linhas_arq.get(i);       
       
        int tamanho = temp.length();
        
        
        
        
        while(ch < tamanho){
        int t = isToken((int)temp.charAt(ch),ch,temp);// Recebe o Token referente ao caracter que está começando o lexema
        int current_last = ch;        
        
           
           
          switch (t){
              
              case IDENTIFICADOR:
                  
                  current_last = delimitadorId(ch,temp);
                  String novo = temp.substring(ch, current_last);
                  Token t1 = new Token(novo, IDENTIFICADOR,i+1,"^([a-zA-Z])\\w*$");
                  tokens.add(t1);
                  
                  ch = current_last;
                 
              break;
                  
              case NUMERO:
                  //System.out.print(ch);
                  current_last = delimitadorNumero(ch,temp);
                  //System.out.print(current_last);
                  String novo3 = temp.substring(ch, current_last);
                  Token t4 = new Token(novo3, NUMERO,i+1,"^([-])?([0-9]+\\.)?\\d+");
                  tokens.add(t4);
                    ch = current_last; 
                    
                  break;
              
              case CADEIA_DE_CARACTERES:
                  boolean pula;
                  current_last = delimitadorCadeia(ch,temp);  
                  String novo4;
                  if((int)temp.charAt(current_last) == 34) pula = true;
                  else pula = false;
                  
                  novo4 = temp.substring(ch, current_last + 1);
                  Token t5 = new Token(novo4, CADEIA_DE_CARACTERES,i+1,"^\"[a-zA-Z][\\d|[a-zA-Z]|\\s]*\"$");
                  tokens.add(t5);
                    ch = current_last;
                    /*if(pula == true)*/ch++;
                     
                  break;
                  
                  case CARACTER:
                      boolean pula1;
                  current_last = delimitadorCaracter(ch,temp);                
                  String novo5;
                  if((int)temp.charAt(current_last) == 39) pula1 = true;
                  else pula1 = false;
                  
                  novo5 = temp.substring(ch, current_last + 1);
                  Token t6 = new Token(novo5, CARACTER,i+1,"^\\'([a-zA-Z]|\\d)\\'$");
                  tokens.add(t6);
                    ch = current_last;
                    ch++;
                    
                  break;
                  
                  case ARITMETICOS:
                      
                  String novo1 = temp.substring(ch, current_last+1);
                  Token t2 = new Token(novo1, ARITMETICOS,i+1,"[\\+\\-\\*\\/]");
                  tokens.add(t2);                  
                  ch++;
                   
                  break;
                  
                  case RELACIONAIS:
                      
                  current_last = delimitadorRelacional(ch,temp);
                  String novo2 = temp.substring(ch, current_last);
                  Token t3 = new Token(novo2, RELACIONAIS,i+1,"[\\>\\<\\=]|<>|>=|<=");
                  tokens.add(t3);
                    ch = current_last;
                    
                  break;
                  
                  case DEL_COMENT:
                      
                   current_last = delimitadorComentario(ch,temp);                
                   String novo6 = temp.substring(ch, current_last+1);
                   Token t7 = new Token(novo6, DEL_COMENT,i+1,"^\\{.*\\}$");
                   tokens.add(t7);
                        ch = current_last;
                        ch++;
                        
                  break;
                  
                  case DELIMITADORES:
                      if((int) temp.charAt(ch) != 32 && (int) temp.charAt(ch) != 9){
                  String novoD = temp.substring(ch, current_last+1);
                  Token tD = new Token(novoD, DELIMITADORES,i+1,true);
                  tokens.add(tD);
                  }
                  ch++;
                 
                  break;
                  
                  case DESCONHECIDO:
                  current_last = delimitadorDesconhecido(ch,temp);
                  String novo7 = temp.substring(ch, current_last);
                  Token t8 = new Token(novo7, DESCONHECIDO,i+1,false);
                  tokens.add(t8);
                  
                  ch = current_last;
                      
                      break;
                  
           
          }
       
        }
        separaDelimitador(); 
 }
   
    }
    /**
     * Separa os lexemas que são palavras reservadas ou operadores lógicos que estão como tipo Identificador
     */
    private void separaDelimitador(){
        
        for (Token token : tokens) {
            if (token.getNome().matches("programa|const|var|funcao|inicio|fim|se|entao|senao|enquanto|faca|leia|escreva|inteiro|real|booleano|verdadeiro|falso|cadeia|caractere")){
                token.setTipo(PALAVRAS_RESERVADAS);                
            }
            if(token.getNome().matches("nao|e|ou")){
                token.setTipo(LOGICOS);                
            }
        }
        
    }
    /**
     * Diz de qual tipo-token é o lexema referente ao caractere inicial
     * @param cod código ASCII do caractere
     * @param index índice da linha
     * @param temp Linha do arquivo
     * @return 
     */
    private int isToken(int cod, int index, String temp){
        
        if(isLetra(cod)) return IDENTIFICADOR; // Identificador,palavras reservadas, 
        else if(cod == 34) return CADEIA_DE_CARACTERES;
        else if(cod == 39) return CARACTER;
        else if(cod == 45){
            int a;
                    if(index + 1 < temp.length()){
                     a = temp.charAt( index + 1);
                     if(isNumero(a)) return NUMERO;
                     else return ARITMETICOS;
                    }
                    return ARITMETICOS;
                 }
        else if(isNumero(cod)) return NUMERO;
        else if(cod == 43 || cod == 42 || cod == 47) return ARITMETICOS;
        else if(cod >=60 && cod <= 62) return RELACIONAIS;
        else if(cod == 123) return DEL_COMENT;
        else if(cod == 40 || cod == 32 || cod == 59 || cod == 44 || cod == 41 || cod == 125 || cod == 9) return DELIMITADORES;
        
         
        else return DESCONHECIDO;// 
    }
    
    /**
     * 
     * @param cod
     * @return 
     */
    private int isToken(int cod){
        
        if(isLetra(cod)) return IDENTIFICADOR; // Identificador,palavras reservadas, 
        else if(cod == 34) return CADEIA_DE_CARACTERES;
        else if(cod == 39) return CARACTER;
        else if(isNumero(cod)) return NUMERO;
        else if(cod == 43 || cod == 42 || cod == 47|| cod == 45) return ARITMETICOS;
        else if(cod >=60 && cod <= 62) return RELACIONAIS;
        else if(cod == 123) return DEL_COMENT;
        else if(cod == 40 || cod == 41 || cod == 32 || cod == 59 || cod == 44 || cod == 125|| cod == 9) return DELIMITADORES;
        
         
        else return DESCONHECIDO;// 
    }
    
    
    
    
    private boolean isLetra(int c){
        return (c>= 97 && c <=122) || (c>= 65 && c <=90);
    }
    
    private boolean isNumero(int c){
        return c>= 48 && c <=57;
    }
    
    private int delimitadorId(int ch, String temp){// Retorna o indice do primeiro delimitador encontrado.
        int a; 
        while(ch < temp.length()){
            a = temp.charAt(ch);
        if(a == 32 || a == 59||a == 44|| a == 43||a == 45||a == 42||a == 47||a == 60|| a == 62|| a == 61|| a == 34||a == 39 || a == 123 || a == 40 || a == 125 || a == 41 || a == 9 ){
            return ch;
        }
        ch++;
        
        }
        return temp.length();
    }
    
    private int delimitadorRelacional(int ch, String temp){// Retorna o indice do primeiro delimitador encontrado.
        int a;
        while(ch < temp.length()){
              a = temp.charAt(ch);
        if(a != 60 && a != 61 && a != 62){
            return ch;
        }
        ch++;
      
        }
        return temp.length();
    }
    
    private int delimitadorNumero(int ch, String temp){// Retorna o indice do primeiro delimitador encontrado.
        int a;
        if(temp.charAt(ch) == 45) ch++;
        while(ch < temp.length()){
            a = temp.charAt(ch);
        if(a == 32 || a == 59|| a == 45 ||a == 44|| a == 43||a == 42||a == 47|| a == 34||a == 39||a == 123||a == 40||a == 60|| a == 62|| a == 61 || a == 125 || a == 41 || a == 9){
            return ch;
        }
        ch++;
        
        }
        return temp.length();
    }
    
    private int delimitadorComentario(int ch, String temp){// Retorna o indice do primeiro delimitador encontrado.
        int a;
        int retorno = -1;
        while(ch < temp.length()){
            a = temp.charAt(ch);
        if(a == 125){// Até encontrar o fecha chaves de comentátio
            retorno = ch;
        }
        ch++;
       //a = temp.charAt(ch);
        }
         if(retorno != -1) return retorno;
        
        return temp.length() - 1;
    }
    
    private int delimitadorDesconhecido(int ch, String temp){// Retorna o indice do primeiro delimitador encontrado.
        int a; 
        while(ch < temp.length()){
            a = temp.charAt(ch);
        if(a == 32 || a== 59 || a == 44 || a == 40 || a == 123 || a == 34 || a == 39|| a == 42 ||a == 43|| a == 45 || a == 47 || a == 60 || a == 61 || a == 62 || a == 125 || a == 41 || a == 9){
            return ch;
        }
        ch++;
        //a = temp.charAt(ch);
        }
        return temp.length();
    }
    
    private int delimitadorCadeia(int ch, String temp){// Retorna o indice do primeiro delimitador encontrado.
       
        int b = ch+1;
         int a;
        while(b < temp.length()){
            a = temp.charAt(b);
        if(a == 34) return b;            
        
        b++;
        
        }
        return temp.length() - 1;
    }
    
    private int delimitadorCaracter(int ch, String temp){// Retorna o indice do primeiro delimitador encontrado.
        int b = ch+1;
        int a;
        while(b < temp.length()){
            a = temp.charAt(b);
        if(a == 39) return b;            
            b++;
        }
        return temp.length() - 1;
    }
    
    public void imprimiTokens(){
        System.out.println(tokens);
    }
    
    public void mostrarLinhas(){
        System.out.println(linhas_arq);
    }
    
    
   
    
    
    
}
