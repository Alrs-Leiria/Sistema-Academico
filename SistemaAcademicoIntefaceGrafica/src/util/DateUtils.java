package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;

import java.util.*;

public class DateUtils {

    private static Calendar cal = Calendar.getInstance();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static SimpleDateFormat dateFormat
            = new SimpleDateFormat("dd/MM/yyyy");

    public static Date createDate(int dia, int mes, int ano) {
        //cal.set(ano, mes, dia, 0 ,0);
        cal.set(Calendar.YEAR, ano);
        cal.set(Calendar.MONTH, --mes);
        cal.set(Calendar.DAY_OF_MONTH, dia);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();
    }

    public static LocalDate createLocalDate(Date data) {
        cal.setTime(data);
        return LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }
    
    public static LocalDate createLocalDate(int dia, int mes, int ano) {
        return LocalDate.of(ano, mes, dia);
    }
    
    public static Date convertLocalDateToDate(LocalDate d){
        return createDate(d.getDayOfMonth(), d.getMonthValue(), d.getYear());
    }
    
    public static java.sql.Date convertLocalDateToSqlDate(LocalDate d){
        return new java.sql.Date(convertLocalDateToDate(d).getTime());
    }
    
    public static LocalDate convertSqlDateToLocalDate(java.sql.Date d){
        return d.toLocalDate();
    }

    public static Calendar getCal() {
        return cal;
    }

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }
    
    public static String formatarDataBR(LocalDate dt) {
        return dt.format(formatter);
    }

    public static Date obterDataAtualSistema() {
        return new Date();
    }

    public static LocalDate obterLocalDateAtualSistema() {
        return LocalDate.now();
    }

    public static Date parseDate(String data) {
        try {
            return dateFormat.parse(data);
        } catch (ParseException e) {
            System.out.println("Falha ao converter a data: " + data);
            return null;
        }

    }

    public static LocalDate parseLocalDate(String data) {

        return LocalDate.parse(data); // formato string ano-mes-dia
    }
    
    public static LocalDate parseLocalDateFormatado(String data) {
        //LocalDate parsedDate = LocalDate.parse(data, formatter);
        return LocalDate.parse(data, formatter);// formato string dia/mes/ano
    }

    public static int getAnoDate(Date data) {
        cal.setTime(data);
        return cal.get(Calendar.YEAR);
    }

    public static int getMesDate(Date data) {
        cal.setTime(data);
        return cal.get(Calendar.MONTH);
    }

    public static int getDiaDate(Date data) {
        cal.setTime(data);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDiaDaSemanaDate(Date data) {
        cal.setTime(data);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int getHoraDate(Date data) {
        cal.setTime(data);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinutosDate(Date data) {
        cal.setTime(data);
        return cal.get(Calendar.MINUTE);
    }

    public static int getQuantidadeDiasMes(Date data) {
        cal.setTime(data);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date addAno(Date data, int anos) {
        cal.setTime(data);
        cal.add(Calendar.YEAR, anos);
        return cal.getTime();
    }

    public static Date addMes(Date data, int mes) {
        cal.setTime(data);
        cal.add(Calendar.MONTH, mes);
        return cal.getTime();
    }

    public static Date addDia(Date data, int dia) {
        cal.setTime(data);
        cal.add(Calendar.DAY_OF_MONTH, dia);
        return cal.getTime();
    }

    public static Date addHoras(Date data, int horas) {
        cal.setTime(data);
        cal.add(Calendar.HOUR_OF_DAY, horas);
        return cal.getTime();
    }

    public static Date addMinutos(Date data, int minutos) {
        cal.setTime(data);
        cal.add(Calendar.MINUTE, minutos);
        return cal.getTime();
    }

    public static LocalDate addAno(LocalDate data, int ano) {
        return data.plusYears(ano);
    }

    public static LocalDate addMes(LocalDate data, int mes) {
        return data.plusMonths(mes);
    }

    public static LocalDate addDia(LocalDate data, int dia) {
        return data.plusDays(dia);
    }

    public static LocalTime addHora(LocalTime data, int hora) {
        return data.plusHours(hora);
    }

    public static LocalTime addMinutos(LocalTime data, int minutos) {
        return data.plusMinutes(minutos);
    }

    public static long quantidadeDiasEntreDatas(LocalDate data1, LocalDate data2) {
        return data1.until(data2, ChronoUnit.DAYS);
    }

    public static long quantidadeAnosEntreDatas(LocalDate data1, LocalDate data2) {
        return data1.until(data2, ChronoUnit.YEARS);
    }

    public static long quantidadeDiasEntreDatas(Date data1, Date data2) {
        long milisegundos = 0;
        if (data1.before(data2)) {
            milisegundos = data1.getTime() - data2.getTime();
        } else {
            milisegundos = data2.getTime() - data1.getTime();
        }
        cal.setTimeInMillis(milisegundos);
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    public static long quantidadeHorasEntreDatas(Date data1, Date data2) {
        long milisegundos = 0;
        if (data1.before(data2)) {
            milisegundos = data1.getTime() - data2.getTime();
        } else {
            milisegundos = data2.getTime() - data1.getTime();
        }
        cal.setTimeInMillis(milisegundos);
        return cal.get(Calendar.HOUR_OF_DAY);
    }
    
    public static boolean isAnoBissexto(LocalDate data){
        return data.isLeapYear();
    }
    
    public static boolean isAnoBissexto(Date data){
        GregorianCalendar c = (GregorianCalendar) 
                 GregorianCalendar.getInstance();
        return c.isLeapYear(getAnoDate(data));
    }
    
    public static LocalDate quintoDiaUtilMes(LocalDate data){
        LocalDate d= data.with(firstDayOfMonth());
        int count = 0; 
        while(count < 5){
            if( d.getDayOfWeek() != DayOfWeek.SATURDAY && d.getDayOfWeek() != DayOfWeek.SUNDAY ){
                count++;
            }
            d = d.plusDays(1);
        }
        
        return d;
    }

    public static int calcularDiasUteis(LocalDate dataInicial, LocalDate dataFinal) {
        int diasUteis = 0;

        LocalDate dataAtual = dataInicial;
        while (!dataAtual.isAfter(dataFinal)) {
            if (isDiaUtil(dataAtual)) {
                diasUteis++;
            }
            dataAtual = dataAtual.plusDays(1);
        }

        return diasUteis;
    }

    public static boolean isDiaUtil(LocalDate data) {
        // Verificar se é um dia da semana (segunda a sexta-feira)
        if (data.getDayOfWeek() == DayOfWeek.SATURDAY || data.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }

        // Verificar se é um feriado
        List<LocalDate> feriados = getFeriados();
        if (feriados.contains(data)) {
            return false;
        }

        return true;
    }

    public static List<LocalDate> getFeriados() {
        // Definir a lista de feriados relevantes para o seu contexto
        List<LocalDate> feriados = new ArrayList<>();
        feriados.add(LocalDate.of(2023, 1, 1)); // Ano Novo
        feriados.add(LocalDate.of(2023, 4, 21)); // Tiradentes
        feriados.add(LocalDate.of(2023, 5, 1)); // Dia do Trabalho
        feriados.add(LocalDate.of(2023, 6, 8)); // Corpus Christi
        feriados.add(LocalDate.of(2023, 9, 7)); // Independência do Brasil
        feriados.add(LocalDate.of(2023, 10, 12)); // Nossa Sr.a Aparecida
        feriados.add(LocalDate.of(2023, 11, 2)); // Finados
        feriados.add(LocalDate.of(2023, 11, 15)); // Proclamação da República
        feriados.add(LocalDate.of(2023, 12, 25)); // Natal

        // Adicione mais feriados conforme necessário

        return feriados;
    }

}
