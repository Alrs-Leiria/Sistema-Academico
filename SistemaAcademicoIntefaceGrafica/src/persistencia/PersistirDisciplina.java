package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Disciplina;

/**
 *
 * @author Andre Leiria
 */

public class PersistirDisciplina extends PersistirCurso {

    public Disciplina cadastrarDisciplina(Disciplina disciplina) {
        
        PreparedStatement pst = null;
        Connection conn = null;

        try {
            String sql = "INSERT INTO disciplina(nome, cargahoraria, semestre, fk_curso) VALUES (?, ?, ?, ?)";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setString(1, disciplina.getNome());
            pst.setInt(2, disciplina.getCargahoraria());
            pst.setInt(3, disciplina.getSemestre());
            pst.setInt(4, disciplina.getCurso().getId());

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
        return disciplina;
    }

    public Disciplina recuperarDisciplina(Disciplina disciplina) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM disciplina WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, disciplina.getId());

            rset = pst.executeQuery();

            while (rset.next()) {
                disciplina.setId(rset.getInt("id"));
                disciplina.setNome(rset.getString("nome"));
                disciplina.setCargahoraria(rset.getInt("cargahoraria"));
                disciplina.setSemestre(rset.getInt("semestre"));
                disciplina.setCurso(super.recuperarCurso(rset.getInt("fk_curso")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return disciplina;
    }

        public Disciplina recuperarDisciplina(int iddisciplina) {
        
        Disciplina disciplina = new Disciplina();
        
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM disciplina WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, iddisciplina);

            rset = pst.executeQuery();

            while (rset.next()) {
                
                disciplina.setId(rset.getInt("id"));
                disciplina.setNome(rset.getString("nome"));
                disciplina.setCargahoraria(rset.getInt("cargahoraria"));
                disciplina.setSemestre(rset.getInt("qtdsemestres"));
                disciplina.setCurso(super.recuperarCurso(rset.getInt("fk_curso")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return disciplina;
    }
        
    public List<Disciplina> listarDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<Disciplina>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM disciplina";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            rset = pst.executeQuery();

            while (rset.next()) {
                Disciplina disciplina = new Disciplina();

                disciplina.setId(rset.getInt("id"));
                disciplina.setNome(rset.getString("nome"));
                disciplina.setCargahoraria(rset.getInt("cargahoraria"));
                disciplina.setSemestre(rset.getInt("semestre"));
                disciplina.setCurso(super.recuperarCurso(rset.getInt("fk_curso")));

                disciplinas.add(disciplina);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return disciplinas;
    }

    public void atualizarDisciplina(Disciplina disciplina) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            String sql = "UPDATE disciplina SET nome = ?, cargahoraria = ?, semestre = ?, fk_curso = ? WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setString(1, disciplina.getNome());
            pst.setInt(2, disciplina.getCargahoraria());
            pst.setInt(3, disciplina.getSemestre());
            pst.setInt(4, disciplina.getCurso().getId());
            pst.setInt(5, disciplina.getId());

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

    public void excluirDisciplina(Disciplina disciplina) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            String sql = "DELETE FROM disciplina WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setInt(1, disciplina.getId());

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

}
