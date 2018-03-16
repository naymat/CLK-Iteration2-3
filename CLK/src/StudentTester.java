import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentTester {
    public static void main(String [] args)
    {
        Database clickerDatabase = new Database();

        String studentId;
        String firstName;
        String lastName;
        try {
            clickerDatabase.getConnection();
            clickerDatabase.addStudent(50133412,"John","Doe");
            ResultSet resultSet = clickerDatabase.searchStudents("SELECT StudentId,FirstName,LastName FROM students");
            while (resultSet.next()){
                studentId = resultSet.getString("StudentId");
                firstName = resultSet.getString("FirstName");
                lastName = resultSet.getString("LastName");
                System.out.println(studentId + " " + firstName + " " + lastName);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
