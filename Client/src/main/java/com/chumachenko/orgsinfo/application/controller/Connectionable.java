package com.chumachenko.orgsinfo.application.controller;

import com.chumachenko.orgsinfo.connection.clientconnection.ClientConnection;

public interface Connectionable {
    void setAccess(ClientConnection access);
}
