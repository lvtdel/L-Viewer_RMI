package Presentation;

import BLL.chat_audio.client.ClientAudioBLL;
import BLL.chat_audio.client.ClientChatBLL;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientChatUi extends ChatForm {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ClientChatUi.createInstanceClientChatForm("192.168.1.8", 2001);
                ClientChatUi frame = ClientChatUi.getInstance();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private ClientChatBLL bll_LANClientChat = null;
    public static ClientChatUi instance = null;

    public static ClientChatUi getInstance() {
        return instance;
    }

    public static void createInstanceClientChatForm(String IP, int port) {
        instance = new ClientChatUi(IP, port);
    }

    public static void removeInstance() {
        instance.CloseAudioChat();
        instance = null;
    }

    private ClientChatUi(String IP, int port) {
        super();
        KhoiTaoEventSend();
        khoiTaoEventSendFile();
        StartClientChatSocket(IP, port);
        setTitle("Chat (c)");
        SetLblPartnerIP(IP);
    }

    @Override
    public void MicStateChange() {
        // TODO Auto-generated method stub
        super.MicStateChange();
        try {
            if (chbxMic.isSelected()) {
                System.out.println("Client mic open");
                try {
                    ClientAudioBLL.getInstance().startRecordingAndSending();
                } catch (Exception e) {
                    System.out.println("Khong ket noi duoc den server");
                }
            } else {
                System.out.println("Client mic close");
                ClientAudioBLL.getInstance().stopRecordingAndSending();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public void SpeakerStateChange() {
        // TODO Auto-generated method stub
        super.SpeakerStateChange();
        try {
            if (chbxSpeaker.isSelected()) {
                System.out.println("Client speaker open");
                ClientAudioBLL.getInstance().startReceivingAndSpeaking();
            } else {
                System.out.println("Client speaker close");
                ClientAudioBLL.getInstance().stopReceivingAndSpeaking();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public void KhoiTaoEventSend() {
        //Khoi tao event send
        btnSend.addActionListener(e -> SendMessageInTextBox());
        //Cho textbox
        txtText.addKeyListener(new KeyAdapter() {
            private boolean isShift = false;

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !isShift) {
                    int length = txtText.getText().length();
                    txtText.setText(txtText.getText().substring(0, length - 1)); //Cắt \n

                    SendMessageInTextBox();
                    txtText.setText("");
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && isShift) {
                    txtText.append("\n");
                }
                if (e.getKeyCode() == 16)
                    isShift = false;
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 16 && !isShift)
                    isShift = true;
            }
        });
    }

    @Override
    public void SendMessageInTextBox() {
        try {
            if (!txtText.getText().equals("")) {
                String message = txtText.getText().replace("\n", "\n      ");
                txtText.setText("");
                if (bll_LANClientChat != null) {
                    bll_LANClientChat.sendMessage(message);
                    chatData += "Me: " + message + "\n";
                    txtChatBox.setText(chatData);
                    //System.out.println("Client send: "+message);
                } else {
                    System.out.println("No connection");
                }
            }
        } catch (Exception e) {
            System.out.println("Can not send message!");
        }
    }

    @Override
    public void khoiTaoEventSendFile() {
        btnSendFile.addActionListener(e -> {
            String path = super.getPath();

            if (path != null && !path.isEmpty()) {
                bll_LANClientChat.sendFile(path);
            }
        });
    }

    String serverIP;
    int serverPort;
    private ClientAudioBLL bll_LANAudioClient;

    public void StartClientChatSocket(String IP, int port) {
        try {
            serverIP = IP;
            serverPort = port;
            bll_LANClientChat = new ClientChatBLL(InetAddress.getByName(IP), port);
            bll_LANClientChat.start();
        } catch (UnknownHostException e) {
            System.out.println("IP khong hop le!");
        }
    }

    @Override
    public void OpenAudioChat() {
        try {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                    if (bll_LANClientChat != null) {
                        int myLocalPort = bll_LANClientChat.GetClientChatSocketPort();
                        bll_LANAudioClient = ClientAudioBLL.getInstance(myLocalPort + 1, serverIP, serverPort + 1);
                        System.out.println("Start client audio at " + (myLocalPort + 1));
                        break;
                    }
                } catch (Exception e) {
                }
            }
            bll_LANAudioClient.startSocketAndInitAudio();
            bll_LANAudioClient.startReceivingAndSpeaking();
            bll_LANAudioClient.startRecordingAndSending();
        } catch (Exception e) {
            System.out.println("Khoi tao voice chat that bai!");
        }
    }

    @Override
    public void CloseAudioChat() {
        // TODO Auto-generated method stub
        ClientAudioBLL.RemoveInstance();
        //BLL_RemoteScreenForm.GetInstance().AnnounceConnectError("Mat ket noi!");
    }

    @Override
    public void CloseChat() {
        // TODO Auto-generated method stub
        bll_LANClientChat.stop();
        setVisible(false);
        dispose();
        instance = null;
    }
}
