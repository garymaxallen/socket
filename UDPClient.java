import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        createClient2();
    }

    public static void createClient2() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            System.out.println("Client started");

            while (true) {
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                byte[] sendBuf = new byte[1024];
                String sentence = input.readLine();
                sendBuf = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length,
                        InetAddress.getByName("localhost"), 5000);
                socket.send(sendPacket);
                if (sentence.equals("bye")){
                    break;
                }

                byte[] receiveBuf = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);
                socket.receive(receivePacket);
                String modifiedSentence = new String(receivePacket.getData());
                System.out.println("FROM SERVER:" + modifiedSentence);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            socket.close();
        }
    }

    public static void createClient() {
        Scanner sc = null;
        DatagramSocket socket = null;
        try {
            sc = new Scanner(System.in);
            socket = new DatagramSocket();
            byte buf[] = null;

            while (true) {
                String input = sc.nextLine();

                buf = input.getBytes();
                socket.send(new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), 5000));

                // break the loop if user enters "bye"
                if (input.equals("bye"))
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {

        }

    }
}