package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SIGN_UP = "signUp";
    private static final String EXIT = "exit";
    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static Scanner scanner;
    private final int port;

    public Client(int port) {
        this.port = port;
    }

    public void start() {
        try {
            clientSocket = new Socket("localhost", port);
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            scanner = new Scanner(System.in);
            String fromServer = in.readLine();
            System.out.println(fromServer);

            while (true) {
                String input = scanner.nextLine();
                if (input != null) {
                    if (input.equals(SIGN_UP)) {
                        signUp();
                        break;
                    } else if (input.equalsIgnoreCase(EXIT)) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                out.close();
                in.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void signUp() throws IOException {
        out.write(SIGN_UP + "\n");
        out.flush();
        System.out.println(in.readLine());
        String inputInfo = scanner.nextLine();
        out.write(inputInfo + "\n");
        out.flush();

        System.out.println(in.readLine());
        inputInfo = scanner.nextLine();
        out.write(inputInfo + "\n");
        out.flush();
        System.out.println(in.readLine());
    }
}
