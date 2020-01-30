/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Diego
 * 
 * Porque criamos essa classe
 * 
 * quando passamos um parametro no get e vamos usa uma lista que recebe numeros inteiros
 * para isso precisamos fazer uma espécie de parse na passagem dos parâmetros
 * porque senão ele meio que considera como Double lá na url
 * algo como:
 * pages/categorias?produtos=1,2,3 entende. 
 * essa classe pega a lista e divide os strings e converte como se fosse um substring
 * 
 * 
 * 
 */
public class URL {
    //Veja que também precisamos tratar o encoding que é o espaço da url que ela sempre preenche com %20
    //EX: encodeURIComponent("tv led") no F12 ou na barra de endereços
    //ele retorna TV%20LED
    //Vamos criar um método decode
    
    public static String decodeParam(String s){
        try {
            return URLDecoder.decode(s,"UTF-8");
        } catch (UnsupportedEncodingException ex) {
          return "";
        }
    }
    
    //subindo
    
    public static List<Integer> decodeIntList(String s){
        String[] vet = s.split(",");
        List<Integer> list = new ArrayList<>();
        for(int i = 0;i < vet.length;i++){
            list.add(Integer.parseInt(vet[i]));
        }
        return list;
        //Agora com java 8 é possível fazer o laço tudo que foi feito acima de outra forma
        //return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
        
    }
    
}
