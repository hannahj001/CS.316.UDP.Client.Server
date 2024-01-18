package clientserverInteraction.udp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClientUDP {
    //type a message in command line
    //create datagramPacket
    //needs to send a message(packet) to the server
    //receive the response and display it to the user
    //datagram means udp packet

    private DatagramSocket socket;

    public ClientUDP()throws SocketException{
        socket = new DatagramSocket(); //requesting socket form os
    }

    //you need to specify the destination, you need and IP, PN of server
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("syntax: ClientUDP <serverIP> <serverPort>");
            return; // this ends the program
        }try {
            InetAddress serverIP = InetAddress.getByName(args[0]);
            int serverPort = Integer.parseInt(args[1]);
            ClientUDP client = new ClientUDP();
            client.service(serverIP,serverPort);
        }catch(SocketException e){
            System.out.println("Socket Error");
        }catch(UnknownHostException e){
            System.out.println("Unknown Host");
        }catch (IOException e){
            System.out.println("IO Exception Error");
        }
    }
    public void service(InetAddress serverIP, int serverPort) throws IOException {
        System.out.printf("Enter message you want to send to %s through %d\n",serverIP.toString(),serverPort);
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        byte [] buffer = message.getBytes();

        //1. create a datagram packet containing message
        DatagramPacket request = new DatagramPacket(buffer,buffer.length,serverIP,serverPort);

        //2. send packet to server
        socket.send(request);

        //3. receive the reply packet from server response
        DatagramPacket response = new DatagramPacket(new byte[request.getLength()], request.getLength());
        socket.receive(response);
        
        //4. display the reply to user
        byte[] responseMessage = response.getData();
        System.out.println(new String((responseMessage)));
    }
}
