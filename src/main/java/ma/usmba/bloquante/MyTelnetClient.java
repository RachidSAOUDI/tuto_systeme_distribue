package ma.usmba.bloquante;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MyTelnetClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            InputStream inputStream=socket.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            OutputStream outputStream=socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream,true);
            new Thread(()->{
                try {
                String request;
                while ((request=bufferedReader.readLine())!=null){
                        System.out.println(request);
                }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            Scanner scanner=new Scanner(System.in);
            while (true){
                String request = scanner.nextLine();
                printWriter.println(request);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
