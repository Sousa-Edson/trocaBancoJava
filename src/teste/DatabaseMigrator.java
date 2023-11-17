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

public class DatabaseMigrator {
    private static final String SOURCE_DB_URL = "jdbc:postgresql://localhost:5432/SysEstoqueVersao51";
    private static final String SOURCE_DB_USER = "admin";
    private static final String SOURCE_DB_PASSWORD = "123456";

    private static final String DESTINATION_DB_URL = "jdbc:postgresql://localhost:5432/SysEstoqueSwing2023";
    private static final String DESTINATION_DB_USER = "admin";
    private static final String DESTINATION_DB_PASSWORD = "123456";

    public static void migrateTable(String sourceTableName, String destinationTableName, String filterCondition) {
        try (Connection sourceConnection = DriverManager.getConnection(SOURCE_DB_URL, SOURCE_DB_USER, SOURCE_DB_PASSWORD);
             Connection destinationConnection = DriverManager.getConnection(DESTINATION_DB_URL, DESTINATION_DB_USER, DESTINATION_DB_PASSWORD)) {

            Statement sourceStatement = sourceConnection.createStatement();
            ResultSet resultSet = sourceStatement.executeQuery("SELECT * FROM " + sourceTableName + (filterCondition != null ? " WHERE " + filterCondition : ""));

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            StringBuilder insertQuery = new StringBuilder("INSERT INTO ").append(destinationTableName).append(" VALUES (");
            for (int i = 1; i <= columnCount; i++) {
                insertQuery.append("?,");
            }
            insertQuery.deleteCharAt(insertQuery.length() - 1); // Remover a vírgula final
            insertQuery.append(")");

            PreparedStatement destinationStatement = destinationConnection.prepareStatement(insertQuery.toString());

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    destinationStatement.setObject(i, resultSet.getObject(i));
                }
                destinationStatement.addBatch();
            }

            destinationStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Substitua 'source_table_name' e 'destination_table_name' pelos nomes das suas tabelas
        String sourceTableName = "nota";
        String destinationTableName = "nota";

        // Substitua 'filter_condition' pela condição de filtro desejada (opcional)
        String filterCondition = null; // Exemplo: "column_name > 10"

        migrateTable(sourceTableName, destinationTableName, filterCondition);
    }
}
