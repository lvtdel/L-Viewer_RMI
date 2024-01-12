package BLL.rmi;

import BLL.server.IServerChatBLLCallback;
import Presentation.HomeUi;
import Presentation.ServerChatUi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteDesktopImpl extends UnicastRemoteObject implements IRemoteDesktop {
    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    final Robot mr_robot;

    IServerChatBLLCallback serverChatBLLCallback;


    public RemoteDesktopImpl() throws RemoteException, AWTException {
        super();
        this.mr_robot = new Robot();
    }

    @Override
    public boolean verify(String pass) throws RemoteException {
        boolean result = pass.equals(HomeUi.getInstance().txtYourPassword.getText());

        if (result) {
//            openServerChat();
            return true;
        }

        return false;
    }

    @Override
    public byte[] takeScreenshotServer(String quality) throws Exception {
        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle bounds = new Rectangle(screen_size);
        BufferedImage screenshot = this.mr_robot.createScreenCapture(bounds);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.setUseCache(false); // TODO: not using disk cache (using ram)
        ImageIO.write(screenshot, quality, bos);
        return bos.toByteArray();
    }

    // TODO: mouse
    @Override
    public void mouseMovedServer(double x, double y) throws RemoteException {
        int dx = (int) Math.round(screenSize.getWidth() * x);
        int dy = (int) Math.round(screenSize.getHeight() * y);

        this.mr_robot.mouseMove(dx, dy);
    }

    @Override
    public void mousePressedServer(int button) throws RemoteException {
        this.mr_robot.mousePress(button);
    }

    @Override
    public void mouseReleasedServer(int button) throws RemoteException {
        this.mr_robot.mouseRelease(button);
    }

    @Override
    public void mouseWheelServer(int wheel_amt) throws RemoteException {
        this.mr_robot.mouseWheel(wheel_amt);
    }

    // TODO: keyboard
    @Override
    public void keyPressedServer(int keycode) throws RemoteException {
        this.mr_robot.keyPress(keycode);
    }

    @Override
    public void keyReleasedServer(int keycode) throws RemoteException {
        this.mr_robot.keyRelease(keycode);
    }

    @Override
    public void registerChat(IClientCallback clientCallback) throws RemoteException {
        serverChatBLLCallback = new ServerChatUi(clientCallback).getServerChatBLLCallback();
    }

    @Override
    public void receiveMessageServer(String mess) throws RemoteException {
        serverChatBLLCallback.onReceiveMessageServer(mess);
    }

    @Override
    public void receiveFileServer(byte[] fileByte, String fileName) throws RemoteException {
        serverChatBLLCallback.onReceiveFileServer(fileByte, fileName);
    }

    @Override
    public void receiveAudioServer(byte[] audioData) throws RemoteException {
        serverChatBLLCallback.onReceiveAudio(audioData);
    }
}
