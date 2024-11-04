package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

@Component
public class Server {
    private static final String SIGN_UP = "signUp";
    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    @Autowired
    private UsersService usersService;

    public void init(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {    
        System.out.println("Server started!");
        try {
            clientSocket = serverSocket.accept();            
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                out.write("Hello from Server!\n");
                out.flush();

                String commandFromClient = in.readLine();
                System.out.println(commandFromClient);

                if (commandFromClient.equalsIgnoreCase(SIGN_UP)) {
                    out.write("Enter username:\n");
                    out.flush();
                    String username = in.readLine();
                    out.write("Enter password:\n");
                    out.flush();
                    String password = in.readLine();
    
                    if (usersService.signUp(username, password)) {
                        out.write("Successful!\n");
                        out.flush();
                    } else {
                        out.write("Registration failed: User already exists.\n");
                        out.flush();
                    }
                } else {
                    System.out.println("Incorrect command: " + commandFromClient);
                }
            } finally {
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
            System.out.println("Server closed!");
        }
        
    }

}