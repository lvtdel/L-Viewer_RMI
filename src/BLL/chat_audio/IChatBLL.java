package BLL.chat_audio;

public interface IChatBLL {
	void start();
	void stop();
	void sendMessage(String message);
	void sendFile(String path);
}
