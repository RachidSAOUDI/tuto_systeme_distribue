package ma.usmba.bloquante;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadTchatServer extends Thread {
    private List<Conversation> conversations=new ArrayList<>();

    int clientsCount;
    public static void main(String[] args) {
        new MultiThreadTchatServer().start();
    }

    @Override
    public void run() {
        System.out.println("Server running in port : 1234");
        try {
            ServerSocket serverSocket=new ServerSocket(1234);
            while (true){
                Socket socket = serverSocket.accept();
                ++clientsCount;
                Conversation conversation = new Conversation(socket, clientsCount);
                conversations.add(conversation);
                conversation.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
     }

     public void broadcastMessage(String message, Conversation convFrom){
         try {
             for (Conversation conversation:conversations){
                 if (conversation!=convFrom){
                     Socket socket=conversation.socket;
                     OutputStream outputStream=socket.getOutputStream();
                     PrintWriter printWriter=new PrintWriter(outputStream,true);
                     printWriter.println(message);
                 }
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
                    System.out.println("New Request IP :"+ip+" Request= "+request);
                    broadcastMessage(request,this);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
