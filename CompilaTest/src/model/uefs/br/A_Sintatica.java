/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.uefs.br;

import java.util.ArrayList;

/**
 *
 * @author kocarmo
 */
public class A_Sintatica {
    
    
    private static  int index;
    private static ArrayList<Token> tokens;
    private static Token current ;
    
    
    public static void analisarSintaxe(ArrayList<Token> tokens){
        A_Sintatica.tokens = tokens;
        A_Sintatica.index = 0;
        A_Sintatica.current = tokens.get(index);
        
        Program();
    }
    
    
    private static void Program(){
        declaracao_var_global();
        declaracao_programa();
        funcoes();
    }
    
    private static void ERRO(){
        int a = 0;
        while(true){
            if(a == 0) {
                System.err.print("ERRO !!! Token nao esperado: " + A_Sintatica.current.getNome() + A_Sintatica.current.getLinha());
            a=1;
        }
        }
    }
    
    private static void lerProximoToken(){
              A_Sintatica.index++;
              
      current = tokens.get(index);
      
    
    }
    
    private static void declaracao_var_global(){
        if(current.getNome().equals("const")) {
            DEC_CONST();
            declaracao_var_global();
        }
        else if(current.getNome().equals("var")){
            DEC();
            declaracao_var_global();
        }
        else{//Produção Vazia
            return;
        }
        
       
    }
    
    private static void declaracao_programa(){
        if(current.getNome().equals("programa")){
            lerProximoToken();
            tipo();
            id();
            if(current.getNome().equals("(")){
                lerProximoToken();
                parametro_programa();
                if(current.getNome().equals(")")){
                    lerProximoToken();
                    if(current.getNome().equals("inicio")){
                        lerProximoToken();
                        BXR();
                        if(current.getNome().equals("fim")){
                            lerProximoToken();
                            if(current.getNome().equals("(")){
                                lerProximoToken();
                                RETORNO();
                                if(current.getNome().equals(")")){
                                    lerProximoToken();
                                    if(current.getNome().equals(";")){
                                        lerProximoToken();
                                    }else{
                                        ERRO();
                                    }
                                }else{
                                    ERRO();
                                }
                            }else{
                                ERRO();
                            }
                        }else{
                            ERRO();
                        }
                    }else{
                        ERRO();
                    }
                }else{
                    ERRO();
                }
            }else{
                ERRO();
            }
            
        }else {
            ERRO();
        }
    }
    
    private static void RETORNO(){
        if(current.getNome().equals("nao") || current.getNome().equals("-") || current.getTipo()==2 ||
                current.getTipo()==3 || current.getNome().equals("(") || current.getNome().equals("verdadeiro") || current.getNome().equals("falso")){
            expressao_booleana();                                    
        }else{
            if(current.getTipo() == 9 || current.getTipo() ==10){
                lerProximoToken();                
            }else{//Produção vazia
                return;
            }
        }
    }
    
    private static void expressao_booleana(){
        Aux_Expression();
        expressao_booleanaR();
    }
    
    private static void  expressao_booleanaR(){
        if(current.getNome().equals("e") || current.getNome().equals("ou")){
            lerProximoToken();
            expressao_booleana();
        }else{//Produção vazia
            return;
        }
    }
    
    private static void Aux_Expression(){
        nao_expressao_aritmetica();
        Aux_ExpressionR();
    }
    
    private static void nao_expressao_aritmetica(){
        if(current.getNome().equals("nao")){
            lerProximoToken();
            expressao_aritmetica();
        }else{
            if(current.getNome().equals("-") || current.getTipo()==2 || current.getTipo()==3 || current.getNome().equals("(") || 
                    current.getNome().equals("verdadeiro") || current.getNome().equals("falso")){
                expressao_aritmetica();
            }else{
                ERRO();
            }
        }
    }
    
    private static void expressao_aritmetica(){
        Mult_Exp();
        expressao_artimeticaR();
        
    }
    private static void Mult_Exp(){
        Neg_Exp();
        Mult_ExpR();
    }
    
    private static void Neg_Exp(){
        if(current.getNome().equals("-")){
            lerProximoToken();
            Valor();
        }else{
            if(current.getTipo()==2 || current.getTipo()==3 || current.getNome().equals("(") || 
                    current.getNome().equals("verdadeiro") || current.getNome().equals("falso")){
                Valor();
            }else{
                ERRO();
            }
        }
    }
    
    private static void Valor(){
        if(current.getTipo()==2){
            id();
            aux_valor1();
        }else{
            if(current.getTipo()==3){
                lerProximoToken();
            }else{
                if(current.getNome().equals("(")){
                    lerProximoToken();
                    expressao_booleana();
                    if(current.getNome().equals(")")){
                        lerProximoToken();
                    }else{
                        ERRO();
                    }
                }else{
                    if(current.getNome().equals("verdadeiro") || current.getNome().equals("falso")){
                        lerProximoToken();
                    }else{
                        ERRO();
                    }
                }
            }
    }
    }
    
    private static void aux_valor1(){
        if(current.getNome().equals("(")){
            lerProximoToken();
            aux_valor2();
        }else{//Produção vazia
            return;
        }
    }    
    private static void aux_valor2(){
        if(current.getNome().equals("(")){
            lerProximoToken();
            aux_valor3();
        }else{
            if(current.getTipo() == 9 || current.getNome().equals("nao") ||current.getNome().equals("-") || current.getTipo()==2 || current.getTipo()==3 || current.getNome().equals("(") || 
                    current.getNome().equals("verdadeiro") || current.getNome().equals("falso") || current.getTipo() == 10){
                parametro();
                if(current.getNome().equals(")")){
                    lerProximoToken();
                }else{
                    ERRO();
                }
                
            }else{
                ERRO();
            }
        }
    }
    
    private static void aux_valor3(){
        Valor();
        if(current.getNome().equals(")")){
            lerProximoToken();
            if(current.getNome().equals(")")){
               lerProximoToken();
               aux_valor4();
            }else{
                ERRO();
            }
        }else{
            ERRO();
        }
    }
    
    private static void parametro(){
        if(current.getTipo() == 9){
            lerProximoToken();
            R();
        }else{
            if(current.getNome().equals("nao") ||current.getNome().equals("-") || current.getTipo()==2 || current.getTipo()==3 || current.getNome().equals("(") || 
                    current.getNome().equals("verdadeiro") || current.getNome().equals("falso")){
                expressao_booleana();
                R();
            }else{
                if(current.getTipo() == 10){
                    lerProximoToken();
                    R();
                }
                else{//Produção vazia
                    return;
                }
            }
        }
    }
    
    private static void R(){
        if(current.getNome().equals(",")){
            lerProximoToken();
            parametro();
        }else{//Produção vazia
            return;
        }
    }
    
    private static void Attr(){
        id();
        aux_valor1();
        AttR1();
    }
    
    private static void AttR1(){
        if(current.getNome().equals("=")){
            lerProximoToken();
            AttR2();
        }else{
            if(current.getNome().equals(";")){
                lerProximoToken();
            }else{
                ERRO();
            }
        }
    }
    
    private static void AttR2(){
        if(current.getNome().equals("-") || current.getTipo()==2 || current.getTipo()==3 || current.getNome().equals("(") || 
                    current.getNome().equals("verdadeiro") || current.getNome().equals("falso")){
            expressao_aritmetica();
            if(current.getNome().equals(";")){
                lerProximoToken();
            }else{
                ERRO();
            }
        }else{
            if(current.getTipo() == 9){
                lerProximoToken();
                if(current.getNome().equals(";")){
                    lerProximoToken();
                }else{
                    ERRO();
                }
            }else{
                if(current.getTipo() == 10){
                    lerProximoToken();
                    if(current.getNome().equals(";")){
                    lerProximoToken();
                    }else{
                        ERRO();
                    }
                }else{
                    ERRO();
                }
            }
        }
    }
    
    private static void aux_valor4(){
        if(current.getNome().equals("(")){
            lerProximoToken();
            if(current.getNome().equals("(")){
                lerProximoToken();
                Valor();
                if(current.getNome().equals(")")){
                    lerProximoToken();
                    if(current.getNome().equals(")")){
                        lerProximoToken();
                        aux_valor4();
                    }
                }
            }
        }else{//Produção vazia
            return;
        }            
    }
    
    private static void Enquanto(){
        if(current.getNome().equals("enquanto")){
            lerProximoToken();
            if(current.getNome().equals("(")){
            lerProximoToken();
            expressao_booleana();
            if(current.getNome().equals(")")){
                lerProximoToken();
                if(current.getNome().equals("faca")){
                    lerProximoToken();
                    bloco_de_codigo();
                }else{
                    ERRO();
                }
            }else{
                ERRO();
            }
            }else{
                ERRO();
            }
        }else{
           ERRO();
        }
    }
    
    private static void se_entao_senao(){
        if(current.getNome().equals("se")){
            lerProximoToken();
            if(current.getNome().equals("(")){
                lerProximoToken();
                expressao_booleana();
                if(current.getNome().equals(")")){
                    lerProximoToken();
                    if(current.getNome().equals("entao")){
                        lerProximoToken();
                        bloco_de_codigo();
                        SE();
                    }else{
                        ERRO();
                    }
                }else{
                    ERRO();
                }
            }else{
                ERRO();
            }
        }else{
            ERRO();
        }                
    }
    
    private static void SE(){
        if(current.getNome().equals("senao")){
            NEGACAO();
        }else{//Produção vazia
           return;
        }
    }
    
    private static void NEGACAO(){
        if(current.getNome().equals("senao")){
            lerProximoToken();
            bloco_de_codigo();
        }
        else{
            ERRO();
        }
    }
    
    
    
    private static void Mult_ExpR(){
        if(current.getNome().equals("*") || current.getNome().equals("/")){
            lerProximoToken();
            Mult_Exp();
        }else{//Produção vazia
            return;
        }
    }
    
    
    private static void expressao_artimeticaR(){
        if(current.getNome().equals("+") || current.getNome().equals("-")){
            lerProximoToken();
            expressao_aritmetica();
        }else{//Produção vazia
            return;
        }
    }
    
    private static void Aux_ExpressionR(){
        if(current.getNome().equals(">") || current.getNome().equals("<") || current.getNome().equals("<=") || current.getNome().equals(">=")
                || current.getNome().equals("=") || current.getNome().equals("<>")){
            lerProximoToken();
            Aux_Expression();
        }else{//Produção vazia
            return;
        }
    }
    
    private static void bloco_de_codigo(){
        if(current.getNome().equals("inicio")){
            lerProximoToken();
            BXR();
             if(current.getNome().equals("fim")){
                 lerProximoToken();
             }else{
                 ERRO();
             }
        }else{
            ERRO();
        }
    }
    
    private static void BXR(){
        BX();
        BXR2();
    }
    
     private static void BXR2(){
        if(current.getNome().equals("se") || current.getNome().equals("var")|| current.getTipo() == 2 || current.getNome().equals("enquanto")
           || current.getNome().equals("leia") || current.getNome().equals("escreva")){
            BXR();
        }else{//Produção vazia
            return;
        }
    }
    
    private static void BX(){
        if(current.getNome().equals("se")){
            se_entao_senao();
        }else{
            if(current.getNome().equals("var")){
                DEC();
            }else{
                if(current.getTipo() == 2){
                    Attr();
                }else{
                    if(current.getNome().equals("enquanto")){
                        Enquanto();
                    }else{
                        if(current.getNome().equals("leia")){
                            Leia();
                        }else{
                            if(current.getNome().equals("escreva")){
                                Escreva();
                            }else{
                                ERRO();
                            }
                        }                        
                    }
                }
            }
        }
    }
    
    private static void parametro_programa(){/*DEU MERDA*/
       if(current.getNome().equals("inteiro") || current.getNome().equals("cadeia") || current.getNome().equals("real") || current.getNome().equals("booleano") || current.getNome().equals("caractere")){
            id();
            X();
        }else{
           return;//Produção vazia
       }
    }
    
    private static void X(){
         if(current.getNome().equals(",")){
             parametro3();
         }else{//Produção vazia
             return;
         }
    }
    
    private static void parametro3(){
        if(current.getNome().equals(",")){
            lerProximoToken();
            parametro_programa();
        }else{
            ERRO();
        }
    }
    
    private static void funcoes(){
        if(current.getNome().equals("funcao")){
            Funcao();
            FX();
        }else{
            return;//Produção vazia
        }
    }
    
    private static void FX(){
        if(current.getNome().equals("funcao")){
            Funcao();
        }else{//Produção Vazia
            return;
        }
    }
    
    private static void DEC_CONST(){
        if(current.getNome().equals("const")){
            lerProximoToken();
            tipo();
            id();
            if(current.getNome().equals("=")) {
            lerProximoToken();
            DEC_CONST2();
            if(current.getNome().equals(";")) {
                lerProximoToken();
            }else {
                ERRO();
            }
        }else {
            ERRO();
            }
        }else {
            ERRO();
        }
        
         
    }
    
    private static void tipo(){
        if(current.getNome().equals("inteiro") || current.getNome().equals("cadeia") || current.getNome().equals("real") || current.getNome().equals("booleano") || current.getNome().equals("caractere")){
            lerProximoToken();
            
        }
        else {
            
            ERRO();
        }
        
    }
    
     private static void id(){
        if(current.getTipo() == 2) {            
            lerProximoToken();
        }
        else {
            ERRO();
        }
    }
     
     private static void DEC(){
         if(current.getNome().equals("var")){
             lerProximoToken();
             //System.out.print("PASSEI AQUI!");
             tipo();
             id();
             A();
             V1();
             if(current.getNome().equals(";")){
                 lerProximoToken();
             }else{
                 ERRO();
             }
         }else{
             ERRO();
         }
     }
     
     private static void V1(){
         if(current.getNome().equals(",")){
             lerProximoToken();
             id();
             A();
             V1();
             
         }else{//Produção vazia
             return;
         }
     }
     
     private static void A(){
         if(current.getNome().equals("(")){
             lerProximoToken();
             if(current.getNome().equals("(")){
                 lerProximoToken();
                 if(current.getTipo() == 3){
                     lerProximoToken();
                     if(current.getNome().equals(")")){
                          lerProximoToken();
                        if(current.getNome().equals(")")){
                            lerProximoToken();
                            A();
                        }else{
                            ERRO();
                        }
                 }else{
                        ERRO(); 
                     }
                                
             }else{
                     ERRO();
                 }
            
         }else{
                ERRO();
             }
     }else{//Produção vazia
             return;
         }
     }
     
     private static void DEC_CONST2(){         
        if(current.getTipo()== 3 || current.getTipo() == 9 || current.getTipo()==10) {
            lerProximoToken();
        }
        else {
            ERRO();
        }
     }
     
     private static void Funcao(){
         if(current.getNome().equals("funcao")){
             lerProximoToken();
             tipo();
             id();
             if(current.getNome().equals("(")){
                 lerProximoToken();
                 D();
                 if(current.getNome().equals(")")){
                     lerProximoToken();
                     if(current.getNome().equals("inicio")){
                         lerProximoToken();
                         BXR();
                         if(current.getNome().equals("fim")){
                             lerProximoToken();
                             if(current.getNome().equals("(")){
                                 lerProximoToken();
                                 RETORNO();
                                 if(current.getNome().equals(")")){
                                     lerProximoToken();
                                     if(current.getNome().equals(";")){
                                         lerProximoToken();
                                     }else{
                                         ERRO();
                                     }
                                 }else{
                                     ERRO();
                                 }
                             }else{
                                 ERRO();
                             }
                         }else{
                             ERRO();
                         }
                     }else{
                         ERRO();
                     }
                 }else{
                    ERRO();
                 }
             }else{
                 ERRO();
             }
         }else{
             ERRO();
         }
     }
     
     private static void D(){
         tipo();
         id();
         D2();
     }
     
     private static void D2(){
         if(current.getNome().equals(",")){
             Q();
         }else{//Produção vazia
             return;
         }
     }
     
      private static void Q(){
         if(current.getNome().equals(",")){
             lerProximoToken();
             D();
         }
     }
     
     private static void EXPRESSAO(){
         RETORNO();
         if(current.getNome().equals(")")){
             lerProximoToken();
             if(current.getNome().equals(";")){
                 lerProximoToken();
             }else{
                 ERRO();
             }
         }else{
             ERRO();
         }
     }
     
     private static void Escreva(){
         if(current.getNome().equals("escreva")){
             lerProximoToken();
             if(current.getNome().equals("(")){
                 lerProximoToken();
                 EXPRESSAO();
             }else{
                 ERRO();
             }
         }else{
             ERRO();
         }
     }
     
     private static void Exp(){
         id();
         aux_valor4();
         Exp2();
         
     }
     
     private static void Exp2(){
         if(current.getNome().equals(",")){
            lerProximoToken();
            Exp();            
         }else{//Produção vazia
             return;
         }
         
     }
     
     private static void Leia(){
         if(current.getNome().equals("leia")){
             lerProximoToken();
             if(current.getNome().equals("(")){
                 lerProximoToken();
                 Exp();
                 if(current.getNome().equals(")")){
                     lerProximoToken();
                     if(current.getNome().equals(";")){
                         lerProximoToken();
                     }else{
                         ERRO();
                     }
                 }else{
                    ERRO();
                 }
             }else{
                 ERRO();
             }
         }else{
             ERRO();
         }
     }
    
    
}
