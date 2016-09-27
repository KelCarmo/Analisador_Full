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
        else{//Produção Vazia
            return;
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
        if(token.getNome().equals("nao") || token.getNome().equals("-") || token.getTipo()==2 ||
                token.getTipo()==3 || token.getNome().equals("(") || token.getNome().equals("verdadeiro") || token.getNome().equals("falso")){
            expressao_booleana();                                    
        }else{
            if(token.getTipo() == 9 || token.getTipo() ==10){
                lerProximoToken();                
            }else{//Produção vazia
                return;
            }
        }
    }
    
    public static void expressao_booleana(){
        Aux_Expression();
        expressao_booleanaR();
    }
    
    public static void  expressao_booleanaR(){
        if(token.getNome().equals("e") || token.getNome().equals("ou")){
            lerProximoToken();
            expressao_booleana();
        }else{//Produção vazia
            return;
        }
    }
    
    public static void Aux_Expression(){
        nao_expressao_aritmetica();
        Aux_ExpressionR();
    }
    
    public static void nao_expressao_aritmetica(){
        if(token.getNome().equals("nao")){
            lerProximoToken();
            expressao_aritmetica();
        }else{
            if(token.getNome().equals("-") || token.getTipo()==2 || token.getTipo()==3 || token.getNome().equals("(") || 
                    token.getNome().equals("verdadeiro") || token.getNome().equals("falso")){
                expressao_aritmetica();
            }else{
                //ERRO!
            }
        }
    }
    
    public static void expressao_aritmetica(){
        Mult_Exp();
        expressao_artimeticaR();
        
    }
    public static void Mult_Exp(){
        Neg_Exp();
        Mult_ExpR();
    }
    
    public static void Neg_Exp(){
        if(token.getNome().equals("-")){
            lerProximoToken();
            Valor();
        }else{
            if(token.getTipo()==2 || token.getTipo()==3 || token.getNome().equals("(") || 
                    token.getNome().equals("verdadeiro") || token.getNome().equals("falso")){
                Valor();
            }else{
                //ERRO
            }
        }
    }
    
    public static void Valor(){
        if(token.getTipo()==2){
            id();
            aux_valor1();
        }else{
            if(token.getTipo()==3){
                lerProximoToken();
            }else{
                if(token.getNome().equals("(")){
                    lerProximoToken();
                    expressao_booleana();
                    if(token.getNome().equals(")")){
                        lerProximoToken();
                    }else{
                        //ERRO!
                    }
                }else{
                    if(token.getNome().equals("verdadeiro") || token.getNome().equals("falso")){
                        lerProximoToken();
                    }else{
                        //ERRO
                    }
                }
            }
    }
    }
    
    public static void aux_valor1(){
        if(token.getNome().equals("(")){
            lerProximoToken();
            aux_valor2();
        }else{//Produção vazia
            return;
        }
    }    
    public static void aux_valor2(){
        if(token.getNome().equals("(")){
            lerProximoToken();
            aux_valor3();
        }else{
            if(token.getTipo() == 9 || token.getNome().equals("nao") ||token.getNome().equals("-") || token.getTipo()==2 || token.getTipo()==3 || token.getNome().equals("(") || 
                    token.getNome().equals("verdadeiro") || token.getNome().equals("falso") || token.getTipo() == 10){
                parametro();
                if(token.getNome().equals(")")){
                    lerProximoToken();
                }else{
                    //ERRO
                }
                
            }else{
                //ERRO
            }
        }
    }
    
    public static void aux_valor3(){
        Valor();
        if(token.getNome().equals(")")){
            lerProximoToken();
            if(token.getNome().equals(")")){
               lerProximoToken();
               aux_valor4();
            }else{
                //ERRO
            }
        }else{
            //ERRO
        }
    }
    
    public static void parametro(){
        if(token.getTipo() == 9){
            lerProximoToken();
            R();
        }else{
            if(token.getNome().equals("nao") ||token.getNome().equals("-") || token.getTipo()==2 || token.getTipo()==3 || token.getNome().equals("(") || 
                    token.getNome().equals("verdadeiro") || token.getNome().equals("falso")){
                expressao_booleana();
                R();
            }else{
                if(token.getTipo() == 10){
                    lerProximoToken();
                    R();
                }
                else{//Produção vazia
                    return;
                }
            }
        }
    }
    
    public static void R(){
        if(token.getNome().equals(",")){
            lerProximoToken();
            parametro();
        }else{//Produção vazia
            return;
        }
    }
    
    public static void Attr(){
        id();
        aux_valor1();
        AttR1();
    }
    
    public static void AttR1(){
        if(token.getNome().equals("=")){
            lerProximoToken();
            AttR2();
        }else{
            if(token.getNome().equals(";")){
                lerProximoToken();
            }else{
                //ERRO
            }
        }
    }
    
    public static void AttR2(){
        if(token.getNome().equals("-") || token.getTipo()==2 || token.getTipo()==3 || token.getNome().equals("(") || 
                    token.getNome().equals("verdadeiro") || token.getNome().equals("falso")){
            expressao_aritmetica();
            if(token.getNome().equals(";")){
                lerProximoToken();
            }else{
                //Erro
            }
        }else{
            if(token.getTipo() == 9){
                lerProximoToken();
                if(token.getNome().equals(";")){
                    lerProximoToken();
                }else{
                    //ERRO
                }
            }else{
                if(token.getTipo() == 10){
                    lerProximoToken();
                    if(token.getNome().equals(";")){
                    lerProximoToken();
                    }else{
                        //ERRO
                    }
                }else{
                    //ERRO
                }
            }
        }
    }
    
    public static void aux_valor4(){
        if(token.getNome().equals("(")){
            lerProximoToken();
            if(token.getNome().equals("(")){
                lerProximoToken();
                Valor();
                if(token.getNome().equals(")")){
                    lerProximoToken();
                    if(token.getNome().equals(")")){
                        lerProximoToken();
                        aux_valor4();
                    }
                }
            }
        }else{//Produção vazia
            return;
        }            
    }
    
    public static void Enquanto(){
        if(token.getNome().equals("equanto")){
            lerProximoToken();
            if(token.getNome().equals("(")){
            lerProximoToken();
            expressao_booleana();
            if(token.getNome().equals(")")){
                lerProximoToken();
                if(token.getNome().equals("faca")){
                    lerProximoToken();
                    bloco_de_codigo();
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
    }
    
    public static void se_entao_senao(){
        if(token.getNome().equals("se")){
            lerProximoToken();
            if(token.getNome().equals("(")){
                lerProximoToken();
                expressao_booleana();
                if(token.getNome().equals(")")){
                    lerProximoToken();
                    if(token.getNome().equals("entao")){
                        lerProximoToken();
                        bloco_de_codigo();
                        SE();
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
    }
    
    public static void SE(){
        if(token.getNome().equals("senao")){
            NEGACAO();
        }else{//Produção vazia
           return;
        }
    }
    
    public static void NEGACAO(){
        if(token.getNome().equals("senao")){
            lerProximoToken();
            bloco_de_codigo();
        }
        else{
            //ERRO
        }
    }
    
    
    
    public static void Mult_ExpR(){
        if(token.getNome().equals("*") || token.getNome().equals("/")){
            lerProximoToken();
            Mult_Exp();
        }else{//Produção vazia
            return;
        }
    }
    
    
    public static void expressao_artimeticaR(){
        if(token.getNome().equals("+") || token.getNome().equals("-")){
            lerProximoToken();
            expressao_aritmetica();
        }else{//Produção vazia
            return;
        }
    }
    public static void Aux_ExpressionR(){
        if(token.getNome().equals(">") || token.getNome().equals("<") || token.getNome().equals("<=") || token.getNome().equals(">=")
                || token.getNome().equals("=") || token.getNome().equals("<>")){
            lerProximoToken();
            Aux_Expression();
        }else{//Produção vazia
            return;
        }
    }
    
    public static void BXR(){
        
    }
    
    public static void parametro_programa(){/*DEU MERDA*/
       if(token.getNome().equals("inteiro") || token.getNome().equals("cadeia") || token.getNome().equals("real") || token.getNome().equals("booleano") || token.getNome().equals("caractere")){
            id();
            X();
        }else{
           return;//Produção vazia
       }
    }
    
    public static void X(){
         if(token.getNome().equals(",")){
             parametro3();
         }else{//Produção vazia
             return;
         }
    }
    
    public static void parametro3(){
        if(token.getNome().equals(",")){
            lerProximoToken();
            parametro_programa();
        }else{
            //ERRO
        }
    }
    
    public static void funcoes(){
        if(token.getNome().equals("funcao")){
            Funcao();
            FX();
        }else{
            return;//Produção vazia
        }
    }
    
    public static void FX(){
        if(token.getNome().equals("funcao")){
            Funcao();
        }else{//Produção Vazia
            return;
        }
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
            lerProximoToken();
        }
        else {
            //ERRO
        }
    }
     
     public static void DEC(){
         if(token.getNome().equals("var")){
             lerProximoToken();
             tipo();
             id();
             A();
             V1();
             if(token.getNome().equals(";")){
                 lerProximoToken();
             }else{
                 //ERRO
             }
         }else{
             //ERRO
         }
     }
     
     public static void V1(){
         if(token.getNome().equals(",")){
             lerProximoToken();
             id();
             A();
             V1();
             
         }else{//Produção vazia
             return;
         }
     }
     
     public static void A(){
         if(token.getNome().equals("(")){
             lerProximoToken();
             if(token.getNome().equals("(")){
                 lerProximoToken();
                 if(token.getTipo() == 3){
                     lerProximoToken();
                     if(token.getNome().equals(")")){
                          lerProximoToken();
                        if(token.getNome().equals(")")){
                            lerProximoToken();
                            A();
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
     }else{//Produção vazia
             return;
         }
     }
     
     public static void DEC_CONST2(){         
        if(token.getTipo()== 3 || token.getTipo() == 9 || token.getTipo()==10) lerProximoToken();
        else ;//ERRO
     }
     
     public static void Funcao(){
         
     }
    
    
}
