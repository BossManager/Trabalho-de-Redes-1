package cliente;

import java.net.InetAddress;

public class Cliente {
	private int udp_port;
	private int  station;
	private InetAddress ia;
	private boolean hello;
	public Cliente(int udp_port, InetAddress inetAddress) {
		super();
		this.udp_port = udp_port;
		station = -1;
		ia = inetAddress;
		setHello(false);
	}
	public InetAddress getIa() {
		return ia;
	}
	public void setIa(InetAddress ia) {
		this.ia = ia;
	}
	public int getUdp_port() {
		return udp_port;
	}
	public void setUdp_port(int udp_port) {
		this.udp_port = udp_port;
	}
	public int getStation() {
		return station;
	}
	public void setStation(int station) {
		this.station = station;
	}
	public boolean isHello() {
		return hello;
	}
	public void setHello(boolean hello) {
		this.hello = hello;
	}
	
	
}
