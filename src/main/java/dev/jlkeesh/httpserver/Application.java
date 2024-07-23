package dev.jlkeesh.httpserver;

import com.sun.net.httpserver.HttpServer;
import dev.jlkeesh.httpserver.config.SettingsConfig;
import dev.jlkeesh.httpserver.todo.TodoController;
import dev.jlkeesh.httpserver.todo.TodoCreateController;
import dev.jlkeesh.httpserver.todo.TodoDAO;
import dev.jlkeesh.httpserver.todo.TodoService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(SettingsConfig.get("server.port"));
        int concurrentRequest = Integer.parseInt(SettingsConfig.get("concurrent.request"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        TodoDAO todoDAO = new TodoDAO();
        TodoService todoService = new TodoService(todoDAO);
        server.createContext("/todo", new TodoController(todoService));
        server.createContext("/todo/add", new TodoCreateController(todoService));
        server.setExecutor(Executors.newFixedThreadPool(concurrentRequest));
        server.start();
        logger.info("server started on port : " + port);
    }
}
