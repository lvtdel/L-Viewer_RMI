package BLL.chat_audio;

public interface IClientAudioBLL {
	void StartSocketAndInitAudio();
	void StartRecordingAndSending();
	void StopRecordingAndSending();
	void StartReceivingAndSpeaking();
	void StopReceivingAndSpeaking();
}
