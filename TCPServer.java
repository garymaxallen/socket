import java.net.*;
import java.util.Arrays;
import java.io.*;

public class TCPServer {
    public static void main(String args[]) {
        // String port = args[0];
        // receiveData(Integer.valueOf(port));
        createServer1();
    }

    public static void createServer1() {
        Socket socket = null;
        ServerSocket serverSocket = null;
        int port = 49152;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port " + port);
            while (true) {
                socket = serverSocket.accept();
                OutputStream out = new DataOutputStream(socket.getOutputStream());
                byte[] byteArray = { (byte) 0, (byte) 0, (byte) 0, (byte) 13, (byte) 192, (byte) 168, (byte) 110,
                        (byte) 11, (byte) -64, (byte) 0, (byte) 17, (byte) 1, (byte) 15, (byte) 255, (byte) 23,
                        (byte) 112, (byte) -128 };
                out.write(byteArray);
                String str1 = Arrays.toString(byteArray);
                System.out.println("Sent to client: " + str1);

                InputStream is = socket.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                while (true) {
                    String data = "";
                    int s = bis.read();
                    if (s == -1) {
                        break;
                    }
                    data += "" + (char) s;
                    int len = bis.available();
                    if (len > 0) {
                        byte[] byteData = new byte[len];
                        bis.read(byteData);
                        data += new String(byteData);

                        System.out.print("byte array received from the client: ");
                        System.out.printf("%02x ", s);
                        for (byte b : byteData) {
                            System.out.printf("%02x ", b);
                        }
                        System.out.println();
                    }
                    System.out.println("Message received from the client: " + data);
                }
            }
        } catch (Exception e) {
        }
    }

    public static void createServer() {
        Socket socket = null;
        ServerSocket serverSocket = null;
        DatagramSocket udpSocket = null;
        try {
            int port = 49152;
            serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port " + port);
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Client Accepted, Client address is:" + socket.getInetAddress().getHostAddress()
                        + ":" + socket.getPort());
                OutputStream out = new DataOutputStream(socket.getOutputStream());
                byte[] arr = { (byte) 0, (byte) 0, (byte) 0, (byte) 13, (byte) 192, (byte) 168, (byte) 110, (byte) 11,
                        (byte) -64, (byte) 0, (byte) 17, (byte) 1, (byte) 15, (byte) 255, (byte) 23, (byte) 112,
                        (byte) -128 };
                out.write(arr);
                String req = Arrays.toString(arr);
                System.out.println("Sent to client : " + req);

                InputStream is = socket.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                while (true) {
                    String data = "";
                    int s = bis.read();
                    if (s == -1) {
                        break;
                    }
                    data += "" + (char) s;
                    int len = bis.available();
                    // System.out.println("Len got : " + len);
                    if (len > 0) {
                        byte[] byteData = new byte[len];
                        bis.read(byteData);
                        data += new String(byteData);
                    }
                    System.out.println("Message received from the client : " + data);

                    if (data.trim().equals("E")) {
                        System.out.println("Message sent is wrong, resend");
                        byte[] arr2 = { (byte) 0, (byte) 0, (byte) 0, (byte) 13, (byte) 192, (byte) 168, (byte) 110,
                                (byte) 11, (byte) -64, (byte) 0, (byte) 7, (byte) 1, (byte) 15, (byte) 255, (byte) 23,
                                (byte) 112, (byte) -128 };
                        out.write(arr2);
                        req = Arrays.toString(arr2);
                        System.out.println("Sent to client : " + req);
                    } else if (data.trim().equals("S")) {
                        // System.out.println("send udp server message");
                        // byte[] arr2 = { (byte) 192, (byte) 168, (byte) 110, (byte) 11, (byte) -64,
                        // (byte) 0 };
                        // out.write(arr2);
                        // req = Arrays.toString(arr2);
                        // System.out.println("Sent to client : " + req);
                        udpSocket = new DatagramSocket();
                        byte[] sendBuf = new byte[1024];
                        String str = "xxxxx";
                        sendBuf = str.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length,
                                InetAddress.getByName("192.168.22.103"), 61556);
                        while (true) {
                            udpSocket.send(sendPacket);
                        }
                        // udpSocket.close();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void receiveData(int port) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            // int port = 49152;
            serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port " + port);
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Client Accepted, Client address is:" + socket.getInetAddress().getHostAddress()
                        + ":" + socket.getPort());

                InputStream is = socket.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);

                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osr = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osr);

                Thread t = new ClientHandler(socket, bis, bw);
                t.start();
            }
        } catch (Exception e) {

        }
    }
}

class ClientHandler extends Thread {
    final Socket socket;
    final BufferedInputStream bis;
    final BufferedWriter bw;

    public ClientHandler(Socket socket, BufferedInputStream bis, BufferedWriter bw) {
        this.socket = socket;
        this.bis = bis;
        this.bw = bw;
    }

    public void run() {
        try {
            int i = 0;
            while (true) {
                String data = "";
                int s = bis.read();
                if (s == -1) {
                    break;
                }
                data += "" + (char) s;
                int len = bis.available();
                // System.out.println("Len got : " + len);
                if (len > 0) {
                    byte[] byteData = new byte[len];
                    bis.read(byteData);
                    data += new String(byteData);
                }
                System.out.println("Message " + i++ + " received from the client : " + data);

                bw.write(data);
                bw.flush();
            }
        } catch (Exception e) {
        }
    }
}