/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.alice.txt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author K00241356
 */
public class AliceServer {

  
    public static void main(String[] args) {
        
        System.out.println("Server has started....");
        
        try
        {
            ExecutorService exe = Executors.newCachedThreadPool(); // used to call any threads
            
            ServerSocket socket = new ServerSocket(8081);
            
            int ClientN =1;
            
            while(true)
            {
            Socket ConnectToClient = socket.accept();
                System.out.println("client ="+ ClientN);
                HandleAClient thread = new HandleAClient(ConnectToClient);
                
                exe.submit(thread);
                ClientN++;
            
            }
        }
        
        catch(IOException exe){
        
            System.out.println("Error" + exe);
        
        }
        
        
    }
    
}

class HandleAClient implements Runnable{

private final Socket ConnectToClient;

public HandleAClient(Socket socket){

ConnectToClient = socket;

}
static String ReadFile(String path,Charset encoding) throws IOException{

Path TargetFile = Paths.get("Alice.txt");//targeting alice.txt
byte[] lines = Files.readAllBytes(TargetFile); // read from a file
return new String(lines, encoding);
}

@Override
public void run(){

    try{
    
        DataInputStream REFromClient = new DataInputStream(ConnectToClient.getInputStream());
        DataOutputStream REToClient = new DataOutputStream(ConnectToClient.getOutputStream());
    
        while(true){
        
            
            String book;
            
            int chapternumber = REFromClient.readInt();//taking in a number from the client
            
            book = ReadFile("Alice.txt", Charset.forName("UTF-8"));// calling the readfile function from alice.txt line by line
        
            
            String[] Ch = book.split("CHAPTER");//spliting the text file best on chapter
            
           REToClient.writeUTF(Ch[chapternumber]);//witing the string to the client and sending result.
        }
    
    }
    catch(IOException exe){
        System.out.println("Error" + exe);// error handling
    }
    
    
}

}
