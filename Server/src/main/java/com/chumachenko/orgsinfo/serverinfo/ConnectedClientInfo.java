package com.chumachenko.orgsinfo.serverinfo;

import java.net.Socket;

public class ConnectedClientInfo {

    private Socket connectionSocket;

    private int idInDB;

    private ConnectedClientInfo() {
        connectionSocket = new Socket();
    }

    public ConnectedClientInfo(Socket connectionSocket) {
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
