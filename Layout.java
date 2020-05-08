import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.awt.*;

public class Layout {

    public void creatPanel(JPanel p, int i) {

        p.setBackground(Color.DARK_GRAY);
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 5));
        panel.setBackground(Color.DARK_GRAY);
        JTextField key = new JTextField("new Header");
        key.setBackground(Color.GRAY);
        key.setForeground(Color.WHITE);
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        key.setBorder(border);
        JTextField value = new JTextField("new value");
        value.setBorder(border);
        value.setBackground(Color.GRAY);
        value.setForeground(Color.WHITE);
        int keyWidth = key.getPreferredSize().width + 50;
        int keyHeight = key.getPreferredSize().height + 10;
        key.setPreferredSize(new Dimension(keyWidth, keyHeight));
        int valueWidth = value.getPreferredSize().width + 50;
        int valueHeight = value.getPreferredSize().height + 10;
        value.setPreferredSize(new Dimension(valueWidth, valueHeight));
        JCheckBox c1 = new JCheckBox();
        ImageIcon recycleBin = new ImageIcon("trash can-1.1s-18px.png");
        JButton recycle = new JButton(recycleBin);
        if (i == 0) {

            c1.setBackground(Color.DARK_GRAY);
            c1.setBorder(border);
            recycle.setBackground(Color.DARK_GRAY);
            recycle.setBorder(border);
            int recycleWidth = recycle.getPreferredSize().width - 1000;
            int recycleHeight = recycle.getPreferredSize().height - 4;
            recycle.setPreferredSize(new Dimension(recycleWidth, recycleHeight));
        }
        panel.add(key);
        panel.add(value);
        if (i == 0) {
            panel.add(c1);
            panel.add(recycle);
        }

        JButton newButton;

        GridBagConstraints gbc2 = new GridBagConstraints();
        if (i == 0) {
            newButton = new JButton("+  NEW");
            newButton.setForeground(Color.WHITE);
            newButton.setBackground(new Color(90, 80, 160));
            gbc2.gridx = 0;
            gbc2.gridy = 0;
            gbc2.fill = GridBagConstraints.HORIZONTAL;
            gbc2.insets = new Insets(3, 2, 2, 2);
            p.add(newButton, gbc2);

            gbc2.gridx = 0;
            gbc2.gridy = 1;
            gbc2.fill = GridBagConstraints.HORIZONTAL;
            gbc2.insets = new Insets(10, 10, 10, 10);
            p.add(panel, gbc2);

        } else if (i == 1) {

            gbc2.gridx = 0;
            gbc2.gridy = 0;
            gbc2.insets = new Insets(50, 50, 50, 50);

            p.add(panel, gbc2);

            newButton = new JButton("Copy to Clipboard");
            newButton.setForeground(Color.WHITE);
            newButton.setBackground(new Color(90, 80, 160));
            gbc2.gridx = 0;
            gbc2.gridy = 1;
            gbc2.insets = new Insets(50, 50, 50, 50);
            p.add(newButton, gbc2);

        }

        gbc2.gridx = 0;
        gbc2.gridy = 2;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        p.add(new JLabel(" "), gbc2);

    }
}