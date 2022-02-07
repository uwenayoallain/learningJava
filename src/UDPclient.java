import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPclient{
    public static void main(String[] args){
        try {
            //client response
            DatagramSocket ds = new DatagramSocket();
            while(true) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter a message: ");
                String requestString = sc.nextLine();
                //send message
                byte[] requestBytes = requestString.getBytes();
                InetAddress ip = InetAddress.getByName("localhost");
                DatagramPacket dp = new DatagramPacket(requestBytes, requestBytes.length, ip, 7000);
                ds.send(dp);
                //receive response
                byte[] responseBytes = new byte[1000];
                DatagramPacket dp2 = new DatagramPacket(responseBytes, responseBytes.length);
                ds.receive(dp2);
                String responseString = new String(dp2.getData(),dp2.getOffset(),dp2.getLength());
                System.out.println("Server: " + responseString);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

