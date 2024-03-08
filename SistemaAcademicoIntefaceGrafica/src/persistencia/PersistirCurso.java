package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Curso;

/**
 *
 * @author Andre Leiria
 */

public class PersistirCurso extends PersistirDocente {

    public Curso cadastrarCurso(Curso curso) {
        
        PreparedStatement pst = null;
        Connection conn = null;

        try {
            String sql = "INSERT INTO curso(nome, cargahoraria, qtdsemestres, id_docente) VALUES (?, ?, ?, ?)";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setString(1, curso.getNome());
            pst.setInt(2, curso.getCargaHoraria());
            pst.setInt(3, curso.getQtdSemestres());
            pst.setInt(4, curso.getCoordenador().getIdDocente());

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
        return curso;
    }

    public Curso recuperarCurso(Curso curso) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM curso WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, curso.getId());

            rset = pst.executeQuery();

            while (rset.next()) {
                curso.setId(rset.getInt("id"));
                curso.setNome(rset.getString("nome"));
                curso.setCargaHoraria(rset.getInt("cargahoraria"));
                curso.setQtdSemestres(rset.getInt("qtdsemestres"));
                curso.setCoordenador(super.recuperarDocente(rset.getInt("id_docente")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return curso;
    }

        public Curso recuperarCurso(int idcurso) {
        
        Curso curso = new Curso();
        
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM curso WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idcurso);

            rset = pst.executeQuery();

            while (rset.next()) {
                
                curso.setId(rset.getInt("id"));
                curso.setNome(rset.getString("nome"));
                curso.setCargaHoraria(rset.getInt("cargahoraria"));
                curso.setQtdSemestres(rset.getInt("qtdsemestres"));
                curso.setCoordenador(super.recuperarDocente(rset.getInt("id_docente")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return curso;
    }
        
    public List<Curso> listarCursos() {
        List<Curso> cursos = new ArrayList<Curso>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM curso";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            rset = pst.executeQuery();

            while (rset.next()) {
                Curso curso = new Curso();

                curso.setId(rset.getInt("id"));
                curso.setNome(rset.getString("nome"));
                curso.setCargaHoraria(rset.getInt("cargahoraria"));
                curso.setQtdSemestres(rset.getInt("qtdsemestres"));
                curso.setCoordenador(super.recuperarDocente(rset.getInt("id_docente")));

                cursos.add(curso);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return cursos;
    }

    public void atualizarCurso(Curso curso) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            String sql = "UPDATE curso SET nome = ?, cargahoraria = ?, qtdsemestres = ?, id_docente = ? WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setString(1, curso.getNome());
            pst.setInt(2, curso.getCargaHoraria());
            pst.setInt(3, curso.getQtdSemestres());
            pst.setInt(4, curso.getCoordenador().getIdDocente());
            pst.setInt(5, curso.getId());

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

    public void excluirCurso(Curso curso) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            String sql = "DELETE FROM curso WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setInt(1, curso.getId());

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
    
    public boolean verificarChaveEstrangeiraAluno(int idcurso){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;
        int r = 1;
        
        try {
            String sql = " SELECT COUNT(curso.id)as r FROM curso "
                    + " LEFT JOIN aluno ON(curso.id = aluno.id_curso) "
                    + " WHERE aluno.id_curso IS NOT NULL AND curso.id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idcurso);

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
    public boolean verificarChaveEstrangeiraDisciplina(int idcurso){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;
        int r = 1;
        
        try {
            String sql = " SELECT COUNT(curso.id)as r FROM curso "
                    + " LEFT JOIN disciplina ON(curso.id = disciplina.fk_curso) "
                    + " WHERE disciplina.fk_curso IS NOT NULL AND curso.id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idcurso);

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
