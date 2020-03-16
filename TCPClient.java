import java.net.*;
import java.io.*;

public class TCPClient {

    public static void main(String args[]) {
        String host = args[0];
        String port = args[1];
        String data = args[2];
        // sendData(host, Integer.valueOf(port));
        sendData1(host, Integer.valueOf(port), data);
        // receiveData();
    }

    public static void sendData1(String host, int port, String data) {
        Socket socket;
        try {
            // String host = "192.168.122.107";
            // int port = 6000;
            socket = new Socket(host, port);
            System.out.println("Connected to server");
            // String data = "qqq";
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osr = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osr);
            bw.write(data);
            bw.flush();
            System.out.println("Data sent to server: " + data);

            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            int s = bis.read();
            if (s == -1) {
                System.out.println("No data from the server");
            } else {
                String receivedData = "";
                receivedData += "" + (char) s;
                int len = bis.available();
                if (len > 0) {
                    byte[] byteData = new byte[len];
                    bis.read(byteData);
                    receivedData += new String(byteData);

                    System.out.print("byte array received from the server: ");
                    System.out.printf("%02x ", s);
                    for (byte b : byteData) {
                        System.out.printf("%02x ", b);
                    }
                    System.out.println();
                }
                System.out.println("Data received from the server: " + receivedData);
            }
        } catch (Exception e) {
        }
    }

    public static void receiveData() {
        Socket socket;
        try {
            String host = "localhost";
            int port = 49152;
            socket = new Socket(host, port);
            System.out.println("Connected to server");
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
                }
                System.out.println("Message received from the server: " + data);
            }
        } catch (Exception e) {
        }
    }

    public static void sendData(String host, int port) {
        Socket socket;
        try {
            // String host = "192.168.110.11";
            // int port = 49152;
            socket = new Socket(host, port);
            System.out.println("Connected to server");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            String msg = "xxxxxxxxxxxxx\n";
            int i = 0;
            while (true) {
                i++;
                Thread.sleep(1000);
                bw.write(msg);
                bw.flush();
                System.out.println("Message " + i + " sent to the server is " + msg);

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
                System.out.println("Message " + i + " received from the server : " + data);
            }
        } catch (Exception e) {
        }
    }
}