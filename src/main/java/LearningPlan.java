import java.util.List;

public class LearningPlan {
    private int exercisesNumber, excercisesDone;
    private Mode mode;
    private List<Exercise> exerciseList;
    private final static double PLAN_DONE_THRESHOLD = 70;
    private final static double GRADE_3_THRESHOLD = 0.5;
    private final static double GRADE_4_THRESHOLD = 0.7;
    private final static double GRADE_5_THRESHOLD = 0.9;

    public LearningPlan(){}

    public LearningPlan(Mode mode, List<Exercise> exerciseList){
        this.mode = mode;
        this.exerciseList = exerciseList;
        this.exercisesNumber = exerciseList.size();
    }

    public int calculateDoneExercisesPercent() {
        int result = calculateDoneExercises() * 100 / getExercisesNumber();
        if (result < 0 || result > 100) throw new IllegalArgumentException();

        return result;
    }

    public boolean isDone() {
        return calculateDoneExercisesPercent() > PLAN_DONE_THRESHOLD;
    }

    public int calculateDoneExercises() {
        int sum = 0;
        for (Exercise ex: getExerciseList()) {
            if(ex.isDone()) sum++;
        }
        return sum;
    }

    public int calculateGrade(double grade){
        if(grade < 0.0 || grade > 1.0) throw new IllegalArgumentException();

        if(grade < GRADE_3_THRESHOLD) return 2;
        else if(grade < GRADE_4_THRESHOLD) return 3;
        else if(grade < GRADE_5_THRESHOLD) return 4;

        return 5;
    }

    public double getAverageGrade(List<Exercise> exerciseList){
        double average = exerciseList
                .stream()
                .mapToDouble(a -> a.getGrade())
                .average().orElse(0.0);
        return average;
    }

    public void addExercise(Exercise exercise){
        this.exerciseList.add(exercise);
        exercisesNumber++;
    }

    public void removeExercise(int index){
        this.exerciseList.remove(index);
        exercisesNumber--;
    }

    public List<Exercise> getExerciseList(){
        return exerciseList;
    }

    public void setExercisesNumber(int number){
        this.exercisesNumber = number;
    }

    public int getExercisesNumber(){
        return exercisesNumber;
    }

}
