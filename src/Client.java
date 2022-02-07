import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            while (true) {
                Socket socket = new Socket("192.168.1.106", 8000);
                OutputStream requestStream = socket.getOutputStream();
                DataOutputStream request = new DataOutputStream(requestStream);
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter your request: ");
                String requestString = scanner.nextLine();
                request.writeUTF(requestString);
                InputStream inputStream = socket.getInputStream();
                DataInputStream response = new DataInputStream(inputStream);
                System.out.println(response.readUTF());
                socket.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
