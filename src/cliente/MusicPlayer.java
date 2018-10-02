package cliente;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicPlayer{
	private File music;
	private Player player;

	public MusicPlayer(File music){
		this.music = music;
	}

	public void play(){
		Scanner sc =new Scanner(System.in);
		try{
			FileInputStream stream = new FileInputStream(music);
			BufferedInputStream buffer = new BufferedInputStream(stream);
			this.player = new Player (buffer);
			player.play();
		}
		catch (Exception e) {
			System.out.println("Erro!");
			e.printStackTrace();
		}
	}

}