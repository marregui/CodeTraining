package io.marregui.microservice;

import com.google.gson.Gson;
import io.marregui.util.ILogger;
import io.marregui.util.Logger;
import spark.Route;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.marregui.microservice.MicroserviceUtils.*;
import static spark.Spark.get;
import static spark.Spark.notFound;

public class Microservice {
    private static final ILogger LOGGER = Logger.loggerFor(Microservice.class);

    private static final Map<String, String> HELLO_AVAILABLE_COMMANDS = new HashMap<>();

    static {
        HELLO_AVAILABLE_COMMANDS.put("hello",
                "[/hello | /hello/param1/../paramn ('//' is replaced by white)]: returns a greeting");
    }

    private static final Gson GSON = new Gson();

    <T> void defaultPage(T usage) {
        get(ROOT_PATH, APP_JSON, (req, res) -> {
            setResponseHeaders(res);
            return GSON.toJson(ResponseMessage.success(usage));
        });
        notFound((req, res) -> {
            setResponseHeaders(res);
            return ResponseMessage.failure(String.format("command not supported: '%s', use: %s", req.pathInfo(), usage));
        });
        LOGGER.info(String.format("Default page: %s", usage));
    }

    void armCommand(String command) {
        LOGGER.info("arming command: '%s'", command);
        Route routeAction = (req, res) -> {
            String[] params = extractCommandParameters(command, req);
            setResponseHeaders(res);
            return GSON.toJson(ResponseMessage.success(String.format("Hello!, plus extra: %s", Arrays.toString(params))));
        };
        get(routePath(command, false), APP_JSON, routeAction); // parameter-less
        get(routePath(command, true), APP_JSON, routeAction); // with parameters
    }

    public static void main(String[] args) {
        MicroserviceUtils.setPort((args.length > 0) ? Integer.parseInt(args[0]) : 2012);
        Microservice hello = new Microservice();
        hello.defaultPage(HELLO_AVAILABLE_COMMANDS);
        for (String command : HELLO_AVAILABLE_COMMANDS.keySet()) {
            hello.armCommand(command);
        }
    }
}
