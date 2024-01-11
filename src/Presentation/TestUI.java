package Presentation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class TestUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Image Panel Example");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            // Tạo một JPanel và thêm nó vào JFrame
//            JPanel imagePanel = new JPanel();
//            frame.getContentPane().add(imagePanel);
//
//            // Đọc hình ảnh từ file (đường dẫn của hình ảnh cần được điều chỉnh)
//            Image image = null;
//            try {
//                Image originImage = ImageIO.read(new File("D:\\Pictures\\Picture2.png"));
//                int scaledWidth = 200; // Điều chỉnh kích thước theo nhu cầu
//                int scaledHeight = 200;
//                image = originImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // Tạo một JLabel để hiển thị hình ảnh
//            JLabel label = new JLabel(new ImageIcon(image));
//
//            // Thêm JLabel vào JPanel
//            imagePanel.add(label);
//
//            frame.setSize(400, 400);
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
            Image image = null;
            try {
                Image originImage = ImageIO.read(new File("D:\\Pictures\\Picture2.png"));
                int scaledWidth = 200; // Điều chỉnh kích thước theo nhu cầu
                int scaledHeight = 200;
                image = originImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            OpenForm("192.168.1.135", "1999", "ahihi", 1);

//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            RemoteScreenForm.GetInstance().panel.add(new JLabel(new ImageIcon(image)));

//            try {
//                new RemoteFrame();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
        });
    }
}
