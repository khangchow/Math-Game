package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import controller.Controller;
import model.Player;

public class Ranking {
	DefaultTableModel model;
	static int num = -1;
	JTable table;
	JTableHeader header;
	JScrollPane sp;
	JFrame frame;
	Controller controller = new Controller();
	static ArrayList<Player> list = new ArrayList<Player>();
	boolean swapped = false, changed = false;
	
	public Ranking() {
		String[] columnsName = {"RANK", "PLAYER", "LEVEL", "USED-TIME"};
		Object[][] data = {{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}};
		
		model = new DefaultTableModel(data, columnsName);
		
		table = new JTable() {
		      @Override
		      public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
			      Component comp = super.prepareRenderer(renderer, row, col);
			      ((JLabel) comp).setHorizontalAlignment(JLabel.CENTER);
			      return comp;
		      }
		      
		      public boolean editCellAt(int row, int column, java.util.EventObject e) {
		            return false;
		      }
		};
		    
		table.setModel(model);
		 
		header = table.getTableHeader();
		header.setFont(new Font("MV Boli", Font.BOLD, 20));
		header.setBackground(Color.BLACK);
		header.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		header.setForeground(Color.GREEN);
		header.setPreferredSize(new Dimension(100, 50));
		header.setReorderingAllowed(false);
		    
		table.setForeground(Color.GREEN);
		table.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		table.setFont(new Font("MV Boli", Font.BOLD, 20));
		table.setRowHeight(50);	
		table.setPreferredScrollableViewportSize(new Dimension(700, 500));
		table.setBackground(Color.BLACK);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(1).setMaxWidth(300);
		table.getColumnModel().getColumn(1).setMinWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setMinWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setMaxWidth(200);
		table.getColumnModel().getColumn(3).setMinWidth(200);

		
		sp = new JScrollPane(table);
		
		frame = new JFrame("TOP 10 BEST PLAYERS");
		frame.setIconImage(new ImageIcon("rank.png").getImage());
		frame.setLocationRelativeTo(null);
		frame.setLayout(new FlowLayout());
		frame.add(sp);
		frame.pack();
		frame.setLocationRelativeTo(null);		
		frame.setResizable(false);
	}
	
	public void addOldData(Object[] data) {
		num++;
		Player player = new Player((String)data[0], (int)data[1], (int)data[2]);
		for(int i = 0; i < data.length; i++) {
	    	model.setValueAt(data[i], num, i+1);
	    } 	
		list.add(player);
	}
	
	public void addToTable(Object[] data) {
		Player player = new Player((String)data[0], (int)data[1], (int)data[2]);
		if(num < 10) {
			num++;
		}
		if(num < 10){	
		    for(int i = 0; i < data.length; i++) {
		    	model.setValueAt(data[i], num, i+1);
		    } 			    
		    list.add(player);
		}else {
			if(((int)model.getValueAt(9, 2) < (int)data[1]) || ((int)model.getValueAt(9, 2) == (int)data[1]) && ((int)model.getValueAt(9, 3) > (int)data[2])) {
				for(int i = 0; i < data.length; i++) {
			    	model.setValueAt(data[i], 9, i+1);
			    } 	
				list.set(9, player);
				changed = true;
			}
		}
		sortTable(list, player);
	}
	
	private void sortTable(ArrayList<Player> list, Player player) {
		
		Collections.sort(list, new Comparator<Player>() {
			
			@Override
			public int compare(Player o1, Player o2) {
				if(o2.getLevel() < o1.getLevel()) {
					swapped = true;
					return -1;
				}else if(o1.getLevel() == o2.getLevel()){
					if(o2.getTimeUsed() > o1.getTimeUsed()) {
						swapped = true;
						return -1;
					}
				}
				return 0;
			}		
		});
		if(swapped == false) {
			if(num < 10) {
				controller.writePlayerToFile(player);
			}else {
				if(changed == true) {
					controller.writePlayersToFile(list);
				}
			}
		}else if(swapped == true) {
			int i = 0;
			for (Player player1 : list) {
				if(i <= num) {				
					Object[] data = {player1.getIngame(), player1.getLevel(), player1.getTimeUsed()};
					for(int j = 0; j < data.length; j++) {
						model.setValueAt(data[j], i, j+1);
					}		
					i++;
				}		
			}
			controller.writePlayersToFile(list);
			swapped = false;
		}
	}
	
	public void visibility() { 
		frame.setVisible(true);
	}
}
