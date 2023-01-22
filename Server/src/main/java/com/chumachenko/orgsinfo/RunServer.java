package com.chumachenko.orgsinfo;

import com.chumachenko.orgsinfo.serverinfo.ServerProcessingThread;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class RunServer {


    private static int port;


    private static ServerProcessingThread serverProcessingThread;


    private static final Thread.UncaughtExceptionHandler exceptionHandler  = (th, ex) -> System.out.println(ex);


    private static Properties getPropertiesFromConfig() throws IOException {

        var properties = new Properties();
        String propFileName = "Server/src/main/resources/config.properties";
        var inputStream = new FileInputStream(propFileName);
        if (inputStream == null)
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        properties.load(inputStream);
        return properties;
    }

    public static void main(String[] args) throws Exception {

        var properties = getPropertiesFromConfig();
        port = Integer.parseInt(properties.getProperty("serverPort"));

        serverProcessingThread = new ServerProcessingThread(port);


        serverProcessingThread.setName("Server processing thread");


        serverProcessingThread.setUncaughtExceptionHandler(exceptionHandler);


        serverProcessingThread.start();


    }
}
