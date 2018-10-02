package pacotes;

import java.io.Serializable;

public class PacoteIC implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private byte replyType;
	private byte replyTypeStringSize;
	private char[] replyString;
	public PacoteIC(byte replyTypeStringSize,char[] replyString) {
		setReplyType((byte)2);
		setReplyTypeStringSize(replyTypeStringSize);
		setReplyString(replyString);
	}
	public byte getReplyType() {
		return replyType;
	}
	public void setReplyType(byte replyType) {
		this.replyType = replyType;
	}
	public byte getReplyTypeStringSize() {
		return replyTypeStringSize;
	}
	public void setReplyTypeStringSize(byte replyTypeStringSize) {
		this.replyTypeStringSize = replyTypeStringSize;
	}
	public char[] getReplyString() {
		return replyString;
	}
	public void setReplyString(char[] replyString) {
		this.replyString = replyString;
	}

}
