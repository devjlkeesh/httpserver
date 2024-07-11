package dev.jlkeesh.httpserver.todo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class TodoController implements HttpHandler {
    private final TodoService todoService;
    public TodoController() {
        this.todoService = new TodoService();
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200,0);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json");
        OutputStream os = httpExchange.getResponseBody();
        os.write(new GsonBuilder().setPrettyPrinting().create().toJson(todoService.getAll()).getBytes());
        os.close();
    }
}
