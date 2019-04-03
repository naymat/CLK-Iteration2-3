import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client_GUI {
    private static JFrame frame;
    private JTextField JTextFieldStuduentNumber;
    private JTextField JTextFieldFirstName;
    private JTextField JTextFieldLastName;
    private JButton JButtonConnect;
    private JPanel JPanelMain;
    private JPanel JPanelLogin;
    private JList allCourses;
    private JTextField courseTextField;
    private JButton viewAllCoursesButton;
    private JButton joinSessionButton;
    private JButton enrollButton;
    private JPanel StudentCourses;
    private JList<String> studentCourses;
    private JPanel JPanelCourses;
    private JButton studentCoursesButton;
    private JPanel sessionJPanel;
    private JLabel quesitonJLabel;
    private JTextField answerTextField;
    private JButton submitButton;
    private JButton refreshQuestionButton;
    private JButton goBackButton;


    private int studentNumber;
    private String firstName;
    private String lastName;

    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    ClickerProtocol protocol;
    Student student;
    Question question;

    public static void main(String[] args){
        frame = new JFrame("Student App");

        frame.setContentPane(new Client_GUI().JPanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public Client_GUI() {
        JButtonConnect.addActionListener(e -> {
            try {
                studentNumber = Integer.parseInt(JTextFieldStuduentNumber.getText());
                firstName = JTextFieldFirstName.getText();
                lastName = JTextFieldLastName.getText();

                if(firstName.equals("") || lastName.equals("")){
                    throw new IllegalArgumentException();
                }
                startConnection();


            }
            catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null,"Please enter a numeric value for student number");
            }
            catch (IllegalArgumentException exception){
                JOptionPane.showMessageDialog(null,"Please enter a value for first/last name field(s)");
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Unable to connect, make sure server is running");
            }


        });

        enrollButton.addActionListener(e -> {

            try {
                enrollStudent();
                getStudentCourses();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                //
            }
        });

        studentCoursesButton.addActionListener(e ->{
            try {
                getStudentCourses();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }

        });


        viewAllCoursesButton.addActionListener(e -> {
            try {
                String courses[] =  protocol.requestAllCourses();
                allCourses.setListData(courses);

            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        joinSessionButton.addActionListener(e -> {
            startSession();
        });
        refreshQuestionButton.addActionListener(e -> {
            refreshQuestion();
        });
        submitButton.addActionListener(e -> {
            checkAnswer();
        });
        goBackButton.addActionListener(e -> {
            sessionJPanel.setVisible(false);
            JPanelCourses.setVisible(true);
        });
    }
    private void checkAnswer(){
        try{
            String answer = answerTextField.getText();
            if(answer.equals(""))
                throw new IllegalArgumentException();

            if (answer.equalsIgnoreCase(question.getAnswer())){
                JOptionPane.showMessageDialog(null,"You got it correct!");
            }
            else
                JOptionPane.showMessageDialog(null, "Sorry, you got it wrong");
        }
        catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(null,"Please enter an answer in the 'Your Answer' field");
        }

    }
    private void refreshQuestion(){
        try {
            question = protocol.getQuestion();
            quesitonJLabel.setText(question.getQuestion());
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    private void startSession() {
        try {
            String strCourseId = courseTextField.getText();
            if (strCourseId.equals(""))
                throw new IllegalArgumentException();
            if(protocol.startSession(strCourseId)){
                JPanelCourses.setVisible(false);
                sessionJPanel.setVisible(true);
                refreshQuestion();
            }
            else
                JOptionPane.showMessageDialog(null, "No current session for that course");

        }
        catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(null,"Please enter a valid course for the course field");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    public void startConnection() throws IOException {
        socket = new Socket("localhost", 4444);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

        protocol = new ClickerProtocol(inputStream,outputStream);

        JPanelLogin.setVisible(false);
        JPanelCourses.setVisible(true);

        student = new Student(studentNumber,firstName,lastName);
        protocol.sendStudent(student);

    }
    public void getStudentCourses() throws IOException, ClassNotFoundException {
        String courses[] = protocol.requestStudentCourses(student);
        studentCourses.setListData(courses);
    }
    public void enrollStudent() throws IOException {
        String course = courseTextField.getText();
        protocol.enrollStudent(course);
    }

}
