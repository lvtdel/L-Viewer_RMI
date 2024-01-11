package util;

import BLL.constants.ChatConstant;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSupport {

    public static void saveToFile(String filePath, byte[] data) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String saveFile(DataInputStream iStream) {
        try {
            int bytes = 0;

            String fileName = iStream.readUTF();
            long size = iStream.readLong(); // read file size

            String pathSave = ChatConstant.SAVE_FILE_LOCATION + fileName;
            FileOutputStream fileOutputStream = new FileOutputStream(pathSave);

            byte[] buffer = new byte[1024 * 1024]; // max 4GB
            while (size > 0
                    && (bytes = iStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                fileOutputStream.write(buffer, 0, bytes);
                size -= bytes; // read upto file size
            }

//            System.out.println("File is Received, location: " + pathSave);
//            String message = "Đã nhận 1 tệp tin: " + pathSave;
//            ClientChatForm.GetInstance().AddMessage("Partner", message);

            fileOutputStream.close();
            return pathSave;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
