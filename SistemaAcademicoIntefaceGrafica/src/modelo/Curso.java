package modelo;

/**
 *
 * @author Andre Leiria
 */

public class Curso implements IDescricao{
    protected int id;
    protected String nome;
    protected int cargaHoraria;
    protected int qtdSemestres;
    protected Docente coordenador;
    
    //variável auxiliar 
    private int qtdAlunosCurso;
    
    public Curso() {
    }

    public Curso(String nome, int cargaHoraria, int qtdSemestres, Docente coordenador) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.qtdSemestres = qtdSemestres;
        this.coordenador = coordenador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getQtdSemestres() {
        return qtdSemestres;
    }

    public void setQtdSemestres(int qtdSemestres) {
        this.qtdSemestres = qtdSemestres;
    }

    public Docente getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Docente coordenador) {
        this.coordenador = coordenador;
    }

    public int getQtdAlunosCurso() {
        return qtdAlunosCurso;
    }

    public void setQtdAlunosCurso(int qtdAlunosCurso) {
        this.qtdAlunosCurso = qtdAlunosCurso;
    }
    
    public void exibirInformacoes(){
        System.out.println("Curso: " + nome + " | Carga horária: " + cargaHoraria + " | Quantidade semestre: " + qtdSemestres + " | Coordenador: " + coordenador.getNome() + " | Quantidade alunos: " + qtdAlunosCurso);
    }
    
    @Override
    /**
     * Retorna a propriedade que melhor descreve/representa o objeto 
     */
    public String getDescricao() {
        return nome;
    }
}
