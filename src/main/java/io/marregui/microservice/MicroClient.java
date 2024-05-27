package io.marregui.microservice;

import com.google.gson.Gson;
import io.marregui.util.ILogger;
import io.marregui.util.Logger;
import io.marregui.util.ThrlStringBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public final class MicroClient {
    private static final Gson GSON = new Gson();
    private static final ILogger log = Logger.loggerFor(MicroClient.class);

    private static String prepareCommand(String[] params, int offset) {
        StringBuilder sb = ThrlStringBuilder.get();
        sb.append(MicroserviceUtils.ROOT_PATH);
        for (int i = offset; i < params.length; i++) {
            String param = params[i].replaceAll(" ", "//");
            sb.append(param).append(MicroserviceUtils.PATH_SEP);
        }
        int len = sb.length();
        if (len > 0) {
            sb.setLength(len - MicroserviceUtils.PATH_SEP.length());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            log.info("Syntax: java -cp.. %s host port command [parameters]", MicroClient.class.getName());
            log.info("       note: the param 'this//is//cool' becomes 'this is cool' ('//' is interpreted as blank)");
            System.exit(1);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String command = prepareCommand(args, 2);
        log.info("sending command: %s", command);

        try (Socket sck = new Socket(host, port);
             BufferedReader br = new BufferedReader(new InputStreamReader(sck.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sck.getOutputStream(), StandardCharsets.UTF_8))) {

            // request
            bw.write(MicroserviceUtils.createRequestHeader(command));
            bw.flush();

            // response
            ResponseMessage<?> response = GSON.fromJson(HttpChunkedReader.slurp(br), ResponseMessage.class);
            log.info("response: %s", response);
        } catch (Exception e) {
            log.error("error [%s:%d] %s => %s", host, port, command, e.getMessage());
            e.printStackTrace();
        }
    }

    private MicroClient() {
        throw new UnsupportedOperationException("no instances are allowed, it is a utilities class");
    }
}
