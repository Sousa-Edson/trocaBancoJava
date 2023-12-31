/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unidade;

/**
 *
 * @author edson
 */
import conexao.ConexaoAntiga;
import conexao.ConexaoNova;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe para migrar dados da tabela unidade de um banco de dados antigo para
 * um novo.
 */
public class Unidade {

    public static void migrarTabela() {
        try (Connection remetenteConexao = ConexaoAntiga.obterConexao() ) {

            String query = "SELECT id_unidade, id_referenciaunidade, sigla_unidade,"
                    + " desc_unidade, registro_unidade, usuario_unidade, stunid, fragmento_unidade\n"
                    + "FROM public.unidade  "
                    + "WHERE sigla_unidade IS NOT NULL AND sigla_unidade <> '' "
                    + " and desc_unidade IS NOT NULL AND desc_unidade <> '';";

            try (Statement sourceStatement = remetenteConexao.createStatement(); ResultSet resultSet = sourceStatement.executeQuery(query)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id_referenciaunidade");
                    String sigla = resultSet.getString("sigla_unidade");
                    String descricao = resultSet.getString("desc_unidade");
                    System.out.println("" + id);
                    salvar(id, sigla, descricao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void salvar(int idAntigo, String sigla, String descricao) {
        try (Connection novaConexao = ConexaoNova.obterConexao()) {
            String sql = "INSERT INTO unidade (sigla, descricao, ativo,idAntigo) VALUES (?, ?, true,?)";

            try (PreparedStatement preparedStatement = novaConexao.prepareStatement(sql)) {
                // Verifica se sigla e descricao não são nulos antes de definir os valores no PreparedStatement
                preparedStatement.setString(1, (sigla != null) ? sigla : "");
                preparedStatement.setString(2, (descricao != null) ? descricao : "");
                preparedStatement.setInt(3, idAntigo);

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTable(String tableName) {
        try (Connection connection = ConexaoNova.obterConexao()) {
            Statement statement = connection.createStatement();

            // Adiciona a opção CASCADE para remover as dependências
            String sql = "DROP TABLE " + tableName + " CASCADE";
            statement.executeUpdate(sql);

            System.out.println("Tabela " + tableName + " excluída com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTableUnidade() {
        try (Connection connection = ConexaoNova.obterConexao()) {
            Statement statement = connection.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS unidade ("
                    + "id SERIAL PRIMARY KEY,"
                    + "sigla VARCHAR(255) NOT NULL,"
                    + "descricao VARCHAR(255) NOT NULL,"
                    + "ativo BOOLEAN NOT NULL)";

            statement.executeUpdate(sql);

            System.out.println("Tabela unidade criada com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void adicionarColunaDeletado() {
        try (Connection connection = ConexaoNova.obterConexao()) {
            Statement statement = connection.createStatement();

            String sql = "ALTER TABLE unidade ADD deletado BOOLEAN DEFAULT false";
            statement.executeUpdate(sql);

            System.out.println("Coluna 'deletado' adicionada à tabela 'unidade' com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void adicionarColunaIdAntigo() {
        try (Connection connection = ConexaoNova.obterConexao()) {
            Statement statement = connection.createStatement();

            // Adiciona a coluna 'idAntigo' à tabela 'unidade'
            String sql = "ALTER TABLE unidade ADD idAntigo int";
            statement.executeUpdate(sql);

            System.out.println("Coluna 'idAntigo' adicionada à tabela 'unidade' com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        dropTable("unidade");
        createTableUnidade();
        adicionarColunaDeletado();
        adicionarColunaIdAntigo();
        migrarTabela();
    }
}
