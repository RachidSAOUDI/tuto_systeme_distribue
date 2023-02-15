package ma.usmba.bloquante;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SampleServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(1234);
        System.out.println("Waiting for new connection");
        Socket socket = serverSocket.accept();
        InputStream inputStream=socket.getInputStream();
        OutputStream outputStream=socket.getOutputStream();
        System.out.println("Waiting for data");
        int nombre = inputStream.read();
        System.out.println("send response");
        int response = nombre*23;
        outputStream.write(response); //send result
        socket.close();
    }
}
