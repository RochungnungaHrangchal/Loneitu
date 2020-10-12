package in.ibridge.aizawl.loneitu;

public class QuestionModel {

    String question;
    String userid;
    String quesionid;

    public String getQuesionid() {
        return quesionid;
    }

    public void setQuesionid(String quesionid) {
        this.quesionid = quesionid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public QuestionModel()
    {

    }

    public QuestionModel(String questionid,String question,String userid)
    {
        this.quesionid=questionid;
        this.question=question;
        this.userid=userid;
    }


}
