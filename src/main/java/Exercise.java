public abstract class Exercise {
    String task;
    protected double grade;
    protected String[] correctAnswers;
    protected String[] userAnswers;
    static final double DONE_THRESHOLD = 0.7f;

    public boolean isDone(){
        return grade > DONE_THRESHOLD;
    }

    public double getGrade(){
        return grade;
    }

    public void setGrade(double grade){this.grade = grade;}
}
