/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Andre Leiria
 */

public class DatabaseConnection {
/* ---------------------------------------------------------------------------------------------------- */
    // Listas
/* ---------------------------------------------------------------------------------------------------- */
    // Variaveis 
    private static Connection con = null;

/* ---------------------------------------------------------------------------------------------------- */
    // Construtores
    private static void criarConexao()
    {
        String url = "jdbc:mysql://localhost:3306/prova";
        String user = "root";
        String pass = "1234";
        try {
       
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println("Falha ao criar a conecxÃ£o com o banco de dados\n"+e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            if(con==null || con.isClosed()){
                  criarConexao();
            }
            return con;
        } catch (SQLException ex) {
            System.out.println("Falha ao obter a conexao!\n"+ex.getMessage());
            return null;
        }
    }
}
