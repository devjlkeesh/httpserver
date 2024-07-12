package dev.jlkeesh.httpserver;

import com.sun.net.httpserver.HttpServer;
import dev.jlkeesh.httpserver.todo.TodoController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Application {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(5433),0);
        server.createContext("/todo",new TodoController()) ;
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
    }
}
