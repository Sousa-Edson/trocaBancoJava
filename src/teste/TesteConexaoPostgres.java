/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teste;

/**
 *
 * @author edson
 */
import conexao.ConexaoNova;
import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexaoPostgres {

    public static void main(String[] args) {
        Connection conexao = null;
        try {
            conexao = ConexaoNova.obterConexao();
            if (conexao != null) {
                System.out.println("Conexão bem-sucedida!");
            } else {
                System.out.println("Falha na conexão!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
