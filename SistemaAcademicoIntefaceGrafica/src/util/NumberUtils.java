package util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

public class NumberUtils {

    private static DecimalFormat df = new DecimalFormat();

    public static double arredondar(double valor, int casasDecimais) {
        if (casasDecimais == 0) {
            return Math.round(valor);
        }
        valor = valor * Math.pow(10, casasDecimais) ;
        valor = Math.round(valor);
        valor = valor / Math.pow(10, casasDecimais);
        return valor;
    }

    public static double parseDouble(String valor) {
        Double val;
        //System.out.println("valor inicial: "+ val);
        if (valor.contains(",")) {
            valor = valor.replace(".", "");
            valor = valor.replace(",", ".");
        }
        return Double.parseDouble(valor);
    }

    public static float parseFloat(String valor) {
        if (valor.contains(",")) {
            valor = valor.replace(".", "");
            valor = valor.replace(",", ".");
        }
        return Float.parseFloat(valor);
    }

    public static int parseInt(String valor) {
        double val = parseDouble(valor);
        return (int) Math.round(val);
    }

    public static long parseLong(String valor) {
        double val = parseDouble(valor);
        return Math.round(val);
    }

    public static void ordenarLista(ArrayList<Integer> numeros, boolean ordemCrescente) {
        if (ordemCrescente) {
            Collections.sort(numeros);
        } else {
            Collections.sort(numeros, Collections.reverseOrder());
            //Collections.reverse(numeros);
        }
    }

    public static String formatarValorMonetario(double valor) {
        return DecimalFormat.getCurrencyInstance().format(valor);
    }
    
    public static String formatarValor(double valor, int casasDecimais) {
        df.setMinimumFractionDigits(casasDecimais);
        df.setMaximumFractionDigits(casasDecimais);
        return df.format(valor);
    }

    public static String formatarValorPorcentagem(double valor, int casasDecimais) {
        valor = valor / 100;
        NumberFormat formatPorcentagem = NumberFormat.getPercentInstance();
        formatPorcentagem.setMinimumFractionDigits(casasDecimais);
        formatPorcentagem.setMaximumFractionDigits(casasDecimais);

        return formatPorcentagem.format(valor);
    }

    public static int menorValor(ArrayList<Integer> list) {
        Collections.sort(list);
        return list.get(0);
    }

    public static int maiorValor(ArrayList<Integer> list) {
        Collections.sort(list, Collections.reverseOrder());
        return list.get(0);
    }

    public static double mediaValores(ArrayList<Integer> list) {
        double soma = 0;
        double media = 0;
        for (int i = 0; i < list.size(); i++) {
            soma += list.get(i);
        }
        media = soma / list.size();
        return media;
    }

    public static double mediaPonderadaValores(ArrayList<Integer> valores, ArrayList<Integer> pesos) {
        double soma = 0;
        double somaPesos = 0;
        double media = 0;
        for (int i = 0; i < valores.size(); i++) {
            soma += (valores.get(i) * pesos.get(i));
            somaPesos += pesos.get(i);
        }
        media = soma / somaPesos;
        return media;
    }

    public static double medianaValores(ArrayList<Integer> valores) {
        Collections.sort(valores);
        if (valores.size() % 2 == 0) { //valor Par
            int pos1 = (int) Math.floor(valores.size() / 2);
            int pos2 = pos1 - 1;
            return (valores.get(pos1) + valores.get(pos2)) / 2;
        } else {
            int pos = (int) Math.floor(valores.size() / 2);
            return valores.get(pos);
        }
    }

    public static ArrayList<Integer> moda(ArrayList<Integer> valores) {
        ArrayList<Integer> moda = new ArrayList<>();
        if (valores == null || valores.isEmpty()) {
            return moda;
        }

        int maiorQtdRepeticao = 0;
        int qtd = 0;
        Collections.sort(valores);
        for (int i = 1; i < valores.size(); i++) {
            int num = valores.get(i);
            if (num == valores.get(i - 1)) {
                qtd++;
                if (qtd > maiorQtdRepeticao) {
                    maiorQtdRepeticao = qtd;
                }
            } else {
                qtd = 0;
            }
        }

        qtd = 0;
        for (int i = 1; i < valores.size(); i++) {
            int num = valores.get(i);
            if (num == valores.get(i - 1)) {
                qtd++;
                if (qtd == maiorQtdRepeticao) {
                    moda.add(valores.get(i - 1));
                }
            } else {
                qtd = 0;
            }
        }

        return moda;
    }
}

