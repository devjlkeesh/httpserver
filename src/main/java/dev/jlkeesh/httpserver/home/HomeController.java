package dev.jlkeesh.httpserver.home;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.OutputStream;

@Log
public class HomeController implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        log.info(httpExchange.getRequestURI().toString() + " : " + httpExchange.getRequestMethod());
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
        os.close();
    }
}
