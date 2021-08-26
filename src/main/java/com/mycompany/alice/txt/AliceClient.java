/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.alice.txt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author K00241356
 */
public class AliceClient {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		try {

			Scanner scan = new Scanner(System.in);
			Socket connectToServer = new Socket("localhost", 8081);

			DataInputStream ReFromServer = new DataInputStream(connectToServer.getInputStream());
			DataOutputStream SeToServer = new DataOutputStream(connectToServer.getOutputStream());

			while (true) {

				System.out.println("Enter chapter number :");
				int chapternumber = scan.nextInt();

				SeToServer.writeInt(chapternumber);
				SeToServer.flush();
				
				 String chapter = ReFromServer.readUTF();
                
                System.out.println(chapter);
			}

		} catch (IOException ex) {
			System.err.println(ex);

		}

	}

}
