package com.example.multicasttest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                System.out.println(networkInterface.getName());
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while(inetAddresses.hasMoreElements()){
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if(inetAddress instanceof Inet4Address){
                        System.out.println("Inet4Address : " + inetAddress);
                    } else {
                        System.out.println("Inet6Address : " + inetAddress);
                    }
                }


                System.out.println();

            }

        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        System.out.println();


        MulticastPublisher multicastPublisher = new MulticastPublisher();
        multicastPublisher.start();



        MulticastReceiver multicastReceiver = new MulticastReceiver();
        multicastReceiver.start();



        //launch();
    }
}