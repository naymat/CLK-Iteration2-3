import java.util.ArrayList;

public class Course
{
    private String id;
    private String title;
    private Instructor instructor;
    private String university;

    ArrayList<Student> enrolledStudents;


    public Course(String id, Instructor instructor, String university){
        this.id = id;
        this.instructor = instructor;
        this.university = university;

    }

    public Course(String id, int instructorId,String firstName, String lastName, String university){
        this.id = id;
        this.instructor = new Instructor(instructorId,firstName,lastName);
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

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
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
