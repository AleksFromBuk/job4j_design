package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class.getName());
    private final static String HELLO = "Hello";
    private final static String EXIT = "Exit";

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream output = socket.getOutputStream();
                     BufferedReader input = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    output.write("HTTP/1.1 200 OK \r\n\r\n".getBytes());
                    String str = input.readLine();
                    System.out.println(str);
                    String data = getMessage(str);
                    if (EXIT.equals(data)) {
                        server.close();
                        break;
                    } else {
                        output.write(HELLO.equals(data) ? (HELLO + "\r\n\r\n").getBytes() : (data + "\r\n\n").getBytes());
                    }
                    output.flush();
                }
            }
        } catch (IOException e) {
            LOG.error("Ошибка ввода-вывода", e);
        }
    }

    private static String getMessage(String string) {
        return string.contains("=") ? string.split("\\?")[1].split(" ")[0].split("=")[1] : string;
    }
}
