package BLL.client;

import BLL.rmi.IRemoteDesktop;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;

public abstract class RemoteScreenBLL {
    IRemoteDesktop remoteDesktop;

    public abstract void setScreen(Image image);

    public RemoteScreenBLL(IRemoteDesktop remoteDesktop) {
        this.remoteDesktop = remoteDesktop;
    }

    public void onKeyRelease(int keyCode) {
        try {
            remoteDesktop.keyReleasedServer(keyCode);
            System.out.println("Key release: " + keyCode);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void onKeyPress(int keyCode) {
        try {
            remoteDesktop.keyPressedServer(keyCode);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void onMouseRelease(int button) {
        try {
            this.remoteDesktop.mouseReleasedServer(button);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void onMousePress(int button) {
        try {
            this.remoteDesktop.mousePressedServer(button);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void onMouseMove(double xRatio, double yRatio) throws RemoteException {
        this.remoteDesktop.mouseMovedServer(xRatio, yRatio);
    }

    public void onMouseWheel(int wheelRotation) {
        try {
            this.remoteDesktop.mouseWheelServer(wheelRotation);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void showScreen() {
        Thread receiveScreen = new Thread(() -> {
            while (true) {

                byte[] dgram;
                try {
                    dgram = remoteDesktop.takeScreenshotServer("jpg");

                    ByteArrayInputStream bis = new ByteArrayInputStream(dgram);
                    BufferedImage screenshot = ImageIO.read(bis);

                    setScreen(screenshot);
                    Thread.sleep(1000 / 30);
//                    System.out.println("setScreen");

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        receiveScreen.start();
    }

    public static void main(String[] args) {
//        new RemoteScreenBLL();
    }
}
