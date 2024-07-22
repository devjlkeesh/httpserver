package dev.jlkeesh.httpserver.todo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dev.jlkeesh.httpserver.todo.dto.TodoCreateDto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TodoCreateController implements HttpHandler {
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "text/html";
    private final TodoService todoService;

    public TodoCreateController() {
        this.todoService = new TodoService();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        switch (httpExchange.getRequestMethod()) {
            case "GET" -> processGetRequest(httpExchange);
            case "POST" -> processPostRequest(httpExchange);
        }
    }

    private void processPostRequest(HttpExchange httpExchange) throws IOException {
        InputStream is = httpExchange.getRequestBody();
        String formDataAsString = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> formData = parseFormData(formDataAsString);
        TodoCreateDto dto = new TodoCreateDto(
                formData.get("title"),
                formData.get("description"),
                Long.valueOf(formData.get("userId")),
                Priority.valueOf(formData.get("priority"))
        );
        todoService.create(dto);
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        os.write("<div>success <a href=\"/todo\">Home Page</a></div>".getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    private Map<String, String> parseFormData(String formDataAsString) {
        HashMap<String, String> formData = new HashMap<>();

        for (String keyValue : formDataAsString.split("&")) {
            String[] keyValueArray = keyValue.split("=");
            formData.put(keyValueArray[0], keyValueArray[1]);
        }
        return formData;
    }

    private void processGetRequest(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        OutputStream os = httpExchange.getResponseBody();
        os.write(todo_add_html.getBytes());
        os.close();
    }

    String todo_add_html = """
            <!doctype html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>Todo Add</title>
            </head>
            <body>
            <h1>Todo add</h1>
            <form method="POST">
                <div>
                    <label>Title</label> <br/>
                    <input type="text" name="title">
                </div>
                <div>
                    <label>Description</label> <br/>
                    <textarea name="description" cols="30" rows="10"></textarea>
                </div>
                <div>
                    <label>User Id</label> <br/>
                    <input type="number" name="userId" min="1">
                </div>
                <div>
                    <label>Priority</label> <br/>
                    <select name="priority">
                        <option value="LOW">Low</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="HIGH">High</option>
                    </select>
                </div>
                <input type="submit" value="save todo">
            </form>
            </body>
            </html>""";
}
