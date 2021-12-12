package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicProgressBarUI;

import controller.Controller;
import model.Player;
import view.Lobby;

public class Play{
	BufferedImage img;
	JPanel panel;
	JLabel backGround, lv, ques, quiz, status, result, timeUsed;
	JFrame frame;
	JLayeredPane layeredPane;
	ImageIcon bg, vic;
	JProgressBar countDown;
	int level, count, ans = 0, time, best;
	JButton a1, a2, a3, a4, lobby, again;
	DecimalFormat df = new DecimalFormat("#.###");
	boolean win, end = false;
	Thread t1 = null, t2 = null;
	static String name = null;
	static Ranking rank;
	Controller controller = new Controller();

	public Play(String ingame, Ranking ranking) {
		best = 0;
		level = 0;
		getPicture("/pictures/game.gif");
		bg = new ImageIcon(img);
		getPicture("/pictures/win.PNG");
		vic = new ImageIcon(img);
		
		name = ingame;
		rank = ranking;
		a1 = new JButton();
		a1.setBounds(60, 200, 300, 100);
		a1.setFont(new Font("MV Boli", Font.BOLD, 40));
		a1.setFocusable(false);
			
		a2 = new JButton();
		a2.setBounds(380, 200, 300, 100);
		a2.setFont(new Font("MV Boli", Font.BOLD, 40));
		a2.setFocusable(false);
		
		a3 = new JButton();
		a3.setBounds(60, 320, 300, 100);
		a3.setFont(new Font("MV Boli", Font.BOLD, 40));
		a3.setFocusable(false);
		
		a4 = new JButton();
		a4.setBounds(380, 320, 300, 100);
		a4.setFont(new Font("MV Boli", Font.BOLD, 40));
		a4.setFocusable(false);
		
		a1.addActionListener(e->{
			if(ans == 0) {
				best = time;
				randomQuiz();
			}else {
				end = true;
				win = false;
				winOrLose();
			}
		});
		a2.addActionListener(e->{
			if(ans == 1) {
				best = time;
				randomQuiz();
			}else {
				end = true;
				win = false;
				winOrLose();
			}
		});
		a3.addActionListener(e->{
			if(ans == 2) {
				best = time;
				randomQuiz();
			}else {
				end = true;
				win = false;
				winOrLose();
			}
		});
		a4.addActionListener(e->{
			if(ans == 3) {
				best = time;
				randomQuiz();
			}else {
				end = true;
				win = false;
				winOrLose();
			}
		});
		
		lv = new JLabel();
		lv.setForeground(Color.YELLOW);
		lv.setFont(new Font("MV Boli", Font.BOLD, 20));
		lv.setBounds(0, 210, 200, 30);
		lv.setText("Level: "+String.valueOf(level));
		
		timeUsed = new JLabel();
		timeUsed.setText("Time used: "+String.valueOf(time));
		timeUsed.setForeground(Color.RED);
		timeUsed.setFont(new Font("MV Boli", Font.BOLD, 20));
		timeUsed.setBounds(0, 240, 200, 30);
		
		ques = new JLabel();
		ques.setForeground(Color.GREEN);
		ques.setFont(new Font("MV Boli", Font.BOLD, 70));
		ques.setHorizontalAlignment(JLabel.CENTER);
		ques.setVerticalAlignment(JLabel.CENTER);
		ques.setBounds(70, 70, 600, 100);
		
		quiz = new JLabel();
		quiz.setOpaque(true);
		quiz.setBackground(Color.black);
		quiz.setLayout(null);
		quiz.setBorder(BorderFactory.createLineBorder(Color.green, 5));
		quiz.setBounds(210, 100, 750, 500);
		quiz.add(a1);
		quiz.add(a2);
		quiz.add(a3);
		quiz.add(a4);
		quiz.add(lv);
		quiz.add(ques);
		
		lobby = new JButton("LOBBY");
		lobby.setBounds(420, 370, 200, 100);
		lobby.setFont(new Font("MV Boli", Font.BOLD, 40));
		lobby.setFocusable(false);
		lobby.addActionListener(e->{
			frame.dispose();
			Lobby lobby = new Lobby();
		});
		
		again = new JButton("AGAIN");
		again.setBounds(140, 370, 200, 100);
		again.setFont(new Font("MV Boli", Font.BOLD, 40));
		again.setFocusable(false);
		again.addActionListener(e->{
			frame.dispose();
			Play play = new Play(ingame, rank);
		});
		
		result = new JLabel();
		result.setBounds(80, 70, 600, 300);
		result.setForeground(Color.GREEN);
		result.setFont(new Font("MV Boli", Font.BOLD, 50));
		result.setHorizontalAlignment(JLabel.CENTER);
		result.setVerticalAlignment(JLabel.CENTER);
		
		panel = new JPanel();
		panel.setOpaque(true);
		panel.setBackground(Color.black);
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createLineBorder(Color.green, 5));
		panel.setBounds(210, 100, 750, 500);
		panel.add(result);
		panel.add(lobby);
		panel.add(again);
	
		countDown = new JProgressBar();
		countDown.setMaximum(3);
		countDown.setMinimum(0);
		countDown.setStringPainted(true);
		countDown.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
		countDown.setForeground(Color.black);
		countDown.setBackground(Color.black);
		countDown.setUI(new BasicProgressBarUI() {
			protected Color getSelectionForeground() {
				return Color.green;
			}
			protected Color getSelectionBackground() {
				return Color.green;
			}
		});
		countDown.setFont(new Font("MV Boli", Font.BOLD, 300));
		countDown.setBounds(350, 150, 500, 300);
		
		status = new JLabel();
		status.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		status.setBackground(Color.BLACK);
		status.setOpaque(true);
		status.setForeground(Color.GREEN);
		status.setFont(new Font("MV Boli", Font.BOLD, 20));
		status.setText(ingame);
		status.setVerticalAlignment(JLabel.TOP);
		status.setBounds(0, 180, 195, 100);
			
		backGround = new JLabel();
		backGround.setIcon(bg);
		backGround.setBounds(0, 0, 1000, 700);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1000, 700);
		layeredPane.add(backGround, Integer.valueOf(0));
		layeredPane.add(status, Integer.valueOf(1));	
		layeredPane.add(countDown, Integer.valueOf(2));
		layeredPane.add(lv, Integer.valueOf(3));
		layeredPane.add(timeUsed, Integer.valueOf(4));
		
		
		frame = new JFrame("MATH GAME");
		frame.setSize(1000, 700);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setIconImage(new ImageIcon("3.png").getImage());
		frame.setResizable(false);
		frame.add(layeredPane);
		frame.setVisible(true);

		getReady();
	}
	
	void getReady(){
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				int count = 3;
				while(count > 0) {
					countDown.setValue(count);
					countDown.setString(String.valueOf(count));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					count -= 1;
				}
				countDown.setString("GO");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				t1 = new Thread(new Runnable() {					
					@Override
					public void run() {
						time = 0;
						while(end == false) {
							time++;
							timeUsed.setText("Time used: "+String.valueOf(time));
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				});				
				t1.start();
				layeredPane.add(quiz, Integer.valueOf(4));
				randomQuiz();
				return null;
			}
		
		};
		worker.execute();
	}

	void randomQuiz() {
		level++;
		if(level > 30) {
			win = true;
			end = true;
			winOrLose();
		}else {
			int num1 = 0, num2 = 0, op = 0;
			double res = 0;
			char ope = 0;
			if(level <= 10) {
				num1 = (int) (Math.random() * (9 - 0 + 1) + 0);	
				num2 = (int) (Math.random() * (9 - 0 + 1) + 0);
			}else if(level <= 20){
				num1 = (int) (Math.random() * (99 - 10 + 1) + 10);
				num2 = (int) (Math.random() * (9 - 0 + 1) + 0);
			}else if(level <=30) {
				num1 = (int) (Math.random() * (99 - 10 + 1) + 10);
				num2 = (int) (Math.random() * (99 - 10 + 1) + 10);
			}
			if(level <= 10) {
				op = (int) (Math.random() * (1 - 0 + 1) + 0);
			}else if(level <= 30) {
				op = (int) (Math.random() * (3 - 2 + 1) + 2);
			}
			
			if(op == 0) {
				ope = '+';
				res = num1+num2;
			}else if(op == 1) {
				ope = '-';
				res = num1-num2;
			}else if(op == 2) {
				ope = '*';
				res = num1*num2;
			}else if(op == 3) {
				ope = '/';
				if(num2 == 0) {
					num2 = (int) (Math.random() * (9 - 1 + 1) + 1);
				}
				res = Double.valueOf(df.format((double)num1/num2));	
			}
			lv.setText("Level: "+String.valueOf(level));
			ques.setText(String.valueOf(num1)+" "+ope+" "+String.valueOf(num2));
			randomAnswers(res, ope);
		}
	}
	
	void randomAnswers(double res, char ope) {	
		ans = (int) (Math.random() * (3 - 0 + 1) + 0);
		int gap = 0; 
		double[] arrf = new double[3];
		int[] arri = new int[3];
		double tempAns;
		if(ope != '/'){
			if(level <= 10) {
				arri[2] = (int) (res-1);
				gap = 1;
				for(int i = 0; i < 2; i++) {
					arri[i] = (int) (res + gap);
					gap += 1;
				}
			}else if(level <= 30) {
					arri[2] = (int) (res-10);
					gap = 10;
					for(int i = 0; i < 2; i++) {
						arri[i] = (int) (res + gap);
						gap += 10;
					}
				}
		}else {
			for(int i = 0; i < 3; i++) {
				tempAns = (double) (Math.random() * ((res+0.01) - (res-0.01) + 1) + (res-0.01));
				if(repeat(arrf, tempAns, res) == 0) {
					arrf[i] = tempAns;
				}
			}
		}
		if(ans == 0) {
			if(ope != '/') {
				a1.setText(String.valueOf(String.valueOf((int)res)));
				a2.setText(String.valueOf(String.valueOf(arri[0])));
				a3.setText(String.valueOf(String.valueOf(arri[1])));
				a4.setText(String.valueOf(String.valueOf(arri[2])));
			}else if(ope == '/') {
				a1.setText(String.valueOf(String.valueOf(res)));
				a2.setText(String.valueOf(df.format(arrf[0])));
				a3.setText(String.valueOf(df.format(arrf[1])));
				a4.setText(String.valueOf(df.format(arrf[2])));
			}
		}else if(ans == 1) {
			if(ope != '/') {
				a2.setText(String.valueOf(String.valueOf((int)res)));
				a1.setText(String.valueOf(String.valueOf(arri[0])));
				a3.setText(String.valueOf(String.valueOf(arri[1])));
				a4.setText(String.valueOf(String.valueOf(arri[2])));
			}else if(ope == '/') {
				a2.setText(String.valueOf(String.valueOf(res)));
				a1.setText(String.valueOf(df.format(arrf[0])));
				a3.setText(String.valueOf(df.format(arrf[1])));
				a4.setText(String.valueOf(df.format(arrf[2])));
			}
		}else if(ans == 2) {
			if(ope != '/') {
				a3.setText(String.valueOf(String.valueOf((int)res)));
				a2.setText(String.valueOf(String.valueOf(arri[0])));
				a1.setText(String.valueOf(String.valueOf(arri[1])));
				a4.setText(String.valueOf(String.valueOf(arri[2])));
			}else if(ope == '/') {
				a3.setText(String.valueOf(String.valueOf(res)));
				a2.setText(String.valueOf(df.format(arrf[0])));
				a1.setText(String.valueOf(df.format(arrf[1])));
				a4.setText(String.valueOf(df.format(arrf[2])));
			}
		}else if(ans == 3) {
			if(ope != '/') {
				a4.setText(String.valueOf(String.valueOf((int)res)));
				a2.setText(String.valueOf(String.valueOf(arri[0])));
				a3.setText(String.valueOf(String.valueOf(arri[1])));
				a1.setText(String.valueOf(String.valueOf(arri[2])));
			}else if(ope == '/') {
				a4.setText(String.valueOf(String.valueOf(res)));
				a2.setText(String.valueOf(df.format(arrf[0])));
				a3.setText(String.valueOf(df.format(arrf[1])));
				a1.setText(String.valueOf(df.format(arrf[2])));
			}
		}
	}
	
	int repeat(double[] arr, double tempA, double res) {
		for(int i = 0; i < 3; i++) {
			if(arr[i] == tempA || tempA == res) {
				return 1;
			}
		}
		return 0;
	}
	
	void winOrLose() {
		if(win == false) {
			result.setText("<html>YOU LOSE<BR>AT LEVEL: "+level);
		}else {
			result.setText("CONGRATULATION");
			result.setIcon(resizeIcon(vic, 400, 200));
			result.setIconTextGap(0);
			result.setVerticalTextPosition(JLabel.TOP);
			result.setHorizontalTextPosition(JLabel.CENTER);
		}
		layeredPane.add(panel, Integer.valueOf(5));
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {		
				if(level-1 > 0) {
					Object[] newData = {name, level-1, best};
					rank.addToTable(newData);
				}

			}
		});
		t2.start();
		a1.setEnabled(false);
		a2.setEnabled(false);
		a3.setEnabled(false);
		a4.setEnabled(false);
	}
	
	ImageIcon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
	    Image img = icon.getImage();
	    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
	    return new ImageIcon(resizedImage);
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
