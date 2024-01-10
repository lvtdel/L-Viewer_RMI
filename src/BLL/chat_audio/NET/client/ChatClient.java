package BLL.chat_audio.NET.client;

import BLL.chat_audio.NET.LANChat;
import BLL.chat_audio.NET.constants.ChatConstant;
import util.FileSupport;
import Presentation.ClientChatForm;

import java.io.*;
import java.net.Socket;


public class ChatClient extends Thread implements LANChat {
    private Socket socket = null;
    private DataInputStream iStream = null;
    private DataOutputStream oStream = null;
    private final NET.SocketInfor serverInfor;
    private boolean isChatting = false;
    private ClientChatForm myClientChatForm = null;

    public ChatClient(NET.SocketInfor serverInfor) {
        this.serverInfor = serverInfor;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        try {
            System.out.println("Gọi ClientChatForm.getInstance()");
            myClientChatForm = ClientChatForm.getInstance();
            socket = new Socket(serverInfor.getIp(), serverInfor.getPort());
            System.out.println("Connected: " + socket);
            open();
            isChatting = true;
            System.out.println("Khoi tao client thanh cong!");
            while (isChatting) {
                try {
                    String message = iStream.readUTF();
                    if (message.equals(ChatConstant.SEND_FILE_COMMAND)) {
                        receiveFile();
//                        message = "Đã gửi 1 file";
                        continue;
                    }
//                    System.out.println(message);
                    myClientChatForm.AddMessage("Partner", message);
                } catch (IOException ioe) {
                    System.out.println("Khong co ket noi!");
                    isChatting = false;
                }
            }
            close();

        } catch (Exception e) {
            System.out.println("Loi khoi tao client");
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) {
        try {
            oStream.writeUTF(message);
            oStream.flush();

        } catch (IOException e) {
            System.out.println("Khong gui tin nhan duoc!");
        }

    }

    public void sendFile(String path, String fileName) {
        try {
            int bytes = 0;
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);

            oStream.writeUTF(ChatConstant.SEND_FILE_COMMAND);
            oStream.writeUTF(fileName);
            oStream.writeLong(file.length());

            byte[] buffer = new byte[1024 * 1024];// max 4GB
            while ((bytes = fileInputStream.read(buffer)) != -1) {
                oStream.write(buffer, 0, bytes);
                oStream.flush();
            }

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveFile() {
        String pathSave = FileSupport.saveFile(iStream);

        String message = pathSave != null ? "Đã gửi 1 tệp tin: " + pathSave : "Nhận tệp tin thất bại";
        ClientChatForm.getInstance().AddMessage("Partner", message);
    }


    @Override
    public void open() throws IOException {
        // TODO Auto-generated method stub
        iStream = new DataInputStream(socket.getInputStream());
        oStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void close() throws IOException {
        try {
            isChatting = false;
            if (socket != null)
                socket.close();
            if (iStream != null)
                iStream.close();
            if (oStream != null)
                oStream.close();
            System.out.println("Chat dong ket noi");
        } catch (Exception e) {
            System.out.println("Loi dong ket noi");
        }
    }

    @Override
    public void stopChat() {
        isChatting = false;
        try {
            close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public int GetClientChatSocketPort() {
        // TODO Auto-generated method stub
        return socket.getLocalPort();
    }
}
