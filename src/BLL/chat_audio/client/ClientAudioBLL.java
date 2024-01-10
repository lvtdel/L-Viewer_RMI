package BLL.chat_audio.client;

import BLL.chat_audio.IClientAudioBLL;
import BLL.chat_audio.NET.client.AudioClient;


import javax.sound.sampled.TargetDataLine;
import java.net.InetAddress;

public class ClientAudioBLL implements IClientAudioBLL {

    public static void main(String[] args) {
        //Mo port 2000 va ket noi toi port 1999 cua server
        ClientAudioBLL bll_LANAudioSender = new ClientAudioBLL(2000, "192.168.1.135", 1999);
        bll_LANAudioSender.StartSocketAndInitAudio();
        //bll_LANAudioSender.StartRecordingAndSending();
        bll_LANAudioSender.StartReceivingAndSpeaking();
    }

    private static ClientAudioBLL instance = null;

    public static ClientAudioBLL GetInstance(int clientPort, String serverIp, int serverPort) {
        if (instance == null) {
            instance = new ClientAudioBLL(clientPort, serverIp, serverPort);
        }
        return instance;
    }

    public static ClientAudioBLL GetInstance() {
        return instance;
    }

    public static void RemoveInstance() {
        if (instance != null) {
            try {
                //instance.StopRecordingAndSending();
                //instance.StopReceivingAndSpeaking();
                instance.StopLANAudioClient();
            } catch (Exception e) {
                // TODO: handle exception
            }
            instance = null;
        }
    }

    //private boolean Sending =false;
    private int serverPort;
    private int clientPort;
    private String serverIP;
    private TargetDataLine audio_in;
    private AudioClient lanAudioClientThread = null;

    public ClientAudioBLL(int clientPort, String serverIp, int serverPort) {
        serverIP = serverIp;
        this.serverPort = serverPort;
        this.clientPort = clientPort;
    }

    public void StartSocketAndInitAudio() {
        try {
            InetAddress inet = InetAddress.getByName(serverIP);
            lanAudioClientThread = new AudioClient(clientPort, inet, serverPort);
            //Sending =true;
        } catch (Exception ex) {
            System.out.println("BLL_LANAudioSender Exception: create UDP faild");
        }
    }

    //Recording and sending
    public void StartRecordingAndSending() {
        if (lanAudioClientThread != null)
            lanAudioClientThread.RecordAndSend();
    }

    public void StopRecordingAndSending() {
        if (lanAudioClientThread != null)
            lanAudioClientThread.StopRecordAndSend();
    }

    //Receive and speaking
    public void StartReceivingAndSpeaking() {
        if (lanAudioClientThread != null)
            lanAudioClientThread.ReceiveAndSpeak();
        else {
            System.out.println("lanAudioClientThread null");
        }

    }

    public void StopReceivingAndSpeaking() {
        if (lanAudioClientThread != null)
            lanAudioClientThread.StopReceiveAndSpeak();
    }

    public void StopLANAudioClient() {
        if (lanAudioClientThread != null)
            lanAudioClientThread.StopLANAudioClient();
    }
}
