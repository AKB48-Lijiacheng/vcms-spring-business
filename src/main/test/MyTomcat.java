import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyTomcat  {
    public static void main(String[] args) throws IOException {
start();

    }

    public static void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(82);
        System.out.println("服务器已经启动=====");
        Socket socket = null;
        byte[] arr = new byte[5024];
        StringBuffer sb = new StringBuffer();
        while (true) {
            try {
                 socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                is.read(arr);
                for (byte b : arr) {
                    System.out.print((char) b);
                }
                OutputStream os = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(os);
                writer.println("HTTP/1.1 201 OK ");
                writer.println("Content-Type: text/html ");
                writer.println("\n\r");
                writer.println("<html>hello </html>");
                writer.flush();
            }
        finally {
          if (socket!=null){
              socket.close();
          }
       }
        }
    }
}
