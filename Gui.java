
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import jdk.jshell.execution.Util;

import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.*;

/**
 * this class represent a GUI for insomnia client
 * 
 * @author sevda imany
 * @version 0.0
 */
public class Gui {

    private JFrame frame;
    String[] argsMain;
    ArrayList<String> argsArrayList;
    int i;
    ArrayList<String> headerRequestArrayList;
    ArrayList<String> formDataRequestArrayList;
    Border border = BorderFactory.createLineBorder(Color.GRAY);
    int jHeader = 3;
    int jFormdata = 3;
    int indexBinary = 0;
    boolean chooseFileBoolean = false;
    String nameDirectory = null;
    boolean exit = true;

    public Gui() {
        headerRequestArrayList = new ArrayList<>();
        formDataRequestArrayList = new ArrayList<>();
        argsArrayList = new ArrayList<>();

        i = 1;

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

        options.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // create option Dialog
                JDialog optionDialog = new JDialog(frame, "Options", false);
                optionDialog.setVisible(true);
                optionDialog.setBounds(530, 280, 300, 210);
                optionDialog.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

                JCheckBox followRedirectCheckBox = new JCheckBox(" follow redirect ");
                followRedirectCheckBox.setPreferredSize(new Dimension(250, 30));
                followRedirectCheckBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        boolean selected = followRedirectCheckBox.isSelected();
                        if (selected) {
                            argsArrayList.add("-f");
                        } else {
                            argsArrayList.remove("-f");
                        }
                    }
                });

                JRadioButton exiRadioButton = new JRadioButton(" Exit ", true);
                JRadioButton hideRadioButton = new JRadioButton(" Hide on system tray ");
                ButtonGroup exiButtonGroup = new ButtonGroup();
                exiButtonGroup.add(exiRadioButton);
                exiButtonGroup.add(hideRadioButton);
                exiRadioButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (exiRadioButton.isSelected()) {
                            exit = true;
                        } else {
                            exit = false;
                        }
                    }
                });
                hideRadioButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (hideRadioButton.isSelected()) {
                            exit = false;
                        } else {
                            exit = true;
                        }
                    }
                });

                // JRadioButton lightTheme = new JRadioButton("light theme");
                // JRadioButton darkTheme = new JRadioButton("dark theme", true);
                // ButtonGroup theme = new ButtonGroup();
                // theme.add(lightTheme);
                // theme.add(darkTheme);

                JButton saveButton = new JButton("Save");
                saveButton.setPreferredSize(new Dimension(100, 25));
                saveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        optionDialog.dispose();
                    }
                });

                optionDialog.add(followRedirectCheckBox);
                optionDialog.add(exiRadioButton);
                optionDialog.add(hideRadioButton);
                // optionDialog.add(lightTheme);
                // optionDialog.add(darkTheme);
                optionDialog.add(saveButton);
            }
        });

        // Exit
        JMenuItem Exit = new JMenuItem("Exit");

        Exit.setMnemonic(KeyEvent.VK_E);
        Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        Exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (exit) {
                    System.exit(0);
                } else {
                    if (SystemTray.isSupported()) {

                        SystemTray tray = SystemTray.getSystemTray();
                        Image image = Toolkit.getDefaultToolkit().getImage("tray.gif");
                        TrayIcon trayIcon = new TrayIcon(image);
                        frame.setVisible(false);
                        MouseListener mouseListener = new MouseListener() {

                            public void mouseClicked(MouseEvent e) {
                                frame.setVisible(true);
                            }

                            public void mouseEntered(MouseEvent e) {

                            }

                            public void mouseExited(MouseEvent e) {

                            }

                            public void mousePressed(MouseEvent e) {

                            }

                            public void mouseReleased(MouseEvent e) {

                            }
                        };

                        trayIcon.setImageAutoSize(true);
                        trayIcon.addMouseListener(mouseListener);
                        try {
                            tray.add(trayIcon);

                        } catch (AWTException ex) {
                            System.err.println("TrayIcon could not be added.");
                        }
                    }

                }
            }
        });

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
        toggleFullScreen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
        JMenuItem toggleSidebar = new JMenuItem("Toggle Sidebar");
        toggleSidebar.setMnemonic(KeyEvent.VK_F12);
        toggleSidebar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, ActionEvent.CTRL_MASK));

        // // add components
        menuView.add(toggleFullScreen);
        menuView.add(toggleSidebar);

        // create help menu
        JMenu menuHelp = new JMenu("Help");
        menuHelp.setMnemonic(KeyEvent.VK_H);

        JMenuItem about = new JMenuItem("About");
        about.setMnemonic(KeyEvent.VK_B);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.ALT_MASK));

        // add actionlistener for about
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane aboutOptionPane = new JOptionPane("About");
                aboutOptionPane.showMessageDialog(frame,
                        "Hi i'm sevda!\nInsomnia started as a project of mine in 2020.\nMy email : sevdaimany@gmail.com\nMy student number : 9831010 ",
                        "About", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        JMenuItem help = new JMenuItem("Help");
        help.setMnemonic(KeyEvent.VK_H);
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK));
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane helpOptionPane = new JOptionPane("Help");
                helpOptionPane.showMessageDialog(frame,
                        "Show app options    Alt+O\n\nExit    Alt+E\n\ntoggle full screen    Ctrl+\\ \n\ntoggle sidebar    Ctrl+/\n\nAbout    Alt+F2\n\nKeyBoard Shortcuts    Alt+F1\n\nCreate Requests    Ctrl+N\n\nCreate Folder    Ctrl+Shift+N\n",
                        "Help", JOptionPane.NO_OPTION);
            }
        });

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
        JPanel panelCenter_North = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        panelCenter_North.setBackground(Color.WHITE);

        String comboboxChoices[] = { "GET", "DELETE", "POST", "PUT", "PATCH" };
        JComboBox comboBox = new JComboBox(comboboxChoices);
        Border border2 = BorderFactory.createLineBorder(Color.WHITE);
        comboBox.setBorder(border2);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.GRAY);
        int indext = 1;
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // argsMain[i] = "--method";
                // i++;
                // argsMain[i] = (String) comboBox.getSelectedItem();
                // i++;
                if (argsArrayList.contains("--method")) {
                    int index = argsArrayList.indexOf("--method");
                    argsArrayList.add(index + 1, (String) comboBox.getSelectedItem());
                } else {
                    argsArrayList.add("--method");
                    argsArrayList.add((String) comboBox.getSelectedItem());
                }
            }
        });

        JTextField URLAddress = new JTextField("https://api.myproduct.com/v1/users");
        int addressWidth = URLAddress.getPreferredSize().width + 400;
        int addressHeight = URLAddress.getPreferredSize().height + 12;
        URLAddress.setPreferredSize(new Dimension(addressWidth, addressHeight));
        URLAddress.setBorder(border2);

        JButton sendButton = new JButton("Send");
        sendButton.setBorder(border2);
        sendButton.setBackground(Color.WHITE);
        sendButton.setForeground(Color.GRAY);
        sendButton.setPreferredSize(new DimensionUIResource(45, 30));

      

        // add components
        panelCenter_North.add(comboBox);
        panelCenter_North.add(URLAddress);
        panelCenter_North.add(sendButton);
        // panelCenter_North.add(saveButton);

        JTabbedPane tabbedPane = new JTabbedPane();

        // create body panel
        JPanel panelBody = new JPanel(new BorderLayout());
        panelBody.setBackground(Color.DARK_GRAY);

        // create header panel
        JPanel panelHeaderCenter = new JPanel(new GridBagLayout());
        panelHeaderCenter.setBackground(Color.DARK_GRAY);

        // create Query panel
        JPanel panelQueryCenter = new JPanel(new GridBagLayout());
        panelQueryCenter.setBackground(Color.DARK_GRAY);

        // create Auth panel
        JPanel panelAuth = new JPanel(new GridBagLayout());
        panelAuth.setBackground(Color.DARK_GRAY);

        // add panels to tapeddpane
        tabbedPane.add("Body", panelBody);
        tabbedPane.add("Header", panelHeaderCenter);
        tabbedPane.add("Query", panelQueryCenter);
        tabbedPane.add("Auth", panelAuth);

        // panel header center

        JPanel panelHeader = newHeader();

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
        gbc2.gridy = 2;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        JLabel labelHeader = new JLabel(" ");
        panelHeaderCenter.add(labelHeader, gbc2);

        newHeader.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel panelNewHaeder = newHeader();
                panelHeaderCenter.remove(labelHeader);
                gbc2.gridx = 0;
                gbc2.gridy = jHeader;
                jHeader++;
                gbc2.weightx = 0;
                gbc2.weighty = 0;
                gbc2.fill = GridBagConstraints.HORIZONTAL;
                gbc2.insets = new Insets(10, 10, 10, 10);
                panelHeaderCenter.add(panelNewHaeder, gbc2);
                gbc2.gridx = 0;
                gbc2.gridy = jHeader;
                jHeader++;
                gbc2.weightx = 1;
                gbc2.weighty = 1;
                panelHeaderCenter.add(labelHeader, gbc2);
                panelHeaderCenter.revalidate();
                panelHeaderCenter.repaint();
            }
        });

        // panel body

        String choices[] = { "Form Data", "JSON", "Binary Data" };
        JComboBox comboBox2 = new JComboBox(choices);
        comboBox2.setPreferredSize(new Dimension(760, 30));
        comboBox2.setBackground(Color.GRAY);
        comboBox2.setForeground(Color.white);

        CardLayout cardRequestBody = new CardLayout(3, 3);
        JPanel panelBodyCenter = new JPanel(cardRequestBody);
        panelBodyCenter.setBackground(Color.DARK_GRAY);

        JPanel panelBodyCenterFormData = new JPanel(new GridBagLayout());
        panelBodyCenterFormData.setBackground(Color.DARK_GRAY);

        // panelBody.add(comboBox2);
        JPanel panelFormData = newFormData();

        // create new button
        JButton newFormData = new JButton("+  NEW");
        newFormData.setForeground(Color.WHITE);
        newFormData.setBackground(new Color(90, 80, 160));

        GridBagConstraints gbc2FormData = new GridBagConstraints();

        gbc2FormData.gridx = 0;
        gbc2FormData.gridy = 0;
        gbc2FormData.fill = GridBagConstraints.HORIZONTAL;
        gbc2FormData.insets = new Insets(3, 2, 2, 2);
        panelBodyCenterFormData.add(newFormData, gbc2FormData);

        // second row
        gbc2FormData.gridx = 0;
        gbc2FormData.gridy = 1;
        gbc2FormData.fill = GridBagConstraints.HORIZONTAL;
        gbc2FormData.insets = new Insets(5, 5, 5, 5);
        panelBodyCenterFormData.add(panelFormData, gbc2FormData);

        gbc2FormData.gridx = 0;
        gbc2FormData.gridy = 2;
        gbc2FormData.weightx = 1;
        gbc2FormData.weighty = 1;
        JLabel labelFormData = new JLabel(" ");
        panelBodyCenterFormData.add(labelFormData, gbc2FormData);

        newFormData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JPanel panelNewFormData = newFormData();
                panelBodyCenterFormData.remove(labelFormData);
                gbc2FormData.gridx = 0;
                gbc2FormData.gridy = jFormdata;
                jFormdata++;
                gbc2FormData.weightx = 0;
                gbc2FormData.weighty = 0;
                gbc2FormData.fill = GridBagConstraints.HORIZONTAL;
                gbc2FormData.insets = new Insets(5, 5, 5, 5);
                panelBodyCenterFormData.add(panelNewFormData, gbc2FormData);

                gbc2FormData.gridx = 0;
                gbc2FormData.gridy = jFormdata;
                jFormdata++;
                gbc2FormData.weightx = 1;
                gbc2FormData.weighty = 1;
                panelBodyCenterFormData.add(labelFormData, gbc2FormData);
                panelBodyCenterFormData.revalidate();
                panelBodyCenterFormData.repaint();

            }
        });

        panelBodyCenter.add("Form Data", panelBodyCenterFormData);

        JPanel panelBodyCenterBinary = new JPanel(new FlowLayout());
        panelBodyCenterBinary.setBackground(Color.DARK_GRAY);

        JTextField BinaryPath = new JTextField("No file selected");
        BinaryPath.setPreferredSize(new Dimension(760, 30));
        BinaryPath.setBackground(Color.GRAY);
        BinaryPath.setForeground(Color.white);

        JButton chooseFile = new JButton("Choose file");
        chooseFile.setBackground(Color.DARK_GRAY);
        chooseFile.setBorder(border);
        chooseFile.setPreferredSize(new Dimension(120, 25));
        chooseFile.setForeground(Color.LIGHT_GRAY);

        chooseFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // argsMain[i] = "--binary";
                    // indexBinary = i;
                    // i++;
                    // argsMain[i] = selectedFile.getAbsolutePath();
                    // i++;
                    BinaryPath.setEditable(false);
                    BinaryPath.setText(selectedFile.getAbsolutePath());
                    chooseFileBoolean = true;
                    if (argsArrayList.contains("--binary")) {
                        int index = argsArrayList.indexOf("--binary");
                        argsArrayList.add(index + 1, selectedFile.getAbsolutePath());
                    } else {
                        argsArrayList.add("--binary");
                        argsArrayList.add(selectedFile.getAbsolutePath());
                    }
                }

            }
        });

        JButton resetFile = new JButton("Reset file");
        resetFile.setBackground(Color.DARK_GRAY);
        resetFile.setPreferredSize(new Dimension(120, 25));
        resetFile.setForeground(Color.LIGHT_GRAY);
        resetFile.setBorder(border);
        resetFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chooseFileBoolean) {
                    // argsMain[indexBinary] = "";
                    // argsMain[indexBinary + 1] = "";
                    if (argsArrayList.contains("--binary")) {
                        int index = argsArrayList.indexOf("--binary");
                        argsArrayList.remove(index);
                        argsArrayList.remove(index + 1);
                    }
                    BinaryPath.setText("No file selected");
                }
            }
        });
        panelBodyCenterBinary.add(BinaryPath);
        panelBodyCenterBinary.add(chooseFile);
        panelBodyCenterBinary.add(resetFile);

        panelBodyCenter.add("Binary", panelBodyCenterBinary);

        panelBody.add(comboBox2, BorderLayout.NORTH);
        panelBody.add(panelBodyCenter, BorderLayout.CENTER);

        comboBox2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (((String) comboBox2.getSelectedItem()).equals("Form Data")) {
                    // panelBodyCenter.removeAll();
                    panelBodyCenter.revalidate();
                    panelBodyCenter.repaint();
                    cardRequestBody.show(panelBodyCenter, "Form Data");

                    panelBodyCenter.revalidate();
                    panelBodyCenter.repaint();

                } else if (((String) comboBox2.getSelectedItem()).equals("Binary Data")) {
                    panelBodyCenter.revalidate();
                    panelBodyCenter.repaint();
                    cardRequestBody.show(panelBodyCenter, "Binary");
                    panelBodyCenter.revalidate();
                    panelBodyCenter.repaint();

                }
            }
        });

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

        int keyWidth = keyQuery.getPreferredSize().width + 50;
        int keyHeight = keyQuery.getPreferredSize().height + 10;
        int valueWidth = valueQuery.getPreferredSize().width + 50;
        int valueHeight = valueQuery.getPreferredSize().height + 10;
        ImageIcon recycleBin = new ImageIcon("trash can-1.1s-18px.png");

        keyQuery.setPreferredSize(new Dimension(keyWidth, keyHeight));
        valueQuery.setPreferredSize(new Dimension(valueWidth, valueHeight));

        // create checkBox
        JCheckBox cQuery = new JCheckBox();
        cQuery.setBackground(Color.DARK_GRAY);
        cQuery.setBorder(border);

        // create recycle button
        JButton recycleQuery = new JButton(recycleBin);
        int recycleWidth = recycleQuery.getPreferredSize().width - 1000;
        int recycleHeight = recycleQuery.getPreferredSize().height - 4;
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

        JLabel token = new JLabel("Token");
        token.setForeground(Color.GRAY);
        JTextField tokenTextField = new JTextField();
        tokenTextField.setPreferredSize(new Dimension(180, 30));
        tokenTextField.setBackground(Color.GRAY);
        JLabel prefix = new JLabel("Prefix");
        prefix.setForeground(Color.GRAY);

        JTextField prefixTextFeilField = new JTextField();
        prefixTextFeilField.setPreferredSize(new Dimension(180, 30));
        prefixTextFeilField.setBackground(Color.GRAY);

        JRadioButton enableButton = new JRadioButton("Enable");
        enableButton.setBackground(Color.DARK_GRAY);

        GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.gridx = 0;
        gbc7.gridy = 0;
        gbc7.fill = GridBagConstraints.HORIZONTAL;
        gbc7.insets = new Insets(10, 20, 20, 10);
        gbc7.gridwidth = 2;
        panelAuth.add(comboBoxAuth, gbc7);
        gbc7.gridwidth = 1;

        gbc7.gridx = 0;
        gbc7.gridy = 3;
        gbc7.insets = new Insets(10, 20, 20, 10);
        panelAuth.add(token, gbc7);

        gbc7.gridx = 1;
        gbc7.gridy = 3;
        gbc7.weightx = 1;
        gbc7.insets = new Insets(10, 20, 20, 10);
        panelAuth.add(tokenTextField, gbc7);

        gbc7.gridx = 0;
        gbc7.gridy = 6;
        gbc7.insets = new Insets(10, 20, 20, 10);
        panelAuth.add(prefix, gbc7);

        gbc7.gridx = 1;
        gbc7.gridy = 6;
        gbc7.weightx = 1;
        gbc7.insets = new Insets(10, 20, 20, 10);
        panelAuth.add(prefixTextFeilField, gbc7);

        gbc7.gridx = 0;
        gbc7.gridy = 8;
        gbc7.insets = new Insets(10, 20, 20, 10);
        panelAuth.add(enableButton, gbc7);

        gbc7.gridx = 0;
        gbc7.gridy = 9;
        gbc7.weightx = 1;
        gbc7.weighty = 1;
        panelAuth.add(new JLabel(" "), gbc7);

        panelCenter.add(panelCenter_North, BorderLayout.NORTH);
        panelCenter.add(tabbedPane, BorderLayout.CENTER);

        // <<<<<<<<<<<<<<<<< create west panel
        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.
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
        panelWest_Center.setLayout(new BorderLayout());
        panelWest_Center.setBackground(Color.DARK_GRAY);

        // create filter text field
        JTextField filter = new JTextField("Filter");
        filter.setPreferredSize(new Dimension(200, 30));
        filter.setBackground(Color.GRAY);
        filter.setForeground(Color.WHITE);

        // create menubar
        JMenuBar mb2 = new JMenuBar();
        JMenu menu2 = new JMenu("  +");
        JMenuItem saveRequest = new JMenuItem("save Request");
        saveRequest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        JMenuItem newFolder = new JMenuItem("New Folder");
        newFolder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK));
        menu2.add(saveRequest);
        menu2.add(newFolder);
        mb2.add(menu2);

        JPanel panelWest_Center_North = new JPanel(new FlowLayout());
        panelWest_Center_North.setBackground(Color.DARK_GRAY);
        panelWest_Center_North.add(filter);
        panelWest_Center_North.add(mb2);

        saveRequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane newRequestOptionPane = new JOptionPane("Save Request");
                nameDirectory = newRequestOptionPane.showInputDialog("Enter a directory name to Save request.");
                if (argsArrayList.contains("--save")) {
                    int index = argsArrayList.indexOf("--save");
                    argsArrayList.add(index + 1, nameDirectory);
                } else {
                    argsArrayList.add("--save");
                    argsArrayList.add(nameDirectory);
                }

            }
        });

        JPanel panelWest_Center_Center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // create jTree
        DefaultMutableTreeNode requests = new DefaultMutableTreeNode("Requests");
        // DefaultMutableTreeNode folder = new DefaultMutableTreeNode();
        // DefaultMutableTreeNode folder2 = new DefaultMutableTreeNode();
        // requests.add(folder);
        // requests.add(folder2);
        JTree jt = new JTree(requests);
        jt.setBackground(Color.DARK_GRAY);
        try {
            scanner(jt);
        } catch (InterruptedException e) {
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panelWest_Center_Center.add(jt, gbc);

        panelWest_Center.add(panelWest_Center_North, BorderLayout.NORTH);
        panelWest_Center.add(panelWest_Center_Center, BorderLayout.CENTER);
        ;

        panelWest.add(insomniaLable, BorderLayout.NORTH);
        panelWest.add(panelWest_Center, BorderLayout.CENTER);

        newFolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane newFolderOptionPane = new JOptionPane("new Folder");
                nameDirectory = newFolderOptionPane.showInputDialog("Enter a directory name to create.");
                Jurl.createFolder(nameDirectory);
                DefaultMutableTreeNode requests = new DefaultMutableTreeNode("Requests");
                JTree jt = new JTree(requests);
                jt.setBackground(Color.DARK_GRAY);
                try {
                    scanner(jt);
                } catch (InterruptedException ex) {
                }
                JPanel panelWest_Center_Center = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 1;
                gbc.weighty = 1;
                gbc.fill = GridBagConstraints.BOTH;
                panelWest_Center_Center.add(jt, gbc);
                panelWest_Center.add(panelWest_Center_Center, BorderLayout.CENTER);
                panelWest_Center_Center.repaint();
                panelWest_Center_Center.revalidate();

                jt.repaint();
                jt.revalidate();
            }
        });
        // <<<<<<<<<<<<<<<<< create East panel >>>>>>>>>>>>>>>>>>>>>>>>>>>
        JPanel panelEast = new JPanel(new BorderLayout(0, 0));

        // create panel east north
        JPanel panelEast_North = new JPanel(new FlowLayout(FlowLayout.LEFT, 60, 8));
        panelEast_North.setBackground(Color.WHITE);
        JLabel label1 = new JLabel("200 OK");
        label1.setForeground(new Color(50, 200, 180));
        JLabel label2 = new JLabel("6.60s");
        JLabel label3 = new JLabel("15.2KB");

        // add lables
        panelEast_North.add(label1);
        panelEast_North.add(label2);
        panelEast_North.add(label3);

        // crate panel east center
        JTabbedPane tabbedPane2 = new JTabbedPane();

        // messsage body

        JPanel panelMessageBody = new JPanel(new BorderLayout(5, 5));
        panelMessageBody.setBackground(Color.DARK_GRAY);

        CardLayout card = new CardLayout(3, 3);
        JPanel panelMessageBodyCenter = new JPanel(card);
        panelMessageBodyCenter.setBackground(Color.DARK_GRAY);
        String choices2[] = { "Raw", "Preview", "JSON" };
        JComboBox comboBox3 = new JComboBox(choices2);
        comboBox3.setBackground(Color.GRAY);
        comboBox3.setForeground(Color.WHITE);
        comboBox3.setPreferredSize(new Dimension(500, 30));

        panelMessageBody.add(comboBox3, BorderLayout.NORTH);
        panelMessageBody.add(panelMessageBodyCenter, BorderLayout.CENTER);

        comboBox3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (((String) comboBox3.getSelectedItem()).equals("Raw")) {

                    card.show(panelMessageBodyCenter, "Raw");

                }

                else if (((String) comboBox3.getSelectedItem()).equals("Preview")) {
                    // panelMessageBodyPreview.removeAll();
                    // panelMessageBodyPreview.repaint();

                    // panelMessageBodyPreview.repaint();

                    JPanel panelMessageBodyPreview = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
                    ImageIcon image = new ImageIcon("GuiPreview.png");
                    JLabel label = null;
                    if (image != null) {
                        label = new JLabel(image);
                    }
                    panelMessageBodyPreview.add(label);
                    panelMessageBodyCenter.add("Preview", panelMessageBodyPreview);

                    card.show(panelMessageBodyCenter, "Preview");

                }

            }

        });

        // header

        JPanel panelHeaderEast = new JPanel(new BorderLayout(3, 3));
        panelHeaderEast.setBackground(Color.DARK_GRAY);

     
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // argsMain[0] = URLAddress.getText();
                // argsMain[i] = "Gui";
                // i++;
                final long startTime = System.nanoTime();

                if (argsArrayList.contains("URL")) {
                    int index = argsArrayList.indexOf("URL");
                    argsArrayList.add(index + 1, URLAddress.getText());

                } else {
                    argsArrayList.add("URL");
                    argsArrayList.add(URLAddress.getText());
                }
                argsArrayList.add("Gui");

                if (headerRequestArrayList.size() > 0) {

                    if (argsArrayList.contains("--headers")) {
                        int index = argsArrayList.indexOf("--headers");
                        argsArrayList.add(index + 1, convertToString(headerRequestArrayList));
                    } else {
                        argsArrayList.add("--headers");
                        argsArrayList.add(convertToString(headerRequestArrayList));
                    }
                } else {
                    if (argsArrayList.contains("--headers")) {
                        int index = argsArrayList.indexOf("--headers");
                        argsArrayList.remove("--headers");
                        argsArrayList.remove(index + 1);
                    }
                }

                if (formDataRequestArrayList.size() > 0) {

                    if (argsArrayList.contains("--data")) {
                        int index = argsArrayList.indexOf("--data");
                        argsArrayList.add(index + 1, convertToString(formDataRequestArrayList));
                    } else {
                        argsArrayList.add("--data");
                        argsArrayList.add(convertToString(formDataRequestArrayList));
                    }
                } else {
                    if (argsArrayList.contains("--data")) {
                        int index = argsArrayList.indexOf("--data");
                        argsArrayList.remove("--data");
                        argsArrayList.remove(index + 1);
                    }
                }

                argsMain = convertToArray(argsArrayList);
                connectionClass.setConnectionClassFields(argsMain, panelWest_Center, panelMessageBodyCenter, panelHeaderEast, card, label1, label3, label2, startTime);
            }
            
            
        });

        tabbedPane2.add("Message Body", panelMessageBody);
        tabbedPane2.add("Header", panelHeaderEast);

        panelEast.add(panelEast_North, BorderLayout.NORTH);
        panelEast.add(tabbedPane2, BorderLayout.CENTER);

        mainPanel.add(panelCenter, BorderLayout.CENTER);
        mainPanel.add(panelWest, BorderLayout.WEST);
        mainPanel.add(panelEast, BorderLayout.EAST);

    }

    public void updateGUI(JPanel panelWest_Center,JPanel panelMessageBodyCenter,JPanel panelHeaderEast,CardLayout card,JLabel label1,JLabel label3 ,JLabel label2,long startTime) {
        setJTree(panelWest_Center);
        int lengthFile = 0;
        try {
            BufferedInputStream in2 = new BufferedInputStream(new FileInputStream("GuiError.txt"));
            lengthFile = in2.readAllBytes().length;
        } catch (IOException exc) {
        }

        if (lengthFile > 0) {
            showError(card, panelMessageBodyCenter, panelHeaderEast, label1);

        } else {
            showHeader(label1, label3, panelHeaderEast);
            showRaw(card, panelMessageBodyCenter);
        }

        double duration = System.nanoTime() - startTime;
        timeTake(duration, label2);
    }

    public void timeTake(double duration, JLabel label2) {
        duration = duration / 1000000000.0;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        String formatResult = decimalFormat.format(duration);
        duration = Double.parseDouble(formatResult);
        label2.setText(String.valueOf(duration) + "s");
        label2.repaint();
    }

    public void showHeader(JLabel label1, JLabel label3, JPanel panelHeaderEast) {
        panelHeaderEast.removeAll();
        JButton newButton = new JButton("Copy to Clipboard");
        newButton.setForeground(Color.WHITE);
        newButton.setBackground(new Color(90, 80, 160));
        panelHeaderEast.add(newButton, BorderLayout.NORTH);

        JPanel panelHeaderGridBagLayout = new JPanel(new GridBagLayout());
        JScrollPane scrollPane2 = new JScrollPane(panelHeaderGridBagLayout);
        panelHeaderGridBagLayout.setBackground(Color.DARK_GRAY);
        String[] headerStrings = headerStringArray();
        int jHeaderResponse = 0;
        GridBagConstraints gbc4 = new GridBagConstraints();

        for (int p = 1; p < headerStrings.length; p += 2) {

            if (headerStrings[p].equals("null")) {
                String[] nullStrings = headerStrings[p + 1].split(" ");

                StringBuffer buffer = new StringBuffer();
                for (int r = 1; r < nullStrings.length; r++) {
                    buffer.append(nullStrings[r] + " ");
                }
                label1.setText(buffer.toString());
                int codeResponse = Integer.parseInt(nullStrings[1]);
                if (codeResponse / 100 == 2)
                    label1.setForeground(Color.GREEN);
                else if (codeResponse / 100 == 3)
                    label1.setForeground(Color.BLUE);
                else if (codeResponse / 100 == 4)
                    label1.setForeground(Color.ORANGE);
                else if (codeResponse / 100 == 5)
                    label1.setForeground(Color.RED);
                label1.repaint();
                continue;
            }
            if (headerStrings[p].equals("Content-Length")) {
                label3.setText(headerStrings[p + 1] + "B");
                label3.repaint();

            }

            JPanel panelHeader1 = newHeaderResponse(p, headerStrings);
            gbc4.gridx = 0;
            gbc4.gridy = jHeaderResponse;
            gbc4.fill = GridBagConstraints.HORIZONTAL;
            panelHeaderGridBagLayout.add(panelHeader1, gbc4);
            jHeaderResponse++;

            if (argsArrayList.contains("--save")) {
                int index = argsArrayList.indexOf("--save");
                argsArrayList.remove("--save");
                argsArrayList.remove(index + 1);
            }
        }
        gbc4.gridx = 0;
        gbc4.gridy = jHeaderResponse;
        gbc4.weightx = 1;
        gbc4.weighty = 1;
        gbc4.insets = new Insets(50, 50, 50, 50);
        JLabel labelHeaderResponse = new JLabel(" ");
        panelHeaderGridBagLayout.add(labelHeaderResponse, gbc4);
        panelHeaderEast.add(scrollPane2, BorderLayout.CENTER);

        panelHeaderGridBagLayout.revalidate();
        panelHeaderGridBagLayout.repaint();
        panelHeaderEast.revalidate();
        panelHeaderEast.repaint();

    }

    public void showRaw(CardLayout card, JPanel panelMessageBodyCenter) {
        JPanel panelMessageBodyRaw = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
        JTextArea raw = new JTextArea();
        raw.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(raw);

        raw.setPreferredSize(new Dimension(2000, 800));
        scrollPane.setPreferredSize(new Dimension(499, 679));

        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream("GuiResponseBody.txt"));

            raw.append(new String(in.readAllBytes()));
        } catch (Exception ex) {
        }

        panelMessageBodyRaw.add(scrollPane);

        panelMessageBodyCenter.add("Raw", panelMessageBodyRaw);

        card.show(panelMessageBodyCenter, "Raw");

        panelMessageBodyRaw.repaint();
        panelMessageBodyRaw.revalidate();

    }

    public void showError(CardLayout card, JPanel panelMessageBodyCenter, JPanel panelHeaderEast, JLabel label1) {
        JPanel panelMessageBodyRaw = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
        JTextArea raw = new JTextArea();
        raw.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(raw);
        raw.setLineWrap(true);

        raw.setPreferredSize(new Dimension(2000, 800));
        scrollPane.setPreferredSize(new Dimension(499, 679));
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream("GuiError.txt"));

            raw.append(new String(in.readAllBytes()));
        } catch (Exception ex) {
        }

        panelMessageBodyRaw.add(scrollPane);

        panelMessageBodyCenter.add("Raw", panelMessageBodyRaw);

        card.show(panelMessageBodyCenter, "Raw");

        panelMessageBodyRaw.repaint();
        panelMessageBodyRaw.revalidate();

        panelHeaderEast.removeAll();
        JButton newButton = new JButton("Copy to Clipboard");
        newButton.setForeground(Color.WHITE);
        newButton.setBackground(new Color(90, 80, 160));
        panelHeaderEast.add(newButton, BorderLayout.NORTH);
        JPanel panelHeaderGridBagLayout = new JPanel(new GridBagLayout());
        panelHeaderGridBagLayout.setBackground(Color.DARK_GRAY);
        panelHeaderEast.add(panelHeaderGridBagLayout, BorderLayout.CENTER);
        panelHeaderGridBagLayout.revalidate();
        panelHeaderGridBagLayout.repaint();
        panelHeaderEast.revalidate();
        panelHeaderEast.repaint();

        label1.setText("ERROR");
        label1.setForeground(Color.RED);
        label1.repaint();

    }

    public void setJTree(JPanel panelWest_Center) {
        if (argsArrayList.contains("--save")) {
            DefaultMutableTreeNode requests = new DefaultMutableTreeNode("Requests");
            JTree jt = new JTree(requests);
            jt.setBackground(Color.DARK_GRAY);
            try {
                scanner(jt);
            } catch (InterruptedException ex) {
            }
            JPanel panelWest_Center_Center = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.BOTH;
            panelWest_Center_Center.add(jt, gbc);
            panelWest_Center.add(panelWest_Center_Center, BorderLayout.CENTER);
             panelWest_Center_Center.repaint();
             panelWest_Center_Center.revalidate();

            jt.repaint();
            jt.revalidate();
        }
    }

    
    public String[] headerStringArray() {
        BufferedInputStream in = null;
        String headers;
        String[] seprateHeaders = null;
        try {
            in = new BufferedInputStream(new FileInputStream("GuiResponseHeaders.txt"));
            headers = new String(in.readAllBytes());
            seprateHeaders = headers.split("&&&");
        } catch (Exception e) {
        }
        // for (int y = 0; y < seprateHeaders.length; y++)
        // System.out.println(seprateHeaders[y]);
        return seprateHeaders;
    }

    public JPanel newHeaderResponse(int p, String[] headerStrings) {
        JPanel panelHeader1 = new JPanel(new FlowLayout());
        panelHeader1.setBackground(Color.DARK_GRAY);

        // create key
        JTextArea key2 = new JTextArea("new Header");
        key2.setBackground(Color.DARK_GRAY);
        key2.setForeground(Color.WHITE);
        key2.setBorder(border);
        key2.setEditable(false);
        key2.setText(headerStrings[p]);
        key2.setLineWrap(true);
        // key2.repaint();

        // create value
        JTextArea value2 = new JTextArea("new value");
        value2.setBorder(border);
        value2.setBackground(Color.DARK_GRAY);
        value2.setForeground(Color.WHITE);
        value2.setEditable(false);
        value2.repaint();
        int keyWidth = 210;
        int keyHeight = 35;
        int valueWidth = 210;
        int valueHeight = 35;
        key2.setPreferredSize(new Dimension(keyWidth, keyHeight));
        value2.setPreferredSize(new Dimension(valueWidth, valueHeight));
        value2.setText(headerStrings[p + 1]);
        value2.setLineWrap(true);
        // add components to header panel
        panelHeader1.add(key2);
        panelHeader1.add(value2);
        return panelHeader1;

    }

    public JPanel newHeader() {
        JPanel panelHeader = new JPanel(new GridLayout(1, 4, 10, 5));
        panelHeader.setBackground(Color.DARK_GRAY);

        // key in header
        StringBuffer headerResponse = new StringBuffer();
        JTextField key = new JTextField("new Header");
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        key.setBackground(Color.GRAY);
        key.setForeground(Color.WHITE);
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

        c1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean selected = c1.isSelected();
                if (selected) {
                    if (!(key.getText().equals("new Headers") && key.getText().equals("new value"))) {
                        headerResponse.append(key.getText() + ":" + value.getText() + ";");
                        headerRequestArrayList.add(headerResponse.toString());
                    }
                }
            }
        });

        // recycle button in header
        ImageIcon recycleBin = new ImageIcon("trash can-1.1s-18px.png");
        JButton recycle = new JButton(recycleBin);
        recycle.setBackground(Color.DARK_GRAY);
        recycle.setBorder(border);
        int recycleWidth = recycle.getPreferredSize().width - 1000;
        int recycleHeight = recycle.getPreferredSize().height - 4;
        recycle.setPreferredSize(new Dimension(recycleWidth, recycleHeight));
        recycle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headerRequestArrayList.remove(key.getText() + ":" + value.getText() + ";");
                value.setText("new value");
                key.setText("new Header");
            }
        });

        // add components to header panel
        panelHeader.add(key);
        panelHeader.add(value);
        panelHeader.add(c1);
        panelHeader.add(recycle);

        return panelHeader;

    }

    public JPanel newFormData() {
        JPanel panelFormData = new JPanel(new GridLayout(1, 4, 10, 5));
        panelFormData.setBackground(Color.DARK_GRAY);

        // key in header
        JTextField keyFormData = new JTextField("name");

        keyFormData.setBackground(Color.GRAY);
        keyFormData.setForeground(Color.WHITE);
        keyFormData.setBorder(border);

        // value in header
        JTextField valueFormData = new JTextField("value");
        valueFormData.setBorder(border);
        valueFormData.setBackground(Color.GRAY);
        valueFormData.setForeground(Color.WHITE);

        int keyWidth = keyFormData.getPreferredSize().width + 50;
        int keyHeight = keyFormData.getPreferredSize().height + 10;
        int valueWidth = valueFormData.getPreferredSize().width + 50;
        int valueHeight = valueFormData.getPreferredSize().height + 10;
        ImageIcon recycleBin = new ImageIcon("trash can-1.1s-18px.png");

        keyFormData.setPreferredSize(new Dimension(keyWidth, keyHeight));
        valueFormData.setPreferredSize(new Dimension(valueWidth, valueHeight));

        // checkbox in header
        StringBuffer formDataRequBuffer = new StringBuffer();
        JCheckBox c1FormData = new JCheckBox();
        c1FormData.setBackground(Color.DARK_GRAY);
        c1FormData.setBorder(border);
        c1FormData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean selected = c1FormData.isSelected();
                if (selected) {
                    if (!(keyFormData.getText().equals("name") && keyFormData.getText().equals("value"))) {
                        formDataRequBuffer.append(keyFormData.getText() + "=" + valueFormData.getText() + "&");
                        formDataRequestArrayList.add(formDataRequBuffer.toString());
                    }

                } else {
                    formDataRequestArrayList.remove(keyFormData.getText() + "=" + valueFormData.getText() + "&");
                }
            }
        });

        // recycle button in header
        JButton recycleFormData = new JButton(recycleBin);
        int recycleWidth = recycleFormData.getPreferredSize().width - 1000;
        int recycleHeight = recycleFormData.getPreferredSize().height - 4;
        recycleFormData.setBackground(Color.DARK_GRAY);
        recycleFormData.setBorder(border);
        recycleFormData.setPreferredSize(new Dimension(recycleWidth, recycleHeight));
        recycleFormData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formDataRequestArrayList.remove(keyFormData.getText() + "=" + valueFormData.getText() + "&");
                valueFormData.setText("value");
                keyFormData.setText("name");
            }
        });

        // add components to header panel
        panelFormData.add(keyFormData);
        panelFormData.add(valueFormData);
        panelFormData.add(c1FormData);
        panelFormData.add(recycleFormData);

        return panelFormData;

    }

    public String convertToString(ArrayList<String> arrayList) {
        StringBuffer output = new StringBuffer();
        for (int p = 0; p < arrayList.size(); p++) {
            output.append(arrayList.get(p));
        }
        return output.toString();

    }

    public String[] convertToArray(ArrayList<String> arrayList) {
       
        int index = 1;
        String[] args = new String[arrayList.size() - 1];
        for (int u = 0; u < arrayList.size(); u++) {
            if (arrayList.get(u) != null) {
                if (arrayList.get(u).equals("URL")) {
                    int indexArrayList = arrayList.indexOf("URL");
                    args[0] = arrayList.get(indexArrayList + 1);
                    u++;
                } else {
                    args[index] = arrayList.get(u);
                    index++;
                }
            }
        }
        return args;
    }

    public static void displayDirectoryContents(File dir, DefaultMutableTreeNode root2, JTree jt)
            throws InterruptedException {

        DefaultMutableTreeNode newdir = new DefaultMutableTreeNode();

        // creates array of file type for all the files found
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file == null) {
                System.out.println("NUll directory found ");
                continue;
            }
            if (file.isDirectory()) {
                // file is a directory that is a folder has been dound

                if (file.listFiles() == null) {
                    // skips null files
                    continue;
                }

                // gets the current model of the jtree
                DefaultTreeModel model = (DefaultTreeModel) jt.getModel();

                // gets the root
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

                // generates a node newdir using filename
                newdir = new DefaultMutableTreeNode(file.getName());

                // adds a node to the root of the jtree
                root2.add(newdir);

                // refresh the model to show the changes
                model.reload();

                // recursively calls the function again to explore the contents
                // folder
                displayDirectoryContents(file, newdir, jt);
            } else {
                // Else part File is not a directory

                // gets the current model of the tree
                DefaultTreeModel model = (DefaultTreeModel) jt.getModel();

                // selected node is the position where the new node will be
                // inserted
                DefaultMutableTreeNode selectednode = root2;

                DefaultMutableTreeNode newfile = new DefaultMutableTreeNode(file.getName());

                // inserts a node newfile under selected node which is the root
                model.insertNodeInto(newfile, selectednode, selectednode.getChildCount());

                // refresh the model to show the changes
                model.reload();

            }

        }
    }

    public static void scanner(JTree jt) throws InterruptedException {
        // creates a file with the location filename
        String location = "D:\\java\\project2\\requests";
        File currentDir = new File(location);

        // result is the variable name for jtree
        DefaultTreeModel model = (DefaultTreeModel) jt.getModel();
        // gets the root of the current model used only once at the starting
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        // function caled
        displayDirectoryContents(currentDir, root, jt);
    }

  

    public static void main(String[] args){
        try{ 
        connectionClass.main(args);
        }catch(Exception e){}
    }
}