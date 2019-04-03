import java.sql.*;
import java.util.ArrayList;

public class Database {
    private  static Connection connection;


    public void getConnection() throws SQLException, ClassNotFoundException {
        //Links sqlite library
        Class.forName("org.sqlite.JDBC");
        //Gets connection to Database
        connection = DriverManager.getConnection("jdbc:sqlite:Clicker.db");

        initializeStudents();
        initializeCourses();

    }

    private void initializeCourses() throws SQLException{
        Statement statement = connection.createStatement();
        //Creates a students table with columns StudentId,FirstName,LastName,Courses
        //If it already exists, it ignores the create table command
        statement.execute("CREATE TABLE IF NOT EXISTS courses(" +
                "CourseId text PRIMARY KEY," +
                "InstructorId integer NOT NULL," +
                "InstructorFirstName text NOT NULL," +
                "InstructorLastName text NOT NULL," +
                "University text" +
                ");");
    }
    public void createSession(String courseId) throws SQLException, ClassNotFoundException {
        //If there isn't a connection we call getConnection and create it there
        if(connection == null){
            getConnection();
        }
        //PreparedStatement creates template for SQLite insert
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " +
                courseId +"(" +
                "Question text PRIMARY KEY," +
                "Answer text NOT NULL);");

        //Set what ? means below for the preparedStatement
        preparedStatement.execute();
    }
    public String[] getSessions(int instructorId) throws SQLException, ClassNotFoundException {
        String[] courses = getInstructorCourses(instructorId);
        String sessions = "";
        for(int i = 0; i < courses.length ; i++){
            if (tableExists(courses[i]))
                sessions += courses[i] + ":";
        }
        return sessions.split(":");
    }
    public void addQuestion(String courseId, Question question) throws SQLException, ClassNotFoundException {
        if(connection == null){
            getConnection();
        }
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                courseId +
                "(Question,Answer) values(?,?);");
        preparedStatement.setString(1,question.getQuestion());
        preparedStatement.setString(2,question.getAnswer());

        preparedStatement.execute();

    }
    public ArrayList<Question> getQuestions(String courseId) throws SQLException, ClassNotFoundException {
        ArrayList<Question> questionsList;
        String strQuestion;
        String strAnswer;
        Question question;

        questionsList = new ArrayList<Question>();
        //Makes sure were connected to Database file before we continue, if were not then we connect to it
        if(connection == null){
            getConnection();
        }

        if(tableExists(courseId)){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Question,Answer FROM " + courseId);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()){
                strQuestion = result.getString("Question");
                strAnswer = result.getString("Answer");

                question = new Question(strQuestion,strAnswer);
                questionsList.add(question);
            }
        }
        return questionsList;

    }
    public Boolean tableExists(String courseId) throws SQLException, ClassNotFoundException {
        if(connection == null){
            getConnection();
        }
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM sqlite_master where type='table' AND name=?;");
        preparedStatement.setString(1,courseId);

        ResultSet result = preparedStatement.executeQuery();

        if (result.next())
            return true;
        else
            return false;

    }

    public void addCourse(Course course) throws SQLException, ClassNotFoundException {
        Instructor instructor = course.getInstructor();

        addCourse(course.getId(),instructor.getId(),instructor.getFirstName(),instructor.getLastName(),course.getUniversity());

    }
    public void addCourse(String id, int instructorId,String firstName,String lastName, String University) throws SQLException, ClassNotFoundException {
        //If there isn't a connection we call getConnection and create it there
        if(connection == null){
            getConnection();
        }
        //PreparedStatement creates template for SQLite insert
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO courses(CourseId,InstructorId,InstructorFirstName,InstructorLastName, University) values(?,?,?,?,?);");
        //Set what ? means below for the preparedStatement
        preparedStatement.setString(1, id);
        preparedStatement.setInt(2,instructorId);
        preparedStatement.setString(3,firstName);
        preparedStatement.setString(4,lastName);
        preparedStatement.setString(5,University);

        preparedStatement.execute();
    }

    public String[] getAllCourses() throws SQLException, ClassNotFoundException {
        if(connection == null){
            getConnection();
        }
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT CourseId FROM courses");
        String[] courses = this.resultSetToStringArray(resultSet,"CourseId");

        return courses;
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
     * @throw/
     */
    public String getStudentCourses(int studetnId) throws SQLException, ClassNotFoundException {
        if(connection == null){
            getConnection();
        }

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

    public String[] getStudentCoursesArray(int studentId) throws SQLException, ClassNotFoundException {
        String courses = getStudentCourses(studentId);
        String[] coursesArray = {""} ;
        if (courses != null)
            coursesArray = courses.split(":");
        return coursesArray;
    }

    public String[] getInstructorCourses(int instructorId) throws SQLException, ClassNotFoundException {
        if(connection == null){
            getConnection();
        }
        //String courses;
        ResultSet result;
        String strResult = "";

        //Searches Database by StudentId and selects Courses and StudentId
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT CourseId FROM courses WHERE InstructorId=?;");
        preparedStatement.setInt(1,instructorId);
        //Gets the value in the Courses field for the given StudentId
        result = preparedStatement.executeQuery();
       // courses = result.getString("CourseId");
        while (result.next()){
            strResult += result.getString("CourseId") + ":";
        }

        return strResult.split(":");
    }

    /***
     * Adds a course to a student object
     * @param studentId
     * @param newCourse
     * @throws SQLException
     */
    public void addCourseForStudent(int studentId, String newCourse) throws SQLException, ClassNotFoundException {
        String courses;
        //gets courses string from SQLite student table using getStudentCourses()
        courses = getStudentCourses(studentId);
        //if there are no courses, we make the course String equal to the new course
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

    public ResultSet searchStudentsTable(String sqlStatement) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlStatement);
        return resultSet;
    }

    /***
     * Checks if a student is currently registered in the database by Student Number
     * @param student - Student object
     * @return True - if Student is already registered
     *         False - if there is no Student Object
     */
    public Boolean isStudent(Student student) throws SQLException {
        ResultSet result;

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT StudentId FROM students WHERE StudentId=?;");
        preparedStatement.setInt(1,student.getId());

        result = preparedStatement.executeQuery();
        //If there is a result, then the Student object's Student Id is in the database
        if(result.next())
            return true;
        //If there is no result, then the Student object is not currently in database
        else
            return false;
    }

    public String[] resultSetToStringArray(ResultSet resultSet, String columnLabel) throws SQLException {
        String courses = "";
        while (resultSet.next()){
            courses = courses + resultSet.getString(columnLabel) + ":";
        }
        return courses.split(":");
    }

}

