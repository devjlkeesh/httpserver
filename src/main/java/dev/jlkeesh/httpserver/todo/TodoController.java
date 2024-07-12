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
        switch (httpExchange.getRequestMethod()) {
            case "GET":
                processGetRequest(httpExchange);
            case "POST":
                processPostRequest(httpExchange);
            case "DELETE":
                processDeleteRequest(httpExchange);
        }
    }

    private void processDeleteRequest(HttpExchange httpExchange) throws IOException {
        OutputStream os = httpExchange.getResponseBody();
        String uri = getPath(httpExchange);
        Long id = getPathVariable(uri);
        todoService.deleteById(id);
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        os.write("todo successfully deleted".getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    private void processPostRequest(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        OutputStream os = httpExchange.getResponseBody();
        InputStream is = httpExchange.getRequestBody();
        TodoCreateDto dto = GsonUtil.fromJson(is, TodoCreateDto.class);
        todoService.create(dto);
        os.write("todo successfully added".getBytes());
        os.close();
    }

    private void processGetRequest(HttpExchange httpExchange) throws IOException {
        String uri = getPath(httpExchange);
        OutputStream os = httpExchange.getResponseBody();
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        Object responseData;
        if (uri.equals("/todo")) {
            responseData = todoService.getAll();
        } else {
            Long id = getPathVariable(uri);
            responseData = todoService.getById(id);
        }
        os.write(GsonUtil.objectToByteArray(responseData));
        os.close();
    }

    private static String getPath(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().getPath();
    }

    private static long getPathVariable(String uri) {
        return Long.parseLong(uri.split("/")[2]);
    }
}
