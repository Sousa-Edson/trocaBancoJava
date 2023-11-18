/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class ConexaoAntiga {
      private static final String URL = "jdbc:postgresql://localhost:5432/SysEstoqueVersao51";
    private static final String USUARIO = "admin";
    private static final String SENHA = "123456";
     public static Connection obterConexao() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (Exception e) {
            System.out.println("ERRO:\n" + e);
        }
        return null;
    }

    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
    
}
