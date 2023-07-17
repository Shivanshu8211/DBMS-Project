// package postgresql.jdbc
import java.sql.*;
import java.util.Scanner;

public class ex1 {
    public static void main(String[] args) {
        // establishing database connection
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/univdb","postgres","password")){
            System.out.println("Connection established successfully");
            Statement stmt =connection.createStatement();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter name of the table");
            String table_name= sc.next();
            System.out.println("Enter value of k");
            String k = sc.next();
            String query = "select * from "+table_name+" limit "+ k;
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int no_col = rsmd.getColumnCount();
            StringBuilder sbuild = new StringBuilder();

            for (int i=0;i<no_col;i++){
                int len = rsmd.getColumnDisplaySize(i+1)+3;
                sbuild.append(String.format("%-"+len+"s",rsmd.getColumnName(i+1)));
            }
            sbuild.append('\n');

            while(rs.next()) {
                for (int i = 0; i < no_col; i++) {
                    int len = rsmd.getColumnDisplaySize(i + 1) + 3;
                    String lname = rs.getString(i + 1);
                    sbuild.append(String.format("%-"+len+"s",lname));
                }
                sbuild.append('\n');
            }
            System.out.println(sbuild);
            connection.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}