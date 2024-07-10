package dev.jlkeesh.httpserver;

import com.sun.net.httpserver.HttpServer;
import dev.jlkeesh.httpserver.home.HomeController;
import dev.jlkeesh.httpserver.todo.TodoController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;

public class Application {
    static ResourceBundle settings = ResourceBundle.getBundle("application");

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(settings.getString("server.port"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new HomeController());
        server.createContext("/todo", new TodoController());
        server.setExecutor(Executors.newFixedThreadPool(20));
        server.start();
    }
}
