// package postgresql.jdbc
import java.sql.*;
import java.util.Scanner;

public class ex2 {
//    public static String rec(String course_id, Statement stmt){
//        String query = "select * from prereq where course_id='403'";
//        String value="";
//        try {
//            ResultSet rs = stmt.executeQuery(query);
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int no_col = rsmd.getColumnCount();
//            if (no_col == 0) {
//                return "";
//            }
//            while (rs.next()) {
//                for (int i = 0; i < no_col; i++) {
//                    value = rs.getString(i + 1);
//                    System.out.print(value + "   ");
//                }
////            return rec(value,stmt);
//            }
//            System.out.println();
//        }
//
//        catch(SQLException e){
//            e.printStackTrace();
//        }
//        System.out.println();
//                    return rec(value,stmt);
//
//    }
    public static void main(String[] args) {
        // establishing database connection
        try{
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/univdb","postgres","password");
            System.out.println("Connection established successfully");
            Statement stmt =connection.createStatement();
            Statement stmt2 =connection.createStatement();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter course id: ");
            String course_id= sc.next();
            String query = "with recursive rec_prereq as ( select course_id, prereq_id from prereq where course_id = '"+course_id+"' union\n"+
                            "select R.course_id, R.prereq_id\n"+
                            "from rec_prereq as L join prereq as R\n"+
                            "on L.prereq_id = R.course_id)\n"+
                            "select * from rec_prereq;\n";
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int no_col = rsmd.getColumnCount();
            StringBuilder sbuild = new StringBuilder();

//            while(rs.next()) {
//                String q= "select title from course where course_id ='"+rs.getString(2)+"';";
//                ResultSet r1=stmt.executeQuery(q);
//                r1.next();
//                String title = r1.getString(1);
//                System.out.println("title of prerequesite "+rs.getString(2)+" is " +title);
//            }

            for (int i=0;i<no_col;i++){
                int len = rsmd.getColumnDisplaySize(i+1)+3;
                sbuild.append(String.format("%-"+len+"s",rsmd.getColumnName(i+1)));
            }
            sbuild.append('\n');

            while(rs.next()) {
                String q= "select title from course where course_id ='"+rs.getString(2)+"';";
                ResultSet r1=stmt2.executeQuery(q);
                r1.next();
                String title = r1.getString(1);
                System.out.println("title of prerequesite "+rs.getString(2)+" is " +title);
                for (int i = 0; i < no_col; i++) {
                    int len = rsmd.getColumnDisplaySize(i + 1) + 3;
                    String lname = rs.getString(i + 1);
                    sbuild.append(String.format("%-"+len+"s",lname));
                }
                sbuild.append('\n');
            }
            System.out.println(sbuild);

            connection.close();

        }catch (SQLException e){
            System.out.println(e.getMessage());

        }


    }

}