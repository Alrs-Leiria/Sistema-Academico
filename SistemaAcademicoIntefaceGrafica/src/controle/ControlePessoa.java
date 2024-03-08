package controle;

import java.time.LocalDate;
import java.util.HashMap;
import javax.swing.JOptionPane;

import modelo.Pessoa;

/**
 *
 * @author Andre Leiria
 * @param <T>
 */
public abstract class ControlePessoa<T extends Pessoa> extends ControleCadastroGenerico<T>{
    
    public ControlePessoa(Class classeModelo) {
        super(classeModelo);
    }
    
    public void setarDadosObjeto(Pessoa p, HashMap<String, Object> dados){
        if(p == null){
            JOptionPane.showMessageDialog(null, "Falha ao Setar Dados da Pessoa!", "Falha ao Setar Dados", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        p.setNome((String) dados.getOrDefault("nome", ""));
        p.setCpf((String) dados.getOrDefault("cpf", ""));
        p.setEmail((String) dados.getOrDefault("email", ""));
        p.setGenero((String) dados.getOrDefault("genero", ""));
        p.setDataNascimento((LocalDate) dados.getOrDefault("datanascimento", null));
        
        //Dados de Endereço
        p.getEndereco().setCidade((String) dados.getOrDefault("cidade", ""));
        p.getEndereco().setRua((String) dados.getOrDefault("rua", ""));
        p.getEndereco().setNumero((String) dados.getOrDefault("numero", ""));
    }
    
    public HashMap<String, Object> getDadosObjeto(Pessoa p){
        HashMap<String, Object> dados = new HashMap<>();
        
        dados.put("nome", p.getNome());
        dados.put("cpf", p.getCpf());
        dados.put("email", p.getEmail());
        dados.put("genero", p.getGenero());
        dados.put("datanascimento",  p.getDataNascimento());

        dados.put("cidade", p.getEndereco().getCidade());
        dados.put("rua", p.getEndereco().getRua());
        dados.put("numero", p.getEndereco().getNumero());
        
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
    
}
