package Presentation;

import BLL.chat_audio.server.ServerAudioBLL;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ServerChatUi extends ChatForm {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ServerChatUi.CreateInstanceServerChatForm(2001);
                    ServerChatUi frame = ServerChatUi.GetInstance();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static ServerChatUi instance = null;

    public static ServerChatUi GetInstance() {
        return instance;
    }

    public static void CreateInstanceServerChatForm(int serverPort) {
        instance = new ServerChatUi(serverPort);
        System.out.println("Da CreateInstanceServerChatForm ");
    }

    private ServerChatUi(int serverPort) {
        super();
        KhoiTaoEventSend();
        khoiTaoEventSendFile();
        StartServerChatSocket(serverPort);
        setTitle("Chat (server)");
    }

    @Override
    public void MicStateChange() {
        // TODO Auto-generated method stub
        super.MicStateChange();
        try {
            if (chbxMic.isSelected()) {
                System.out.println("Server mic open");
                ServerAudioBLL.GetInstance().startRecordingAndSending();
            } else {
                System.out.println("Server mic close");
                ServerAudioBLL.GetInstance().stopRecordingAndSending();
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
                System.out.println("Server speaker open");
                ServerAudioBLL.GetInstance().startReceivingAndSpeaking();

            } else {
                System.out.println("Server speaker close");
                ServerAudioBLL.GetInstance().stopReceivingAndSpeaking();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public void KhoiTaoEventSend() {
        btnSendFile.addActionListener(e -> {});
        //Khoi tao event send
        btnSend.addActionListener(e -> SendMessageInTextBox());
        //Cho textbox
        txtText.addKeyListener(new KeyAdapter() {
            private boolean isShift = false;

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10 && !isShift) {
                    int length = txtText.getText().length();
                    txtText.setText(txtText.getText().substring(0, length - 1));
                    SendMessageInTextBox();
                    txtText.setText("");
                } else if (e.getKeyCode() == 10 && isShift) {
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
                if (bll_LANServerChat != null) {
                    bll_LANServerChat.sendMessage(message);
                    chatData += "Me: " + message + "\n";
                    txtChatBox.setText(chatData);
                    //System.out.println("Server send: "+message);
                } else {
                    System.out.println("No connection");
                }
            }
        } catch (Exception e) {
            System.out.println("Can not send message!");
        }
    }

    private BLL.server.ServerChatBLL bll_LANServerChat = null;

    public void StartServerChatSocket(int serverPort) {
        System.out.println("Start server chat socket");
        mylocalport = serverPort;
        bll_LANServerChat = new BLL.server.ServerChatBLL(serverPort);
        bll_LANServerChat.start();
    }

    private int mylocalport;
    private ServerAudioBLL bll_LANAudioServer;

    @Override
    public void OpenAudioChat() {
        // TODO Auto-generated method stub
        try {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                    if (bll_LANServerChat != null) {
                        NET.SocketInfor clientChatInfo = bll_LANServerChat.getClientChatInfor();
                        bll_LANAudioServer = ServerAudioBLL.GetInstance(mylocalport + 1);
                        bll_LANAudioServer.SetClientIPAndPort(clientChatInfo.getIp(), clientChatInfo.getPort() + 1);
                        System.out.println("Start client audio at " + (mylocalport + 1));
                        break;
                    }
                } catch (Exception e) {
                }
            }
            bll_LANAudioServer.startReceivingAndSpeaking();
            bll_LANAudioServer.startRecordingAndSending();

        } catch (Exception e) {
            System.out.println("Khoi tao voice chat that bai!");
        }
    }

    @Override
    public void CloseAudioChat() {
        // TODO Auto-generated method stub
        ServerAudioBLL.RemoveInstance();
    }

    @Override
    public void CloseChat() {
        // TODO Auto-generated method stub
        bll_LANServerChat.stop();
        setVisible(false);
        dispose();
        instance = null;
    }

    @Override
    public void khoiTaoEventSendFile() {
        btnSendFile.addActionListener(e -> {
            String path = super.getPath();

            if (path != null && !path.isEmpty()) {
                bll_LANServerChat.sendFile(path);
            }
        });
    }
}
