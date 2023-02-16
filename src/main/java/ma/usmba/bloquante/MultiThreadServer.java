package ma.usmba.bloquante;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer extends Thread {
    int clientsCount;
    public static void main(String[] args) {
        new MultiThreadServer().start();
    }

    @Override
    public void run() {
        System.out.println("Server running in port : 1234");
        try {
            ServerSocket serverSocket=new ServerSocket(1234);
            while (true){
                Socket socket = serverSocket.accept();
                ++clientsCount;
                new Conversation(socket, clientsCount).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
     }

    class Conversation extends Thread {
        private Socket socket;
        public int clientId;
        public Conversation(Socket socket, int clientId){
            this.socket = socket;
            this.clientId = clientId;
        }
        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter printWriter=new PrintWriter(outputStream,true);
                String ip = socket.getRemoteSocketAddress().toString();
                System.out.println("New Client Connection => "+clientId+" IP :"+ip);
                printWriter.println("Welcome Client ID : "+clientId);
                String request;
                while ((request=bufferedReader.readLine())!=null){
//                while (true){
//                    String request=bufferedReader.readLine();
                    System.out.println("New Request IP :"+ip+" Request= "+request);
                    String response="Size = "+request.length();
                    printWriter.println(response);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
