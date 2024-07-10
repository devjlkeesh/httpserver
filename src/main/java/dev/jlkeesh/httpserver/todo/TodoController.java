package dev.jlkeesh.httpserver.todo;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Log
public class TodoController implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        log.info(httpExchange.getRequestURI().toString() + " : " + httpExchange.getRequestMethod());
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add("Content-Type", "text/html");
        OutputStream os = httpExchange.getResponseBody();

        os.write("<p style=\"color:red; weight: bold; \">HTTP/1.1 200 OK TODO Controller </p>".getBytes());
        os.close();
    }
}
