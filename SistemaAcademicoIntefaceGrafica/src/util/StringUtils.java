package util;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    
    public static int quantidadeCaracteres(String str){
        return str.length();
    }
    
    public static String maiusculo(String str){
        return str.toUpperCase();
    }
    
    public static String minusculo(String str){
        return str.toLowerCase();
    }
    
    public static int quantidadeVogais(String str){
        int cont =0;
        str = str.toLowerCase();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(c == 'a' || c == 'e' || c =='i' || c =='o' || c =='u'){
                cont++;
            }
        }
        return cont;
    }
    
    public static String substituirCaracteres(String str, String c1, String c2){
        return str.replace(c1, c2);
    }
    
    public static boolean verificarStubstring(String texto, String substring){
       return texto.contains(substring);
    }
    
    public static boolean isSomenteLetras(String texto){
        String str = texto.toLowerCase();
        char caracteres[] = str.toCharArray();
        for (int i = 0; i < caracteres.length; i++) {
            if(caracteres[i] < 97 ||  caracteres[i] > 122){
                return false;
            }
        }
        return true;
        //return str.matches("[a-z]*"); //expressão regular
    }
    
    public static boolean isPalindromo(String texto){
        String reverso = new StringBuffer(texto).reverse().toString();
        return texto.equalsIgnoreCase(reverso);
    }
    
    public static String fomartarValorMoeda(float valor){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String valorFormatado = nf.format(valor);
        return valorFormatado;
    }
    
    public static String obterIniciaisNome(String nome){
        //e, do, da, dos, das, de, di,
        nome = nome.replaceAll(" e ", " ");
        nome = nome.replaceAll(" do ", " ");
        nome = nome.replaceAll(" da "," ");
        nome = nome.replaceAll(" das "," ");
        nome = nome.replaceAll(" de "," ");
        nome = nome.replaceAll(" di "," ");
        nome = nome.replaceAll(" dos "," ");
        nome = nome.replaceAll(" e "," ");
        
        nome = nome.replaceAll("\\B\\w\\s*","").toUpperCase();
//        \\B -> limita ao primeiro caractere
//        \\w -> representa qualquer caractere de palavra
//        \\W -> representa tudo que não seja um caractere de palavra
//        \\s -> representa qualquer espaço em branco(" ", tab)
        
        Pattern p = Pattern.compile("\\b[a-zA-Z]"); //pega o primeiro caractere entre [a a Z] de cada palavra 
        Matcher m = p.matcher(nome);
  
        String str = new String();
        while (m.find()){
            str+=m.group();
            System.out.print(m.group());
        }
        
        return nome;
    }
}

