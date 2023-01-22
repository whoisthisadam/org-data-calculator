package com.chumachenko.orgsinfo.serverinfo;

import java.io.IOException;

public class ServerProcessingThread extends Thread {

    private ServerConfig server;

    public ServerProcessingThread(int port) throws Exception {


        server = new ServerConfig(port);
    }

    @Override
    public void run() {

        try {


            server.runServer();
            super.run();

        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void interrupt() {
        try {


            server.stopServer();
            super.interrupt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public int getAmountOfConnectedClients(){
        return server.getAmountOfConnectedClients();
    }
}

