package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import pacotes.FactoryPackets;
import pacotes.PacoteA;
import pacotes.PacoteH;
import pacotes.PacoteIC;
import pacotes.PacoteSS;
import pacotes.PacoteW;

public class Client_TCP {
	private String host;
	private int porta;
	private int porta_UDP;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;
	private boolean isConnected = false;
	private Socket cliente = null;
	public Client_TCP(String host, int porta, int porta_UDP) throws UnknownHostException, IOException {
		this.host = host;
		this.porta = porta;
		this.porta_UDP = porta_UDP;
		this.cliente =new Socket(this.host, this.porta);
		outputStream = new ObjectOutputStream(cliente.getOutputStream());
		inputStream = new ObjectInputStream(cliente.getInputStream());
	}

	public void executa() throws ClassNotFoundException, UnknownHostException, IOException {
		Scanner sc = new Scanner(System.in);
		String s;
		while (!cliente.isClosed()) {
			try {
				s = sc.next();
				if(s.equals("q"))
					break;
				Object packet = FactoryPackets.getPacket(s);
				if (packet == null) {
					break;
				}
				if (packet.getClass() == PacoteH.class) {
					PacoteH ph = (PacoteH) packet;
					ph.setUdpPort((short) this.porta_UDP);
					outputStream.writeObject(ph);
					Object a = inputStream.readObject();
					if (a.getClass() == PacoteW.class) {
						PacoteW pw = (PacoteW) a;
						System.out.println("Estações disponiveis");
						for (int i = 0; i < (pw.getNumStations() & 0xFFFF); i++) {
							System.out.println("Estação " + i);
						}
					}else{
						PacoteIC pic = (PacoteIC) packet;
						System.out.println(new String(pic.getReplyString()));
						break;
					}
				} else if (packet.getClass() == PacoteSS.class) {
					PacoteSS pss = (PacoteSS) packet;
					outputStream.writeObject(pss);
					Object a = inputStream.readObject();
					if(a.getClass()==PacoteA.class) {
						PacoteA pa = (PacoteA) a;
						System.out.println("Nome da musica:" + new String(pa.getSongname()));						
					}else {
						PacoteIC pic = (PacoteIC) packet;
						System.out.println(new String(pic.getReplyString()));
						break;
					}
					
				}
				outputStream.flush();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				break;
			}
		}
		outputStream.close();
		inputStream.close();
		cliente.close();
		sc.close();
		System.out.println("Cliente encerrado");
	}

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		String host = "127.0.0.1";
		int porta_servidor = 12345;
		int porta_udp = 10535;
		Scanner sc = new Scanner(System.in);
		if (args.length == 3) {
			host = args[0];
			porta_servidor = Integer.parseInt(args[1]);
			porta_udp = Integer.parseInt(args[2]);
		}else {
			host = sc.next();
			porta_servidor = Integer.parseInt(sc.next());
			porta_udp = Integer.parseInt(sc.next());
		}
		new Client_TCP(host, porta_servidor, porta_udp).executa();
	}
}
