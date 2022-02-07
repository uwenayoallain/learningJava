import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UDPServer{
    public static void main(String[] args){
        try {
                DatagramSocket serverSocket = new DatagramSocket(7000);
            while(true) {
                Scanner scan = new Scanner(System.in);
                byte[] buffer = new byte[1024];
                DatagramPacket dprequest = new DatagramPacket(buffer,buffer.length);
                serverSocket.receive(dprequest);
                String requestString = new String(dprequest.getData(),dprequest.getOffset(),dprequest.getLength());
                System.out.println("client said : "+requestString);
                //sending response to the client
                String response = scan.nextLine();
                byte[] responseByte = response.getBytes();
                DatagramPacket dpresponse = new DatagramPacket(responseByte,responseByte.length,dprequest.getAddress(),dprequest.getPort());
                serverSocket.send(dpresponse);

            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
