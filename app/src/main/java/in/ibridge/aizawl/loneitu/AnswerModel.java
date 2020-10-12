package in.ibridge.aizawl.loneitu;

public class AnswerModel {

    String questionid;
    String questionuserid;
    String answer;
    String answerid;
    String answeruserid;

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getQuestionuserid() {
        return questionuserid;
    }

    public void setQuestionuserid(String questionuserid) {
        this.questionuserid = questionuserid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerid() {
        return answerid;
    }

    public void setAnswerid(String answerid) {
        this.answerid = answerid;
    }

    public String getAnsweruserid() {
        return answeruserid;
    }

    public void setAnsweruserid(String answeruserid) {
        this.answeruserid = answeruserid;
    }

    public AnswerModel(){

    }

    public AnswerModel( String questionid,
            String questionuserid,
            String answer,
            String answerid,
            String answeruserid)
    {
        this.questionid=questionid;
        this.questionuserid=questionuserid;
        this.answer=answer;
        this.answeruserid=answeruserid;
        this.answerid=answerid;
    }
}
