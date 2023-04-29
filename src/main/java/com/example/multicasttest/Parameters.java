package com.example.multicasttest;

import java.net.*;
import java.util.Enumeration;

public class Parameters {

    public static NetworkInterface getNetworkInterface() {
        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                try {
                    // here we specify, that it should be IPv4, supports Multicast and when connected to the VPN we use eth, on mac it would be en
                    if (inetAddress instanceof Inet4Address && networkInterface.supportsMulticast() && networkInterface.getName().contains("eth")) {
                        return networkInterface;
                    }
                } catch (SocketException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}
