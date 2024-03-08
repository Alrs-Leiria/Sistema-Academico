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
import modelo.Docente;
import modelo.Funcionario;

/**
 *
 * @author Andre Leiria
 */

public class PersistirDocente extends PersistirFuncionario {

    public Funcionario cadastrarDocente(Docente docente) {
        PreparedStatement pst = null;
        Connection conn = null;

        //aluno.setEndereco(endereco.cadastrarEndereco(endereco));
        super.cadastrarFuncionario(docente);
        try {
            String sql = "INSERT INTO docente(formacao, id_funcionario) VALUES (?, ?)";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setString(1, docente.getFormacao());
            pst.setInt(2, docente.getIdFuncionario());

            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }

                DatabaseConnection.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return docente;

    }

    public List<Funcionario> listarDocentes() {
        List<Funcionario> docentes = new ArrayList<Funcionario>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM docente";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            rset = pst.executeQuery();

            while (rset.next()) {
                Docente docente = new Docente();
                docente.setIdDocente(rset.getInt("id"));
                docente.setFormacao(rset.getString("formacao"));
                docente.setIdFuncionario(rset.getInt("id_funcionario"));
                super.recuperarFuncionario(docente);

                docentes.add(docente);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return docentes;
    }

    public Funcionario recuperarDocente(Docente docente) {

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM docente WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, docente.getIdDocente());

            rset = pst.executeQuery();

            while (rset.next()) {
                docente.setIdDocente(rset.getInt("id"));
                docente.setFormacao(rset.getString("formacao"));
                docente.setIdFuncionario(rset.getInt("id_funcionario"));
                super.recuperarFuncionario(docente);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
       
        return docente;
    }

    public Docente recuperarDocente(int id) {
        Docente docente = new Docente();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM docente WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            rset = pst.executeQuery();

            while (rset.next()) {
                docente.setIdDocente(rset.getInt("id"));
                docente.setFormacao(rset.getString("formacao"));
                docente.setIdFuncionario(rset.getInt("id_funcionario"));
                super.recuperarFuncionario(docente);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return docente;
    }

    public void atualizarDocente(Docente docente) {
        Connection conn = null;
        PreparedStatement pst = null;

        super.atualizarFuncionario(docente);
        try {
            String sql = "UPDATE docente SET formacao = ? WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setString(1, docente.getFormacao());
            pst.setInt(2, docente.getIdDocente());

            pst.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void excluirDocente(Docente docente) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            String sql = "DELETE FROM docente WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setInt(1, docente.getIdDocente());

            pst.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        super.excluirFuncionario(docente);
    }
    public boolean verificarChaveEstrangeiraCurso(int idDocenete){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;
        int r = 1;
        
        try {
            String sql = " SELECT COUNT(docente.id)as r FROM docente "
                    + " LEFT JOIN curso ON(docente.id = curso.id_docente) "
                    + " WHERE curso.id_docente IS NOT NULL AND docente.id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idDocenete);

            rset = pst.executeQuery();

            while (rset.next()) {
                r = rset.getInt("r");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(r == 0){
            return true;
        }else
            return false;
    }
}
