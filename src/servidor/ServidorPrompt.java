package servidor;

import java.util.Scanner;

public class ServidorPrompt implements Runnable{
	private Servidor server = null;
	
	public ServidorPrompt(Servidor servidor) {
		this.server = servidor;
	}
	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		String s;
		while(sc.hasNext()) {
			s = sc.next();
			if(s.equals("p")) {
				server.showClientsPerStation();
			}else if(s.equals("q")) {
				server.theEnd();
			}
		}
	}

}
