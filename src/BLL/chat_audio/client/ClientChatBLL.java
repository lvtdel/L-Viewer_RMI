package BLL.chat_audio.client;

import BLL.chat_audio.IClientChatBLL;
import BLL.chat_audio.NET.client.ChatClient;

import java.net.InetAddress;
import java.nio.file.Path;

public class ClientChatBLL implements IClientChatBLL {
    private ChatClient lanClientChat = null;
    private NET.SocketInfor serverInfor = null;

    public ClientChatBLL(InetAddress serverIP, int serverPort) {
        serverInfor = new NET.SocketInfor(serverIP, serverPort);
    }

    public ClientChatBLL(NET.SocketInfor serverInfor) {
        this.serverInfor = serverInfor;
    }

    @Override
    public void Start() {
        lanClientChat = new ChatClient(serverInfor);
        lanClientChat.start();
    }

    @Override
    public void Stop() {
        if (lanClientChat != null) {
            lanClientChat.stopChat();
            lanClientChat = null;
        }
    }

    @Override
    public void sendMessage(String message) {
        if (lanClientChat != null)
            lanClientChat.sendMessage(message);
    }
    @Override
    public void sendFile(String path) {
        String fileName = Path.of(path).getFileName().toString();

        lanClientChat.sendFile(path, fileName);
    }

    public int GetClientChatSocketPort() {
        return lanClientChat.GetClientChatSocketPort();
    }
}
