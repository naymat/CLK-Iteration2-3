import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/***
 * Prepares objects to be sent through outputStream
 * Adds a byte before sending an object to keep information about Object Type
 *
 * Byte Legend
 * S - Student Object
 * T - String Object(Text)
 *
 */
public class ClickerProtocol {

    ObjectOutputStream output;
    ObjectInputStream input;

    //Constructors

    public ClickerProtocol(ObjectInputStream input, ObjectOutputStream output) {
        this.input = input;
        this.output = output;
    }

    /***
     * Prepares and outputs student Object through OutputStream
     * @param student
     * @throws IOException
     */
    public void sendStudent(Student student) throws IOException {
        output.flush();
        output.writeByte('S');
        output.writeObject(student);
    }

    /**
     * Prepares and sends string through an OutputStream
     * @param string
     * @throws IOException
     */
    public void sendString(String string)throws IOException{
        output.flush();
        output.writeByte('T');
        output.writeObject(string);
    }

    public String[] requestStudentCourses(Student student) throws IOException, ClassNotFoundException {
        //Sends request
        output.flush();
        output.writeByte('R');
        output.writeObject(student);
        //Receives array of courses(String)
        Byte type = input.readByte();
        if (type == 'R'){
            return (String[]) input.readObject();
        }
        return null;
    }

    public String[] requestAllCourses() throws IOException, ClassNotFoundException{
        //Sends request
        output.flush();
        output.writeByte('A');
        output.writeObject("filler");
        //Receives array of courses(String)
        Byte type = input.readByte();
        if (type == 'R'){
            return (String[]) input.readObject();
        }
        return null;
    }

    public void enrollStudent(String course) throws IOException {
        output.flush();
        output.writeByte('E');
        output.writeObject(course);
    }


    public Boolean startSession(String strCourseId) throws IOException, ClassNotFoundException {
        output.flush();
        output.writeByte('B');
        output.writeObject(strCourseId);

        String status = (String)input.readObject();
        if(status.equalsIgnoreCase("true")){
            return true;
        }
        else
            return false;
    }
    public Question getQuestion() throws IOException, ClassNotFoundException {
        output.flush();
        output.writeByte('Q');
        output.writeObject("dummy");

        return (Question) input.readObject(); 
    }


}
