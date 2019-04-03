import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Server_GUI {
    private static JFrame frame;
    private JPanel mainPanel;
    private JPanel connectionPanel;
    private JTextField instructorTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JButton startConnectionButton;
    private JPanel coursePanel;
    private JTextField universityTextField;
    private JList<String> myCoursesJList;
    private JButton viewMyCoursesButton;
    private JTextField courseTextField;
    private JButton createCourseButton;
    private JButton createSessionButton;
    private JButton startSessionButton;
    private JPanel sessionEditorPanel;
    private JList sessionsList;
    private JTextField questionTextField;
    private JTextField answerTextField;
    private JButton backToCoursesButton;
    private JButton createQuestionButton;
    private JPanel sessionPanel;
    private JLabel questionLabel;
    private JButton backToCoursesButton1;
    private JButton nextQuestionButton;

    public static final int PORT=4444;
    Database clickerDatabase;
    Server server;

    String university;
    Instructor instructor;
    Session session;
    Iterator<Question> questionsIterator;

    String courseIdSession;

    public Server_GUI() {
        session = new Session();
        clickerDatabase = new Database();
        try {
            clickerDatabase.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        server = new Server(clickerDatabase,session);

        startConnectionButton.addActionListener(e -> {
            getInstructorInfoTextfields();
            startServer();
        });

        viewMyCoursesButton.addActionListener(e ->{
            updateMyCourses();
            updateSessionJList();
        });

        createCourseButton.addActionListener(e ->{
            addCourse();
            updateMyCourses();
        });

        createSessionButton.addActionListener(e->{
            try {
                createSession();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        createQuestionButton.addActionListener(e -> {
            createQuestion();
        });
        backToCoursesButton.addActionListener(e -> {
            courseIdSession = "";
            sessionEditorPanel.setVisible(false);
            coursePanel.setVisible(true);
        });
        startSessionButton.addActionListener(e -> {
            startSession();
        });
        backToCoursesButton1.addActionListener(e -> {
            courseIdSession = "";
            session.setInSession(false);
            sessionPanel.setVisible(false);
            coursePanel.setVisible(true);
        });
        nextQuestionButton.addActionListener(e -> {

        });
        nextQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextQuestion();
            }
        });
    }


    public static void main(String[] args) throws IOException {
        frame = new JFrame("Instructor App");

        frame.setContentPane(new Server_GUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void startServer(){
        System.out.println("Server is running");
        SwingWorker<Void,Void> swingWorker = new SwingWorker<Void, Void>() {
                protected Void doInBackground() throws Exception {
                    server.runServer();
                    return null;
                }
            };
        swingWorker.execute();
    }
    public void updateMyCourses(){
        try {
            String[] courses = clickerDatabase.getInstructorCourses(instructor.getId());
            myCoursesJList.setListData(courses);
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
    private void updateSessionJList() {
        try {
            String[] sessions = clickerDatabase.getSessions(instructor.getId());
            sessionsList.setListData(sessions);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void getInstructorInfoTextfields(){
        university = universityTextField.getText();
        String strInstructorId = instructorTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();

        try {
            int intInstructorId = Integer.parseInt(strInstructorId);

            if (university.equals("") || firstName.equals("") || lastName.equals("")){
                throw new IllegalArgumentException();
            }
            instructor = new Instructor(intInstructorId,firstName,lastName);
            hideConnectionPanel();
            updateMyCourses();
        }
        catch (NumberFormatException exception){
            JOptionPane.showMessageDialog(null,"Please enter a numeric value for instructor id");
        }
        catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(null, "Please enter a value for university/first/last name field(s)");
        }

    }

    public void hideConnectionPanel(){
        connectionPanel.setVisible(false);
        coursePanel.setVisible(true);
    }

    public void addCourse(){
        try {
            String strCourse = courseTextField.getText().toUpperCase();
            if (strCourse.equals(""))
                throw new IllegalArgumentException();

            Course course = new Course(strCourse,instructor,university);
            clickerDatabase.addCourse(course);
        }
        catch (IllegalArgumentException exception){
            JOptionPane.showMessageDialog(null, "Please enter a value for course field");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void createSession() throws SQLException, ClassNotFoundException {
        try {
            String strCourse = courseTextField.getText();
            if (strCourse.equals(""))
                throw new IllegalArgumentException();

            courseIdSession = strCourse;
            clickerDatabase.createSession(courseIdSession);

            coursePanel.setVisible(false);
            sessionEditorPanel.setVisible(true);
        }
        catch (IllegalArgumentException exception){
            JOptionPane.showMessageDialog(null, "Please enter a value for course field");
        }
    }
    private void createQuestion(){
        try {
            String strQuestion = questionTextField.getText();
            String strAnswer = answerTextField.getText();

            if (strQuestion.equals("") || strAnswer.equals("")) {
                throw new IllegalArgumentException();
            }
            Question question = new Question(strQuestion,strAnswer);
            clickerDatabase.addQuestion(courseIdSession,question);

            questionTextField.setText("");
            answerTextField.setText("");
        }
        catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(null,"Please enter values for question and answer fields");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"That question already exists");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void startSession() {
        try {
            String strCourse = courseTextField.getText();
            if (strCourse.equals("")) {
                throw new IllegalArgumentException();
            }

            courseIdSession = strCourse;

            Course course = new Course(courseIdSession,instructor,university);
            ArrayList<Question> questions = clickerDatabase.getQuestions(course.getId());
            questionsIterator = questions.iterator();

            session.setCourse(course);
            session.setQuestions(questions);
            session.setInSession(true);
            session.setQuestionIterator(questionsIterator);

            coursePanel.setVisible(false);
            sessionPanel.setVisible(true);


            nextQuestion();

        }
        catch (IllegalArgumentException exception){
            JOptionPane.showMessageDialog(null, "Please enter a value for course field");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void nextQuestion(){
        try {
            Question question = questionsIterator.next();
            session.setCurrentQuestion(question);
            questionLabel.setText(question.getQuestion());
        }
        catch (NoSuchElementException e){
            JOptionPane.showMessageDialog(null,"No more questions");
        }

    }


}
