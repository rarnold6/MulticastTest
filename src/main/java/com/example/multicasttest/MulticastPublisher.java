package com.example.multicasttest;

import java.io.IOException;
import java.net.*;

public class MulticastPublisher extends Thread {
    @Override
    public void run() {
        while(true){
            InetAddress ip = null;
            try {
                ip = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }

            MulticastSocket socket = null;
            try {
                socket = new MulticastSocket();
                NetworkInterface networkInterface = Parameters.getNetworkInterface();
                System.out.println("Server Network IFace: " + networkInterface);

                socket.setNetworkInterface(networkInterface);
                socket.setTimeToLive(50);

                // or with IPv6Address: FF0E:0:0:0:0:0:0:101
                InetAddress groupSendIP = InetAddress.getByName("230.0.0.1");
                byte[] buf = ip.getAddress();

                DatagramPacket packet = new DatagramPacket(buf, buf.length, groupSendIP, 50101);
                System.out.println("send IPpacket...");
                socket.send(packet);
                System.out.println("Sending to "+ packet.getPort());
                System.out.println("sending to "+ packet.getSocketAddress());
                System.out.println("send IP Address: " + InetAddress.getByAddress(packet.getData()));
                System.out.println("Network Interface: " + socket.getNetworkInterface().getName());
                System.out.println();
                socket.close();
                Thread.sleep(5000);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
