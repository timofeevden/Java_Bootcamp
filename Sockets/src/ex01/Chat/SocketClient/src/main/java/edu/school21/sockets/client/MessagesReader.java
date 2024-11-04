package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;

public class MessagesReader extends Thread {
    private BufferedReader in;

    public MessagesReader(BufferedReader input) {
        this.in = input;
    }

    @Override
    public void run()  {
        try {
            while (true) {
                String messageFromServer = in.readLine();
                if (messageFromServer != null && !messageFromServer.equals("")) {
                    System.out.println(messageFromServer);
                }
            }
        } catch (IOException e) {
        }
    }

}






