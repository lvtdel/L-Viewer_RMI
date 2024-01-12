package BLL.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientCallback extends Remote {
    void receiverMessageClient(String mess) throws RemoteException;
    void receiveFileClient(byte[] fileByte, String fileName) throws RemoteException;
    void receiveAudioServer(byte[] audioData) throws RemoteException;
}
