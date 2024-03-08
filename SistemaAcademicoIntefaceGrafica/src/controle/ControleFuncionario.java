/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.util.HashMap;
import javax.swing.JOptionPane;
import modelo.Funcionario;
import persistencia.PersistirFuncionario;
import util.DialogBoxUtils;
import util.NumberUtils;
import visao.TelaCadastroPessoa;

/**
 *
 * @author Andre Leiria
 */
public class ControleFuncionario extends ControlePessoa<Funcionario> {
    
    PersistirFuncionario pfunc = new PersistirFuncionario();
    
    public ControleFuncionario() {
        super(Funcionario.class);
        setTelaCadastro(new TelaCadastroPessoa(this));
    }
    
    public ControleFuncionario(Class classeModelo) {
        super(classeModelo);
    }

    public void setarDadosObjeto(Funcionario func, HashMap<String, Object> dados) {
        try {
            super.setarDadosObjeto(func, dados);
            func.setCtps((String) dados.getOrDefault("ctps", ""));
            func.setSalario(NumberUtils.parseFloat((String) dados.getOrDefault("salario", 0)));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao Setar Dados do Funcionário!\n"+e.getMessage(), "Falha ao Setar Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    public HashMap<String, Object> getDadosObjeto(Funcionario func) {
        HashMap<String, Object> dados = super.getDadosObjeto(func);
        dados.put("ctps", func.getCtps());
        dados.put("salario", func.getSalario());
        
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
    public void editar(HashMap<String, Object> dados) {
        if (registroSelecionado != null) {
            setarDadosObjeto(registroSelecionado, dados);
            pfunc.atualizarFuncionario(registroSelecionado);  
        }
    }

    @Override
    public void salvar(HashMap<String, Object> dados) {
        Funcionario funcionario = new Funcionario();
        setarDadosObjeto(funcionario, dados);
        pfunc.cadastrarFuncionario(funcionario);
        
    }
    
    @Override
    public void recuperarRegistros(){
        registros = pfunc.listarFuncionarios();
    }
    
    @Override
    public boolean removerCadastro(int index) {
        try {
            Funcionario funcionario = registros.get(index);
            pfunc.excluirFuncionario(funcionario);
            registros.remove(index);
            return true;
        } catch (Exception e) {
            DialogBoxUtils.exibirMensagemDeErro("Falha ao Remover Cadastro", "Falha ao Remover Cadastro\n" + e.getMessage());
            return false;
        }
    }    
}
