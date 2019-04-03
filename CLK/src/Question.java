import java.io.Serializable;

public class Question implements Serializable {

    private String question;
    private String answer;
    private String dummyAnswer1;
    private String dummyAnswer2;
    private String getDummyAnswer3;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDummyAnswer1() {
        return dummyAnswer1;
    }

    public void setDummyAnswer1(String dummyAnswer1) {
        this.dummyAnswer1 = dummyAnswer1;
    }

    public String getDummyAnswer2() {
        return dummyAnswer2;
    }

    public void setDummyAnswer2(String dummyAnswer2) {
        this.dummyAnswer2 = dummyAnswer2;
    }

    public String getGetDummyAnswer3() {
        return getDummyAnswer3;
    }

    public void setGetDummyAnswer3(String getDummyAnswer3) {
        this.getDummyAnswer3 = getDummyAnswer3;
    }

    public Boolean isAnswerCorrect(String answer){
        if(answer.equalsIgnoreCase(this.answer))
            return true;

        return false;
    }

}
