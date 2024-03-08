/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controle;

import java.util.HashMap;

/**
 *
 * @author Andre Leiria
 */
public interface IControleCadastro {
    
    public String[] gerarColunasTabela();
    public String[][] gerarDadosTabela(int qtdColunas);
    public void atualizarTabelaTelaListagem();
    public void abrirTelaListagem();
    public void abrirTelaCadastro();
    public void abrirTelaCadastroParaEdicao(int index);
    public boolean removerCadastro(int index);
    public void editar(HashMap<String, Object> dados);
    public void salvar(HashMap<String, Object> dados);
    public void recuperarRegistros();
}
