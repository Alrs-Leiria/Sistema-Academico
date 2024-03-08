/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import modelo.Endereco;

/**
 *
 * @author Andre Leiria
 */

public class PersistirEndereco {
           
        public Endereco cadastrarEndereco(Endereco endereco){
 
        PreparedStatement pst  = null;
        Connection conn = null;
                 
        try {
            String sql = "INSERT INTO endereco(cidade, rua, numero) VALUES (?, ?, ?)";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, endereco.getCidade());
            pst.setString(2, endereco.getRua());
            pst.setString(3, endereco.getNumero());
            
            
            pst.execute();
            
            ResultSet lastid = pst.executeQuery("SELECT MAX(id) FROM endereco");
            
            while(lastid.next()) {           
                endereco.setId(lastid.getInt(1));
            }       
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(pst != null)
                    pst.close();
                
                conn.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return endereco;
    }
        
        public List<Endereco> listarEnderecos(){
            
            List<Endereco> enderecos = new ArrayList<Endereco>();
            
            Connection conn = null;
            PreparedStatement pst = null;        
            ResultSet rset = null;
            
            try{
                String sql = "SELECT * FROM endereco";
                conn = DatabaseConnection.getConnection();
                pst = conn.prepareStatement(sql);
                
                rset = pst.executeQuery();
                
                while(rset.next()){
                    Endereco endereco = new Endereco();
                    
                    endereco.setId(rset.getInt("id"));
                    endereco.setCidade(rset.getString("cidade"));
                    endereco.setRua(rset.getString("rua"));
                    endereco.setNumero(rset.getString("numero"));
                    
                    enderecos.add(endereco);
            }
            
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                
                try{
                    if(rset != null)
                        rset.close();

                    if(pst != null)
                        pst.close();   

                    if(conn != null)
                        conn.close();

                }catch(Exception e){
                    e.printStackTrace();                
                }
            }    
        return enderecos;
    }
        
        public void atualizarEndereco(Endereco endereco){
            Connection conn = null;
            PreparedStatement pst = null; 

            try {
                String sql = "UPDATE endereco SET cidade = ?, rua = ?, numero = ? WHERE id = ?";
                conn = DatabaseConnection.getConnection();
                pst = conn.prepareStatement(sql);

                pst.setString(1, endereco.getCidade());
                pst.setString(2, endereco.getRua());
                pst.setString(3, endereco.getNumero());
                pst.setInt(4, endereco.getId());

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
        
        public void excluirEndereco(int idendereco){
            Connection conn = null;
            PreparedStatement pst = null; 
            try {
                String sql = "DELETE FROM endereco WHERE id = ?";
                conn = DatabaseConnection.getConnection();
                pst = conn.prepareStatement(sql);

                pst.setInt(1, idendereco);

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
        
        public Endereco recuperarEndereco(int idendereco){
            Endereco endereco = new Endereco();
            
            Connection conn = null;
            PreparedStatement pst = null;        
            ResultSet rset = null;
            
            try{
                String sql = "SELECT * FROM endereco WHERE id = ?";
                conn = DatabaseConnection.getConnection();
                pst = conn.prepareStatement(sql);
                pst.setInt(1, idendereco);
                
                rset = pst.executeQuery();
                
                while(rset.next()){  
                    endereco.setId(rset.getInt("id"));
                    endereco.setCidade(rset.getString("cidade"));
                    endereco.setRua(rset.getString("rua"));
                    endereco.setNumero(rset.getString("numero"));                  
                }   
            
            }catch(Exception e){
                e.printStackTrace();
            }               
            return endereco;
        }
}
