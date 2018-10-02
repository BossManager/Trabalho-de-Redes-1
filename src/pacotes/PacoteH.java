package pacotes;

import java.io.Serializable;

public class PacoteH implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte commandType;
	private short udpPort;
	public PacoteH(byte commandType,short udpPort) {
		this.commandType = commandType;
		this.udpPort = udpPort;
	}
	public void printAtributtes() {
		System.out.println("com sinal:"+commandType);
		System.out.println("sem sinal:"+(commandType & 0xFF));
		System.out.println("com sinal:"+udpPort);
		System.out.println("sem sinal:"+(udpPort & 0xFFFF));
	}
	public static void main(String[] args) {
		PacoteH p = new PacoteH((byte)50,(short)50000);
		p.printAtributtes();
		
	}
	public byte getCommandType() {
		return commandType;
	}
	public void setCommandType(byte commandType) {
		this.commandType = commandType;
	}
	public short getUdpPort() {
		return udpPort;
	}
	public void setUdpPort(short udpPort) {
		this.udpPort = udpPort;
	}
	

}
