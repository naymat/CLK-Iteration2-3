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
        statement.execute("CREATE TABLE IF NOT EXISTS students(" +
                "StudentId integer PRIMARY KEY," +
                "FirstName text NOT NULL," +
                "LastName text NOT NULL" +
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
        //If there isn't a connection we call getConnection and
        if(connection == null){
            getConnection();
        }

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students(StudentId,FirstName,LastName) values(?,?,?);");
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2,firstName);
        preparedStatement.setString(3,lastName);

        preparedStatement.execute();
    }

    public ResultSet searchStudents(String sqlStatement) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlStatement);
        return resultSet;
    }

}

