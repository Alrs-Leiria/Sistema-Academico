package visao;

import controle.IControleCadastro;
import java.util.HashMap;

/**
 *
 * @author Andre Leiria
 */
public abstract class TelaCadastro extends javax.swing.JFrame {

    protected IControleCadastro controle;
    protected boolean editarDados;
    /**
     * Creates new form TelaCadastroGenerico
     * @param controle
     */
    public TelaCadastro(IControleCadastro controle) {
        this.controle = controle;
        setLocationRelativeTo(null);
    }

    public boolean isEditarDados() {
        return editarDados;
    }

    public void setEditarDados(boolean editarDados) {
        this.editarDados = editarDados;
    }
    
    public abstract void setarDadosTela(HashMap<String, Object> dados);
    
    public abstract HashMap<String, Object> getDadosTela();
    
    public abstract void inicializarComponentesTela();
}
