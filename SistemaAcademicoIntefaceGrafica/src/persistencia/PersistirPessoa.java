package persistencia;

import modelo.Aluno;
import modelo.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Andre Leiria
 */

public class PersistirPessoa {

    PersistirEndereco pend = new PersistirEndereco();

    public Pessoa cadastrarPessoa(Pessoa pessoa) {
        PreparedStatement pst = null;
        Connection conn = null;

        pend.cadastrarEndereco(pessoa.getEndereco());
        try {
            String sql = "INSERT INTO pessoa(nome, cpf, email, genero, datanascimento, id_endereco) VALUES (?, ?, ?, ?, ?, ?)";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setString(1, pessoa.getNome());
            pst.setString(2, pessoa.getCpf());
            pst.setString(3, pessoa.getEmail());
            pst.setString(4, pessoa.getGenero());
            Date date = null;
            date = date.valueOf(pessoa.getDataNascimento());

            pst.setDate(5, date);
            pst.setInt(6, pessoa.getEndereco().getId());
            pst.execute();

            ResultSet lastid = pst.executeQuery("SELECT MAX(id) FROM pessoa");

            while (lastid.next()) {
                pessoa.setIdPessoa(lastid.getInt(1));
            }

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
        return pessoa;
    }

    public Pessoa recuperar(Pessoa pessoa) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM pessoa WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, pessoa.getIdPessoa());

            rset = pst.executeQuery();

            while (rset.next()) {
                pessoa.setNome(rset.getString("nome"));
                pessoa.setCpf(rset.getString("cpf"));
                pessoa.setEmail(rset.getString("email"));
                pessoa.setGenero(rset.getString("genero"));
                Date date = rset.getDate("datanascimento");
                LocalDate localDate = date.toLocalDate();
                pessoa.setDataNascimento(localDate);
                pessoa.setEndereco(pend.recuperarEndereco(rset.getInt("id_endereco")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pessoa;

    }

    public List<Pessoa> listar() {
        List<Pessoa> pessoas = new ArrayList<Pessoa>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rset = null;

        try {
            String sql = "SELECT * FROM pessoa";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            rset = pst.executeQuery();

            while (rset.next()) {

                Pessoa pessoa = new Aluno();

                pessoa.setIdPessoa(rset.getInt("id"));
                pessoa.setNome(rset.getString("nome"));
                pessoa.setCpf(rset.getString("cpf"));
                pessoa.setEmail(rset.getString("email"));
                pessoa.setGenero(rset.getString("genero"));
                Date date = rset.getDate("datanascimento");
                LocalDate localDate = date.toLocalDate();
                pessoa.setDataNascimento(localDate);
                pessoa.setEndereco(pend.recuperarEndereco(rset.getInt("id_endereco")));

                pessoas.add(pessoa);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pessoas;
    }

    public void atualizarPessoa(Pessoa pessoa) {
        Connection conn = null;
        PreparedStatement pst = null;

        pend.atualizarEndereco(pessoa.getEndereco());
        try {
            String sql = "UPDATE pessoa SET nome = ?, cpf = ?, email = ?, genero = ?, datanascimento = ? WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setString(1, pessoa.getNome());
            pst.setString(2, pessoa.getCpf());
            pst.setString(3, pessoa.getEmail());
            pst.setString(4, pessoa.getGenero());
            pst.setDate(5, Date.valueOf(pessoa.getDataNascimento()));
            pst.setInt(6, pessoa.getIdPessoa());

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

    public void excluirPessoa(Pessoa pessoa) {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            String sql = "DELETE FROM pessoa WHERE id = ?";
            conn = DatabaseConnection.getConnection();
            pst = conn.prepareStatement(sql);

            pst.setInt(1, pessoa.getIdPessoa());

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
        pend.excluirEndereco(pessoa.getEndereco().getId());
    }

}
