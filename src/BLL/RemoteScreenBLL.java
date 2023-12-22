package BLL;

import BLL.rmi.IRemoteDesktop;
import GUI.RemoteScreenForm;

import javax.imageio.ImageIO;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;

public class RemoteScreenBLL {
    RemoteScreenForm remoteScreenForm;
    IRemoteDesktop remoteDesktop;


    private static RemoteScreenBLL instance;

    public static void create(RemoteScreenForm remoteScreenForm, IRemoteDesktop remoteDesktop) {
        instance = new RemoteScreenBLL(remoteScreenForm, remoteDesktop);
    }

    public static RemoteScreenBLL getInstance() {
        return instance;
    }

    public RemoteScreenBLL(RemoteScreenForm remoteScreenForm, IRemoteDesktop remoteDesktop) {
        this.remoteDesktop = remoteDesktop;
        this.remoteScreenForm = remoteScreenForm;
        remoteScreenForm.setVisible(true);

        remoteScreenForm.panel.addMouseWheelListener(e -> {
            if (remoteScreenForm.chbxMouse.isSelected()) {
                onMouseWheel(e.getWheelRotation());
            }
        });
        remoteScreenForm.panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent arg0) {
                if (remoteScreenForm.chbxMouse.isSelected()) {
                    float xRatio = (float) (arg0.getX() * 1.0 / remoteScreenForm.panel.getWidth());
                    float yRatio = (float) (arg0.getY() * 1.0 / remoteScreenForm.panel.getHeight());
//                    bll_RemoteScreenForm.SendMouseMove(xRatio, yRatio);
                    System.out.println(xRatio + " " + yRatio);

                    try {
                        onMouseMove(xRatio, yRatio);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (remoteScreenForm.chbxMouse.isSelected()) {
                    double xRatio = (e.getX() * 1.0 / remoteScreenForm.panel.getWidth());
                    double yRatio = (e.getY() * 1.0 / remoteScreenForm.panel.getHeight());

                    System.out.println(xRatio + " " + yRatio);
//                    bll_RemoteScreenForm.SendMouseMove(xRatio, yRatio);
                    try {
                        onMouseMove(xRatio, yRatio);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        remoteScreenForm.panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { //Bat luc nhan xuong
                if (remoteScreenForm.chbxMouse.isSelected()) {
                    System.out.println("Mouse press");
                    int button = InputEvent.getMaskForButton(e.getButton());

                    onMousePress(button);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (remoteScreenForm.chbxMouse.isSelected()) {
                    System.out.println("Mouse release");
                    int button = InputEvent.getMaskForButton(e.getButton());

                    onMouseRelease(button);
                }
            }
        });

        showScreen();
    }

    private void onMouseRelease(int button) {
        try {
            this.remoteDesktop.mouseReleasedServer(button);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void onMousePress(int button) {
        try {
            this.remoteDesktop.mousePressedServer(button);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void onMouseMove(double xRatio, double yRatio) throws RemoteException {
        this.remoteDesktop.mouseMovedServer(xRatio, yRatio);
    }

    private void onMouseWheel(int wheelRotation) {
        try {
            this.remoteDesktop.mouseWheelServer(wheelRotation);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void showScreen() {
        Thread receiveScreen = new Thread(() -> {
            while (true) {

                byte[] dgram;
                try {
                    dgram = remoteDesktop.takeScreenshotServer("jpg");

                    ByteArrayInputStream bis = new ByteArrayInputStream(dgram);
                    BufferedImage screenshot = ImageIO.read(bis);

                    remoteScreenForm.setScreenImage(screenshot);
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
