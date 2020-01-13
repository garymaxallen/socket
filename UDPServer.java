import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) {
        createServer2();

    }

    public static void createServer2() {
        DatagramSocket socket = null;
        try {
            int port = 49152;
            socket = new DatagramSocket(port);
            System.out.println("Server Started and listening to the port " + port);
            while (true) {
                byte[] receiveBuf = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);
                socket.receive(receivePacket);
                String sentence = new String(receivePacket.getData()).trim();
                System.out.println("RECEIVED: " + sentence);
                if(sentence.equals("bye")){
                    break;
                }
                
                byte[] sendBuf = new byte[1024];
                //sendBuf = sentence.toUpperCase().getBytes();
                String str = "xxxxxxxxx";
                sendBuf = str.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            socket.close();
        }
    }

    public static void createServer() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(5000);
            System.out.println("Server Started and listening to the port 5000");
            byte[] buf = new byte[65535];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String msg = new String(packet.getData()).trim();
                System.out.println("Client: " + msg);

                if (msg.equals("bye")) {
                    System.out.println("Client sent bye.....EXITING");
                    break;
                }

                // Clear the buffer after every message.
                buf = new byte[65535];
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            socket.close();
        }
    }
}