import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;

public class Endscreen extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane_1;
	private JTable rundenTable;
	private JLabel lblGewonnen;
	private JTable table;




	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Endscreen frame = new Endscreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 *
	 *
	 */

	// Werte aus Rundendatenliste zur Tabelle hinzufuegen
	public void getStats(int ID) {
		JOptionPane.showMessageDialog(null, "Das Spiel ist vorbei: Deine ID: "+ID);
		DefaultTableModel rundenTableModel = (DefaultTableModel) rundenTable.getModel();
		for(int i=0;i<rd.size();i++) {
			RundenDaten r = rd.get(i);
			//"Runde", "Kapital", "Geldkapital", "Ertrag", "Lager", "Lagermenge", "Verkaufsmenge", "Kreditprozentsatz", "Kreditnahme"
			rundenTableModel.addRow(new Object[] {r.Runde, r.Kapital, r.Geldkapital, r.Geldaenderung, r.Lager, r.Lagermenge,r.Verkaufsmenge, r.Kreditprozentsatz, r.Kreditnahme});
		}
	}

	private ArrayList<RundenDaten> rd;
	private boolean isWinner;

	//Festlegen ob Gewinner or nah
	public void setWinner(boolean winner){
		this.isWinner=winner;
		if(isWinner)lblGewonnen.setText("Du hast gewonnen!");
		else lblGewonnen.setText("Du hast leider verloren...");
	}

	//Setter fuer Rundendaten
	public void setRundenDaten(ArrayList<RundenDaten> rd){
		this.rd = rd;
	}

	public Endscreen() { //boolean isWInner
		//this.rd  = rd;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(563, 83, 142, 354);
		contentPane.add(scrollPane);


		lblGewonnen = new JLabel("Du hast gewonnen!");

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Spieler"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		scrollPane.setViewportView(table);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(25, 83, 514, 354);
		contentPane.add(scrollPane_1);

		rundenTable = new JTable();
		scrollPane_1.setViewportView(rundenTable);
		rundenTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		rundenTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Runde", "Kapital", "Geldkapital", "Ertrag", "Lager", "Lagermenge", "Verkaufsmenge", "Kreditprozentsatz", "Kreditnahme"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		rundenTable.getColumnModel().getColumn(0).setResizable(false);
		rundenTable.getColumnModel().getColumn(1).setResizable(false);
		rundenTable.getColumnModel().getColumn(2).setResizable(false);
		rundenTable.getColumnModel().getColumn(3).setResizable(false);
		rundenTable.getColumnModel().getColumn(3).setPreferredWidth(83);
		rundenTable.getColumnModel().getColumn(4).setResizable(false);
		rundenTable.getColumnModel().getColumn(5).setResizable(false);
		rundenTable.getColumnModel().getColumn(6).setResizable(false);
		rundenTable.getColumnModel().getColumn(7).setResizable(false);
		rundenTable.getColumnModel().getColumn(7).setPreferredWidth(100);
		rundenTable.getColumnModel().getColumn(8).setResizable(false);


		lblGewonnen.setFont(new Font("Dialog", Font.BOLD, 23));
		lblGewonnen.setBounds(197, 12, 348, 49);
		contentPane.add(lblGewonnen);
	//*******************************************************

	}
}
