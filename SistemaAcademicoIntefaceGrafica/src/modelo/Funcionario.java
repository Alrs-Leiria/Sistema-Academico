package modelo;

import java.time.LocalDate;

/**
 *
 * @author Andre Leiria
 */

public class Funcionario extends Pessoa{
    protected int idfuncionario;
    protected String ctps;
    protected double salario;

    public Funcionario() {
    }

    public Funcionario(String ctps, double salario, int id, String nome, String cpf, String email, String genero, LocalDate dataNascimento, Endereco endereco) {
        super(id, nome, cpf, email, genero, dataNascimento, endereco);
        this.ctps = ctps;
        this.salario = salario;
    }

    public int getIdFuncionario() {
        return idfuncionario;
    }

    public void setIdFuncionario(int id) {
        this.idfuncionario = id;
    }
    
    
    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public void exibirInformacoes(){
        System.out.println( nome + " | Cpf: " + cpf + " | Idade: " + calcularIdade() + " anos " +
                " | Cidade: "+ endereco.getCidade() + " | Rua : " + endereco.getRua() + " | Número: " + endereco.getNumero() + " | "
                + "Ctps: " + ctps + " |  Salario: " + salario
        );
    }
}
