package BLL;

import BLL.rmi.IRemoteDesktop;
import BLL.rmi.RmiClient;
import BLL.rmi.RmiServer;
import GUI.HomeUi;
import GUI.RemoteScreenForm;
import ultil.Ip;
import ultil.Pass;

public class HomeBLL {

    HomeUi homeUi;
    RmiServer rmiServer;

    public HomeBLL(RmiServer rmiServer) {
        this.rmiServer = rmiServer;

        HomeUi.OpenForm();
        homeUi = HomeUi.GetInstance();

        homeUi.btnOpenchat.addActionListener(arg0 -> {
//                bll_LANForm.ReOpenChat();
            onOpenChatClick();
        });

        homeUi.btnConnect.addActionListener(e -> {
            String host = homeUi.txtPartnerIP.getText();
            int port = Integer.parseInt(homeUi.txtPartnerPort.getText());
            String pass = homeUi.txtPartnerPassword.getText();

            onConnectClick(host, port, pass);
        });

        homeUi.btnOpenConnect.addActionListener(e -> {
            onAllowConnectClick();
        });

        setLabel();
    }

    private void setLabel() {
        String ip = Ip.GetMyIPv4();
        String pass = Pass.RandomPassword(6);
        String port = "1111";

        homeUi.txtYourIP.setText(ip);
        homeUi.txtYourPassword.setText(pass);
        homeUi.txtYourPort.setText(port);

    }

    private void onConnectClick(String host, int port, String pass) {
        System.out.println("Connect clicked");

        RmiClient rmiClient = new RmiClient();
        try {
            rmiClient.startConnectingToRmiServer(host, port);

            IRemoteDesktop remoteDesktop = rmiClient.getRemoteObject();
            boolean verify = remoteDesktop.verify(pass);
            System.out.println("verify: " + verify);

            if (verify) {
                RemoteScreenBLL.create(new RemoteScreenForm(), remoteDesktop);
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
            } else {
                this.rmiServer.startBindingOnRmiServer("localhost", 1111);
                System.out.println("Server listening");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onOpenChatClick() {
        System.out.println("OpenChat clicked");

    }

    public static void main(String[] args) {
        new HomeBLL(new RmiServer());
    }
}

