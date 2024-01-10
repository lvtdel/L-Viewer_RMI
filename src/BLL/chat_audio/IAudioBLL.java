package BLL.chat_audio;

public interface IAudioBLL {
	void startSocketAndInitAudio();
	void startRecordingAndSending();
	void stopRecordingAndSending();
	void startReceivingAndSpeaking();
	void stopReceivingAndSpeaking();
}
