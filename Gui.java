
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.plaf.metal.MetalComboBoxButton;

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
        Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
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

        // panel1
        JPanel panel1 = new JPanel(new BorderLayout(0, 0));
        JPanel panel12 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel12.setBackground(Color.WHITE);

        String comboboxChoices[] = { "GET", "DELETE", "POST", "PUT", "PATCH" };
        JComboBox comboBox = new JComboBox(comboboxChoices);
        Border border2 = BorderFactory.createLineBorder(Color.WHITE);
        comboBox.setBorder(border2);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.GRAY);

     
        

        JTextField URLAddress = new JTextField("https://api.myproduct.com/v1/users");
        int addressWidth = URLAddress.getPreferredSize().width + 430;
        int addressHeight = URLAddress.getPreferredSize().height + 12;
        URLAddress.setPreferredSize(new Dimension(addressWidth, addressHeight));
        URLAddress.setBorder(border2);
        
        JButton sendButton = new JButton("Send");
        sendButton.setBorder(border2);
        sendButton.setBackground(Color.WHITE);
        sendButton.setForeground(Color.GRAY);

        panel12.add(comboBox);
        panel12.add(URLAddress);
        panel12.add(sendButton);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
        p1.setBackground(Color.DARK_GRAY);

        JPanel p2 = new JPanel(new GridBagLayout());
        p2.setBackground(Color.DARK_GRAY);

        JPanel p3 = new JPanel(new GridBagLayout());
        p3.setBackground(Color.DARK_GRAY);

        JPanel p4 = new JPanel();
        p4.setBackground(Color.DARK_GRAY);

        tabbedPane.add("Body", p1);
        tabbedPane.add("Header", p2);
        tabbedPane.add("Query", p3);
        tabbedPane.add("Auth", p4);

        // header
        JPanel panelHeader = new JPanel(new GridLayout(1, 4, 10, 5));
        panelHeader.setBackground(Color.DARK_GRAY);
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
        c1.setBackground(Color.DARK_GRAY);
        c1.setBorder(border);
        ImageIcon recycleBin = new ImageIcon("trash can-1.1s-18px.png");
        JButton recycle = new JButton(recycleBin);
        recycle.setBackground(Color.DARK_GRAY);
        recycle.setBorder(border);
        int recycleWidth = recycle.getPreferredSize().width - 1000;
        int recycleHeight = recycle.getPreferredSize().height - 4;
        recycle.setPreferredSize(new Dimension(recycleWidth, recycleHeight));
        panelHeader.add(key);
        panelHeader.add(value);
        panelHeader.add(c1);
        panelHeader.add(recycle);

    

        JButton newHeader = new JButton("+  NEW");
        newHeader.setForeground(Color.WHITE);
        newHeader.setBackground(new Color(90,80,160));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(3,2,2,2);
        p2.add(newHeader, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 2;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(10,10,10,10);
        p2.add(panelHeader, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 3;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        p2.add(new JLabel(" "), gbc2);

        // body

        String choices[] = { "Form Data", "JSON" };
        JComboBox comboBox2 = new JComboBox(choices);
        comboBox2.setPreferredSize(new Dimension(760, 30));
        comboBox2.setBackground(Color.GRAY);
        comboBox2.setForeground(Color.white);
        p1.add(comboBox2);

        //query
        JPanel panelQuery = new JPanel(new GridLayout(1, 4, 10, 5));
        panelQuery.setBackground(Color.DARK_GRAY);
        JTextField keyQuery = new JTextField("new Header");
        keyQuery.setBackground(Color.GRAY);
        keyQuery.setForeground(Color.WHITE);
        keyQuery.setBorder(border);
        JTextField valueQuery = new JTextField("new value");
        valueQuery.setBorder(border);
        valueQuery.setBackground(Color.GRAY);
        valueQuery.setForeground(Color.WHITE);
        keyQuery.setPreferredSize(new Dimension(keyWidth, keyHeight));
        valueQuery.setPreferredSize(new Dimension(valueWidth, valueHeight));
        JCheckBox cQuery = new JCheckBox();
        cQuery.setBackground(Color.DARK_GRAY);
        cQuery.setBorder(border);
        JButton recycleQuery = new JButton(recycleBin);
        recycleQuery.setBackground(Color.DARK_GRAY);
        recycleQuery.setBorder(border);
        recycleQuery.setPreferredSize(new Dimension(recycleWidth, recycleHeight));
        panelQuery.add(keyQuery);
        panelQuery.add(valueQuery);
        panelQuery.add(cQuery);
        panelQuery.add(recycleQuery);


        JButton newQuery = new JButton("+  NEW");
        newQuery.setForeground(Color.WHITE);
        newQuery.setBackground(new Color(90,80,160));
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.insets = new Insets(3,2,2,2);
        p3.add(newQuery, gbc3);

        gbc3.gridx = 0;
        gbc3.gridy = 2;
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.insets = new Insets(10,10,10,10);
        p3.add(panelQuery, gbc3);

        gbc3.gridx = 0;
        gbc3.gridy = 3;
        gbc3.weightx = 1;
        gbc3.weighty = 1;
        p3.add(new JLabel(" "), gbc3);


         // Auth

         String choicesAuth[] = { "Bearer"};
         JComboBox comboBoxAuth = new JComboBox(choicesAuth);
         comboBoxAuth.setPreferredSize(new Dimension(760, 30));
         comboBoxAuth.setBackground(Color.GRAY);
         comboBoxAuth.setForeground(Color.white);
         p4.add(comboBoxAuth);


        panel1.add(panel12, BorderLayout.NORTH);
        panel1.add(tabbedPane, BorderLayout.CENTER);

        // panel2
        JPanel panel2 = new JPanel(new BorderLayout(0, 0));
        JLabel insomniaLable = new JLabel("Insomnia");
        insomniaLable.setFont(new Font("Verdana", Font.PLAIN, 25));
        insomniaLable.setBackground(new Color(100, 0, 200));
        insomniaLable.setForeground(Color.WHITE);
        insomniaLable.setOpaque(true);
        insomniaLable.setPreferredSize(new Dimension(250, 60));
        insomniaLable.setHorizontalAlignment(SwingConstants.CENTER);
        panel2.add(insomniaLable, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel22 = new JPanel();
        panel22.setLayout(new GridBagLayout());

        gbc.weightx = 0.5;
        gbc.weighty = 0.01;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField filter = new JTextField("Filter");
        panel22.add(filter, gbc);
        filter.setPreferredSize(new Dimension(200, 20));

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 0.01;
        gbc.weightx = 0.5;

        JMenuBar mb2 = new JMenuBar();
        JMenu menu2 = new JMenu("  +");
        JMenuItem newRequest = new JMenuItem("New Request");
        JMenuItem newFolder = new JMenuItem("New Folder");
        menu2.add(newRequest);
        menu2.add(newFolder);
        mb2.add(menu2);
        panel22.add(mb2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.BOTH;
    
        DefaultMutableTreeNode requests = new DefaultMutableTreeNode("Requests");
        DefaultMutableTreeNode folder = new DefaultMutableTreeNode();
        DefaultMutableTreeNode folder2 = new DefaultMutableTreeNode();
        requests.add(folder);
        requests.add(folder2);
        JTree jt = new JTree(requests);
        jt.setBackground(Color.DARK_GRAY);
        panel22.add(jt, gbc);

        gbc.weightx = 1;
        gbc.weighty = 1;

        panel22.add(new JLabel(" "), gbc);
        panel2.add(panel22, BorderLayout.CENTER);

        // panel3
        JPanel panel3 = new JPanel(new BorderLayout(0, 0));
        JPanel panel32 = new JPanel(new FlowLayout(FlowLayout.LEFT, 60, 8));
        panel32.setBackground(Color.WHITE);
        JLabel label1 = new JLabel("200 OK");
        label1.setBackground(Color.GREEN);
        JLabel label2 = new JLabel("6.60s");
        JLabel label3 = new JLabel("15.2KB");
        panel32.add(label1);
        panel32.add(label2);
        panel32.add(label3);

        JTabbedPane tabbedPane2 = new JTabbedPane();

        JPanel p5 = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
        p5.setBackground(Color.DARK_GRAY);
        String choices2[] = { "Raw", "Preview", "JSON" };
        JComboBox comboBox3 = new JComboBox(choices2);
        comboBox3.setPreferredSize(new Dimension(500, 30));
        p5.add(comboBox3);

        JPanel p6 = new JPanel(new FlowLayout());
        p6.setBackground(Color.DARK_GRAY);

        tabbedPane2.add("Message Body", p5);
        tabbedPane2.add("Header", p6);

        panel3.add(panel32, BorderLayout.NORTH);
        panel3.add(tabbedPane2, BorderLayout.CENTER);

        panel.add(panel1, BorderLayout.CENTER);
        panel.add(panel2, BorderLayout.WEST);
        panel.add(panel3, BorderLayout.EAST);

    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {
       //  UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
        Gui g = new Gui();
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
        System.out.println(info.getClassName())
;    }
}