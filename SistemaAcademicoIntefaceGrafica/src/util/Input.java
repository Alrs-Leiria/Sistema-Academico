package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class Input {

    private static Scanner leitor = new Scanner(System.in); //variável de leitura do teclado

    /**
     * *
     * Retorna a variável leitor para efetuar leitura do teclado
     *
     * @return variável leitor do tipo Scanner
     * @see <a href="https://www.w3schools.com/java/java_user_input.asp">java_user_input</a>
     */
    public static Scanner getLeitor() {
        return leitor;
    }

    /**
     * *
     * Retorna a variável dateFormat para efetuar a formatação e conversão de
     * Datas
     *
     * @return variável dateFormat do tipo SimpleDateFormat
     * @see
     * <a href="https://www.devmedia.com.br/trabalhando-com-as-classes-date-calendar-e-simpledateformat-em-java/27401">trabalhando-com-as-classes-date-calendar</a>
     */
    public static SimpleDateFormat getDateFormat() {
        return DateUtils.getDateFormat();
    }

    /**
     * *
     * Realiza a leitura da próxima palavra digitada no terminal, a leitura é
     * feita até encontrar um espaço em branco ou uma quebra de linha.
     *
     * @return Retorna uma String contendo a palavra que foi lida.
     */
    public static String next() {
        return leitor.next();
    }

    /**
     * *
     * Realiza a leitura da próxima linha digitada no terminal, a leitura até
     * localizar uma quebra de linha.
     *
     * @return Retorna uma String contendo o texto lido.
     */
    public static String nextLine() {
        // o comando useDelimiter("\\n") especifica para o leitor que o delimitador será a quebra de linha (\\n).
        // Desta forma o comando next() irá realizar a leitura de toda a linha digitada no terminal.
        return leitor.useDelimiter("\\n").next();
    }

    /**
     * *
     * Realiza a leitura do próxima número inteiro digitado no terminal. Caso o
     * valor informado seja inválido, será exibida uma mensagem de erro e em
     * seguida será solicitado que o número seja informado novamente.
     *
     * @return Retorna um int contendo o valor inteiro que foi lido.
     */
    public static int nextInt() {
        while (true) { // laço de repetição infinito (para garantir que só irá sair da função após a leitura de um número válido)
            String valor = "";

            //trecho de código que pode vir a gerar (lançar) uma exceção. Veja mais em:  https://www.devmedia.com.br/tratando-excecoes-em-java/25514
            try {
                //realiza a leitura do teclado e armazena na variável valor do tipo String (texto).
                valor = leitor.next();

                //converte o valor que está no formato String (texto) para o formato int. 
                //Se o processo de conversão for realizado com sucesso, o valor inteiro será retornado. Caso, haja um problema na conversão, será lancada um Exceção (Exception).  
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) { // A exceção do tipo NumberFormatException é capturada e tratada neste bloco de código
                //Quando a exceção do tipo NumberFormatException for gerada, a função irá informar o usuário e solicitar que seja digitado um valor novamente.
                System.out.println("Erro! o valor " + valor + " é inválido!");
                System.out.println("Informe um valor novamente:");
            }
        }

    }

    /**
     * *
     * Realiza a leitura do próxima número em ponto flutuante digitado no
     * terminal. Caso o valor informado seja inválido, será exibida uma mensagem
     * de erro e em seguida será solicitado que o número seja informado
     * novamente.
     *
     * @return Retorna um float contendo o número que foi lido.
     */
    public static float nextFloat() {
        while (true) {
            String valor = "";
            try {
                valor = leitor.next().replace(",", ".");
                return Float.parseFloat(valor);
            } catch (NumberFormatException e) {
                System.out.println("Erro! o valor " + valor + " é inválido!");
                System.out.println("Informe um valor novamente:");
            }
        }

    }

    /**
     * *
     * Realiza a leitura do próxima número em ponto flutuante digitado no
     * terminal. Caso o valor informado seja inválido, será exibida uma mensagem
     * de erro e em seguida será solicitado que o número seja informado
     * novamente.
     *
     * @return Retorna um double contendo o número que foi lido.
     */
    public static double nextDouble() {
        while (true) {
            String valor = "";
            try {
                valor = leitor.next().replace(",", ".");
                return Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                System.out.println("Erro! o valor " + valor + " é inválido!");
                System.out.println("Informe um valor novamente:");
            }
        }
    }

    /**
     * *
     * Realiza a leitura do próxima data digitada no terminal. Caso o valor
     * informado seja inválido, será exibida uma mensagem de erro e em seguida
     * será solicitado que a data seja informada novamente.
     *
     * @return Retorna um Date contendo a data que foi lida.
     */
    public static Date nextDate() {
        while (true) {
            String valor = leitor.next();
            Date d = DateUtils.parseDate(valor);
            if (d != null) {
                return d;
            }
        }

    }

    /**
     * *
     * Realiza a leitura do próxima data digitada no terminal. Caso o valor
     * informado seja inválido, será exibida uma mensagem de erro e em seguida
     * será solicitado que a data seja informada novamente.
     *
     * @return Retorna um LocalDate contendo a data que foi lida.
     */
    public static LocalDate nextLocalDate() {
        String valor = null;
        while (true) {
            try {
                valor = leitor.next();
                LocalDate d = null;
                if (valor.contains("/")) {
                    d = DateUtils.parseLocalDateFormatado(valor);
                } else {
                    d = DateUtils.parseLocalDate(valor);
                }
                
                if (d != null) {
                    return d;
                }
            } catch (Exception e) {
                System.out.println("Erro! a data " + valor + " é inválida!");
                System.out.println("Informe a data novamente:");
            }
        }

    }

    /**
     * *
     * Realiza a leitura do próximo valor Booleano digitado no terminal. No
     * qual: s ou sim representa o valor verdadeiro e n ou não representa o
     * valor falso. Caso o valor informado seja inválido, será exibida uma
     * mensagem de erro e em seguida será solicitado que o valor seja informado
     * novamente.
     *
     * @return Retorna um boolean contendo o número que foi lido.
     */
    public static boolean nextBoolean() {
        while (true) {
            String valor = "";
            try {
                valor = leitor.next();
                if (valor.equalsIgnoreCase("s") || valor.equalsIgnoreCase("sim")) {
                    return true;
                } else if (valor.equalsIgnoreCase("n") || valor.equalsIgnoreCase("nao")) {
                    return false;
                }
                System.out.println("Erro! o valor " + valor + " é inválido!");
                System.out.println("Informe um valor novamente:");
            } catch (Exception e) {
                System.out.println("Erro! o valor " + valor + " é inválido!");
                System.out.println("Informe um valor novamente:");
            }
        }
    }
}
