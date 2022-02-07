        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.Statement;

        public class DbConn {
        public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "mysql";
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS test.test_table (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255), PRIMARY KEY (id))";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO test.test_table (name) VALUES ('test')";
            stmt.executeUpdate(sql);
            sql = "SELECT * FROM test.test_table";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt("id") + rs.getString("name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        }
        }
