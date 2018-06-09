import java.util.concurrent.TimeUnit;

public class Writing extends Exercise {
    private String[] usefulWords;
    private static final int TIMEOUT = 500;

    public Writing(String task){
        this.task = task;
    }

    public Writing(String task, String[] usefulWords){
        this.task = task;
        this.usefulWords = usefulWords;
    }

    public Writing(String task, String[] usefulWords, String[] correctAnswers){
        this.task = task;
        this.usefulWords = usefulWords;
        this.correctAnswers = correctAnswers;
    }

    public void sendAnswer(String answer){
        try {
            TimeUnit.MILLISECONDS.sleep(TIMEOUT);
            //System.out.println("done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int calculateGradePercent(){
        int sum = 0;
        if(getUserAnswers().length > getCorrectAnswers().length) throw new UnsupportedOperationException();
        for(int i = 0; i < getCorrectAnswers().length; i++){
            if(getUserAnswers()[i].equals(getCorrectAnswers()[i])){
                sum++;
            }
        }
        return sum * 100 / getCorrectAnswers().length;
    }

    public String[] getCorrectAnswers(){
        return correctAnswers;
    }

    public String[] getUserAnswers(){
        return userAnswers;
    }

    public String[] getUsefulWords(){
        return usefulWords;
    }

    public void setUsefulWords(String[] usefulWords){
        this.usefulWords = usefulWords;
    }

    public String getTask(){
        return task;
    }
}
