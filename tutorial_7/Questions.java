public class Questions {
    private final String question;
    private final String option1;
    private final String option2;
    private final String answer;

    public Questions() {
        this("Which keyword creates an object?", "new", "class", "new");
    }

    public Questions(String question, String option1, String option2, String answer) {
        if (question == null || question.trim().isEmpty() || option1 == null
                || option2 == null || option1.equals(option2)
                || (!option1.equals(answer) && !option2.equals(answer))) {
            throw new IllegalArgumentException("A question needs two different options and a matching answer.");
        }
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.answer = answer;
    }

    public String getQuestion() { return question; }
    public String getOption1() { return option1; }
    public String getOption2() { return option2; }
    public boolean isCorrect(String choice) { return answer.equals(choice); }
}
