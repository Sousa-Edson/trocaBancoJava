/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teste;

/**
 *
 * @author edson
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Varredura {

    private static final String ANTIGO_DB_URL = "jdbc:postgresql://localhost:5432/SysEstoqueVersao51";
    private static final String ANTIGO_DB_USER = "admin";
    private static final String ANTIGO_DB_PASSWORD = "123456";

    private static final String DESTINATION_DB_URL = "jdbc:postgresql://localhost:5432/SysEstoqueSwing2023";
    private static final String DESTINATION_DB_USER = "admin";
    private static final String DESTINATION_DB_PASSWORD = "123456";

    private static final String TABLE_NAME = "nota";

    public static void main(String[] args) {
        listarTransacoes();
    }

    public static void listarTransacoes() {
        int id;
        int tipo;
        int cfop;
        int cliente;
        String nota;
        String chave;
        Date dataTransacao;
        Time horaTransacao;
        String informacoesComplementares;
        String nomeMotorista;
        int statusNota;
        try {

            String sql = "select * from nota";
            PreparedStatement preparedStatement = obterConexaoAntiga().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                    id = resultSet.getInt("id_nota");
//                      tipo = resultSet.getInt("tipo");
//                      cfop = resultSet.getInt("cfop");
                      cliente = resultSet.getInt("fornecedorint");
                      nota = resultSet.getString("nota_nota");
                      chave = resultSet.getString("nota_chave");
//                      dataTransacao = resultSet.getDate("nota_data");
//                      horaTransacao = resultSet.getTime("nota_hora");
                      informacoesComplementares = resultSet.getString("nota_observacao");
                      nomeMotorista = resultSet.getString("motorista");
//                      statusNota = resultSet.getInt("nota_situacao");

                System.out.println("Operação::: "+resultSet.getString("nota_operacao"));
        
        

                    // Faça algo com os valores obtidos
                    System.out.println("ID: " + id);
//                    System.out.println("Tipo: " + tipo);
//                    System.out.println("CFOP: " + cfop);
                    System.out.println("Cliente: " + cliente);
                    System.out.println("Nota: " + nota);
                    System.out.println("Chave: " + chave);
//                    System.out.println("Data de Transação: " + dataTransacao);
//                    System.out.println("Hora de Transação: " + horaTransacao);
                    System.out.println("Informações Complementares: " + informacoesComplementares);
                    System.out.println("Nome do Motorista: " + nomeMotorista);
//                    System.out.println("Status da Nota: " + statusNota);
                    System.out.println("------------------------------");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Connection obterConexaoAntiga() throws SQLException {
        try {
            return DriverManager.getConnection(ANTIGO_DB_URL, ANTIGO_DB_USER, ANTIGO_DB_PASSWORD);
        } catch (Exception e) {
            System.out.println("ERRO:\n" + e);
//            JOptionPane.showMessageDialog(null, "ERRO: " + e);
        }
        return null;
    }
}
