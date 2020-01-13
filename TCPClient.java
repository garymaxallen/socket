import java.net.*;
import java.io.*;

public class TCPClient {

    public static void main(String args[]) {
        String host = args[0];
        String port = args[1];
        sendData(host, Integer.valueOf(port));
    }

    public static void sendData(String host, int port) {
        Socket socket;
        try {
            //String host = "192.168.110.11";
            //int port = 49152;
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