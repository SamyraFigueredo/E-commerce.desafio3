import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class meuJDBC {
    public static void main(String[] args) {
        Connection obj = null;
        try {
            obj = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/?user=root/ecommerce",
                    "root",
                    ""
            );
            System.out.println("Conexão estabelecida com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        } finally {
            if (obj != null) {
                try {
                    obj.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }
}
