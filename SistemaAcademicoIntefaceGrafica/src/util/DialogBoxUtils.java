package util;

import javax.swing.JOptionPane;
import principal.Main;

public class DialogBoxUtils {
    
    public static int exibirCaixaConfirmacao(String titulo, String msg){
        JOptionPane.getRootFrame();
        return JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(), msg, titulo, JOptionPane.YES_NO_OPTION);
    }
    
    public static void exibirMensagem(String titulo, String msg){
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), msg, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void exibirMensagemDeErro(String titulo, String msg){
        JOptionPane.getRootFrame();
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), msg, titulo, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void exibirMensagemDeAlerta(String titulo, String msg){
        JOptionPane.getRootFrame();
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), msg, titulo, JOptionPane.WARNING_MESSAGE);
    }
}
