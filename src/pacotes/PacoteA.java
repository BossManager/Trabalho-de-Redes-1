package pacotes;

import java.io.Serializable;

public class PacoteA implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte replyType;
	private byte songnameSize;
	private char[] songname;
	
	public PacoteA(byte songnameSize,char[] songname) {
		this.replyType = 1;
		this.songnameSize = songnameSize;
		this.songname = songname;
	}

	public byte getSongnameSize() {
		return songnameSize;
	}

	public void setSongnameSize(byte songnameSize) {
		this.songnameSize = songnameSize;
	}

	public char[] getSongname() {
		return songname;
	}

	public void setSongname(char[] songname) {
		this.songname = songname;
	}
	
}
