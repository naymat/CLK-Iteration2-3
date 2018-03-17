import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT=4444;
    public static void main(String[] args) throws IOException {
        new Server().runServer();
    }

    public void runServer() throws IOException{
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server up and ready for connection");
        //run in a loop
        while (true) {
            Socket socket = serverSocket.accept();
            new ServerThread(socket).start();
        }

    }

    public class ServerThread extends Thread{
        Socket socket;
        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        public void run(){
            try {
                ObjectOutput output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                String message = "You are now connected";
                Student inputStudent = null;
                do {
                    try {
                        inputStudent = (Student) input.readObject();
                        System.out.println(inputStudent.getId());
                    }
                    catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                while (!message.equals("end"));
                /*
                String message = null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while((message= bufferedReader.readLine()) != null){
                    System.out.println("incoming client message: " + message);
                }
                */
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
