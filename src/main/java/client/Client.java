package client;

import server.ClientHandler;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import static server.ClientHandler.clientHandlers;

public class Client {

    private static Scanner scan = new Scanner(System.in);
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    private String password;
    private String passwordConfirm;

    public static void main(String[] args) throws IOException {
        try {
            LoginRegisterMenu();


//            // TODO: Create client and set login and register
//            System.out.print("Username: ");
//            String username = scan.nextLine();
//            System.out.print("Password: ");
//            String password = scan.nextLine();
//
//            // TODO: Connect to server checking if username or email exists and the password is correct
//            Socket socket = new Socket("localhost", 4321);
//            Client client = new Client(socket, username, password);
//            client.ListenForMessage();
//            client.sendMessage();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("✕ Error creating client.");
        }
    }


    private static void LoginRegisterMenu() throws IOException {
        System.out.println("Welcome to the chat app."+"\n"+
                "1. Login"+"\n"+
                "2. Register"+"\n"+
                "3. Guest"+"\n"+
                "0. Exit");

        System.out.println("Enter your choice:");
        System.out.print("> ");
        String choice = scan.nextLine();

        switch (choice) {
            case "1":
                Login();
                break;
            case "2":
                Register();
                break;
            case "3":
                Guest();
                break;
            case "0":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private void userChatChoice( String receiverUsername, String senderUsername ) throws IOException {
        // TODO CHECK ALL ACTIVE USERS TO CHOICE USER TO CHAT
        // TODO: Implement logic to check all active users and allow the user to choose who to chat with

        if (senderUsername.equals(receiverUsername)) {
            System.out.println("You can't chat with yourself.");
            return;
        }

        if (clientHandlers.isEmpty()) {
            System.out.println("There are no active users.");
            return;
        }

        System.out.println("Active users:");
        for (ClientHandler clientHandler : clientHandlers) {
            if (!clientHandler.getClientUsername().equals(senderUsername)) {
                clientHandler.bufferedWriter.write(senderUsername);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            }
        }

        System.out.println("Enter the username of the user you want to chat with:");
        System.out.println("Type /exit to exit.");
        System.out.print(">");

    }


    public static void Register() {

        String [] symbols = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", "{", "}", "[", "]", "|", "\\", ":", ";", "'", "\"", ",", "<", ".", ">", "/", "?", " "};
        String [] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        while (true) {
            System.out.println("Register a new account.");
            System.out.print("Username: ");
            String username = scan.nextLine();
            System.out.print("Password: ");
            String password = scan.nextLine();
            System.out.println("Confirm password: ");
            String passwordConfirm = scan.nextLine();

            if (password.contains(" ")) {
                System.out.println("Password cannot contain spaces.");
            }

            if (!password.contains(Arrays.toString(symbols))) {
                System.out.println("Password must contain at least one symbol.");
            }

            if (!password.contains(Arrays.toString(numbers))) {
                System.out.println("Password must contain at least one number.");
            }

            if (password.length() < 8) {
                System.out.println("Password must be at least 8 characters long.");
            }

            if (password.equals(passwordConfirm) && !username.isEmpty() && !password.isEmpty()) {
                break;
            }

        }

        // TODO: Create client and redirect to login



    }

    public static void Login() {

        while (true) {
            System.out.println("Login to your account.");
            System.out.print("Username: ");
            String username = scan.nextLine();
            System.out.print("Password: ");
            String password = scan.nextLine();

            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("Username and password cannot be empty.");
                break;
            }

            // TODO: Connect to server checking if username or email exists and the password is correct
//            if (username && password) {
//                System.out.println("✓ Login successful.");
//                break;
//            } else {
//                System.out.println("✕ Login failed try again.");
//            }

            // test login
            System.out.println("✓ Login successful.");
            break;
        }


    }

    public static void Guest() throws IOException {

        while (true) {
            System.out.println("Login to your account.");
            System.out.print("Username: ");
            String username = scan.nextLine();

            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
                break;
            }

            System.out.println("✓ Login successful.");

            Socket socket = new Socket("localhost", 4321);
            Client clientGuest = new Client(socket, username);
            clientGuest.ListenForMessage();
            clientGuest.sendMessage();

            // TODO: Connect to server checking if username or email exists and the password is correct
//            if (username && password) {
//                System.out.println("✓ Login successful.");
//                break;
//            } else {
//                System.out.println("✕ Login failed try again.");
//            }

            // test login
            break;
        }


    }

    public Client(Socket socket, String username, String password) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
            this.password = password;
        } catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
        } catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // TODO: Create client and set login and register with server JPA with PostgreSql



            // TODO: Connect to server checking if username or email exists and the password is correct

            Scanner scan = new Scanner(System.in);
            while (socket.isConnected()) {
                System.out.print("> ");
                String messageToSend = scan.nextLine();
                if (messageToSend.equals("/exit")) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
                }
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void ListenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromClient;
                while (socket.isConnected()) {
                    try {
                        messageFromClient = bufferedReader.readLine();
                        System.out.println(messageFromClient);
                    } catch (Exception e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

