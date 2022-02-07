import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;


public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true){
            serverSocket.setSoTimeout(100000);
            Socket socket = serverSocket.accept();
            InputStream inFromclient = socket.getInputStream();
            DataInputStream request = new DataInputStream(inFromclient);
            System.out.println("Server: " + request.readUTF());
            OutputStream outToClient = socket.getOutputStream();
            DataOutputStream response = new DataOutputStream(outToClient);
            response.writeUTF("The request well received "+ LocalDateTime.now());
            socket.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
