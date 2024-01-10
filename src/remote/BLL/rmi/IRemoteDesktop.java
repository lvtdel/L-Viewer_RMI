package remote.BLL.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteDesktop extends Remote {

    boolean verify(String pass) throws  RemoteException;

    byte[] takeScreenshotServer(String quality) throws Exception;

    void mouseMovedServer(double x, double y) throws RemoteException;
    void mousePressedServer(int buttons) throws RemoteException;
    void mouseReleasedServer(int buttons) throws RemoteException;
    void mouseWheelServer(int wheel_amt) throws RemoteException;


    void keyPressedServer(int keycode) throws RemoteException;
    void keyReleasedServer(int keycode) throws RemoteException;
}
