package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import model.Player;

public class Controller {
	private FileWriter fw;
	private BufferedWriter bw;
	private PrintWriter pw;
	private Scanner sc;
	private String fileName = "top10best.txt";
	
	public void openFileToWrite() {
		try {
			fw = new FileWriter(fileName, true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeFileAfterWriting() {
		try {
			pw.close();
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void openFileToRead() {
		try {
			sc = new Scanner(Paths.get(fileName), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeFileAfterReading() {
		try {
			sc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writePlayerToFile(Player player) {
		File file = new File(fileName);
		openFileToWrite();
		pw.println(player.getIngame()+"|"+player.getLevel()+"|"+player.getTimeUsed());
		closeFileAfterWriting();
	}
	
	public void writePlayersToFile(ArrayList<Player> players) {
		File file = new File(fileName);
		FileWriter fw = null;
		try{
			fw = new FileWriter(file, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		openFileToWrite();
		for (Player player: players) {
			pw.println(player.getIngame()+"|"+player.getLevel()+"|"+player.getTimeUsed());
		}
		closeFileAfterWriting();
	}
	
	public ArrayList<Player> getRecordsFromFile() {
		ArrayList<Player> list = new ArrayList<Player>();
		File file = new File(fileName);
		if(file.length() > 0) {
			openFileToRead();
			while(sc.hasNextLine()) {
				String data = sc.nextLine();
				Player player = getPlayerFromFile(data);
				list.add(player);
			}
			closeFileAfterReading();
		}
		return list;
	}
	
	public Player getPlayerFromFile(String data) {
		String[] datas = data.split("\\|");
		return new Player(datas[0], Integer.parseInt(datas[1]), Integer.parseInt(datas[2]));
	}

}
	

