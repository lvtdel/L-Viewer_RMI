package BLL.server;

public interface IServerChatBLLCallback {
    void onReceiveMessageServer(String mess);
    void onReceiveFileServer(byte[] fileByte, String fileName);

    void onReceiveAudio(byte[] audioData);
}
