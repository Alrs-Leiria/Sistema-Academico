/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import modelo.Aluno;

/**
 *
 * @author Andre Leiria
 */

public class PersistirAluno extends PersistirPessoa {
    PersistirCurso curso = new PersistirCurso();
            
    public Aluno cadastrarAluno(Aluno aluno) {

        PreparedStatement pst = null;
        Connection conn = null;

        super.cadastrarPessoa(aluno);
        
        try {
            String sql = "INSERT INTO aluno(ra, datamatricula, situacao, fk_pessoa, id_curso) VALUES (?, ?, ?, ?, ?)";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setString(1, aluno.getRa());
            pst.setDate(2, aluno.getDataMatricula());
            pst.setString(3, aluno.getSituacao());
            pst.setInt(4, aluno.getIdPessoa());
            pst.setInt(5, aluno.getCurso().getId());

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
        return aluno;
    }

    public List<Aluno> listarAlunos() {

        List<Aluno> alunos = new ArrayList<Aluno>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM aluno";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            rset = pst.executeQuery();

            while (rset.next()) {
                Aluno aluno = new Aluno();

                aluno.setId(rset.getInt("id"));
                aluno.setRa(rset.getString("ra"));
                
                Date data = rset.getDate("datamatricula");
                LocalDate localDate = data.toLocalDate();
                aluno.setDataMatricula(localDate);
                
                aluno.setSituacao(rset.getString("situacao"));
                aluno.setIdPessoa(rset.getInt("fk_pessoa"));
                aluno.setCurso(curso.recuperarCurso(rset.getInt("id_curso")));
                super.recuperar(aluno);

                alunos.add(aluno);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return alunos;
    }

    public void atualizarAluno(Aluno aluno) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        super.atualizarPessoa(aluno);
        try {
            String sql = "UPDATE aluno SET ra = ?, datamatricula = ?, situacao = ?, id_curso = ? WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setString(1, aluno.getRa());
            
            Date data = (Date) aluno.getDataMatricula();
            pst.setDate(2, data);
           
            pst.setString(3, aluno.getSituacao());
            pst.setInt(4, aluno.getCurso().getId());
            pst.setInt(5, aluno.getId());

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

    public void excluirAluno(Aluno aluno) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            String sql = "DELETE FROM aluno WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setInt(1, aluno.getId());

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
        super.excluirPessoa(aluno);
    }

    public Aluno recuperarAluno(int idaluno) {
        
        Aluno aluno = new Aluno();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM aluno WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idaluno);

            rset = pst.executeQuery();

            while (rset.next()) {
                
                aluno.setId(rset.getInt("id"));
                aluno.setRa(rset.getString("ra"));
                
                Date data = rset.getDate("datamatricula");
                LocalDate localDate = data.toLocalDate();
                aluno.setDataMatricula(localDate);
                
                aluno.setSituacao(rset.getString("situacao"));
                aluno.setIdPessoa(rset.getInt("fk_pessoa"));
                aluno.setCurso(curso.recuperarCurso(rset.getInt("id_curso")));
                super.recuperar(aluno);            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (rset != null) {
                    rset.close();
                }

                if (pst != null) {
                    pst.close();
                }

                if (conn != null) {
                    conn.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return aluno;
    }
}
