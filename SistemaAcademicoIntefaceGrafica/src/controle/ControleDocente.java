package controle;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import modelo.Docente;
import persistencia.PersistirDocente;
import util.DialogBoxUtils;
import visao.TelaCadastroPessoa;

/**
 *
 * @author Andre Leiria
 */
public class ControleDocente extends ControleFuncionario{
    
    PersistirDocente pdoce = new PersistirDocente();
    
    //protected Docente docenteSelecionado;
    
    public ControleDocente() {
        super(Docente.class);
        setTelaCadastro(new TelaCadastroPessoa(this));
    }
    
    public List<Docente> getListaDocentes(){
        return registros.stream().filter(x -> x.getClass().equals(Docente.class)).map(a -> (Docente) a).collect(Collectors.toList());
    }
    public List<String> getNomesDocentes(){
        return registros.stream().filter(x -> x.getClass().equals(Docente.class)).map(x -> x.getNome()).collect(Collectors.toList()); 
    }
    
    public Docente getDocenteSelecionado(int index) {
        List<Docente> docentes = getListaDocentes();
        if (index >= 0 && index < docentes.size()) {
            return docentes.get(index);
        }
        return null;
    }
    
   public void setarDadosObjeto(Docente docente, HashMap<String, Object> dados) {
        try {
            super.setarDadosObjeto(docente, dados);
            docente.setFormacao((String) dados.getOrDefault("formacao", ""));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao Setar Dados do Docente!\n"+e.getMessage(), "Falha ao Setar Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    public HashMap<String, Object> getDadosObjeto(Docente docente) {
        HashMap<String, Object> dados = super.getDadosObjeto(docente);
        dados.put("formacao", docente.getFormacao());
        
        return dados;
    }

   @Override
    public void abrirTelaCadastroParaEdicao(int index) {
        registroSelecionado = registros.get(index);
        if (registroSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Falha ao Editar \nRegistro não encontrado!", "Falha ao Editar", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        telaCadastro.setarDadosTela(getDadosObjeto(registroSelecionado));
        telaCadastro.setEditarDados(true);
        telaCadastro.setVisible(true);
    }
    
    
    @Override
    public void editar(HashMap<String, Object> dados){
        if(registroSelecionado != null){           
            Docente docenteSelecionado = new Docente(registroSelecionado);
            setarDadosObjeto(docenteSelecionado, dados);
            pdoce.atualizarDocente(docenteSelecionado);
        }
    }

    @Override
    public void salvar(HashMap<String, Object> dados) {
        Docente docente = new Docente();
        setarDadosObjeto(docente, dados);
        pdoce.cadastrarDocente(docente);
    }
    
    @Override
    public void recuperarRegistros(){
        registros = pdoce.listarDocentes();
    }
    
    @Override
    public boolean removerCadastro(int index) {
        Docente docente = (Docente)registros.get(index);
        boolean retorno = true;
        if(pdoce.verificarChaveEstrangeiraCurso(docente.getIdDocente())){
            try {          
                pdoce.excluirDocente(docente);
                registros.remove(index);
                retorno = true;
            } catch (Exception e) {
                DialogBoxUtils.exibirMensagemDeErro("Falha ao Remover Cadastro", "Falha ao Remover Cadastro\n" + e.getMessage());
                retorno = false;
            }           
        }else{
            DialogBoxUtils.exibirMensagemDeErro("Falha ao Remover Cadastro", "O Docente esta vinculado a um ou mais Cursos!\n");            
            retorno = false;
        }      

        
        return retorno;
    }      
}
