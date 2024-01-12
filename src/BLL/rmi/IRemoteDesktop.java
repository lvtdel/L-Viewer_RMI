package BLL.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteDesktop extends Remote {

    boolean verify(String pass) throws RemoteException;

    byte[] takeScreenshotServer(String quality) throws Exception;

    void mouseMovedServer(double x, double y) throws RemoteException;

    void mousePressedServer(int button) throws RemoteException;

    void mouseReleasedServer(int button) throws RemoteException;

    void mouseWheelServer(int wheel_amt) throws RemoteException;


    void keyPressedServer(int keycode) throws RemoteException;

    void keyReleasedServer(int keycode) throws RemoteException;


    void registerChat(IClientCallback clientCallback) throws RemoteException;

    void receiveMessageServer(String mess) throws RemoteException;

    void receiveFileServer(byte[] fileByte, String fileName) throws RemoteException;

    void receiveAudioServer(byte[] audioData) throws RemoteException;
}
