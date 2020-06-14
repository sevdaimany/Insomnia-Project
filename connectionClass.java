import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.*;

public class connectionClass {
    private static String[] args;
    private static JPanel panelWest_Center;
    private static JPanel panelMessageBodyCenter;
    private static JPanel panelHeaderEast;
    private static CardLayout card;
    private static JLabel label1;
    private static JLabel label3;
    private static JLabel label2;
    private static long startTime;
    private static Insomnia insomnia;
    private static Gui gui;

    public static void setConnectionClassFields(String[] args, JPanel panelWest_Center, JPanel panelMessageBodyCenter,
            JPanel panelHeaderEast, CardLayout card, JLabel label1, JLabel label3, JLabel label2, long startTime) {
        connectionClass.args = args;
        connectionClass.panelWest_Center = panelWest_Center;
        connectionClass.panelMessageBodyCenter = panelMessageBodyCenter;
        connectionClass.panelHeaderEast = panelHeaderEast;
        connectionClass.card = card;
        connectionClass.label1 = label1;
        connectionClass.label3 = label3;
        connectionClass.label2 = label2;
        connectionClass.startTime = startTime;
        insomnia = new Insomnia();
        insomnia.execute();
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {
        try {
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            UIManager.getLookAndFeelDefaults().put("Tree.background", Color.RED);
            UIManager.getLookAndFeelDefaults().put("Tree.textBackground", Color.DARK_GRAY);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        gui= new Gui();

    }

    public static void runGUi() {
        gui.updateGUI(panelWest_Center, panelMessageBodyCenter, panelHeaderEast, card, label1, label3, label2,
                startTime);
    }

    public static String[] getArgs() {
        return args;
    }

    public static void setArgs(String[] args) {
        connectionClass.args = args;
    }

    public static JPanel getPanelWest_Center() {
        return panelWest_Center;
    }

    public static void setPanelWest_Center(JPanel panelWest_Center) {
        connectionClass.panelWest_Center = panelWest_Center;
    }

    public static JPanel getPanelMessageBodyCenter() {
        return panelMessageBodyCenter;
    }

    public static void setPanelMessageBodyCenter(JPanel panelMessageBodyCenter) {
        connectionClass.panelMessageBodyCenter = panelMessageBodyCenter;
    }

    public static JPanel getPanelHeaderEast() {
        return panelHeaderEast;
    }

    public static void setPanelHeaderEast(JPanel panelHeaderEast) {
        connectionClass.panelHeaderEast = panelHeaderEast;
    }

    public static CardLayout getCard() {
        return card;
    }

    public static void setCard(CardLayout card) {
        connectionClass.card = card;
    }

    public static JLabel getLabel1() {
        return label1;
    }

    public static void setLabel1(JLabel label1) {
        connectionClass.label1 = label1;
    }

    public static JLabel getLabel3() {
        return label3;
    }

    public static void setLabel3(JLabel label3) {
        connectionClass.label3 = label3;
    }

    public static JLabel getLabel2() {
        return label2;
    }

    public static void setLabel2(JLabel label2) {
        connectionClass.label2 = label2;
    }

    public static long getStartTime() {
        return startTime;
    }

    public static void setStartTime(long startTime) {
        connectionClass.startTime = startTime;
    }

}