package com.chumachenko.orgsinfo.config;

import java.net.Socket;

public class ConnectedClientConfig {

    private Socket connectionSocket;

    private int idInDB;

    private ConnectedClientConfig() {
        connectionSocket = new Socket();
    }

    public ConnectedClientConfig(Socket connectionSocket) {
        this();
        setConnectionSocket(connectionSocket);
    }

    public synchronized Socket getConnectionSocket() {
        return connectionSocket;
    }

    public synchronized void setConnectionSocket(Socket connectionSocket) {
        if (connectionSocket == null) return;
        this.connectionSocket = connectionSocket;
    }

    public int getIdInDB() {
        return idInDB;
    }

    public void setIdInDB(int idInDB) {
        this.idInDB = idInDB;
    }

}
