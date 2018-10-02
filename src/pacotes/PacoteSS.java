package pacotes;

import java.io.Serializable;

public class PacoteSS implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte commandType;
	private short stationNumber;
	public PacoteSS(byte commandType, short stationNumber) {
		this.commandType = commandType;
		this.stationNumber = stationNumber;
	}
	public short getStationNumber() {
		return stationNumber;
	}
	public void setStationNumber(short stationNumber) {
		this.stationNumber = stationNumber;
	}
}
