import java.util.ArrayList;

public class Course
{
    private String id;
    private String title;
    private String instructor;
    private String university;

    ArrayList<Student> enrolledStudents;



    public void Course(){
        this.id = "";
        this.instructor = "";
        this.university = "";
    }

    /**
     * Returns Course id
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets Course id
     * @param id - String for course identification
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    /**
     * Returns ArrayList with Student objects that are enrolled in the course
     * @return ArrayList of type Student
     */
    public ArrayList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    /**
     * Sets the course
     * @param enrolledStudents
     */
    public void setEnrolledStudents(ArrayList<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }



}
