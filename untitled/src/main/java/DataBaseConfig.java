import java.sql.*;
public class DataBaseConfig {

    private static final String URl = "jdbc:sqlite:tasks.db";

    public static Connection getConnetction() throws SQLException{
        return DriverManager.getConnection(URl);
    }

    public static void createTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS tasks
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    description TEXT NOT NULL,
                    priority TEXT,
                    status TEXT
                );
                """;

        try (Connection conn = getConnetction();
             Statement stmt = conn.createStatement()){
            stmt.execute(sql);

    }catch (SQLException e){
            System.out.println("error: table not create "+ e.getMessage());
        }
    }
}
