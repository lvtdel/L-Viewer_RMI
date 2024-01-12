package Presentation;

import BLL.server.ServerChatBLL;
import BLL.server.IServerChatBLLCallback;
import BLL.constants.ChatConstant;
import BLL.rmi.IClientCallback;

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

    public ServerChatUi(IClientCallback clientCallback) {
        super();

        init(clientCallback);

//        KhoiTaoEventSend();
//        khoiTaoEventSendFile();
//        StartClientChatSocket(IP, port);
        setTitle("Chat (server)");
//        SetLblPartnerIP(IP);

        super.addAction();
        addAction();
        setVisible(true);
    }

    public IServerChatBLLCallback getServerChatBLLCallback() {
        return serverChatBLL.getServerChatBLLCallback();
    }


    private void init(IClientCallback clientCallback) {
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

            @Override
            public void onReceiveFileSuccess(String fileName) {
                String message = "Đã gửi 1 tệp " + ChatConstant.SAVE_FILE_LOCATION + fileName;
                addMessage("Partner", message);
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
        serverChatBLL.onSpeakerStateChange(chbxSpeaker.isSelected());
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
