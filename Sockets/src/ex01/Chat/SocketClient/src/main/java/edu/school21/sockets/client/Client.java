package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final String SIGN_UP = "signUp";
    private static final String SIGN_IN = "signIn";
    private static final String EXIT = "exit";
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private final int port;

    public Client(int port) {
        this.port = port;
    }

    public void start() {
        try {
            socket = new Socket("localhost", port);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            String fromServer = in.readLine();
            System.out.println(fromServer);

            loginToAccount();
            startMessage();
        } catch (IOException e) {
            System.out.println("Connection lost");
        //    e.printStackTrace();
        } finally {
            try {
                socket.close();
                out.close();
                in.close();
                inputUser.close();
            } catch (Exception e) {
            //    e.printStackTrace();
            }
        }
    }

    private void loginToAccount() throws IOException {
        while (true) {
            String input = inputUser.readLine();

            if (input == null || input.equalsIgnoreCase(EXIT)) {
                throw new IOException();
            }                
            if (input.equals(SIGN_UP)) {
                signUp();
                break;
            } else if (input.equals(SIGN_IN)) {
                signIn();
                break;
            } else {
                System.out.println("Неизвестная команда: " + input);
            }
        }
    }

    private void signUp() throws IOException {
        out.write(SIGN_UP + "\n");
        out.flush();
        enterUsernameAndPassword();
    }

    private void signIn() throws IOException {
        out.write(SIGN_IN + "\n");
        out.flush();
        enterUsernameAndPassword();
    }

    private void enterUsernameAndPassword() throws IOException {
        System.out.println(in.readLine());
        String inputInfo = inputUser.readLine();
        out.write(inputInfo + "\n");
        out.flush();

        System.out.println(in.readLine());
        inputInfo = inputUser.readLine();
        out.write(inputInfo + "\n");
        out.flush();
        String answer = in.readLine();
        if (answer.equals("Successful!")) {
            System.out.println(answer);
        } else {
            if (answer != null) {
                System.out.println(answer);
            }
            throw new IOException();
        }
    }

    private void startMessage() throws IOException {
        MessagesReader messagesReader = new MessagesReader(in);
        messagesReader.start();

        String message = "";
        while (true) {
            message = inputUser.readLine();
            out.write(message + "\n");
            out.flush();

            if (message.equals(EXIT)) {
                System.out.println("You have left the chat.");
                break;
            }
        }
    }
}
