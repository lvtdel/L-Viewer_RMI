package remote.GUI;

import remote.BLL.RemoteScreenBLL;
import remote.BLL.rmi.IRemoteDesktop;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.ResourceBundle;

public class RemoteScreenForm extends JFrame {
    public static void main(String[] args) {
        OpenForm("192.168.1.135", "1999", "ahihi", 1);
        //                    Image dimg = oip.image.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
//                    label.setIcon(new ImageIcon(dimg));
    }

    //STATIC
    //Check trang thai form chi mo 1 lan 1 form
    public static RemoteScreenForm instance;
    public static boolean isOpened = false;

    public static void OpenForm(String ip, String port, String pass, int language) {
        if (isOpened)
            return;
        else {
            EventQueue.invokeLater(() -> {
                try {
                    instance = new RemoteScreenForm(new IRemoteDesktop() {
                        @Override
                        public boolean verify(String pass) throws RemoteException {
                            return false;
                        }

                        @Override
                        public byte[] takeScreenshotServer(String quality) throws Exception {
                            return new byte[0];
                        }

                        @Override
                        public void mouseMovedServer(double x, double y) throws RemoteException {

                        }

                        @Override
                        public void mousePressedServer(int buttons) throws RemoteException {

                        }

                        @Override
                        public void mouseReleasedServer(int buttons) throws RemoteException {

                        }

                        @Override
                        public void mouseWheelServer(int wheel_amt) throws RemoteException {

                        }

                        @Override
                        public void keyPressedServer(int keycode) throws RemoteException {

                        }

                        @Override
                        public void keyReleasedServer(int keycode) throws RemoteException {

                        }
                    });
                    instance.setVisible(true);
                    instance.SetLanguage(language);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static RemoteScreenForm getInstance() {
        return instance;
    }

    //Get instance cua BLL_RemoteScreenForm
//    private BLL_RemoteScreenForm bll_RemoteScreenForm = BLL_RemoteScreenForm.GetInstance();

    //Khoi tao GUI
    private JPanel contentPane;
    public JPanel panel;
    public JLabel labelImage;
    public JCheckBox chbxMouse;
    public JCheckBox chbxKeys;

    private JButton btnReOpenChat;
    private JPanel panel_1;
    private JLabel lblStatus;

    RemoteScreenBLL remoteScreenBLL;

    public RemoteScreenForm(IRemoteDesktop remoteDesktop) {
        init(remoteDesktop);

        setFont(new Font("Tahoma", Font.PLAIN, 16));
        setTitle("Remote screen");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                bll_RemoteScreenForm.CloseChatWindow();
//                bll_RemoteScreenForm.DisConnectRemote();
                isOpened = false;
            }

            @Override
            public void windowOpened(WindowEvent e) {
                isOpened = true;
                try {
//                    bll_RemoteScreenForm.ConnectRemoteTo(ip, port, pass);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    dispose();
                }

            }
        });
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(10, 10, 919, 522);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);

        //setUndecorated(true); //cai nay lam mat thanh tieu de
        setVisible(true);


        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        labelImage = new JLabel();

        panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(255, 0, 0)));
        panel.add(labelImage);


        chbxMouse = new JCheckBox("Mouse");
        chbxMouse.setToolTipText("Enable/Disable mouse remote.");
        chbxMouse.setFocusable(false);
        chbxMouse.setSelected(true);
        chbxMouse.setFont(new Font("Tahoma", Font.PLAIN, 15));

        chbxKeys = new JCheckBox("Keys");
        chbxKeys.setToolTipText("Ebable/Disable keys remote.");
        chbxKeys.setFocusable(false);
        chbxKeys.setSelected(true);
        chbxKeys.setFont(new Font("Tahoma", Font.PLAIN, 15));

        btnReOpenChat = new JButton("");
        btnReOpenChat.setFocusable(false);
        btnReOpenChat.setBackground(SystemColor.control);
        btnReOpenChat.setToolTipText("ReOpen Chat");
        try {
            btnReOpenChat.setIcon(new ImageIcon(ImageIO.read(new File("./resource/chatIcon.png"))));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            System.out.println("Can not load icon!");
        }
        btnReOpenChat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
//                bll_RemoteScreenForm.ReOpenChat();
            }
        });

        panel_1 = new JPanel();
        //panel.addMouse
        //panel.setBackground(Color.RED);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 483, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 216, Short.MAX_VALUE)
                                .addComponent(btnReOpenChat, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(chbxMouse)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(chbxKeys)
                                .addContainerGap())
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(panel, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(chbxKeys)
                                                .addComponent(chbxMouse))
                                        .addComponent(btnReOpenChat, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)))
        );

        lblStatus = new JLabel("Status:...");
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addComponent(lblStatus)
                                .addContainerGap(427, Short.MAX_VALUE))
        );
        gl_panel_1.setVerticalGroup(
                gl_panel_1.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
                                .addComponent(lblStatus)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_1.setLayout(gl_panel_1);

        contentPane.setLayout(gl_contentPane);


        addAction();
        remoteScreenBLL.showScreen();
    }

    private void init(IRemoteDesktop remoteDesktop) {
        this.remoteScreenBLL = new RemoteScreenBLL(remoteDesktop) {
            @Override
            public void setScreen(Image image) {
                setScreenImage(image);
            }
        };
    }

    private void addAction() {
        setVisible(true);

        panel.addMouseWheelListener(e -> {
            if (chbxMouse.isSelected()) {
                remoteScreenBLL.onMouseWheel(e.getWheelRotation());
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent arg0) {
                if (chbxMouse.isSelected()) {
                    float xRatio = (float) (arg0.getX() * 1.0 / panel.getWidth());
                    float yRatio = (float) (arg0.getY() * 1.0 / panel.getHeight());
//                    bll_RemoteScreenForm.SendMouseMove(xRatio, yRatio);
                    System.out.println(xRatio + " " + yRatio);

                    try {
                        remoteScreenBLL.onMouseMove(xRatio, yRatio);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (chbxMouse.isSelected()) {
                    double xRatio = (e.getX() * 1.0 / panel.getWidth());
                    double yRatio = (e.getY() * 1.0 / panel.getHeight());

                    System.out.println(xRatio + " " + yRatio);
//                    bll_RemoteScreenForm.SendMouseMove(xRatio, yRatio);
                    try {
                        remoteScreenBLL.onMouseMove(xRatio, yRatio);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { //Bat luc nhan xuong
                if (chbxMouse.isSelected()) {
                    System.out.println("Mouse press");
                    int button = InputEvent.getMaskForButton(e.getButton());

                    remoteScreenBLL.onMousePress(button);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (chbxMouse.isSelected()) {
                    System.out.println("Mouse release");
                    int button = InputEvent.getMaskForButton(e.getButton());

                    remoteScreenBLL.onMouseRelease(button);
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed");
                if (chbxKeys.isSelected()) {
                    remoteScreenBLL.onKeyPress(e.getKeyCode());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (chbxKeys.isSelected()) {
                    remoteScreenBLL.onKeyRelease(e.getKeyCode());
                }
            }
        });
    }

    public void setScreenImage(Image image) {
        try {
//            Image originImage = ImageIO.read(new File("D:\\Pictures\\Picture2.png"));
            int scaledWidth = panel.getWidth() - 15; // Điều chỉnh kích thước theo nhu cầu
            int scaledHeight = panel.getHeight() - 15;
            Image imageScale = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            labelImage.setIcon(new ImageIcon(imageScale));
            panel.updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void ShowStatus(String status) {
        lblStatus.setText("Status: " + status);
    }

    public void ShowMessage(String message, String tile, int type) {
        JOptionPane.showMessageDialog(this, message, tile, type);
    }

    private ResourceBundle languageRB = null;

    public void SetLanguage(int language) {
    }

    public String GetLanguageString(String key) {
        if (languageRB == null)
            languageRB = ResourceBundle.getBundle("internationalization.message.language", new Locale("en"));
        return languageRB.getString(key);
    }

}
