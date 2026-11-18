import java.awt.Component;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class QuizGuiTest {
    private static JButton button(Container root, String text) {
        for (Component child : root.getComponents()) {
            if (child instanceof JButton && text.equals(((JButton) child).getText())) return (JButton) child;
            if (child instanceof Container) {
                JButton found = button((Container) child, text);
                if (found != null) return found;
            }
        }
        return null;
    }

    private static boolean hasLabel(Container root, String text) {
        for (Component child : root.getComponents()) {
            if (child instanceof JLabel && text.equals(((JLabel) child).getText())) return true;
            if (child instanceof Container && hasLabel((Container) child, text)) return true;
        }
        return false;
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void capture(QuizBattleGUI gui, File folder, String name) throws Exception {
        BufferedImage image = new BufferedImage(gui.getWidth(), gui.getHeight(), BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D graphics = image.createGraphics();
        gui.paintAll(graphics);
        graphics.dispose();
        ImageIO.write(image, "png", new File(folder, name + ".png"));
    }

    public static void main(String[] args) throws Exception {
        File folder = new File(args[0]);
        folder.mkdirs();
        SwingUtilities.invokeAndWait(() -> {
            QuizBattleGUI gui = new QuizBattleGUI();
            try {
                gui.setVisible(true);
                check(hasLabel(gui, "Which keyword creates an object?"), "Initial question missing");
                check(!button(gui, "Next question").isEnabled(), "Question can be skipped");
                capture(gui, folder, "initial");
                button(gui, "class").doClick(0);
                check(hasLabel(gui, "Wrong! Try Again!"), "Wrong answer feedback missing");
                check(hasLabel(gui, "Question 1 / 5     |     Score: 0"), "Wrong answer changed score");
                capture(gui, folder, "wrong");
                JButton correct = button(gui, "new");
                correct.doClick(0);
                check(hasLabel(gui, "Correct! You defeated the Code Boss!"), "Correct answer feedback missing");
                correct.doClick(0);
                check(hasLabel(gui, "Question 1 / 5     |     Score: 1"), "Repeated click added points");
                capture(gui, folder, "correct");
                button(gui, "Next question").doClick(0);
                String[] answers = {"private", "extends", "super", "ArrayList"};
                for (int i = 0; i < answers.length; i++) {
                    button(gui, answers[i]).doClick(0);
                    button(gui, i == answers.length - 1 ? "Finish" : "Next question").doClick(0);
                }
                check(hasLabel(gui, "Java Champion! Final score: 5 / 5"), "Quiz did not finish");
                capture(gui, folder, "completed");
                button(gui, "Restart").doClick(0);
                check(hasLabel(gui, "Question 1 / 5     |     Score: 0"), "Restart did not reset quiz");
                check(button(gui, "new").isEnabled(), "Answers disabled after restart");
                Questions question = new Questions();
                check(question.isCorrect("new") && !question.isCorrect("class")
                        && !question.isCorrect(null), "Question answer matching failed");
                System.out.println("GUI: wrong answer, retry, scoring, duplicate click, progression, completion and restart passed.");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            } finally {
                gui.dispose();
            }
        });
    }
}
