package pacotes;

public class FactoryPackets {

	public static Object getPacket(String s) {
		if(s.equals("hello")) {
			return new PacoteH((byte)0,(short)0);
		}else if(s.equals("InvalidCommand")){
			return new PacoteIC((byte)0, null);
		}else if(s.equals("Welcome")){
			return new PacoteW((byte)0, (short)0);
		}else if(s.equals("Announce")){
			return new PacoteA((byte)0, null);
		}else {
		
			try {
				int num = Integer.parseInt(s);
				return new PacoteSS((byte)1, (short) num);
			}catch(NumberFormatException e) {
				return null;
			}
		}
	}
	
	
}
