package dev.jlkeesh.httpserver.todo;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dev.jlkeesh.httpserver.todo.dto.TodoCreateDto;
import dev.jlkeesh.httpserver.utils.GsonUtil;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Log
public class TodoController implements HttpHandler {
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";
    private final TodoService todoService;
    private final Gson gson = GsonUtil.getGson();

    public TodoController() {
        this.todoService = new TodoService();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        URI requestURI = httpExchange.getRequestURI();
        String requestMethod = httpExchange.getRequestMethod();
        switch (requestMethod) {
            case "GET":
                processGetRequest(httpExchange);
            case "POST":
                processPostRequest(httpExchange);
            case "DELETE":
                processDeleteRequest(httpExchange);
        }
    }

    private void processDeleteRequest(HttpExchange httpExchange) {

    }

    private void processPostRequest(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        OutputStream os = httpExchange.getResponseBody();
        InputStream is = httpExchange.getRequestBody();
        TodoCreateDto dto = gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), TodoCreateDto.class);
        todoService.create(dto);
        os.write("todo successfully added".getBytes());
        os.close();
    }

    private void processGetRequest(HttpExchange httpExchange) throws IOException {
        String uri = httpExchange.getRequestURI().getPath();
        OutputStream os = httpExchange.getResponseBody();
        if (uri.equals("/todo")) {
            httpExchange.sendResponseHeaders(200, 0);
            httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
            os.write(GsonUtil.objectToByteArray(todoService.getAll()));
        } else {
            Long id = Long.parseLong(uri.split("/")[2]);
            Todo todo = todoService.getById(id);
            httpExchange.sendResponseHeaders(200, 0);
            httpExchange.getResponseHeaders().add("Content-Type", "application/json");
            os.write(GsonUtil.objectToByteArray(todo));
        }
        os.close();
    }
}
