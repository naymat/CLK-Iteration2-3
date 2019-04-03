import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //String name = args[0];
        Socket socket = new Socket("localhost", 4444);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader bufferedReader = new java.io.BufferedReader(new InputStreamReader(System.in));
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        Student sally = new Student(500755,"Naymat","Khan");
        Student sally2 = new Student(50078292, "Jhon","Smith");
        output.flush();

        while (true) {
            ClickerProtocol protocol = new ClickerProtocol(input,output);

            String courses[] = protocol.requestAllCourses();


           /* input.readByte();
            String courses[] = (String[]) input.readObject();*/
            for(int i = 0; i < courses.length;i++){
                System.out.println(courses[i]);
            }
            /*protocol.requestAllCourses();
*/
/*
            protocol.sendStudent(sally);
            protocol.sendString("IT WORKS!!!");
*/

        }
    }
}