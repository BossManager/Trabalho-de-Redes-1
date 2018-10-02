package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cliente.Cliente;
import pacotes.FactoryPackets;
import pacotes.PacoteA;
import pacotes.PacoteH;
import pacotes.PacoteIC;
import pacotes.PacoteSS;
import pacotes.PacoteW;

public class TrataCliente implements Runnable{
	private ObjectInputStream ins;
	private ObjectOutputStream ous;
	private Socket cliente;
	private Servidor servidor;
	private Cliente c;
	public TrataCliente(ObjectInputStream ins, ObjectOutputStream ous, Socket cliente, Servidor servidor, Cliente c) {
		this.ins = ins;
		this.ous = ous;
		this.cliente = cliente;
		this.servidor = servidor;
		this.c = c;
	}

	@Override
	public void run() {
		
		while(!this.cliente.isClosed()) {
			try {
				Object obj = ins.readObject();
				if(obj.getClass()==PacoteH.class) {
					PacoteH ph = (PacoteH) obj;
					System.out.println("Comando recebido:Hello");
					if(this.c.isHello()) {
						Object i = FactoryPackets.getPacket("InvalidCommand");
						PacoteIC pic = (PacoteIC) i;
						String resp = "Comando hello ja enviado";
						pic.setReplyTypeStringSize((byte)resp.length());
						pic.setReplyString(resp.toCharArray());
						ous.writeObject(pic);
						System.out.println("Comando enviado:InvalidCommand");
						servidor.removeClient(c.getUdp_port());
					}else {
						Object i = FactoryPackets.getPacket("Welcome");
						PacoteW pw = (PacoteW) i;
						pw.setNumStations(servidor.getNumberOfEstations());
						ous.writeObject(pw);
						System.out.println("Comando enviado:Welcome");
						c.setHello(true);
						c.setUdp_port(ph.getUdpPort());
					}
				}else if(obj.getClass()==PacoteSS.class) {
					PacoteSS pss = (PacoteSS) obj;
					System.out.println("Comando recebido:SetStation");
					if(servidor.betweenBandsStation(pss.getStationNumber())) {
						c.setStation(pss.getStationNumber() & 0xFFFF);
						Object i =  FactoryPackets.getPacket("Announce");
						PacoteA pa = (PacoteA)i;
						pa.setSongnameSize(servidor.getSongNameSize(c.getStation()));
						pa.setSongname(servidor.getSongName(c.getStation()));
						ous.writeObject(pa);
						System.out.println("Comando enviado:Announce");
					}else {
						Object i = FactoryPackets.getPacket("InvalidCommand");
						PacoteIC pic = (PacoteIC) i;
						String resp = "Fora da faixa de estacoes";
						pic.setReplyTypeStringSize((byte)resp.length());
						pic.setReplyString(resp.toCharArray());
						ous.writeObject(pic);
						System.out.println("Comando enviado:InvalidCommand");
						servidor.removeClient(c.getUdp_port());
					}
				}
				ous.flush();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				break; //medida de prevenção de erros avestruz
			} 
		}
		try {
			ous.close();
			ins.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		servidor.removeClient(c.getUdp_port());
	}

}
