package remote.BLL;

import remote.BLL.rmi.IRemoteDesktop;
import remote.BLL.rmi.RmiClient;
import remote.BLL.rmi.RmiServer;
import remote.GUI.RemoteScreenForm;

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
                new RemoteScreenForm(remoteDesktop);
            } else {
                notification("Verify failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
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

