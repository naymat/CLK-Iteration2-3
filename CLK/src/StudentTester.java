import com.sun.org.apache.xpath.internal.SourceTree;

import javax.xml.bind.SchemaOutputResolver;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
            clickerDatabase.addCourseForStudent(50078292, "CPS590");
            //Student sally = new Student(50078292, "Jhon","Smith");
            //clickerDatabase.addStudent(sally);
            Instructor sarah = new Instructor(66, "Dave", "Mason");

            //Course cps420 = new Course("CPS590",sarah,"Ryerson");
            //clickerDatabase.addCourse(cps420);
            Course course = new Course("CPS305",sarah,"Ryerson");
            //clickerDatabase.addCourse(course);
           /*String courses[] = clickerDatabase.getInstructorCourses(sarah.getId());
           System.out.println(courses.length);
           for(int i =0; i < courses.length; i++){
               System.out.println(courses[i]);
           }*/
           //clickerDatabase.createSession(course.getId());
           //clickerDatabase.addQuestion(course.getId(),new Question("What's 2+2?","4"));
           //System.out.println("Does the students table exist?" + clickerDatabase.tableExists(course.getId()));
            ArrayList<Question> questions = clickerDatabase.getQuestions(course.getId());
            Iterator iterator = questions.iterator();


                for (Question question : questions) {
                    System.out.println("Question: " + question.getQuestion() + " Answer: " + question.getAnswer());
                }


            // clickerDatabase.searchStudentsTable("DROP TABLE courses;");
           // System.out.println(courses[0]);
            //System.out.println(courses[1]);
            //System.out.println(courses[2]);
            //System.out.println("This is your courses:" + clickerDatabase.getStudentCourses(50078292));
            //System.out.println("is Sally a student: " + clickerDatabase.isStudent(sally));
            //System.out.println("Is jhone a stuent?: " + clickerDatabase.isStudent(new Student(50023213,"Jhone","yolo")));
            //System.out.println("50078292 Courses: " + clickerDatabase.getStudentCourses(50078292));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
