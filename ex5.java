// package postgresql.jdbc
import java.sql.*;
import java.util.Scanner;

public class ex5 {
    public static void main(String[] args) {
        // establishing database connection
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/univdb","postgres","password")){
            System.out.println("Connection established successfully");
            Statement stmt =connection.createStatement();
            String query = "ALTER TABLE student ADD cgpa float";
            stmt.executeUpdate(query);
            String[] lst = {"C-","C ","C+","B-","B ","B+","A-","A ","A+"};
            String querry2 = "Select id from student";
            ResultSet r_querry2 = stmt.executeQuery(querry2);
            ResultSetMetaData rsmd = r_querry2.getMetaData();
            int no_col = rsmd.getColumnCount();
            while(r_querry2.next()) {
                for (int j = 0; j < no_col; j++) {
                    Statement stmt2 =connection.createStatement();
                    int roll = Integer.parseInt(r_querry2.getString(j+1));
                    String query_total = "select sum(credits) from takes,course where takes.id = '" + roll + "' and takes.course_id=course.course_id;";
                    ResultSet total_credit = stmt2.executeQuery(query_total);
                    total_credit.next();
                    int total_cre = Integer.parseInt(total_credit.getString(1));
                    String querry = "select credits,takes.grade from takes,course where takes.id = '" + roll + "' and takes.course_id=course.course_id;";
                    ResultSet total_credit2 = stmt2.executeQuery(querry);
                    int summation = 0;
                    while (total_credit2.next()) {
                        String value = total_credit2.getString(1);
                        int credit = Integer.parseInt(value);
                        String grade = total_credit2.getString(2);
                        for (int i = 0; i < 9; i++) {
//                        System.out.print(grade);
                            if (grade.equals(lst[i])) {
                                summation = summation + credit * (i + 2);
                            }
                        }
                    }
                    float cg = (float) summation / total_cre;
                    String querry3 = "Update student set cgpa = " + cg + " where id = '" + roll + "'";
                    stmt2.executeUpdate(querry3);
                }
            }
            System.out.println("Enter the value of k: ");
            Scanner sc = new Scanner(System.in);
            int k = sc.nextInt();
            String querry4 = "Select * from student order by cgpa desc limit "+k;
            ResultSet r_querry4 = stmt.executeQuery(querry4);
            ResultSetMetaData rsmd1 = r_querry4.getMetaData();
            int no_col1 = rsmd1.getColumnCount();
            while (r_querry4.next()){
                for (int i=0;i<no_col1; i++){
                    String value = r_querry4.getString(i+1);
                    System.out.print(value+"   ");
                }
                System.out.print("\n");
            }

            System.out.println("Enter the value of k: ");
            Scanner sc1 = new Scanner(System.in);
            int k1 = sc1.nextInt();
//            System.out.println();
            sc1.nextLine();
            System.out.println("Enter the name of department");
            String dept = sc1.nextLine();
//            System.out.println();
            String querry5 = "Select * from student where dept_name ='"+dept+"' order by cgpa desc limit "+k1+";";
            ResultSet r_querry5 = stmt.executeQuery(querry5);
            ResultSetMetaData rsmd2 = r_querry5.getMetaData();
            int no_col2 = rsmd2.getColumnCount();
            while (r_querry5.next()){
                for (int i=0;i<no_col2; i++){
                    String value = r_querry5.getString(i+1);
                    System.out.print(value+"   ");
                }
                System.out.print("\n");

            }

            System.out.println("Enter the value of k: ");
            Scanner sc2 = new Scanner(System.in);
            int k2 = sc2.nextInt();
            System.out.println("Enter the id of course: ");
            String Course_id = sc2.next();
            String querry6 = "Select student.name,student.cgpa from student,takes where student.id=takes.id and takes.course_id = '"+Course_id+"' order by cgpa desc limit "+k2;
            ResultSet r_querry6 = stmt.executeQuery(querry6);
            ResultSetMetaData rsmd3 = r_querry6.getMetaData();
            int no_col3 = rsmd3.getColumnCount();
            while (r_querry6.next()){
                for (int i=0;i<no_col3; i++){
                    String value = r_querry6.getString(i+1);
                    System.out.print(value+"   ");
                }
                System.out.print("\n");
            }
            
            String querry7 = "ALTER TABLE student DROP COLUMN cgpa";
            stmt.executeUpdate(querry7);
            connection.close();

        }catch (SQLException e){
            System.out.println(e.getMessage());

        }

    }
}