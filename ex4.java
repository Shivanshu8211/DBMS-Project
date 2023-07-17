// package postgresql.jdbc
import java.sql.*;
import java.util.Scanner;

public class ex4 {
    public static void main(String[] args) {
        // establishing database connection
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/univdb","postgres","password")){
            System.out.println("Connection established successfully");
            Statement stmt =connection.createStatement();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter roll number of the student");
            String roll= sc.next();
            String query_total = "select sum(credits) from takes,course where takes.id = '"+roll+"' and takes.course_id=course.course_id;";
            ResultSet total_credit = stmt.executeQuery(query_total);
            System.out.println("Query ran successfully");
            total_credit.next();
            int total_cre = Integer.parseInt(total_credit.getString(1));
            String querry = "select credits,takes.grade from takes,course where takes.id = '"+roll+"' and takes.course_id=course.course_id;";
            ResultSet total_credit2 = stmt.executeQuery(querry);
            ResultSetMetaData rsmd = total_credit2.getMetaData();
            String[] lst = {"C-","C ","C+","B-","B ","B+","A-","A ","A+"};
            int summation=0;
            while (total_credit2.next()){
                String value = total_credit2.getString(1);
                int credit = Integer.parseInt(value);
                String grade = total_credit2.getString(2);
                for (int i=0;i<9;i++){
                    if(grade.equals(lst[i])){
                        summation=summation+credit*(i+2);
                    }
                }
            }
            System.out.println("Cgpa of student with roll no. "+roll+" is "+(float)summation/total_cre);
            connection.close();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}