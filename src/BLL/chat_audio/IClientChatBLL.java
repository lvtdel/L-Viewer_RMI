package BLL.chat_audio;

public interface IClientChatBLL {
	void Start();
	void Stop();
	void sendMessage(String message);
	void sendFile(String path);
}
