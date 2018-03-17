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
    public ClickerProtocol(ObjectOutputStream output) {
        this.output = output;
    }

    public ClickerProtocol(ObjectInputStream input) {
        this.input = input;
    }

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

    /**
     * Preforms predefined actions for server depending on what type of Object it receives
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void serverRecive() throws IOException, ClassNotFoundException {
        byte type = input.readByte();
        //S - Student
        if (type == 'S') {
            Student inputStudent = (Student) input.readObject();
            System.out.println(inputStudent.getId());
        }
        // T - String(text)
        if(type == 'T'){
            String inputString = (String) input.readObject();
            System.out.println(inputString);
        }

    }


}
