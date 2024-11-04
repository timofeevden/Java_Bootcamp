package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import javafx.util.Pair;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

import java.util.Optional;
import java.util.List;

class ClientHandler extends Thread {
    private static final String SIGN_UP = "signUp";
    private static final String SIGN_IN = "signIn";
    private static final String EXIT = "exit";
    private List<ClientHandler> aliveClients;
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;
    private UsersService usersService;
    private Optional<User> user;

    public ClientHandler(Socket clientSocket, UsersService usersService, List<ClientHandler> clientList) throws IOException {
        this.user = Optional.empty(); 
        this.clientSocket = clientSocket;
        this.usersService = usersService;
        this.aliveClients = clientList;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        try {
            out.write("Hello from Server! Write signIn or signUp to logIn! \n");
            out.flush();            
            String commandFromClient = in.readLine();
            this.user = logIn(commandFromClient);
            startMessaging();
        } catch (IOException e) {
        //      e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
            //    e.printStackTrace();
            }
            aliveClients.remove(this);
        }
    }

    private Optional<User> logIn(String command) throws IOException {
        if (command == null || command.equals("null")) {
            throw new IOException("Input command is null!");
        }
        Optional<User> enteredUser = Optional.empty();

        if (command.equalsIgnoreCase(SIGN_UP)) {
            enteredUser =  signUp();
        } else if (command.equalsIgnoreCase(SIGN_IN)) {
            enteredUser = signIn();
        }
        return enteredUser;
    }

    private Optional<User> signUp() throws IOException {
        Pair<String, String> userInfo = getUsernamePassword();
        Optional<User> newUser = usersService.signUp(userInfo.getKey(), userInfo.getValue());
        if (newUser.isPresent()) {
            out.write("Successful!\n");
            out.flush();
        } else {
            out.write("Registration failed!\n");
            out.flush();
        }
        return newUser;
    }

    private Optional<User> signIn() throws IOException {
        Pair<String, String> userInfo = getUsernamePassword();
        Optional<User> enteredUser = usersService.signIn(userInfo.getKey(), userInfo.getValue());
        if (enteredUser.isPresent()) {
            out.write("Successful!\n");
            out.flush();
        } else {
            this.user = null;
            out.write("Failed: User\\Password is uncorrect! Connection is closed.\n");
            out.flush();
        }
        return enteredUser;
    }

    private Pair<String, String> getUsernamePassword() throws IOException {
        out.write("Enter username:\n");
        out.flush();
        String username = in.readLine();
        out.write("Enter password:\n");
        out.flush();
        String password = in.readLine();
        return new Pair<>(username, password);
    }

    private void sendMessage(Message message) {
        if (user.isPresent()) {
            try {
                out.write(message.getAuthor().getName() + ": " + (message.getText() != null ? message.getText() : "") + "\n");
                out.flush();
            } catch (IOException ignore) {}
        }
    }

    private void sendLeaveNotification(User leaveUser) {           
        try {
            out.write(leaveUser.getName() + " leave the chat.\n");
            out.flush();
        } catch (IOException ignore) {}
    }


    private void startMessaging() throws IOException {
        if (this.user.isPresent()) {
            out.write("Start messaging\n");
            out.flush();
            String text = "";
            while (true) {
                text = in.readLine();
                if (text == null || text.equals(EXIT) || text.equals("null") || !clientSocket.isConnected()) {
                    for (ClientHandler otherClient : aliveClients) {
                        if (otherClient != this) {
                            otherClient.sendLeaveNotification(this.user.get());
                        }
                    }
                    break;
                } else if (!text.equals("")) {
                    Message message = new Message(null, user.get(), null, text);
                    usersService.saveMessage(message);
                    //  System.out.println(message.getAuthor().getName() + ": " + message.getText());
                    for (ClientHandler otherClient : aliveClients) {
                        otherClient.sendMessage(message);
                    }
                }
            }
        }
    }
    
}





