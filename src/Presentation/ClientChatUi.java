package Presentation;

import BLL.constants.ChatConstant;
import BLL.remote.ClientChatBLL;
import BLL.remote.rmi.IRemoteDesktop;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientChatUi extends ChatForm {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
//                ClientChatUi.createInstanceClientChatForm("192.168.1.8", 2001);
//                ClientChatUi frame = ClientChatUi.getInstance();
//                frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
    }

    private ClientChatBLL clientChatBLL;
    public static ClientChatUi instance;

    public static ClientChatUi getInstance() {
        return instance;
    }


    public ClientChatUi(IRemoteDesktop remoteDesktop) {
        super();

        init(remoteDesktop);

//        KhoiTaoEventSend();
//        khoiTaoEventSendFile();
//        StartClientChatSocket(IP, port);
        setTitle("Chat (client)");
//        SetLblPartnerIP(IP);

        super.addAction();
        addAction();
    }


    private void init(IRemoteDesktop remoteDesktop) {
        this.clientChatBLL = new ClientChatBLL(remoteDesktop) {
            @Override
            public void onReceiveMess(String mess) {
                addMessage("Partner", mess);
            }

            @Override
            public void notification(String mess) {
                System.out.println("Client chatUI notification: " + mess);
                addMessage("NOTIF: ", mess);
            }

            public void onSendFileSuccess(String path) {
                String mess = "Đã gửi 1 tệp tin: " + path;

                addMessage("Me", mess);
            }

            @Override
            public void onReceiveFileSuccess(String fileName) {
                String message = "Đã gửi 1 tệp " + ChatConstant.SAVE_FILE_LOCATION + fileName;
                addMessage("Partner", message);
            }
        };

        clientChatBLL.startAudio();
    }

    protected void addAction() {
        //Khoi tao event send
        btnSend.addActionListener(e -> sendMessageInTextBox());
        //Cho textbox
        txtText.addKeyListener(new KeyAdapter() {
            private boolean isShift = false;

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !isShift) {
                    int length = txtText.getText().length();
                    txtText.setText(txtText.getText().substring(0, length - 1)); //Cắt \n

                    sendMessageInTextBox();
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

        btnSendFile.addActionListener(e -> {
            String path = super.getPath();

            clientChatBLL.onSendFileClick(path);
        });

    }

    @Override
    public void micStateChange() {
        super.micStateChange();
        clientChatBLL.onMicStateChange(chbxMic.isSelected());
    }

    @Override
    public void speakerStateChange() {
        super.speakerStateChange();
        clientChatBLL.onMicStateChange(chbxSpeaker.isSelected());
    }

    public void sendMessageInTextBox() {
        try {
            if (!txtText.getText().equals("")) {
                String message = txtText.getText().replace("\n", "\n      ");

                clientChatBLL.onSendMessageActive(message);

                // TODO: replace with addMessage();
                txtText.setText("");
                chatData += "Me: " + message + "\n";
                txtChatBox.setText(chatData);
            }
        } catch (Exception e) {
            System.out.println("Can not send message!");
        }
    }

}
