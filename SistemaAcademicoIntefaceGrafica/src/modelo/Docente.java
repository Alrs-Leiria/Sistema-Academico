package modelo;

import java.time.LocalDate;

/**
 *
 * @author Andre Leiria
 */

public class Docente extends Funcionario{
    protected int id;
    protected String formacao;
    
    public Docente() {
    }
    

    public Docente(Funcionario funcionario) {
       this.setIdPessoa(funcionario.getIdPessoa());
       this.setNome(funcionario.getNome());
       this.setCpf(funcionario.getCpf());
       this.setEmail(funcionario.getEmail());
       this.setGenero(funcionario.getGenero());
       this.setDataNascimento(funcionario.getDataNascimento());
       this.setEndereco(funcionario.getEndereco());
       this.setIdFuncionario(funcionario.getIdFuncionario());
       this.setCtps(funcionario.getCtps());
       this.setSalario(funcionario.getSalario());
    }

    public Docente(String formacao, String ctps, double salario, int id, String nome, String cpf, String email, String genero, LocalDate dataNascimento, Endereco endereco) {
        super(ctps, salario, id, nome, cpf, email, genero, dataNascimento, endereco);
        this.formacao = formacao;
    }

    public int getIdDocente() {
        return id;
    }

    public void setIdDocente(int id) {
        this.id = id;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    @Override
    public void exibirInformacoes(){
        System.out.println( nome + " | Cpf: " + cpf + " | Idade: " + calcularIdade() + " anos " +
                " | Cidade: "+ endereco.getCidade() + " | Rua : " + endereco.getRua() + " | Número: " + endereco.getNumero() + " | "
                + "Ctps: " + ctps + " |  Salario: " + salario + " | Formação: " + formacao
        );
    }
}
