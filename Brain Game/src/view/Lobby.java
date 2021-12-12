package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.Controller;
import model.Player;

public class Lobby{
	BufferedImage img;
	JFrame frame;
	JLabel name, backGround, menu, welcome; 
	ImageIcon bg, icon;
	JLayeredPane layeredPane = new JLayeredPane();
	JButton start, rank, editName, saveName;
	static String ingame = null;
	static boolean firstTime = false, getData = false;
	static ArrayList<Player> list;
	static Ranking ranking;
	Controller controller = new Controller();
	JTextField tf;
	
	public Lobby(){
		getPicture("/pictures/bg.jpg");
		bg = new ImageIcon(img);
		getPicture("/pictures/3.png");
		icon = new ImageIcon(img);
		
		if(getData == false) {
			list = controller.getRecordsFromFile();
			ranking = new Ranking();
			for (Player player : list) {
				Object[] data = {player.getIngame(), player.getLevel(), player.getTimeUsed()};
				ranking.addOldData(data);
			}
			getData = true;
		}
		name = new JLabel();
		name.setText("MATH GAME");
		name.setForeground(Color.RED);
		name.setFont(new Font("MV Boli", Font.BOLD, 100));
		name.setBounds(50, 70, 700, 90);
		
		backGround = new JLabel();
		backGround.setIcon(bg);
		backGround.setBounds(0, 0, 1000, 700);
		
		menu = new JLabel();
		menu.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
		menu.setOpaque(true);
		menu.setBackground(Color.black);
		menu.setBounds(100, 200, 600, 400);
		
		start = new JButton();
		start.setText("NEW GAME");
		start.setBounds(300, 400, 200, 50);
		start.setFocusable(false);
		start.addActionListener(e->{
			frame.dispose();
			Play play = new Play(ingame, ranking);
		});
		
		editName = new JButton();
		editName.setText("EDIT");
		editName.setBounds(320, 300, 150, 50);
		editName.setFocusable(false);
		editName.addActionListener(e->{
			editName.setEnabled(false);
			tf.setEditable(true);
			saveName.setEnabled(true);
		});
		
		saveName = new JButton();
		saveName.setEnabled(false);
		saveName.setText("SAVE");
		saveName.setBounds(500, 300, 150, 50);
		saveName.setFocusable(false);
		saveName.addActionListener(e->{
			tf.setEditable(false);
			editName.setEnabled(true);
			saveName.setEnabled(false);
			ingame = tf.getText();
		});
		
		rank = new JButton();
		rank.setText("TOP 10");
		rank.setBounds(300, 500, 200, 50);
		rank.setFocusable(false);
		rank.addActionListener(e->{
			ranking.visibility();
		});
		
		welcome = new JLabel();
		welcome.setForeground(Color.GREEN);
		welcome.setFont(new Font("MV Boli", Font.PLAIN, 25));
		welcome.setBounds(120, 220, 150, 50);
		welcome.setText("WELCOME:");
		
		tf = new JTextField();
		tf.setBounds(280, 220, 400, 50);
		tf.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		tf.setBackground(Color.black);
		tf.setForeground(Color.GREEN);
		tf.setFont(new Font("MV Boli", Font.PLAIN, 25));
		tf.setEditable(false);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1000, 700);
		layeredPane.add(backGround, Integer.valueOf(0));
		layeredPane.add(menu, Integer.valueOf(1));
		layeredPane.add(start, Integer.valueOf(2));
		layeredPane.add(rank, Integer.valueOf(2));
		layeredPane.add(name, Integer.valueOf(4));
		layeredPane.add(welcome, Integer.valueOf(5));
		layeredPane.add(tf, Integer.valueOf(6));
		layeredPane.add(editName, Integer.valueOf(7));
		layeredPane.add(saveName, Integer.valueOf(8));
		
		frame = new JFrame("MATH GAME");
		frame.setSize(1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setIconImage(new ImageIcon("3.png").getImage());
		frame.setResizable(false);
		frame.add(layeredPane);
		frame.setVisible(true);
		
		getIngame();
	}
	
	void getIngame() {
		if(firstTime == false) {
			ingame = (String) JOptionPane.showInputDialog(null, null, "ENTER INGAME", JOptionPane.INFORMATION_MESSAGE, null, null, "NewPlayer");
			try {
				if(!ingame.equals(null)) {				
					
				}
			} catch (Exception e) {
				ingame = "NewPLayer";
			}
			firstTime = true;
			tf.setText(ingame);
		}else {
			tf.setText(ingame);
		}
	}
	
	void getPicture(String name) {
		try {
			img = ImageIO.read(getClass().getResource(name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
