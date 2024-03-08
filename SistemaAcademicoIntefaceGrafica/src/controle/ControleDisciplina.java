package controle;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import modelo.Curso;
import modelo.Disciplina;
import persistencia.PersistirDisciplina;
import util.DialogBoxUtils;
import visao.TelaCadastroDisciplina;

/**
 *
 * @author Andre Leiria
 */
public class ControleDisciplina extends ControleCadastroGenerico<Disciplina>{
    
    PersistirDisciplina pdisc = new PersistirDisciplina();
    private ControleCurso controleCurso;
    
    
    public ControleDisciplina(ControleCurso controleCurso) {
        super(Disciplina.class);
        this.controleCurso = controleCurso;
        setTelaCadastro(new TelaCadastroDisciplina(this));
        
    }

    public ControleCurso getControleCurso() {
        return controleCurso;
    }
    
    public void setarDadosObjeto(Disciplina disciplina, HashMap<String, Object> dados){
        if(disciplina == null){
            JOptionPane.showMessageDialog(null, "Falha ao Setar Dados!", "Falha ao Setar Dados", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        disciplina.setNome((String) dados.getOrDefault("disciplina", ""));
        disciplina.setCargahoraria((int) dados.getOrDefault("cargahoraria", 0));
        disciplina.setSemestre((int) dados.getOrDefault("semestre", 0));
        disciplina.setCurso((Curso) dados.getOrDefault("curso", null));
    }
    
    public HashMap<String, Object> gerarVetorDados(Disciplina disciplina){
        HashMap<String, Object> dados = new HashMap<>();        
        dados.put("disciplina", disciplina.getNome());
        dados.put("cargahoraria", disciplina.getCargahoraria());
        dados.put("semestre", disciplina.getSemestre());
        dados.put("curso", disciplina.getCurso());
        
        return dados;
    }
    
    public List<String> getNomesDisciplinas(){
       
       return registros.stream().map(x -> x.getNome()).collect(Collectors.toList()); 
    }
    
    public Disciplina getDisciplinaSelecionado(int index) {
        if (index >= 0 && index < registros.size()) {
            return registros.get(index);
        }
        return null;
    }
    
    @Override
    public void abrirTelaCadastroParaEdicao(int index) {
        telaCadastro.inicializarComponentesTela();
        registroSelecionado = getDisciplinaSelecionado(index);
        if (registroSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Falha ao Editar \nRegistro não encontrado!", "Falha ao Editar", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        telaCadastro.setarDadosTela(gerarVetorDados(registroSelecionado));
        telaCadastro.setEditarDados(true);
        telaCadastro.setVisible(true);
    }
    
    
    @Override
    public void editar(HashMap<String, Object> dados){
        if(registroSelecionado != null){
            setarDadosObjeto(registroSelecionado, dados);
            pdisc.atualizarDisciplina(registroSelecionado);
        }
    }

    @Override
    public void salvar(HashMap<String, Object> dados) {
        Disciplina disciplina = new Disciplina();
        setarDadosObjeto(disciplina, dados); 
        pdisc.cadastrarDisciplina(disciplina);
    }
    
    @Override
    public void recuperarRegistros(){
        registros = pdisc.listarDisciplinas();
    }
    
    @Override
    public boolean removerCadastro(int index) {
        try {
            Disciplina disciplina = registros.get(index);
            pdisc.excluirDisciplina(disciplina);
            registros.remove(index);
            return true;
        } catch (Exception e) {
            DialogBoxUtils.exibirMensagemDeErro("Falha ao Remover Cadastro", "Falha ao Remover Cadastro\n" + e.getMessage());
            return false;
        }
    }
}
