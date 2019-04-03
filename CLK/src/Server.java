import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    public static final int PORT=4444;
    Database clickerDatabase;
    JButton next;
    Session session;

    public Server(Database clickerDatabase, Session session) {
        this.session = session;
        this.clickerDatabase = clickerDatabase;
    }

    public void setNext(JButton next) {
        this.next = next;
    }

    public void runServer() throws IOException{
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server up and ready for connection");
        //run in a loop
        while (true) {
            Socket socket = serverSocket.accept();
            new ServerThread(socket,session).start();
        }


    }
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public class ServerThread extends Thread{
        Socket socket;
        ObjectOutputStream output;
        ObjectInputStream input;
        Database clickerDatabase;
        Student student;
        Boolean clientInSession = false;
        Session session;

        public ServerThread(Socket socket, Session session) {
            this.socket = socket;
            this.session = session;
            this.clickerDatabase = new Database();

            try {
                clickerDatabase.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        public void run(){
            try {
                output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
                input = new ObjectInputStream(socket.getInputStream());
                String message = "You are now connected";
                Student inputStudent = null;
                ClickerProtocol protocol = new ClickerProtocol(input,output);

                do {
                    try {
                        serverReceive();
                        System.out.println(session.isInSession());

                    }
                    catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                while (!message.equals("end"));
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        /**
         * Preforms predefined actions for server depending on what type of Object it receives
         * @throws IOException
         * @throws ClassNotFoundException
         */
        public void serverReceive() throws IOException, ClassNotFoundException, SQLException {
            byte type = input.readByte();
            //S - Student
            if (type == 'S') {
                student = (Student) input.readObject();
                if(!clickerDatabase.isStudent(student))
                    clickerDatabase.addStudent(student);

            }
            // T - String(text)
            if(type == 'T'){
                String inputString = (String) input.readObject();
                System.out.println(inputString);
            }
            //E - Enroll Student into a course and register that into the Database
            if(type == 'E'){
                String course = (String) input.readObject();
                clickerDatabase.addCourseForStudent(student.getId(),course);
            }
            //Returns the courses the Student is enrolled in
            if(type == 'R'){
                //Gets list of courses from database by using student number
                Student inputStudent = (Student) input.readObject();
                int studentNumber = inputStudent.getId();
                System.out.println(studentNumber);
                String[] listOfCourses = clickerDatabase.getStudentCoursesArray(studentNumber);
                //Send list of Courses to client
                output.flush();
                output.writeByte('R');
                output.writeObject(listOfCourses);
            }

            if (type == 'A'){
                //String[] allCourses = clickerDatabase.getAll  Courses();
                String recieved = (String) input.readObject();
                String allCourses[] = clickerDatabase.getAllCourses();
                /*for(int i =0; i < allCourses.length; i++){
                    System.out.println(allCourses[i]);
                }*/

                output.flush();
                output.writeByte('R');
                output.writeObject(allCourses);
            }

            if(type == 'B'){
                String recievedCourseId = (String) input.readObject();
                if(recievedCourseId.equalsIgnoreCase(session.getCourse().getId()) && session.isInSession())
                {
                    output.flush();
                    output.writeObject("true");
                    clientInSession = true;
                }
                else {
                    output.flush();
                    output.writeObject("false");
                    clientInSession = false;
                }
            }
            //When client sends this, we quit
            if(type == 'Q'){
                String recived = (String) input.readObject();
                output.writeObject(session.getCurrentQuestion());
            }

        }

    }

}
