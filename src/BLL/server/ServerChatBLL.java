package BLL.server;

import BLL.constants.ChatConstant;
import util.Audio;
import BLL.rmi.IClientCallback;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static util.FileSupport.saveToFile;

public abstract class ServerChatBLL {

    IClientCallback clientCallback;
    IServerChatBLLCallback serverChatBLLCallback;
    boolean shouldRecording;
    private boolean isSpeakerOn = true;

    public ServerChatBLL(IClientCallback clientCallback) {
        this.clientCallback = clientCallback;

        serverChatBLLCallback = new IServerChatBLLCallback() {
            @Override
            public void onReceiveMessageServer(String mess)  {
                System.out.println("Server receive mess: " + mess);
                onReceiveMess(mess);
            }

            @Override
            public void onReceiveFileServer(byte[] fileByte, String fileName) {
                saveToFile(ChatConstant.SAVE_FILE_LOCATION + fileName, fileByte);

                onReceiveFileSuccess(fileName);
            }

            @Override
            public void onReceiveAudio(byte[] audioData) {
                if (!isSpeakerOn) return;

                SourceDataLine line = Audio.getSourceDataLine();
                try {
                    line.open(Audio.getAudioFormat());
                    line.start();

                    line.write(audioData, 0, audioData.length);

                    line.drain();

                    line.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    abstract public void onReceiveMess(String mess);
    abstract public void notification(String mess);
    abstract public void onSendFileSuccess(String path);
    abstract public void onReceiveFileSuccess(String fileName);

    public void onSendMessageActive(String mess) {
        System.out.println("Server send mess: " + mess);

        try {
            clientCallback.receiverMessageClient(mess);
        } catch (Exception e) {
            System.out.println("Client send message error!");
        }
    }

    public void onSendFileClick(String path) {
        if (!path.isEmpty()) {
            File uploadFile = new File(path);
            String fileName = uploadFile.getName();

            try {
                byte[] uploadFileData = Files.readAllBytes(uploadFile.toPath());

                clientCallback.receiveFileClient(uploadFileData, fileName);

                onSendFileSuccess(path);
            } catch (Exception e) {
                e.printStackTrace();
                notification("Tệp quá lớn");
            }
        } else {
            //TODO: notification("Path is empty!")
        }
    }

    public void startAudio() {
        shouldRecording = true;

        Thread audioThread = new Thread(() -> {
            try {
                // Mở microphone
                AudioFormat format = Audio.getAudioFormat();
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

                if (!AudioSystem.isLineSupported(info)) {
                    System.out.println("Microphone is not supported");
                    notification("Microphone is not supported");
                    shouldRecording = false;
                }

                TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();

                System.out.println("Recording...");

                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];

                ByteArrayOutputStream byteArrayOutputStream;

                AtomicBoolean isRecording = new AtomicBoolean(true);
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.scheduleAtFixedRate(() -> {
                    isRecording.set(false);

                }, 500, 500, TimeUnit.MILLISECONDS);
                while (shouldRecording) {
                    isRecording.set(true);
                    byteArrayOutputStream = new ByteArrayOutputStream();
//                    buffer = new byte[bufferSize];
                    while (isRecording.get()) {
                        int count = line.read(buffer, 0, buffer.length);
                        if (count > 0) {
                            byteArrayOutputStream.write(buffer, 0, count);
                        }
                    }

                    byte[] audioData = byteArrayOutputStream.toByteArray();

                    new Thread(()-> {
                        try {
                            clientCallback.receiveAudioServer(audioData);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();

                    System.out.println("Send audio");
                }


                // Đóng microphone
                line.stop();
                line.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
//            }
        });

        audioThread.start();
    }

    public void onMicStateChange(boolean newValue) {
        if (newValue) {
            startAudio();
        }

        shouldRecording = newValue;
    }

    public void onSpeakerStateChange(boolean newValue) {
        isSpeakerOn = newValue;
    }

    public IServerChatBLLCallback getServerChatBLLCallback() {
        return serverChatBLLCallback;
    }
}
