package remote.ultil;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Ip {
//    public static String GetMyIPv4() {
//
//        String ipv4 = "";
//        try {
//            InetAddress localhost = InetAddress.getLocalHost();
//            ipv4 = getIPv4Address(localhost);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return ipv4;
//    }
//
//    private static String getIPv4Address(InetAddress inetAddress) {
//        // Kiểm tra xem địa chỉ có phải là IPv4 không
//        if (inetAddress.getHostAddress().indexOf(':') == -1) {
//            return inetAddress.getHostAddress();
//        } else {
//            return null; // Không phải là địa chỉ IPv4
//        }
//    }

    public static void main(String[] args) {
        String localIPv4Address = getMyIPv4();
        if (localIPv4Address != null) {
            System.out.println("Địa chỉ IPv4 local của bạn là: " + localIPv4Address);
        } else {
            System.out.println("Không thể lấy địa chỉ IPv4 local.");
        }
    }

    public static String getMyIPv4() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();

                    if (inetAddress.getHostAddress().indexOf(':') == -1) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null;
    }


}
