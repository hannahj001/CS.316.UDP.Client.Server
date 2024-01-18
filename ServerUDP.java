package clientserverInteraction.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerUDP {

    private DatagramSocket socket;

    //this is the side that you need to specify the port number
    //port: the port number the server will be running on

    public ServerUDP(int port) throws SocketException{
        socket = new DatagramSocket(port); // this is the request
    }
    // request the socket from OS, always is requested because it is a constructor
    // if you run it multiple time, you will get the same port number each time, and you will create conflicts.

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Syntax: ServerUDP <port>"); // i want my port number to be "..." this is implemented as a string.
            return;
        }
        int port = Integer.parseInt(args[0]);  // changes the string to integer. args[0], is the first argument.
        try {
            ServerUDP server = new ServerUDP(port); // creating the instance here with the port number
            server.service();  // service any user request
        } catch (SocketException ex) {
            System.out.println("Socket Error: " + ex.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        } // you need try and catches because you are accessing bits of hardware.
    }

    private void service() throws IOException{
        while (true){
            //create empty packet
            DatagramPacket request = new DatagramPacket(new byte[1024], 1024); //this is the length

            socket.receive(request);  //this is a blocking call
            //retrieve the character sent by the client
            byte[] buffer = request.getData(); //gets the raw data into a byte array

            //but data into empty packet

            //do not do buffer.toString(), it will alter the message and will give you its memory address, not the message.
            System.out.println(new String((buffer))); //verification, debugging

            //need the clients' info to echo the information. This info is in the headers
            InetAddress clientIP = request.getAddress();
            int clientPort = request.getPort();

            //this is the 'echo' back to the client
            //sending the same thing back.
            DatagramPacket response = new DatagramPacket(buffer,buffer.length,clientIP,clientPort);
            socket.send(response);
        }
    }



}
