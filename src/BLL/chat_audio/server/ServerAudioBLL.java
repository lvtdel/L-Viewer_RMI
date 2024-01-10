package BLL.chat_audio.server;

import BLL.chat_audio.IAudioBLL;
import BLL.chat_audio.NET.server.AudioServer;

import java.net.InetAddress;


public class ServerAudioBLL implements IAudioBLL {
    public static void main(String[] args) {
        ServerAudioBLL bll_LANAudioServer = new ServerAudioBLL(1999);
        bll_LANAudioServer.startSocketAndInitAudio();
        //bll_LANAudioServer.StartReceivingAndSpeaking();
        bll_LANAudioServer.SetClientIPAndPort("192.168.1.135", 2000);//mo server va ket noi toi port 2000 cua client
        bll_LANAudioServer.startRecordingAndSending();
    }

    private static ServerAudioBLL instance = null;

    public static ServerAudioBLL GetInstance(int serverStreamAudioPort) {
        if (instance == null) {
            instance = new ServerAudioBLL(serverStreamAudioPort);
        }
        return instance;
    }

    public static ServerAudioBLL GetInstance() {
        return instance;
    }

    public static void RemoveInstance() {
        if (instance != null) {
            try {
                //instance.StopRecordingAndSending();
                //instance.StopReceivingAndSpeaking();
                instance.StopLANAudioServer();
            } catch (Exception e) {
                // TODO: handle exception
            }
            instance = null;
        }
    }

    private final int port;
    AudioServer lanAudioServerThread = null;


    public ServerAudioBLL(int port) {
        this.port = port;
    }

    public void startReceivingAndSpeaking() {
        if (lanAudioServerThread == null)
            startSocketAndInitAudio();
        lanAudioServerThread.ReceiveAndSpeak();
    }

    public void stopReceivingAndSpeaking() {
        if (lanAudioServerThread != null) {
            lanAudioServerThread.StopReceiveAndSpeak();
        }
    }

    public void startRecordingAndSending() {
        if (lanAudioServerThread != null)
            lanAudioServerThread.RecordAndSend();
    }

    public void stopRecordingAndSending() {
        if (lanAudioServerThread != null)
            lanAudioServerThread.StopRecordAndSend();
    }

    public void StopLANAudioServer() {
        if (lanAudioServerThread != null)
            lanAudioServerThread.StopLANAudioServer();
    }

    public void startSocketAndInitAudio() {
        try {
            lanAudioServerThread = new AudioServer(port);
        } catch (Exception ex) {
            System.out.println("LANAudioServerThread: StartSocketAndInitAudio fail");

        }
    }

    public void SetClientIPAndPort(String clientIP, int clientPort) {
        if (lanAudioServerThread == null)
            startSocketAndInitAudio();
        try {
            InetAddress ip = InetAddress.getByName(clientIP);
            lanAudioServerThread.SetClientIPAndPort(ip, clientPort);

        } catch (Exception e) {
            System.out.println("BLL_LANAudioServer EXC:SetClientIPAndPort");
        }
    }

    public void SetClientIPAndPort(InetAddress clientIP, int clientPort) {
        if (lanAudioServerThread == null)
            startSocketAndInitAudio();
        try {
            lanAudioServerThread.SetClientIPAndPort(clientIP, clientPort);
        } catch (Exception e) {
            System.out.println("BLL_LANAudioServer EXC:SetClientIPAndPort");
        }

    }
}
