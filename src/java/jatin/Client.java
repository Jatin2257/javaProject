package jatin;



// For Client Side Program


import java.io.*;
import java.net.*;
public class Client {
    public static void main(String[] args) {
        try {
            // Establish socket connection with server application
            Socket socket = new Socket("localhost", 12345);

            // Send search string to server application
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            
            writer.println("Java");

            // Receive matching results from server application
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Close socket connection
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
