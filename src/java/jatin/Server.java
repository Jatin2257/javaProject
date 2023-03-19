package jatin;



//For Server side Program



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.sql.*;
public class Server extends Driver{
    private static final int PORT = 12345; // the port number the server listens on

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server listening on port " + PORT);

            // listen for incoming client connections
            while (true) {
                // accept incoming client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                Connection connection = getConnection();

                // create new thread to handle search request
                Thread thread = new Thread(() -> {
                    try {
                        // handle search request
                         InputStream input = clientSocket.getInputStream();
                         BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        ResultSet resultSet = searchData(connection.createStatement(), reader.readLine());
                        String searchResult = getSearchResult(resultSet);
                        clientSocket.getOutputStream().write(searchResult.getBytes());
                        System.out.println("Search result sent to client.");
                    } catch (Exception e) {
                        System.out.println("Error handling search request: " + e.getMessage());
                    } finally {
                        // close client connection
                        try {
                            clientSocket.close();
                            System.out.println("Client disconnected.");
                        } catch (IOException e) {
                            System.out.println("Error closing client connection: " + e.getMessage());
                        }
                    }
                });
                thread.start();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
