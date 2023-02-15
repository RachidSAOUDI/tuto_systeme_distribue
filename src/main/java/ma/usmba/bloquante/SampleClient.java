package ma.usmba.bloquante;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SampleClient {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("localhost", 1234);
        InputStream inputStream=socket.getInputStream();
        OutputStream outputStream=socket.getOutputStream();
        Scanner scanner=new Scanner(System.in);
        System.out.println("Nombre :");
        int nombre = scanner.nextInt();
        outputStream.write(nombre);
        int response = inputStream.read();
        System.out.println("Response="+response);
    }
}
