package pacotes;

import java.io.Serializable;

public class PacoteW implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte replyType;
	private short numStations;
	public PacoteW(byte replyType, short numStations) {
		this.replyType = replyType;
		this.numStations = numStations;
	}
	public short getNumStations() {
		return numStations;
	}
	public void setNumStations(short numStations) {
		this.numStations = numStations;
	}
	
}
