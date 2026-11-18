import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class QuizBattleGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final Questions[] questions = {
        new Questions(),
        new Questions("Which access modifier hides a field?", "public", "private", "private"),
        new Questions("Which keyword declares inheritance?", "extends", "import", "extends"),
        new Questions("Which keyword calls a parent constructor?", "this", "super", "super"),
        new Questions("Which collection can grow as tasks are added?", "ArrayList", "int", "ArrayList")
    };
    private final JLabel lblQuestion = new JLabel();
    private final JLabel lblResult = new JLabel("Answer the question!");
    private final JLabel lblProgress = new JLabel();
    private final JButton btn1 = new JButton();
    private final JButton btn2 = new JButton();
    private final JButton btnNext = new JButton("Next question");
    private final JButton btnRestart = new JButton("Restart");
    private int questionIndex;
    private int score;
    private boolean answered;
    private boolean finished;

    public QuizBattleGUI() {
        super("Programming Quiz Battle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 360);
        setMinimumSize(new java.awt.Dimension(650, 360));
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout(18, 18));
        content.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        setContentPane(content);
        lblProgress.setForeground(new Color(48, 78, 113));
        content.add(lblProgress, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3, 1, 12, 12));
        lblQuestion.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        center.add(lblQuestion);
        JPanel choices = new JPanel(new GridLayout(1, 2, 18, 0));
        choices.add(btn1);
        choices.add(btn2);
        center.add(choices);
        lblResult.setHorizontalAlignment(SwingConstants.LEFT);
        center.add(lblResult);
        content.add(center, BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        controls.add(btnRestart);
        controls.add(btnNext);
        content.add(controls, BorderLayout.SOUTH);
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btnNext.addActionListener(this);
        btnRestart.addActionListener(this);
        showQuestion();
    }

    private void showQuestion() {
        Questions question = questions[questionIndex];
        answered = false;
        finished = false;
        lblQuestion.setText(question.getQuestion());
        btn1.setText(question.getOption1());
        btn2.setText(question.getOption2());
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btnNext.setEnabled(false);
        btnNext.setText(questionIndex == questions.length - 1 ? "Finish" : "Next question");
        lblResult.setForeground(Color.DARK_GRAY);
        lblResult.setText("Answer the question!");
        updateProgress();
    }

    private void updateProgress() {
        lblProgress.setText("Question " + (questionIndex + 1) + " / " + questions.length
                + "     |     Score: " + score);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnRestart) {
            score = 0;
            questionIndex = 0;
            showQuestion();
        } else if (source == btnNext && answered && !finished) {
            if (questionIndex < questions.length - 1) {
                questionIndex++;
                showQuestion();
            } else {
                finished = true;
                lblQuestion.setText("Java Academy Challenge complete!");
                lblResult.setText("Java Champion! Final score: " + score + " / " + questions.length);
                btnNext.setEnabled(false);
            }
        } else if ((source == btn1 || source == btn2) && !answered && !finished) {
            JButton button = (JButton) source;
            if (questions[questionIndex].isCorrect(button.getText())) {
                answered = true;
                score++;
                lblResult.setText("Correct! You defeated the Code Boss!");
                lblResult.setForeground(new Color(26, 112, 60));
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btnNext.setEnabled(true);
                updateProgress();
            } else {
                lblResult.setText("Wrong! Try Again!");
                lblResult.setForeground(new Color(174, 45, 45));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizBattleGUI().setVisible(true));
    }
}
