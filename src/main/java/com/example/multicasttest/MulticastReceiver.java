package com.example.multicasttest;

import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;
import java.util.Enumeration;

public class MulticastReceiver extends Thread{

    private InetAddress serverInetAddress;

    @Override
    public void run() {
        try {
            byte[] buf = new byte[4];
            System.out.println("Search for Server started");

            MulticastSocket multicastSocket = new MulticastSocket(50101);
            NetworkInterface networkInterface = Parameters.getNetworkInterface();
            System.out.println("Client Network IFace: " + networkInterface);
            multicastSocket.setNetworkInterface(networkInterface);

            // Or with IPv6Address : FF0E:0:0:0:0:0:0:101
            InetAddress inetAddress = InetAddress.getByName("230.0.0.1");
            SocketAddress socketAddress = new InetSocketAddress(inetAddress,50101);
            multicastSocket.joinGroup(socketAddress, networkInterface);
            System.out.println("connected to group with Network Interface : " + multicastSocket.getNetworkInterface().getName());
            while(true){
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                multicastSocket.receive(datagramPacket);
                this.serverInetAddress = InetAddress.getByAddress(datagramPacket.getData());
                System.out.println("RECEIVED : " + this.serverInetAddress);
                break;
            }
            multicastSocket.leaveGroup(inetAddress);
            multicastSocket.close();



            //System.out.println("NETWORK IFACE CLIENT: " + key.networkInterface().getName());


            System.out.println();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
