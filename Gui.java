
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.event.KeyEvent;

import java.awt.*;

public class Gui {

    private JFrame frame;

    public Gui() {
        frame = new JFrame("Insomnia - My Request");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(760, 825));
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(2, 2));
        frame.setContentPane(panel);
        frame.setVisible(true);

        // create a menubar
        JMenuBar mb = new JMenuBar();
        // create a menu
        JMenu menuApplication = new JMenu("Application");
        // create menuitems
        JMenuItem options = new JMenuItem("Options");
        options.setMnemonic(KeyEvent.VK_T);

        JMenuItem Exit = new JMenuItem("Exit");
        // add menu items to menu
        menuApplication.add(options);
        menuApplication.add(Exit);
        // add menu to menubar
        mb.add(menuApplication);

        JMenu menuView = new JMenu("View");
        JMenuItem toggleFullScreen = new JMenuItem("Toggle Full Screen");
        JMenuItem toggleSidebar = new JMenuItem("Toggle Sidebar");
        menuView.add(toggleFullScreen);
        menuView.add(toggleSidebar);
        mb.add(menuView);

        JMenu menuHelp = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        JMenuItem help = new JMenuItem("Help");
        menuHelp.add(about);
        menuHelp.add(help);
        mb.add(menuHelp);

         frame.setJMenuBar(mb);

         
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));


      
    }

    public static void main(String[] args) {
        Gui g = new Gui();
    }
}