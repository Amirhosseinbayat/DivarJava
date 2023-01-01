package org.finalproject.server.Http.RequestHandlers;

import org.finalproject.server.Http.Request;
import org.finalproject.server.Http.Response;

/**
 * Implementations of this interface will handle various httpRequests coming from the client.
 * see {@link #getHandlerCode()} and {@link Request#getRequestCode()} these codes are used to determine
 * which RequestHandler should process a given Request.
 */
public interface RequestHandler {

    Response handle(Request request);

    String getHandlerCode();

}
