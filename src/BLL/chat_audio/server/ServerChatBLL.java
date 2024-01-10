package BLL.server;

import BLL.chat_audio.IChatBLL;
import BLL.chat_audio.NET.server.ChatServer;

import java.nio.file.Path;

public class ServerChatBLL implements IChatBLL {
    private ChatServer serverChat = null;
    private int serverPort;

    public ServerChatBLL(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void start() {
        serverChat = new ChatServer(serverPort);
        serverChat.start();//Start server and receive message
    }

    //Stop server
    @Override
    public void stop() {
        if (serverChat != null) {
            serverChat.stopChat();
            serverChat = null;
        }
    }

    //Send message
    @Override
    public void sendMessage(String message) {
        if (serverChat != null) {
            serverChat.sendMessage(message);
        }
    }

    @Override
    public void sendFile(String path) {
        String fileName = Path.of(path).getFileName().toString();

        serverChat.sendFile(path, fileName);
    }

    public NET.SocketInfor getClientChatInfor() {
        // TODO Auto-generated method stub
        return serverChat.getClientChatInfor();
    }
}
