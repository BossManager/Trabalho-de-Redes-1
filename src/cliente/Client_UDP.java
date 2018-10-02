package cliente;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client_UDP {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int porta_udp = sc.nextInt();
		byte[] receiveData = new byte[16000];
		try {
			DatagramSocket clientSocket = new DatagramSocket(porta_udp);
			InetAddress IPAddress = InetAddress.getByName("localhost");
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			MusicPlayer mp3;
			File temp = File.createTempFile("music1", ".mp3");
			FileOutputStream fos = new FileOutputStream(temp.getAbsolutePath());
			int i = 1;
			System.out.println("Iniciando musica");
			while (true) {
				clientSocket.receive(receivePacket);
				fos.write(receivePacket.getData());
				mp3 = new MusicPlayer(temp);
				mp3.play();
				fos.close();
				temp.delete();
				temp = File.createTempFile("music1", ".mp3");
				fos = new FileOutputStream(temp.getAbsolutePath());
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				i++;
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
