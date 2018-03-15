import java.util.ArrayList;

public class Instructror {

    private int id;
    private String firstName;
    private String lastName;
    private ArrayList<Course> coursesTeaching; //ArrayList of all the courses the Instructor is currently teaching

    public Instructror(int id, String firstName, String lastName, ArrayList<Course> coursesTeaching) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.coursesTeaching = coursesTeaching;
    }
}
