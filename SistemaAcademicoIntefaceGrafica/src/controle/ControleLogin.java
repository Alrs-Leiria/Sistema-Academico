package controle;

import javax.swing.JOptionPane;
import visao.TelaLogin;

/**
 *
 * @author Andre Leiria
 */
public class ControleLogin {
    
    private TelaLogin telaLogin;
    private ControlePrincipal controlePrincipal;

    public ControleLogin(ControlePrincipal controlePrincipal) {
        this.controlePrincipal = controlePrincipal;
    }
    
    public void abrirTelaLogin(){
        telaLogin = new TelaLogin(this);
        telaLogin.setVisible(true);
    }
    
    public boolean validarLogin(String login, String senha){
        if(senha.equalsIgnoreCase("123")){
             JOptionPane.showMessageDialog(telaLogin, "Senha correta!",
                     "Login", JOptionPane.INFORMATION_MESSAGE );
             controlePrincipal.abrirTelaPrincipal();
             telaLogin.setVisible(false);
             return true;
        }else{
            JOptionPane.showMessageDialog(telaLogin, "Senha Incorreta!",
                     "Falha no Login", JOptionPane.ERROR_MESSAGE );
            return false;
        }
    }
}
