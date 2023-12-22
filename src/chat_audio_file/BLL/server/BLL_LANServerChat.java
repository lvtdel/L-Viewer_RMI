package BLL.server;

import BLL.BLL_LANChat;
import NET.LANSocketInfor;
import NET.server.LANServerChat;

import java.nio.file.Path;

public class BLL_LANServerChat implements BLL_LANChat {
    private LANServerChat lanServerChat = null;
    private int serverPort;

    public BLL_LANServerChat(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void Start() {
        lanServerChat = new LANServerChat(serverPort);
        lanServerChat.start();//Start server and receive message
    }

    //Stop server
    @Override
    public void Stop() {
        if (lanServerChat != null) {
            lanServerChat.stopChat();
            lanServerChat = null;
        }
    }

    //Send message
    @Override
    public void sendMessage(String message) {
        if (lanServerChat != null) {
            lanServerChat.sendMessage(message);
        }
    }

    @Override
    public void sendFile(String path) {
        String fileName = Path.of(path).getFileName().toString();

        lanServerChat.sendFile(path, fileName);
    }

    public LANSocketInfor GetClientChatInfor() {
        // TODO Auto-generated method stub
        return lanServerChat.GetClientChatInfor();
    }
}
