package Presentation;

import BLL.remote.ServerChatBLL;
import BLL.remote.rmi.ClientCallback;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ServerChatUi extends ChatForm {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
//                ServerChatUi.CreateInstanceServerChatForm(2001);
//                ServerChatUi frame = ServerChatUi.GetInstance();
//                frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
    }

    ServerChatBLL serverChatBLL;

    public static ServerChatUi instance = null;

    public static ServerChatUi GetInstance() {
        return instance;
    }

    public ServerChatUi(ClientCallback clientCallback) {
        super();

        init(clientCallback);

//        KhoiTaoEventSend();
//        khoiTaoEventSendFile();
//        StartClientChatSocket(IP, port);
        setTitle("Chat (client)");
//        SetLblPartnerIP(IP);

        super.addAction();
        addAction();
        setVisible(true);
    }


    private void init(ClientCallback clientCallback) {
        this.serverChatBLL = new ServerChatBLL(clientCallback) {
            @Override
            public void onReceiveMess(String mess) {
                addMessage("Partner", mess);
            }

            @Override
            public void notification(String mess) {
                System.out.println("Server chatUI notification:" + mess);
                addMessage("NOTIF: ", mess);
            }

            @Override
            public void onSendFileSuccess(String path) {
                String mess = "Đã gửi 1 tệp tin: " + path;

                addMessage("Me", mess);
            }
        };
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

            serverChatBLL.onSendFileClick(path);
        });

    }

    @Override
    public void micStateChange() {
        super.micStateChange();
        serverChatBLL.onMicStateChange(chbxMic.isSelected());
    }

    @Override
    public void speakerStateChange() {
        super.speakerStateChange();
    }

    public void sendMessageInTextBox() {
        try {
            if (!txtText.getText().equals("")) {
                String message = txtText.getText().replace("\n", "\n      ");

                serverChatBLL.onSendMessageActive(message);

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
