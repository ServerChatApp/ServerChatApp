package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4321);
        Server server = new Server(serverSocket);
        server.startServer();
    }

    public Server(ServerSocket serverSocket) {
        try {
            this.serverSocket = serverSocket;
            System.out.println("✓ Server created.");
        } catch (Exception e) {
            System.out.println("✕ Error creating server.");
            e.printStackTrace();
        }
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                String username = clientHandler.getClientUsername();
                System.out.println("✓ "+ username +" connected.");

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("✕ Error starting server.");
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}