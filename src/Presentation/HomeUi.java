package Presentation;


import BLL.remote.HomeBLL;
import util.IpAddress;
import util.Password;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class HomeUi extends JFrame {

    private JPanel contentPane;

    public static boolean isOpened = false;
    //    private final BLL_LANForm bll_LANForm;
    public JTextField txtYourIP;
    public JTextField txtYourPort;
    public JTextField txtYourPassword;
    public JTextField txtPartnerIP;
    public JTextField txtPartnerPort;
    public JTextField txtPartnerPassword;
    private JLabel lblStatus;
    private JLabel lblAllowConnection;
    private JLabel lblRemoteAnother;
    private JLabel lblInfoAllowConnection;
    private JLabel lblInfoRemoteAnother;
    private JLabel lblYourIP;
    private JLabel lblYourPort;
    private JLabel lblYourPassword;
    public JButton btnOpenConnect;
    private JLabel lblPartnerIP;
    private JLabel lblPartnerPort;
    private JLabel lblPartnerPassword;
    public JButton btnConnect;

    public JButton btnOpenChat;


    private HomeBLL homeBLL;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
//                //LANForm frame = new LANForm();
//                //frame.setVisible(true);
//                HomeUi.getInstance();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        HomeUi.getInstance().showWindow();
    }

    private static volatile HomeUi instance = null;

    public static HomeUi getInstance() {
        if (instance == null) {
            instance = new HomeUi();
        }

        return instance;
    }

    public static void RemoveInstance() {
        instance = null;
    }

    /**
     * Create the frame.
     */
    private HomeUi() {
        init();
        createUi();
        initValue();
        addAction();
    }


    private void init() {
        this.homeBLL = new HomeBLL() {
            @Override
            public void notification(String mess) {
                showMessage(mess, "Notification", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void onOpenServerSuccess() {
                btnOpenConnect.setBackground(Color.yellow);
                showStatus("Connection opened");
            }

            @Override
            public void onDisableServer() {
                btnOpenConnect.setBackground(SystemColor.control);
                showStatus("Connection closed");
            }
        };
    }

    private void addAction() {
        btnOpenChat.addActionListener(arg0 -> {
            System.out.println("Open chat clicked");
        });

        btnConnect.addActionListener(e -> {
            String host = txtPartnerIP.getText();
            int port = Integer.parseInt(txtPartnerPort.getText());
            String pass = txtPartnerPassword.getText();

            homeBLL.onConnectClick(host, port, pass);
        });

        btnOpenConnect.addActionListener(e -> {
            homeBLL.onAllowConnectClick();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isOpened = false;
                instance = null;
            }
        });
    }

    private void initValue() {
        String myIp = IpAddress.getMyIPv4();
        String myPassword = Password.randomPassword(4);
        txtYourIP.setText(myIp);
        txtYourPassword.setText(myPassword);

        txtPartnerIP.setText(myIp);
        txtPartnerPassword.setText(myPassword);

        showStatus("Connection closed");
    }

    public void showWindow() {
        setVisible(true);
    }

    public void showStatus(String status) {
        lblStatus.setText("Status: " + status);
    }

    public void showMessage(String message, String tile, int type) {
        JOptionPane.showMessageDialog(instance, message, tile, type);
    }

    private void createUi() {
        setResizable(false);
        setTitle("L-Viewer");
        setBackground(Color.LIGHT_GRAY);
        setForeground(new Color(191, 205, 219));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setBounds(100, 100, 860, 414);

        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.inactiveCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(SystemColor.inactiveCaption);
        statusPanel.setBounds(5, 335, 789, 20);
        statusPanel.setFocusable(false);
        statusPanel.setAlignmentY(0.0f);
        statusPanel.setAlignmentX(0.0f);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(248, 248, 255));
        panel.setBounds(5, 5, 837, 329);
        panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

        JPanel pnlOpenCnn = new JPanel();
        pnlOpenCnn.setBackground(new Color(248, 248, 255));
        pnlOpenCnn.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));

        lblYourIP = new JLabel("Your IP");
        lblYourIP.setAlignmentY(Component.TOP_ALIGNMENT);
        lblYourIP.setFont(new Font("Tahoma", Font.PLAIN, 16));

        lblYourPort = new JLabel("Your Port");
        lblYourPort.setFont(new Font("Tahoma", Font.PLAIN, 16));

        txtYourIP = new JTextField();

        txtYourIP.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtYourIP.setEditable(false);
        txtYourIP.setColumns(10);

        txtYourPort = new JTextField();
        txtYourPort.setText("1111");
        txtYourPort.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtYourPort.setColumns(10);

        lblYourPassword = new JLabel("Password");
        lblYourPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));

        txtYourPassword = new JTextField();
        txtYourPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtYourPassword.setColumns(10);

        btnOpenConnect = new JButton("Allow Connection");
        btnOpenConnect.setBackground(SystemColor.control);
        btnOpenConnect.setFocusable(false);
        btnOpenConnect.setFont(new Font("Tahoma", Font.PLAIN, 16));

        btnOpenChat = new JButton("");
        btnOpenChat.setBorder(null);
        btnOpenChat.setBackground(new Color(248, 248, 255));
        try {
            btnOpenChat.setIcon(new ImageIcon(ImageIO.read(new File("./resource/chatIcon.png"))));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            System.out.println("Can not load icon!");
        }
        btnOpenChat.setFocusable(false);
        btnOpenChat.setFont(new Font("Tahoma", Font.PLAIN, 16));


        lblInfoAllowConnection = new JLabel("<html>Please click \"Allow Connection\" and send your IP, port, password to your partner if you want they to remote your computer.</html>");
        lblInfoAllowConnection.setBackground(new Color(248, 248, 255));
        lblInfoAllowConnection.setFont(new Font("Tahoma", Font.ITALIC, 16));
        lblInfoAllowConnection.setOpaque(true);
        GroupLayout gl_pnlOpenCnn = new GroupLayout(pnlOpenCnn);
        gl_pnlOpenCnn.setHorizontalGroup(
                gl_pnlOpenCnn.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_pnlOpenCnn.createSequentialGroup()
                                .addGroup(gl_pnlOpenCnn.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_pnlOpenCnn.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(lblInfoAllowConnection, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_pnlOpenCnn.createSequentialGroup()
                                                .addGap(31)
                                                .addGroup(gl_pnlOpenCnn.createParallelGroup(Alignment.TRAILING)
                                                        .addGroup(gl_pnlOpenCnn.createSequentialGroup()
                                                                .addGroup(gl_pnlOpenCnn.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(lblYourPassword)
                                                                        .addComponent(lblYourIP))
                                                                .addGap(19))
                                                        .addGroup(gl_pnlOpenCnn.createSequentialGroup()
                                                                .addComponent(lblYourPort)
                                                                .addGap(18)))
                                                .addGroup(gl_pnlOpenCnn.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(txtYourIP, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                                        .addGroup(Alignment.TRAILING, gl_pnlOpenCnn.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(btnOpenChat, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(btnOpenConnect))
                                                        .addComponent(txtYourPassword, 238, 238, Short.MAX_VALUE)
                                                        .addGroup(gl_pnlOpenCnn.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(txtYourPort, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)))))
                                .addGap(28))
        );
        gl_pnlOpenCnn.setVerticalGroup(
                gl_pnlOpenCnn.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_pnlOpenCnn.createSequentialGroup()
                                .addGap(5)
                                .addComponent(lblInfoAllowConnection)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(gl_pnlOpenCnn.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(txtYourIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblYourIP))
                                .addGap(18)
                                .addGroup(gl_pnlOpenCnn.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblYourPort)
                                        .addComponent(txtYourPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(13)
                                .addGroup(gl_pnlOpenCnn.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblYourPassword)
                                        .addComponent(txtYourPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_pnlOpenCnn.createParallelGroup(Alignment.LEADING)
                                        .addComponent(btnOpenConnect)
                                        .addComponent(btnOpenChat, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
                                .addGap(17))
        );
        pnlOpenCnn.setLayout(gl_pnlOpenCnn);

        JPanel pnlRemoteForm = new JPanel();
        pnlRemoteForm.setBackground(new Color(248, 248, 255));
        pnlRemoteForm.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(0, 0, 0)));

        lblPartnerIP = new JLabel("Partner IP");
        lblPartnerIP.setBounds(13, 84, 71, 20);
        lblPartnerIP.setAlignmentY(Component.TOP_ALIGNMENT);
        lblPartnerIP.setFont(new Font("Tahoma", Font.PLAIN, 16));

        lblPartnerPort = new JLabel("Partner Port");
        lblPartnerPort.setBounds(13, 124, 85, 20);
        lblPartnerPort.setAlignmentY(Component.TOP_ALIGNMENT);
        lblPartnerPort.setFont(new Font("Tahoma", Font.PLAIN, 16));

        lblPartnerPassword = new JLabel("Password");
        lblPartnerPassword.setBounds(13, 164, 67, 20);
        lblPartnerPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));

        txtPartnerIP = new JTextField();
        txtPartnerIP.setBounds(141, 79, 216, 26);
        txtPartnerIP.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPartnerIP.setAlignmentY(Component.TOP_ALIGNMENT);
        txtPartnerIP.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtPartnerIP.setColumns(10);

        txtPartnerPort = new JTextField();
        txtPartnerPort.setText("1111");
        txtPartnerPort.setBounds(141, 119, 216, 26);
        txtPartnerPort.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPartnerPort.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        txtPartnerPort.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtPartnerPort.setColumns(10);

        txtPartnerPassword = new JTextField();
        txtPartnerPassword.setBounds(142, 159, 216, 26);
        txtPartnerPassword.setAlignmentY(Component.TOP_ALIGNMENT);
        txtPartnerPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPartnerPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtPartnerPassword.setColumns(10);

        btnConnect = new JButton("Start Remote");
        btnConnect.setBackground(SystemColor.control);
        btnConnect.setBounds(181, 198, 176, 29);
        btnConnect.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnConnect.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        btnConnect.setFocusable(false);
        btnConnect.setFont(new Font("Tahoma", Font.PLAIN, 16));

        lblInfoRemoteAnother = new JLabel("<html>Please enter IP, port, password of the computer that you want to remote.</html>");
        lblInfoRemoteAnother.setBackground(new Color(248, 248, 255));
        lblInfoRemoteAnother.setBounds(43, 13, 284, 40);
        lblInfoRemoteAnother.setFont(new Font("Tahoma", Font.ITALIC, 16));
        lblInfoRemoteAnother.setVerticalTextPosition(SwingConstants.TOP);
        lblInfoRemoteAnother.setOpaque(true);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.inactiveCaption);
        panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

        lblAllowConnection = new JLabel("Allow Connection");
        panel_1.add(lblAllowConnection);
        lblAllowConnection.setFont(new Font("Tahoma", Font.BOLD, 20));

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(SystemColor.inactiveCaption);
        panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

        lblRemoteAnother = new JLabel("Remote Another");
        panel_2.add(lblRemoteAnother);
        lblRemoteAnother.setFont(new Font("Tahoma", Font.BOLD, 20));
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
                                        .addComponent(panel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnlOpenCnn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(pnlRemoteForm, GroupLayout.PREFERRED_SIZE, 401, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addGap(31)
                                                .addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)))
                                .addGap(12))
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                        .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(pnlRemoteForm, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                                        .addComponent(pnlOpenCnn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        pnlRemoteForm.setLayout(null);
        pnlRemoteForm.add(lblInfoRemoteAnother);
        pnlRemoteForm.add(lblPartnerPassword);
        pnlRemoteForm.add(lblPartnerIP);
        pnlRemoteForm.add(lblPartnerPort);
        pnlRemoteForm.add(txtPartnerIP);
        pnlRemoteForm.add(txtPartnerPort);
        pnlRemoteForm.add(txtPartnerPassword);
        pnlRemoteForm.add(btnConnect);
        panel.setLayout(gl_panel);

        lblStatus = new JLabel("Status: ...");
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GroupLayout gl_statusPanel = new GroupLayout(statusPanel);
        gl_statusPanel.setHorizontalGroup(
                gl_statusPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_statusPanel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblStatus)
                                .addContainerGap(748, Short.MAX_VALUE))
        );
        gl_statusPanel.setVerticalGroup(
                gl_statusPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_statusPanel.createSequentialGroup()
                                .addComponent(lblStatus)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        statusPanel.setLayout(gl_statusPanel);
        contentPane.setLayout(null);
        contentPane.add(panel);
        contentPane.add(statusPanel);
    }
}
