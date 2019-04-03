import java.util.ArrayList;
import java.util.Iterator;

public class Session {



    private ArrayList<Question> questions;
    private Course course;
    private boolean inSession = false;
    private int questionNumber = 0;



    private Question currentQuestion;

    private Iterator<Question> questionIterator;


    public Session() {

    }

    public Session(Course course, ArrayList<Question> questions) {
        this.questions = questions;
        this.course = course;

    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public boolean isInSession() {
        return inSession;
    }

    public void setInSession(boolean inSession) {
        this.inSession = inSession;
    }

    public Iterator<Question> getQuestionIterator() {
        return questionIterator;
    }

    public void setQuestionIterator(Iterator<Question> questionIterator) {
        this.questionIterator = questionIterator;
    }

    public int getQuestionNumber(){
        return questionNumber;
    }
    public void setQuestionNumber(int questionNumber){
        this.questionNumber = questionNumber;
    }

    public void resetQuestionNumber(){
        this.questionNumber = 0;
    }


    public Question questionAtIndex(int index){
        Iterator<Question> questionIterator =  questions.iterator();
        for(int i = 0; i < index; i++){
            questionIterator.next();
        }

        return  questionIterator.next();
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }



}
