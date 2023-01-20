package org.finalproject.server.Http;

import com.sun.net.httpserver.HttpHandler;
import org.finalproject.server.Http.RequestHandlers.RequestHandler;

public interface IHttpRequestManager extends HttpHandler {

    void assignHandler(RequestHandler handler);

    void assignHandlers(RequestHandler... handlers);

}
