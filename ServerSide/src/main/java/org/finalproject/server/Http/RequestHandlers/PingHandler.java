package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;

/**
 * Handles a ping request and responds with a pong message.
 * this is used to check if the server is up and reachable.
 */
public class PingHandler implements RequestHandler {
    @Override
    public Response handle(Request request) {
        return new Response(200, "server is up!");
    }

    @Override
    public String getHandlerCode() {
        return "GET:/ping";
    }
}
