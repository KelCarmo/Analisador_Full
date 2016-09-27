/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.uefs.br;

/**
 *
 * @author kocarmo
 */
public class A_Sintatica {
    
    private String cadeia;
    private static Token token;
    
    public static void Program(){
        delaracao_var_global();
        delaracao_programa();
        funcoes();
    }
    
    public static void lerProximoToken(){
        
    }
    
    public static void delaracao_var_global(){
        if(token.getNome().equals("const")) {
            DEC_CONST();
            delaracao_var_global();
        }
        else if(token.getNome().equals("var")){
            DEC();
            delaracao_var_global();
        }
        else{
            System.err.print("SYNTAX.ERROR!!!");
        }
       
    }
    
    public static void delaracao_programa(){
        if(token.getNome().equals("programa")){
            lerProximoToken();
            tipo();
            id();
            if(token.getNome().equals("(")){
                lerProximoToken();
                parametro_programa();
                if(token.getNome().equals(")")){
                    lerProximoToken();
                    if(token.getNome().equals("inicio")){
                        lerProximoToken();
                        BXR();
                        if(token.getNome().equals("fim")){
                            lerProximoToken();
                            if(token.getNome().equals("(")){
                                lerProximoToken();
                                RETORNO();
                                if(token.getNome().equals(")")){
                                    lerProximoToken();
                                    if(token.getNome().equals(";")){
                                        lerProximoToken();
                                    }else{
                                        //ERRO
                                    }
                                }else{
                                    //ERRO
                                }
                            }else{
                                //ERRO
                            }
                        }else{
                            //ERRO
                        }
                    }else{
                        //ERRO
                    }
                }else{
                    //ERRO
                }
            }else{
                //ERRO
            }
            
        }else {
            //Erro
        }
    }
    
    public static void RETORNO(){
        
    }
    
    public static void BXR(){
        
    }
    
    public static void parametro_programa(){
        
    }
    
    public static void funcoes(){
        Funcao();
        FX();
    }
    
    public static void FX(){
        Funcao();
    }
    
    public static void DEC_CONST(){
        if(token.getNome().equals("const")){
            lerProximoToken();
            tipo();
            id();
            if(token.getNome().equals("=")) {
            lerProximoToken();
            DEC_CONST2();
            if(token.getNome().equals(";")) lerProximoToken();
            else ; //ERRO
        }
            else ;//ERRO
        }else ;//EROO
        
         
    }
    
    public static void tipo(){
        if(token.getNome().equals("inteiro") || token.getNome().equals("cadeia") || token.getNome().equals("real") || token.getNome().equals("booleano") || token.getNome().equals("caractere")){
            lerProximoToken();
        }
        else ;//ERRO
        
    }
    
     public static void id(){
        if(token.getTipo() == 2) {
            id();
            lerProximoToken();
        }
        else ;//ERRO
        
    }
     
     public static void DEC(){
         
     }
     
     public static void DEC_CONST2(){         
        if(token.getTipo()== 3 || token.getTipo() == 9 || token.getTipo()==10) lerProximoToken();
        else ;//ERRO
     }
     
     public static void Funcao(){
         
     }
    
    
}
