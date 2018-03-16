import java.sql.*;

public class Database {
    private  static Connection connection;


    public void getConnection() throws SQLException, ClassNotFoundException {
        //Links sqlite library
        Class.forName("org.sqlite.JDBC");
        //Gets connection to Database
        connection = DriverManager.getConnection("jdbc:sqlite:Clicker.db");

        initializeStudents();

    }

    private void initializeStudents() throws SQLException {
        Statement statement = connection.createStatement();
        //Creates a students table with columns StudentId,FirstName,LastName,Courses
        //If it already exists, it ignores the create table command
        statement.execute("CREATE TABLE IF NOT EXISTS students(" +
                "StudentId integer PRIMARY KEY," +
                "FirstName text NOT NULL," +
                "LastName text NOT NULL," +
                "Courses text" +
                ");");
    }

    /**
     * Add Student to students table in SQLite database
     * @param student - Student object
     */
    public void addStudent(Student student) throws SQLException, ClassNotFoundException {
        addStudent(student.getId(),student.getFirstName(),student.getLastName());
    }
    /***
     * Adds student to students table in SQLite Database
     * @param id - integer that represents student's id
     * @param firstName - string representing student's first name
     * @param lastName - string representing student's last name
     * @throws SQLException - if SQL tables or database doesn't exist
     * @throws ClassNotFoundException - if unable to locate SQLite library
     */
    public void addStudent(int id, String firstName, String lastName) throws SQLException, ClassNotFoundException {
        //If there isn't a connection we call getConnection and create it there
        if(connection == null){
            getConnection();
        }
        //PreparedStatement creates template for SQLite insert
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students(StudentId,FirstName,LastName) values(?,?,?);");
        //Set what ? means below for the preparedStatement
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2,firstName);
        preparedStatement.setString(3,lastName);

        preparedStatement.execute();
    }

    /**
     * Gets a students courses from student SQLite table
     * Multiple course are delimited by : and stored in the format: coursecode:coursecode:coursecode
     * Eg: cps590:cps406:cps412
     * @param studetnId -integer Student id
     * @throws SQLException
     */
    public  String getCourses(int studetnId) throws SQLException {
        String courses;
        ResultSet result;

        //Searches Database by StudentId and selects Courses and StudentId
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT StudentId,Courses FROM students WHERE StudentId=?;");
        preparedStatement.setInt(1,studetnId);
        //Gets the value in the Courses field for the given StudentId
        result = preparedStatement.executeQuery();
        courses = result.getString("Courses");

        return courses;
    }

    public void addCourse(int studentId, String newCourse) throws SQLException {
        String courses;
        //gets courses string from SQLite student table using getCourses()
        courses = getCourses(studentId);
        //if there are no courses, we make the courses equal to the new course
        if(courses == null){
            courses = newCourse;
        }
        else {
            courses += ":"+ newCourse;
        }

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE students SET Courses = ? WHERE StudentId=?;");
        preparedStatement.setString(1,courses);
        preparedStatement.setInt(2,studentId);

        preparedStatement.execute();


    }
    public ResultSet searchStudents(String sqlStatement) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlStatement);
        return resultSet;
    }



}

