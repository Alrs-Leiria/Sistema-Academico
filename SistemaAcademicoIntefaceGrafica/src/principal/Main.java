package principal;

import controle.ControleLogin;
import controle.ControlePrincipal;

/**
 *
 * @author Andre Leiria
 */

public class Main {

    private static ControlePrincipal controle;
   
    public static void main(String[] args) {
        controle = new ControlePrincipal();
        ControleLogin controleLogin = new ControleLogin(controle);
        
        controle.getControleLogin().abrirTelaLogin();
    }

    public static ControlePrincipal getControle() {
        return controle;
    }
    
}
