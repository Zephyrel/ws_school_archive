package CSC294HW1;

import java.io.*;
import java.net.*;

/*
 * 	Author:	Russell Ferguson Jr
 *  Class:	CSC294 Fall 2017
 * 	Title:	CSC294 Network Programming Assignment #1
 */

public class CSC294HW1{
	public static void main(String[] args) throws UnknownHostException{
		
		Socket mySocket = new Socket();
		if(args.length==2) {
			try {
				mySocket = new Socket(args[0], Integer.parseUnsignedInt(args[1]));
				System.out.println("Socket sucessfully initialized.\n");
			} catch (IOException e) {
				System.err.println("IO Error occured. Stopping program.");
				e.printStackTrace();
			}
		}else {
			try {
				mySocket = new Socket("www.missouriwestern.edu", 80);
				System.out.println("Socket sucessfully initialized.\n");
			} catch (IOException e) {
				System.err.println("IO Error occured. Stopping program.");
				e.printStackTrace();
			}
		}
		
		InetAddress host = mySocket.getInetAddress();
		String hostString = host.getHostName() + "\t\t" + host.getHostAddress();
		int port = mySocket.getPort();
		
		System.out.println("Host Name and IP Address:\t"+hostString);
		System.out.println("Port: " + port);
		
		InetAddress localAddress = mySocket.getLocalAddress();
		String localString = localAddress.getHostName() + "\t\t" + localAddress.getHostAddress();
		int localPort = mySocket.getLocalPort();
		
		System.out.println("Local Host Name and Local IP Address:\t"+localString);
		System.out.println("Port: " + port);
		
		try {
			System.out.println("\n\nClosing Socket...");
			mySocket.close();
			System.out.println("Socket closed.");
		} catch (IOException e) {
			System.err.println("IO Error occured. Stopping program.");
			e.printStackTrace();
		}
	}
	
}