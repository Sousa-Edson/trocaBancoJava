/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teste;

/**
 *
 * @author edson
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaNota {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/SysEstoqueVersao51";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "123456";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT id_nota, id_referencianota, nota_nota, nota_data, nota_hora, " +
                    "nota_observacao, nota_registro, nota_situacao, nota_chave, nota_operacao, " +
                    "nota_usu, naturezaint, fornecedorint, motorista " +
                    "FROM public.nota " +
                    "WHERE stnota = 1 " +
                    "ORDER BY id_nota asc";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int idNota = resultSet.getInt("id_nota");
                    int idReferenciaNota = resultSet.getInt("id_referencianota");
                    String nota = resultSet.getString("nota_nota");
                    String dataNota = resultSet.getString("nota_data");
                    String horaNota = resultSet.getString("nota_hora");
                    String observacao = resultSet.getString("nota_observacao");
                    String registro = resultSet.getString("nota_registro");
                    String situacao = resultSet.getString("nota_situacao");
                    String chave = resultSet.getString("nota_chave");
                    String operacao = resultSet.getString("nota_operacao");
                    String usuario = resultSet.getString("nota_usu");
                    int naturezaInt = resultSet.getInt("naturezaint");
                    int fornecedorInt = resultSet.getInt("fornecedorint");
                    String motorista = resultSet.getString("motorista");

                    // Imprime os resultados no console
                    System.out.println("ID Nota: " + idNota);
                    System.out.println("ID Referência Nota: " + idReferenciaNota);
                    System.out.println("Nota: " + nota);
                    System.out.println("Data da Nota: " + dataNota);
                    System.out.println("Hora da Nota: " + horaNota);
                    System.out.println("Observação: " + observacao);
                    System.out.println("Registro: " + registro);
                    System.out.println("Situação: " + situacao);
                    System.out.println("Chave: " + chave);
                    System.out.println("Operação: " + operacao);
                    System.out.println("Usuário: " + usuario);
                    System.out.println("Natureza Int: " + naturezaInt);
                    System.out.println("Fornecedor Int: " + fornecedorInt);
                    System.out.println("Motorista: " + motorista);
                    System.out.println("--------------------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
