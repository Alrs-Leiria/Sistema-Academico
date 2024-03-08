/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Funcionario;

/**
 *
 * @author Andre Leiria
 */

public class PersistirFuncionario extends PersistirPessoa{
    
    public Funcionario cadastrarFuncionario(Funcionario funcionario){
            PreparedStatement pst  = null;
            Connection conn = null;

            //aluno.setEndereco(endereco.cadastrarEndereco(endereco));
        super.cadastrarPessoa(funcionario);
        try {
            String sql = "INSERT INTO funcionario(ctps, salario, id_pessoa) VALUES (?, ?, ?)";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, funcionario.getCtps());
            pst.setDouble(2, funcionario.getSalario());
            pst.setInt(3, funcionario.getIdPessoa());
            
            
            pst.execute();
            
            ResultSet lastid = pst.executeQuery("SELECT MAX(id) FROM funcionario");
            
            while(lastid.next()) {           
                funcionario.setIdFuncionario(lastid.getInt(1));
            }     
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(pst != null)
                    pst.close();
                
                DatabaseConnection.getConnection().close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return funcionario;
        
    }
    
    public List<Funcionario> listarFuncionarios(){
        List<Funcionario> funcionarios = new ArrayList<Funcionario>();
            
        Connection conn = null;
        PreparedStatement pst = null;        
        ResultSet rset = null;
            
        try{
            String sql = "SELECT funcionario.* FROM funcionario "
                    + " LEFT JOIN docente ON(docente.id_funcionario = funcionario.id) "
                    + " WHERE docente.id_funcionario IS NULL ";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
                
            rset = pst.executeQuery();
                
            while(rset.next()){
                Funcionario funcionario = new Funcionario();
                    
                funcionario.setIdFuncionario(rset.getInt("id"));
                funcionario.setCtps(rset.getString("ctps"));
                funcionario.setSalario(rset.getDouble("salario"));
                funcionario.setIdPessoa(rset.getInt("id_pessoa"));
                super.recuperar(funcionario);
                    
                funcionarios.add(funcionario);
            }
            
            }catch(Exception e){
                e.printStackTrace();
            }    
        return funcionarios;        
    }
    
    public Funcionario recuperarFuncionario(Funcionario funcionario){
            //Funcionario funcionario = new Funcionario();
            
            Connection conn = null;
            PreparedStatement pst = null;        
            ResultSet rset = null;
            
            try{
                String sql = "SELECT * FROM funcionario WHERE id = ?";
                conn = DatabaseConnection.getConnection();
                pst = conn.prepareStatement(sql);
                pst.setInt(1, funcionario.getIdFuncionario());
                
                rset = pst.executeQuery();
                
                while(rset.next()){  
                    funcionario.setIdFuncionario(rset.getInt("id"));
                    funcionario.setCtps(rset.getString("ctps"));
                    funcionario.setSalario(rset.getDouble("salario"));
                    funcionario.setIdPessoa(rset.getInt("id_pessoa"));
                    super.recuperar(funcionario);
                }   
            
            }catch(Exception e){
                e.printStackTrace();
            }               
            return funcionario;        
    }
    
    public void atualizarFuncionario(Funcionario funcionario){
            Connection conn = null;
            PreparedStatement pst = null; 
            
            super.atualizarPessoa(funcionario);
            try {
                String sql = "UPDATE funcionario SET ctps = ?, salario = ? WHERE id = ?";
                conn = DatabaseConnection.getConnection();
                pst = conn.prepareStatement(sql);

                pst.setString(1, funcionario.getCtps());
                pst.setDouble(2, funcionario.getSalario());
                pst.setInt(3, funcionario.getIdFuncionario());

                pst.execute();
     
            } catch (SQLException e) {
            e.printStackTrace();
            }finally{
                try{
                    if(pst != null)
                        pst.close();   

                    if(conn != null)
                       conn.close();
               }catch(SQLException e) {
                    e.printStackTrace();
                }
            }
    }
    
    public void excluirFuncionario(Funcionario funcionario){
        Connection conn = null;
        PreparedStatement pst = null;
        
        
        try {
            String sql = "DELETE FROM funcionario WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setInt(1, funcionario.getIdFuncionario());

            pst.execute();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(pst != null)
                    pst.close();   

                if(conn != null)
                    conn.close();
               }catch(SQLException e) {
                    e.printStackTrace();
               }
        }
        super.excluirPessoa(funcionario);
    }
    
}
