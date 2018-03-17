import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        String name = args[0];
        Socket socket = new Socket("localhost", 4444);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader bufferedReader = new java.io.BufferedReader(new InputStreamReader(System.in));
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

        Student sally = new Student(500755,"Naymat","Khan");
        output.flush();

        while (true) {
            String readerInput = bufferedReader.readLine();
            output.writeObject(sally);
            //printWriter.println(name + ": " + readerInput);
        }
    }
}
