package com.chumachenko.orgsinfo.serverinfo;

import com.chumachenko.orgsinfo.config.ConnectedClientConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ServerConfig {

    //Порт который будет слушать наш серв
    private int serverPort;

    //Сокет, который слушает порт и принимает входящие подключения
    private final ServerSocket acceptingSocket;


    private List<ClientProcessingThread> processingThreads;

    Thread.UncaughtExceptionHandler exceptionHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable exception) {

            processingThreads.remove(Integer.parseInt(thread.getName()));

            new RuntimeException(exception);

        }
    };

    public ServerConfig(int serverPort) throws Exception {
        this.serverPort = serverPort;
        acceptingSocket = new ServerSocket(serverPort);
        processingThreads = new ArrayList<>();
    }

    public void runServer() throws IOException {

        while (true) {


            var newClientSocket = acceptingSocket.accept();

            var newClient = new ConnectedClientConfig(newClientSocket);

            var newThread = new ClientProcessingThread(newClient);

            newThread.setName(String.valueOf(processingThreads.size()));

            newThread.setUncaughtExceptionHandler(exceptionHandler);

            newThread.start();
            processingThreads.add(newThread);
        }
    }

    public void stopServer() throws IOException {


        acceptingSocket.close();
        for (var thread : processingThreads) {
            thread.interrupt();
        }
    }

    public int getAmountOfConnectedClients(){
        return processingThreads.size();
    }

}