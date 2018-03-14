public class Student
{
    private String id;
    private String firstName;
    private String lastName;

    public void Student(){
        this.id = "";
        this.firstName = "";
        this.lastName = "";
    }
    public void Student(String id){
        this.id = id;

    }
    public void Student(String id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

    }
}
