
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

public class Gui {

    private JFrame frame;

    public Gui() {
        frame = new JFrame("Insomnia - My Request");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(760,825));
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(2,2));
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    public static void main(String[ ] args) {
        Gui g = new Gui();
    }
}