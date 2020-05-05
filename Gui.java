
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.event.*;

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
        Exit.setMnemonic(KeyEvent.VK_E);
        Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
        ActionEvent.CTRL_MASK));
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

    
        JPanel panel1 = new JPanel(new BorderLayout(0, 0));
        JPanel panel12 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

    
        String comboboxChoices[] = {"GET","DELETE","POST","PUT","PATCH"};
        JComboBox comboBox = new JComboBox(comboboxChoices);
        JTextField URLAddress = new JTextField("https://api.myproduct.com/v1/users");
        int addressWidth  = URLAddress.getPreferredSize().width + 100;
        int addressHeight = URLAddress.getPreferredSize().height + 12;
        URLAddress.setPreferredSize(new Dimension(addressWidth,addressHeight));

        JButton sendButton = new JButton("Send");
        panel12.add(comboBox);
        panel12.add(URLAddress);
        panel12.add(sendButton);


        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();

      
        tabbedPane.add("Body", p1);
        tabbedPane.add("Header", p2);
        tabbedPane.add("Query", p3);
        tabbedPane.add("Auth", p4);

       
        
        panel1.add(panel12,BorderLayout.NORTH);
        panel1.add(tabbedPane,BorderLayout.CENTER);


        panel.add(panel1, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        Gui g = new Gui();
    }
}