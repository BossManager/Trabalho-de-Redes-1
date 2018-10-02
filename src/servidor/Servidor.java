package servidor;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cliente.Cliente;


public class Servidor {
	private List<Cliente> clientes;
	private int porta;
	private ArrayList<LinkedList<byte[]>> musicas;
	private String[] songnameList;
	private List<Socket> socketsClientes;
	private ServerSocket servidor;
	public Servidor(int i, String[] musicas) {
		this.porta = i;
		this.clientes = new ArrayList<>();
		this.musicas = new ArrayList<>();
		this.socketsClientes = new ArrayList<>();
		this.songnameList = musicas;
		this.loadMusicFiles();
		TrataMusica tm = new TrataMusica(this.clientes,this.musicas,this);
		new Thread(tm).start();
		ServidorPrompt sp = new ServidorPrompt(this);
		new Thread(sp).start();
	}
	
	private void loadMusicFiles() {
		File f;
		byte[] byteslist;
		for(String s:songnameList) {
			f = new File("musicas/"+s);
			try {
				byteslist = Files.readAllBytes(f.toPath());
				LinkedList<byte[]> ll = new LinkedList<>();
				preencherLinkedList(ll,byteslist);
				this.musicas.add(ll);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void preencherLinkedList(LinkedList<byte[]> ll, byte[] byteslist) {
		for(int i = 0;i<byteslist.length;i+=16000) {
			byte[] parte = Arrays.copyOfRange(byteslist,i, Math.min(byteslist.length, i+16000));
			ll.add(parte);
		}
		
	}
	
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	public void executa() throws IOException {
		servidor = new ServerSocket(this.porta);
		
		while(true) {
			Socket cliente = servidor.accept();
			this.socketsClientes.add(cliente);
			ObjectInputStream ins = new ObjectInputStream(cliente.getInputStream());
			ObjectOutputStream ous = new ObjectOutputStream(cliente.getOutputStream());
			Cliente c = new Cliente(0, cliente.getInetAddress());
			this.clientes.add(c);
			TrataCliente tc = new TrataCliente(ins,ous,cliente,this,c);
			new Thread(tc).start();
		}
	}

	public static void main(String[] args) throws IOException {
		String[] musicas;
		if(args.length>0) {
			musicas = args;
		}else {
			Scanner sc = new Scanner(System.in);
			System.out.println("Numero de estaçoes?");
			int n = sc.nextInt();
			musicas = new String[n];
			sc = new Scanner(System.in);
			for(int i = 0;i<n;i++) {
				musicas[i] = sc.nextLine();
			}
		}
		new Servidor(12345,musicas).executa();
	}

	public short getNumberOfEstations() {
		return (short) this.musicas.size();
	}

	public byte getSongNameSize(int station) {
		return (byte) (songnameList[station].length()-4);
	}

	public char[] getSongName(int station) {
		return songnameList[station].toCharArray();
	}

	public boolean betweenBandsStation(short stationNumber) {
		int sn = stationNumber & 0xFFFF;
		if(sn>=0 && sn<this.musicas.size())
			return true;
		return false;
	}

	public void removeClient(int udp_port) {
		int i = 0;
		for (Cliente p: clientes) {
			if(p.getUdp_port()==udp_port)
				break;
			i++;
		}
		clientes.remove(i);
		
	}

	public void showClientsPerStation() {
		for(int i = 0;i<this.musicas.size();i++) {
			System.out.println("Estacao "+i+":");
			for(Cliente c:clientes) {
				if(c.getStation()==i)
					System.out.println("   cliente "+c.getUdp_port());
			}
		}
		
	}

	public void theEnd() {
		for(int i = 0;i<clientes.size();i++) {
			this.removeClient(clientes.get(i).getUdp_port());
		}
		try {
			servidor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//
		}
		System.exit(0);
	}
}
