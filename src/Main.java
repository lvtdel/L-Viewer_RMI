import BLL.rmi.RmiServer;

import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws MalformedURLException, RemoteException, AWTException {
        RmiServer rmiServer = new RmiServer();
        rmiServer.startBindingOnRmiServer("localhost", 1111);
    }
}