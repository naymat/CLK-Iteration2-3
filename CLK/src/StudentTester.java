import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentTester {
    public static void main(String [] args)
    {
        Database clickerDatabase = new Database();

        Scanner scan = new Scanner(System.in);
        String studentId;
        int intStudentId;
        String firstName;
        String lastName;

        try {
            /*
            System.out.println("Enter Student id: ");
            studentId = scan.nextLine();
            System.out.println("Enter your First Name:");
            firstName = scan.nextLine();
            System.out.println("Enter last name");
            lastName = scan.nextLine();
            intStudentId = Integer.parseInt(studentId);
            System.out.println();
            */

            clickerDatabase.getConnection();
            //clickerDatabase.addStudent(intStudentId,firstName,lastName);
            ResultSet resultSet = clickerDatabase.searchStudentsTable("SELECT StudentId,FirstName,LastName FROM students");
            /*while (resultSet.next()){

                System.out.println(resultSet.getString("StudentId") + " " + resultSet.getString("FirstName")
                        + " " + resultSet.getString("LastName") + " Courses: ");
            }*/
            //clickerDatabase.addCourse(50078292, "cps888");
            Student sally = new Student(50075212, "Jhon","Smith");
            clickerDatabase.addStudent(sally);
            System.out.println("is Sally a student: " + clickerDatabase.isStudent(sally));
            System.out.println("Is jhone a stuent?: " + clickerDatabase.isStudent(new Student(50023213,"Jhone","yolo")));
            //System.out.println("50078292 Courses: " + clickerDatabase.getCourses(50078292));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
