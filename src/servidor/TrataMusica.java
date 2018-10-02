package servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cliente.Cliente;


public class TrataMusica implements Runnable {
	private ArrayList<LinkedList<byte[]>> musicas;
	private List<Cliente> clientes;
	private int[] posicao;
	private Servidor servidor;

	public TrataMusica(List<Cliente> clientes, ArrayList<LinkedList<byte[]>> musicas, Servidor servidor) {
		this.clientes = clientes;
		this.musicas = musicas;
		posicao = new int[musicas.size()];
		this.servidor = servidor;
	}

	@Override
	public void run() {
		DatagramSocket dp;
		DatagramPacket sendPacket;
		while (true) {
			for (Cliente c : clientes) {
				if (c.getStation() != -1) {
					try {
						dp = new DatagramSocket(9876);
						byte[] parte = musicas.get(c.getStation()).get(posicao[c.getStation()]);
						sendPacket = new DatagramPacket(parte, parte.length, InetAddress.getByName("localhost"),
								c.getUdp_port());
						dp.send(sendPacket);
						dp.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			atualizarPosicao();
			this.clientes = servidor.getClientes();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void atualizarPosicao() {
		for (LinkedList<byte[]> ll : musicas) {
			if (posicao[musicas.indexOf(ll)] == ll.size() - 1)
				posicao[musicas.indexOf(ll)] = 0;
			else
				posicao[musicas.indexOf(ll)]++;
		}

	}
}
