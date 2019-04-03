import java.util.ArrayList;

public class Instructor {

    private ArrayList<Course> coursesTeaching; //ArrayList of all the courses the Instructor is currently teaching

    public Instructor(int id, String firstName, String lastName, ArrayList<Course> coursesTeaching) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.coursesTeaching = coursesTeaching;
    }
    public Instructor(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String firstName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String lastName;

    public ArrayList<Course> getCoursesTeaching() {
        return coursesTeaching;
    }

    public void setCoursesTeaching(ArrayList<Course> coursesTeaching) {
        this.coursesTeaching = coursesTeaching;
    }
}
