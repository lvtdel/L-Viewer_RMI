package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class ChatForm extends JFrame {

    protected JPanel contentPane;
    protected JTextArea txtChatBox;
    public JButton btnSend;
    public JButton btnSendFile;
    public JTextArea txtText;
    private String chatData = "";
    public JCheckBox speakerCheckBox;
    public JCheckBox micCheckBox;
    private JLabel lblPartnerIp;

    public void addMessage(String message) {
        chatData += message + "\n";
        txtChatBox.setText(chatData);
    }

    public void changeMicState() {
        if (micCheckBox.isSelected()) {
            micCheckBox.setIcon(new ImageIcon("resource/micOn.png"));
        } else {
            micCheckBox.setIcon(new ImageIcon("resource/micOff.png"));
        }
    }

    public void changeSpeakerState() {
        if (speakerCheckBox.isSelected()) {
            speakerCheckBox.setIcon(new ImageIcon("resource/speakerOn.png"));
        } else {
            speakerCheckBox.setIcon(new ImageIcon("resource/speakerOff.png"));
        }
    }

    /**
     * Create the frame.
     */
//    public abstract void OpenAudioChat();
//
//    public abstract void CloseAudioChat();
//
//    public abstract void CloseChat();
//
//    public abstract void KhoiTaoEventSend();
//
//    public abstract void SendMessageInTextBox();
//    public abstract void khoiTaoEventSendFile();

    public String getPath() {
        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getPath();
        }

        return null;
    }

    public ChatForm() {
        setTitle("Chat");
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
//                OpenAudioChat();
                System.out.println("Chat form duoc open");
            }

            @Override
            public void windowClosing(WindowEvent arg0) {
                //CloseAudioChat();
                micCheckBox.setSelected(false);
                speakerCheckBox.setSelected(false);
                System.out.println("Close audio chat roi!");
            }
        });
        CreateChatFormGUI();
    }

    public void addMessage(String name, String message) {
        chatData += name + ": " + message + "\n";
        txtChatBox.setText(chatData);
    }

    public void CreateChatFormGUI() {
        setBounds(100, 100, 410, 560);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setAutoscrolls(true);

        JPanel panel = new JPanel();


        JScrollPane scrollPane_1 = new JScrollPane();

        txtText = new JTextArea();
        txtText.setMargin(new Insets(4, 10, 4, 4));
        txtText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtText.setWrapStyleWord(true);
        txtText.setLineWrap(true);
        scrollPane_1.setViewportView(txtText);

        btnSend = new JButton("");
        btnSend.setBackground(SystemColor.control);
        try {
            btnSend.setIcon(new ImageIcon(ImageIO.read(new File(".\\resource\\sendIcon.png"))));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Can not load icon!");
        }
        btnSend.setFocusable(false);

        btnSendFile = new JButton("");
        btnSendFile.setBackground(SystemColor.control);
        try {
            btnSendFile.setIcon(new ImageIcon(ImageIO.read(new File(".\\resource\\sendFileIcon.png"))));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Can not load icon!");
        }
        btnSendFile.setFocusable(false);
        btnSendFile.setPreferredSize(new Dimension(20, 20));

        lblPartnerIp = new JLabel("Partner IP");
        lblPartnerIp.setFont(new Font("Tahoma", Font.PLAIN, 15));

        GroupLayout gl_contentPane = new GroupLayout(contentPane);

        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(scrollPane_1)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(btnSendFile).addComponent(btnSend))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(lblPartnerIp)
                                                .addPreferredGap(ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                                                .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lblPartnerIp)
                                        .addComponent(panel, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 383, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                                        .addComponent(btnSendFile, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                                        .addComponent(btnSend, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                                .addContainerGap())
        );


        micCheckBox = new JCheckBox("");
        micCheckBox.setIcon(new ImageIcon("resource\\micOn.png"));
//        micCheckBox.addItemListener(arg0 -> changeMicState());

        speakerCheckBox = new JCheckBox("");
        speakerCheckBox.setIcon(new ImageIcon("resource\\speakerOn.png"));
        speakerCheckBox.setSelected(true);
//        speakerCheckBox.addItemListener(new ItemListener() {
//            public void itemStateChanged(ItemEvent arg0) {
//                changeSpeakerState();
//            }
//        });
        panel.add(speakerCheckBox);
        speakerCheckBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(micCheckBox);
        micCheckBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
        micCheckBox.setSelected(true);

        txtChatBox = new JTextArea();
        txtChatBox.setMargin(new Insets(5, 10, 5, 5));
        txtChatBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtChatBox.setEditable(false);
        scrollPane.setViewportView(txtChatBox);
        txtChatBox.setRows(10);
        txtChatBox.setLineWrap(true);
        txtChatBox.setWrapStyleWord(true);
        txtChatBox.setText(chatData);
        contentPane.setLayout(gl_contentPane);
    }

    public void SetLblPartnerIP(String ip) {
        lblPartnerIp.setText("Partner: " + ip);
    }

}
