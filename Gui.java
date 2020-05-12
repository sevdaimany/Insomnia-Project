
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.*;

import java.awt.*;

/**
 * this class represent a GUI for insomnia client
 * 
 * @author sevda imany
 * @version 0.0
 */
public class Gui {

    private JFrame frame;

    public Gui() {
        
        // create frame
        frame = new JFrame("Insomnia - My Request");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(760, 825));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
      

        JPanel mainPanel = new JPanel(new BorderLayout(2, 2));
        frame.setContentPane(mainPanel);

        // create a menubar
        JMenuBar menuBar = new JMenuBar();
        // create a menu

        // create Application menu
        JMenu menuApplication = new JMenu("Application");
        menuApplication.setMnemonic(KeyEvent.VK_A);

        // create menuitems

        // options
        JMenuItem options = new JMenuItem("Options");


        options.setMnemonic(KeyEvent.VK_O);
        options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));


        options.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                  //create option Dialog
        JDialog optionDialog = new JDialog(frame,"Options",false);
         optionDialog.setVisible(true);
         optionDialog.setBounds(530, 280, 300, 210);
         optionDialog.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
 
         JCheckBox followRedirectCheckBox = new JCheckBox(" follow redirect ");
         followRedirectCheckBox.setPreferredSize(new Dimension(250, 30));
 
         JRadioButton exiRadioButton = new JRadioButton(" Exit ",true);
         JRadioButton hideRadioButton = new JRadioButton(" Hide on system tray ");
         ButtonGroup exiButtonGroup = new ButtonGroup();
         exiButtonGroup.add(exiRadioButton);
         exiButtonGroup.add(hideRadioButton);
 
         JRadioButton lightTheme = new JRadioButton("light theme");
         JRadioButton darkTheme = new JRadioButton("dark theme",true);
         ButtonGroup theme = new ButtonGroup();
         theme.add(lightTheme);
         theme.add(darkTheme);
 
         JButton saveButton = new JButton("Save");
         saveButton.setPreferredSize(new Dimension(100,25));
 
         optionDialog.add(followRedirectCheckBox);
         optionDialog.add(exiRadioButton);
         optionDialog.add(hideRadioButton);
         optionDialog.add(lightTheme);
         optionDialog.add(darkTheme);
         optionDialog.add(saveButton);
            }
        });
      

      

        // Exit
        JMenuItem Exit = new JMenuItem("Exit");
          
        Exit.setMnemonic(KeyEvent.VK_E);
        Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));

        // add menu items to menu
        menuApplication.add(options);
        menuApplication.add(Exit);
        // add menu to menubar

        // create view menu

        JMenu menuView = new JMenu("View");
        menuView.setMnemonic(KeyEvent.VK_V);

        JMenuItem toggleFullScreen = new JMenuItem("Toggle Full Screen");
        toggleFullScreen.setMnemonic(KeyEvent.VK_F11);
        toggleFullScreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SLASH, ActionEvent.CTRL_MASK));
        JMenuItem toggleSidebar = new JMenuItem("Toggle Sidebar");
        toggleSidebar.setMnemonic(KeyEvent.VK_F12);
        toggleSidebar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, ActionEvent.CTRL_MASK));

        // add components
        menuView.add(toggleFullScreen);
        menuView.add(toggleSidebar);

        // create help menu
        JMenu menuHelp = new JMenu("Help");
        menuHelp.setMnemonic(KeyEvent.VK_H);

        JMenuItem about = new JMenuItem("About");
        about.setMnemonic(KeyEvent.VK_B);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.ALT_MASK));
        

      /*  JOptionPane aboutOptionPane = new JOptionPane("About");
        aboutOptionPane.setBorder(new EmptyBorder(5,5,5,5));
        aboutOptionPane.showMessageDialog(frame,"Hi i'm sevda!\nInsomnia started as a project of mine in 2020.\nMy email : sevdaimany@gmail.com\nMy student number : 9831010 ","About", JOptionPane.INFORMATION_MESSAGE);
    */   
        
        JMenuItem help = new JMenuItem("Help");
        help.setMnemonic(KeyEvent.VK_H);
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK));


        // add components
        menuHelp.add(about);
        menuHelp.add(help);

        // add menus to menu bar
        menuBar.add(menuApplication);
        menuBar.add(menuView);
        menuBar.add(menuHelp);

        frame.setJMenuBar(menuBar);

                                                                                    // <<<<<<<<<<<< create center panel >>>>>>>>>>>>>>>
        JPanel panelCenter = new JPanel(new BorderLayout(0, 0));

        // create center_up panel
        JPanel panelCenter_North = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelCenter_North.setBackground(Color.WHITE);

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

        // add components
        panelCenter_North.add(comboBox);
        panelCenter_North.add(URLAddress);
        panelCenter_North.add(sendButton);

        JTabbedPane tabbedPane = new JTabbedPane();

        // create body panel
        JPanel panelBody = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
        panelBody.setBackground(Color.DARK_GRAY);

        // create header panel
        JPanel panelHeaderCenter = new JPanel(new GridBagLayout());
        panelHeaderCenter.setBackground(Color.DARK_GRAY);

        // create Query panel
        JPanel panelQueryCenter = new JPanel(new GridBagLayout());
        panelQueryCenter.setBackground(Color.DARK_GRAY);

        // create Auth panel
        JPanel panelAuth = new JPanel();
        panelAuth.setBackground(Color.DARK_GRAY);

        // add panels to tapeddpane
        tabbedPane.add("Body", panelBody);
        tabbedPane.add("Header", panelHeaderCenter);
        tabbedPane.add("Query", panelQueryCenter);
        tabbedPane.add("Auth", panelAuth);

        // panel header center
        JPanel panelHeader = new JPanel(new GridLayout(1, 4, 10, 5));
        panelHeader.setBackground(Color.DARK_GRAY);

        // key in header
        JTextField key = new JTextField("new Header");
        key.setBackground(Color.GRAY);
        key.setForeground(Color.WHITE);
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        key.setBorder(border);

        // value in header
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

        // checkbox in header
        JCheckBox c1 = new JCheckBox();
        c1.setBackground(Color.DARK_GRAY);
        c1.setBorder(border);

        // recycle button in header
        ImageIcon recycleBin = new ImageIcon("trash can-1.1s-18px.png");
        JButton recycle = new JButton(recycleBin);
        recycle.setBackground(Color.DARK_GRAY);
        recycle.setBorder(border);
        int recycleWidth = recycle.getPreferredSize().width - 1000;
        int recycleHeight = recycle.getPreferredSize().height - 4;
        recycle.setPreferredSize(new Dimension(recycleWidth, recycleHeight));

        // add components to header panel
        panelHeader.add(key);
        panelHeader.add(value);
        panelHeader.add(c1);
        panelHeader.add(recycle);

        // create new button
        JButton newHeader = new JButton("+  NEW");
        newHeader.setForeground(Color.WHITE);
        newHeader.setBackground(new Color(90, 80, 160));

        GridBagConstraints gbc2 = new GridBagConstraints();

        // first row
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(3, 2, 2, 2);
        panelHeaderCenter.add(newHeader, gbc2);

        // second row
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(10, 10, 10, 10);
        panelHeaderCenter.add(panelHeader, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 3;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        panelHeaderCenter.add(new JLabel(" "), gbc2);

        // panel body

        String choices[] = { "Form Data", "JSON" ,"Binary Data"};
        JComboBox comboBox2 = new JComboBox(choices);
        comboBox2.setPreferredSize(new Dimension(760, 30));
        comboBox2.setBackground(Color.GRAY);
        comboBox2.setForeground(Color.white);
        panelBody.add(comboBox2);

        // panel Query
        JPanel panelQuery = new JPanel(new GridLayout(1, 4, 10, 5));
        panelQuery.setBackground(Color.DARK_GRAY);

        // create key
        JTextField keyQuery = new JTextField("new Header");
        keyQuery.setBackground(Color.GRAY);
        keyQuery.setForeground(Color.WHITE);
        keyQuery.setBorder(border);

        // create value
        JTextField valueQuery = new JTextField("new value");
        valueQuery.setBorder(border);
        valueQuery.setBackground(Color.GRAY);
        valueQuery.setForeground(Color.WHITE);
        keyQuery.setPreferredSize(new Dimension(keyWidth, keyHeight));
        valueQuery.setPreferredSize(new Dimension(valueWidth, valueHeight));

        // create checkBox
        JCheckBox cQuery = new JCheckBox();
        cQuery.setBackground(Color.DARK_GRAY);
        cQuery.setBorder(border);

        // create recycle button
        JButton recycleQuery = new JButton(recycleBin);
        recycleQuery.setBackground(Color.DARK_GRAY);
        recycleQuery.setBorder(border);
        recycleQuery.setPreferredSize(new Dimension(recycleWidth, recycleHeight));

        // add cpmonents to Query panel
        panelQuery.add(keyQuery);
        panelQuery.add(valueQuery);
        panelQuery.add(cQuery);
        panelQuery.add(recycleQuery);

        // create new button
        JButton newQuery = new JButton("+  NEW");
        newQuery.setForeground(Color.WHITE);
        newQuery.setBackground(new Color(90, 80, 160));

        GridBagConstraints gbc3 = new GridBagConstraints();

        // first row
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.insets = new Insets(3, 2, 2, 2);
        panelQueryCenter.add(newQuery, gbc3);

        // second row
        gbc3.gridx = 0;
        gbc3.gridy = 2;
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.insets = new Insets(10, 10, 10, 10);
        panelQueryCenter.add(panelQuery, gbc3);

        gbc3.gridx = 0;
        gbc3.gridy = 3;
        gbc3.weightx = 1;
        gbc3.weighty = 1;
        panelQueryCenter.add(new JLabel(" "), gbc3);

        // panel Auth

        String choicesAuth[] = { "Bearer" };
        JComboBox comboBoxAuth = new JComboBox(choicesAuth);
        comboBoxAuth.setPreferredSize(new Dimension(760, 30));
        comboBoxAuth.setBackground(Color.GRAY);
        comboBoxAuth.setForeground(Color.WHITE);
        panelAuth.add(comboBoxAuth);

        panelCenter.add(panelCenter_North, BorderLayout.NORTH);
        panelCenter.add(tabbedPane, BorderLayout.CENTER);

        //                                                               <<<<<<<<<<<<<<<<< create west panel >>>>>>>>>>>>>>>>>>>>>>
        JPanel panelWest = new JPanel(new BorderLayout(0, 0));

        // create insomnia lable
        JLabel insomniaLable = new JLabel("Insomnia");
        insomniaLable.setFont(new Font("Verdana", Font.PLAIN, 25));
        insomniaLable.setBackground(new Color(100, 0, 200));
        insomniaLable.setForeground(Color.WHITE);
        insomniaLable.setOpaque(true);
        insomniaLable.setPreferredSize(new Dimension(250, 60));
        insomniaLable.setHorizontalAlignment(SwingConstants.CENTER);

        // create panel west center
        JPanel panelWest_Center = new JPanel();
        panelWest_Center.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panelWest_Center.setBackground(Color.DARK_GRAY);


       //create filter text field
        JTextField filter = new JTextField("Filter");
        filter.setPreferredSize(new Dimension(200, 20));
        filter.setBackground(Color.GRAY);
        filter.setForeground(Color.WHITE);

        //create menubar
        JMenuBar mb2 = new JMenuBar();
        JMenu menu2 = new JMenu("  +");
        JMenuItem newRequest = new JMenuItem("New Request");
        JMenuItem newFolder = new JMenuItem("New Folder");
        menu2.add(newRequest);
        menu2.add(newFolder);
        mb2.add(menu2);

        //create jTree
        DefaultMutableTreeNode requests = new DefaultMutableTreeNode("Requests");
        DefaultMutableTreeNode folder = new DefaultMutableTreeNode();
        DefaultMutableTreeNode folder2 = new DefaultMutableTreeNode();
        requests.add(folder);
        requests.add(folder2);
        JTree jt = new JTree(requests);
        jt.setBackground(Color.DARK_GRAY);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelWest_Center.add(filter, gbc);
       
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        panelWest_Center.add(mb2, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panelWest_Center.add(jt, gbc);

        gbc.weightx = 1;
        gbc.weighty = 1;
        panelWest_Center.add(new JLabel(" "), gbc);

        panelWest.add(insomniaLable, BorderLayout.NORTH);
        panelWest.add(panelWest_Center, BorderLayout.CENTER);

                                                                        // <<<<<<<<<<<<<<<<< create East panel >>>>>>>>>>>>>>>>>>>>>>>>>>>
        JPanel panelEast = new JPanel(new BorderLayout(0, 0));


        //create panel east north
        JPanel panelEast_North = new JPanel(new FlowLayout(FlowLayout.LEFT, 60, 8));
        panelEast_North.setBackground(Color.WHITE);
        JLabel label1 = new JLabel("200 OK");
        label1.setForeground(new Color(50, 200, 180));
        JLabel label2 = new JLabel("6.60s");
        JLabel label3 = new JLabel("15.2KB");

        //add lables
        panelEast_North.add(label1);
        panelEast_North.add(label2);
        panelEast_North.add(label3);


        //crate panel east center
        JTabbedPane tabbedPane2 = new JTabbedPane();



        // messsage body
        JPanel panelMessageBody = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
        panelMessageBody.setBackground(Color.DARK_GRAY);
        String choices2[] = { "Raw", "Preview", "JSON" };
        JComboBox comboBox3 = new JComboBox(choices2);
        comboBox3.setBackground(Color.GRAY);
        comboBox3.setForeground(Color.WHITE);

        comboBox3.setPreferredSize(new Dimension(500, 30));
        panelMessageBody.add(comboBox3);



        // header
        JPanel panelHeaderEast = new JPanel(new FlowLayout());
        panelHeaderEast.setBackground(Color.DARK_GRAY);

        JPanel panelHeader1 = new JPanel(new GridLayout(1, 4, 10, 5));
        panelHeader1.setBackground(Color.DARK_GRAY);

        //create key 
        JTextField key2 = new JTextField("new Header");
        key2.setBackground(Color.GRAY);
        key2.setForeground(Color.WHITE);
        key2.setBorder(border);

        //create value
        JTextField value2 = new JTextField("new value");
        value2.setBorder(border);
        value2.setBackground(Color.GRAY);
        value2.setForeground(Color.WHITE);
        key2.setPreferredSize(new Dimension(keyWidth, keyHeight));
        value2.setPreferredSize(new Dimension(valueWidth, valueHeight));

        //add components to header panel
        panelHeader1.add(key2);
        panelHeader1.add(value2);


        //create copy to clipboard button
        JButton newButton = new JButton("Copy to Clipboard");
        newButton.setForeground(Color.WHITE);
        newButton.setBackground(new Color(90, 80, 160));



        GridBagConstraints gbc4 = new GridBagConstraints();

        gbc4.gridx = 0;
        gbc4.gridy = 0;
        gbc4.insets = new Insets(50, 50, 50, 50);
        panelHeaderEast.add(panelHeader1, gbc4);

      
        gbc4.gridx = 0;
        gbc4.gridy = 1;
        gbc4.insets = new Insets(50, 50, 50, 50);
        panelHeaderEast.add(newButton, gbc4);

    
        tabbedPane2.add("Message Body", panelMessageBody);
        tabbedPane2.add("Header", panelHeaderEast);

        panelEast.add(panelEast_North, BorderLayout.NORTH);
        panelEast.add(tabbedPane2, BorderLayout.CENTER);

        mainPanel.add(panelCenter, BorderLayout.CENTER);
        mainPanel.add(panelWest, BorderLayout.WEST);
        mainPanel.add(panelEast, BorderLayout.EAST);

    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {

        Gui g = new Gui();

    }
}