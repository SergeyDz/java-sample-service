package com.intapp.releasepromotionsample;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class App 
{
    public static void main(String[] args) {
        int port = 8000;
        App server = new App();
        String version = server.GetVersion();
        System.out.println("Application started. Version=" + version);
        server.StartHttpListener(port);
    }

    public void StartHttpListener(int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            
            server.createContext("/health", new OkHandler());
            server.createContext("/", new OkHandler());
            server.createContext("/version", new VersionHandler());
            server.setExecutor(null);

            System.out.println("Staring http server on port: " + port);
            server.start();
        } catch(IOException err) {
            System.out.println("StartHttpListener exception:" + err.getMessage());
        }
    }

    public String GetVersion() {
        final Properties properties = new Properties();
        
        try (final InputStream stream = this.getClass().getResourceAsStream("/version.properties")) {
            properties.load(stream);
        } catch(IOException err) {
            System.out.println("Get Version exception:" + err.getMessage());
        }

        return properties.getProperty("version");
    }

    static class OkHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "OK";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8));
            os.close();
        }
    }

    static class VersionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            App app = new App();
            String response = app.GetVersion();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8));
            os.close();
        }
    }

}
