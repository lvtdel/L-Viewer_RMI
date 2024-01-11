package BLL.remote;

import BLL.remote.rmi.IRemoteDesktop;
import BLL.remote.rmi.RmiClient;
import BLL.remote.rmi.RmiServer;
import Presentation.ClientChatUi;
import Presentation.RemoteScreenUi;

import java.awt.*;

public abstract class HomeBLL {
    RmiServer rmiServer;

    public HomeBLL() {
        this.rmiServer = RmiServer.getInstance();
    }

    public abstract void notification(String mess);
    public abstract void onOpenServerSuccess();
    public abstract void onDisableServer();

    public void onConnectClick(String host, int port, String pass) {
        System.out.println("Connect clicked");

        RmiClient rmiClient = new RmiClient();
        try {
            rmiClient.startConnectingToRmiServer(host, port);

            IRemoteDesktop remoteDesktop = rmiClient.getRemoteObject();
            boolean verify = remoteDesktop.verify(pass);
            System.out.println("verify: " + verify);

            if (verify) {
//                RemoteScreenBLL.create(new RemoteScreenForm(), remoteDesktop);
                new RemoteScreenUi(remoteDesktop);

                openClientChat();
            } else {
                notification("Verify failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void openClientChat() {
        EventQueue.invokeLater(() -> {
            try {
                ClientChatUi.createInstanceClientChatForm("192.168.1.8", 2001);
                ClientChatUi frame = ClientChatUi.getInstance();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void onAllowConnectClick() {
        System.out.println("AllowConnect clicked");
        try {
            if (this.rmiServer.isBinding()) {
                this.rmiServer.stopBindingOnRmiServer();
                onDisableServer();
            } else {
                this.rmiServer.startBindingOnRmiServer("localhost", 1111);
                onOpenServerSuccess();
                System.out.println("Server listening");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onOpenChatClick() {
        System.out.println("OpenChat clicked");
    }
}

