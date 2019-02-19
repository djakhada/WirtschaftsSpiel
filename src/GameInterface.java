import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.Font;
import java.awt.Color;

public class GameInterface extends JFrame{

	private JPanel contentPane;
	//public int Erz=20000, Lager=0, Abbau=5000, AbbauR=0, LagerR=0, Verkauf=0,LagerL=15000;
	public int markt=100, gewinn=0, bKredit=200000, zinsen=0;
	public int lager1=100000, lager2=200000, lager3=300000,arbeiter1=200000, arbeiter2=250000, arbeiter3=350000, pressluft=300000, bagger=1000000, s_bagger=10000000, kanarien=100000;
	public int holzpfeiler=200000, stahlpfeiler=1000000, wasserpumpen=500000, rauchverbot=600, staubschutz=10000, krankenversicherung=40000, unfallversicherung=40000, streik_umsatzausfall=40000;
	public int werbung1= 10000, werbung2= 50000, werbung3= 125000, erstehilfe= 10000, kredit=0, laufzeit=0, LagerV=0;
	public int Forschungsabbau=0, Forschungslager=0;

	//neue Variablen!!
	private int geldKapital=0;

	private JTextField txtAbbaumenge;
	private JTextField txtLagern;
	private JTextField txtVerkaufen;
	private JTextField txtKredit;
	JLabel lblArbeiter1 = new JLabel(" 200000\u20AC");
	JLabel lblLager3 = new JLabel(" 300000\u20AC");
	JLabel lblLager2 = new JLabel(" 200000\u20AC");
	JLabel lblLager1 = new JLabel(" 100000\u20AC");
	JCheckBox chckbxwerbung3 = new JCheckBox("Werbung 3");
	JCheckBox chckbxwerbung2 = new JCheckBox("Werbung 2");
	JCheckBox chckbxwerbung1 = new JCheckBox("Werbung 1");
	JCheckBox chckbxstreikumsatzausfall = new JCheckBox("Streik Umsatzausfall");
	JCheckBox chckbxunfallversicherung = new JCheckBox("Unfallversicherung");
	JCheckBox chckbxkrankenversicherung = new JCheckBox("Krankenversicherung");
	JCheckBox chckbxerstehilfe = new JCheckBox("Erstehilfekasten");
	JCheckBox chckbxstaubschutz = new JCheckBox("Staubschutz");
	JCheckBox chckbxrauchverbot = new JCheckBox("Rauchverbot ");
	JCheckBox chckbxwasserpumpe = new JCheckBox("Wassserpumpen");
	JCheckBox chckbxstahlpfeiler = new JCheckBox("Stahlpfeiler");
	JCheckBox chckbxholzpfeiler = new JCheckBox("Holzpfeiler");
	JCheckBox chckbxkanarien = new JCheckBox("Kanarien Vogel");
	JCheckBox chckbxbagger = new JCheckBox("Bagger");
	JCheckBox chckbxpressluft = new JCheckBox("Presslufthammer");
	JCheckBox chckbxarbeiter3 = new JCheckBox("Arbeiter bildung 3");
	JCheckBox chckbxarbeiter2 = new JCheckBox("Arbeiter bildung 2");
	JCheckBox chckbxarbeiter1 = new JCheckBox("Arbeiter bildung 1");
	JCheckBox chckbxlager3 = new JCheckBox("Lager Stufe 3");
	JCheckBox chckbxlager2 = new JCheckBox("Lager Stufe 2");
	JCheckBox chckbxlager1 = new JCheckBox("Lager Stufe 1");
	JPanel panel_1 = new JPanel();
	JLabel lblGeldKapital = new JLabel("Geldkapital: 31415€");
	JLabel label_10 = new JLabel(":");
	JLabel label_9 = new JLabel("Runden\u00FCbersicht:");
	JTextArea textA_Runde = new JTextArea();
	JScrollPane scrollPane = new JScrollPane();
	JLabel lblMarktpreis = new JLabel("Aktueller Marktpreis pro Tonne Erz: 314€");
	JLabel lblVerkauf = new JLabel("Verkaufen");
	JLabel lblLager = new JLabel("Lagerbestand: 0t (Wert: 0€)");
	JLabel lblLagern = new JLabel("Lagern");
	JLabel lblAbbaumengemax = new JLabel("Abbaumenge (Max: 5000t)");
	JPanel panel = new JPanel();
	JButton btnRunde = new JButton("Runde Benden ");
	JCheckBox chckbxlaufzeit2 = new JCheckBox("Laufzeit 8 Runden");
	JCheckBox chckbxlaufzeit1 = new JCheckBox("Laufzeit 4 Runden ");
	JTextArea textStatis = new JTextArea();
	JLabel lblMaxKredit = new JLabel("New label");
	JLabel lblKreditsumme = new JLabel("Kreditsumme");
	JLabel lblKapitalBank = new JLabel("New label");
	JPanel panel_2 = new JPanel();
	JLabel lblSbagger = new JLabel(" 10000000\u20AC");
	JCheckBox chckbxSbagger = new JCheckBox("Schaufelradbagger");
	JLabel lblKapitalForschung = new JLabel("geldKapital: 0.0");
	JLabel lblWerbung3 = new JLabel(" 125000\u20AC");
	JLabel lblWerbung2 = new JLabel(" 50000\u20AC");
	JLabel lblWerbung1 = new JLabel(" 10000\u20AC");
	JLabel lblStreikumsatzversicherung = new JLabel(" 40000\u20AC");
	JLabel lblUnfallversicherung = new JLabel(" 40000\u20AC");
	JLabel lblKrankenversicherung = new JLabel(" 40000\u20AC");
	JLabel lblErstehilfekasten = new JLabel(" 10000\u20AC");
	JLabel lblStaubschutz = new JLabel(" 10000\u20AC");
	JLabel lblRauchverbot = new JLabel(" 600\u20AC");
	JLabel lblWasserpumpe = new JLabel(" 500000\u20AC");
	JLabel lblStahlpfeiler = new JLabel(" 1000000\u20AC");
	JLabel lblHolzpfeiler = new JLabel(" 200000\u20AC");
	JLabel lblKanarien = new JLabel(" 100000\u20AC");
	JLabel lblBagger = new JLabel(" 1000000\u20AC");
	JLabel lblPressluft = new JLabel(" 300000\u20AC");
	JLabel lblArbeiter3 = new JLabel(" 350000\u20AC");
	JLabel lblArbeiter2 = new JLabel(" 250000\u20AC");
	JButton btnKredit = new JButton("Kredit");
	JSlider sliderVerkaufen = new JSlider();
	ImageIcon iconbank = new ImageIcon ("Bilder/Bank.png");
	ImageIcon iconforschung = new ImageIcon("Bilder/Forschung.png");
	ImageIcon iconspiel = new ImageIcon("Bilder/Spiel.png");
	JSlider sliderLagern = new JSlider();
	JSlider sliderAbbau = new JSlider();
	JSlider sliderLagerVerkauf = new JSlider();
	JLabel lblMaxLager = new JLabel("Maximaler Lagerplatz: 15000t");
	JLabel lblFreeLager = new JLabel("Noch verfügbarer Lagerplatz: 15000t");
	private JLabel lblLagerverkauf;
	private JTextField txtLagerVerkauf;
	static VerbindungsFenster VF;
	int maxAbbau = 5000;
	int maxLager = 15000;
	int maxKredit = 300000;
	int freeLager = 5000;
	int Lager = 2500;
	int Runde = 1;
	private JLabel lblVerkaufSumme;
	private JTextField txtVerkaufSumme;
	private JSlider sliderKredit;
	private final JLabel lblForschungBild = new JLabel("");
	private final JTextArea textArea_1 = new JTextArea();
	private final JLabel lblAbbaukosten = new JLabel("Momentane Abbaukosten: 314€/t");

	public void Updaten(){
		System.out.println("Client: GameInterface Updaten");
		geldKapital = VF.getGeldKapital();
		maxAbbau = VF.getMaxAbbau()+Forschungsabbau;
		freeLager = VF.getLagerMax()-VF.getLager();
		maxLager = VF.getLagerMax()+Forschungslager;
		Lager = VF.getLager();
		maxKredit = VF.getMaxKredit();

		//Gucken ob Kredit verfügbar ist
		if(VF.getKreditLaufzeit()>0) {
				btnKredit.setEnabled(false);
			}
		else {
				btnKredit.setEnabled(true);
				chckbxlaufzeit1.setEnabled(true);
				chckbxlaufzeit2.setEnabled(true);
			}

		lblGeldKapital.setText("Geldkapital: "+geldKapital+"€");
		lblKapitalForschung.setText(geldKapital+"€");
		lblKapitalBank.setText(geldKapital+"€");
		lblAbbaukosten.setText("Momentane Abbaukosten: "+VF.getAbbauKosten()+"€/t");

		lblMaxKredit.setText("Maximaler Kredit: "+VF.getMaxKredit()+'€');

		lblLager.setText("Lagerbestand: "+VF.getLager()+"t (Wert: "+(VF.getLager()*VF.getMarktPreis())+"€)");
		lblMaxLager.setText("Maximaler Lagerplatz: "+maxLager+"t");
		lblFreeLager.setText("Noch verfügbarer Lagerplatz: "+freeLager+"t");
		label_10.setText("    "+Runde+"");

		lblMarktpreis.setText("Aktueller Marktpreis pro Tonne Erz: "+VF.getMarktPreis()+"€");
		lblAbbaumengemax.setText("Abbaumenge (Max. "+maxAbbau+"t)");
		txtAbbaumenge.setText(""+maxAbbau);

		textStatis.setText("Rückzahlung: "+VF.getKredit()+"€"+
							"\nKreditlaufzeit: "+VF.getKreditLaufzeit()+
							" Runden\nZinsen: "+VF.getZinssatz()+"%"+
							"\nGewinn: "+VF.getGeldaenderung()+"€");

		textA_Runde.setText("Runde:"+Runde+"\n"+"geldKapital:"+VF.getGeldKapital()+"€\n"+"Gewinn:"+VF.getGeldaenderung()+"€\n"+"Marktpreis:"+VF.getMarktPreis()+"€\n"+"Abbaumenge:"+(VF.getVerkaufsmenge()+VF.getLagermenge())+"t\n"+"Verkaufsmenge:"+VF.getLagermenge()+"t\n"+"Lagerstand:"+VF.getLager()+"t\n");
		sliderAbbau.setValue(0);
		sliderAbbau.setValue(100); //Nötig um den sliderAbbau.change Handler aufzurufen
		sliderLagerVerkauf.setValue(50);
		sliderKredit.setValue(49);
		sliderKredit.setValue(50);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameInterface frame = new GameInterface(VF);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void naechsteRound(int runde){
		btnRunde.setEnabled(true);
		Runde=runde;
		JOptionPane.showMessageDialog(null, "Willkommen in Runde "+Runde+"!\nGeldänderung nach der letzten Runde: "+VF.getGeldaenderung());
		Updaten();
	}
	/**
	 * Create the frame.
	 */

	public GameInterface(VerbindungsFenster VF) {
		setResizable(false);

		//panel_2.setBackground("X:\\Start.png");
		//JLabel panel_2 = new JLabel(new ImageIcon("/res/mariocraft_main.png"));

		setTitle("Wirtschaftssim by BDM");
		this.VF = VF;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 800);
		contentPane = new JPanel();
		contentPane.setToolTipText("F\u00FChrt order aus");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1280, 800);
		contentPane.add(tabbedPane);

		sliderLagerVerkauf.setBounds(494, 524, 102, 30);
		panel.add(sliderLagerVerkauf);
		sliderLagerVerkauf.setValue(0);

		sliderKredit = new JSlider();
		sliderKredit.setMinimum(10);
		txtKredit = new JTextField(maxKredit/2+"");
		txtKredit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtKredit.setEditable(false);
		txtVerkaufen = new JTextField();
		txtVerkaufen.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtVerkaufen.setEditable(false);
		txtLagern = new JTextField();
		txtLagern.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtLagern.setEditable(false);

		panel.setLayout(null);
		panel.setToolTipText("F\u00FChrt order aus");
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Spiel ", null, panel, null);

		txtVerkaufSumme = new JTextField();
		txtVerkaufSumme.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtVerkaufSumme.setEditable(false);
		txtVerkaufSumme.setColumns(10);
		txtVerkaufSumme.setBounds(616, 452, 112, 26);
		panel.add(txtVerkaufSumme);
		txtAbbaumenge = new JTextField();
		txtAbbaumenge.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAbbaumenge.setEditable(false);
		txtAbbaumenge.setText(maxAbbau+"");
		txtAbbaumenge.setColumns(10);
		txtAbbaumenge.setBounds(69, 452, 102, 30);
		panel.add(txtAbbaumenge);
		lblAbbaumengemax.setFont(new Font("Tahoma", Font.PLAIN, 15));

		lblAbbaumengemax.setBounds(69, 418, 268, 25);
		panel.add(lblAbbaumengemax);
		lblLagern.setFont(new Font("Tahoma", Font.PLAIN, 15));

		lblLagern.setBounds(69, 493, 72, 20);
		panel.add(lblLagern);

		txtLagern.setColumns(10);
		txtLagern.setBounds(69, 524, 102, 30);
		panel.add(txtLagern);
		lblLager.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblLager.setBounds(855, 138, 391, 25);
		panel.add(lblLager);
		lblVerkauf.setFont(new Font("Tahoma", Font.PLAIN, 15));

		lblVerkauf.setBounds(385, 418, 102, 25);
		panel.add(lblVerkauf);

		txtVerkaufen.setColumns(10);
		txtVerkaufen.setBounds(385, 452, 102, 26);
		panel.add(txtVerkaufen);
		lblMarktpreis.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblMarktpreis.setBounds(69, 630, 294, 34);
		panel.add(lblMarktpreis);

		scrollPane.setBounds(855, 394, 340, 293);
		panel.add(scrollPane);
		textA_Runde.setEditable(false);

		scrollPane.setViewportView(textA_Runde);
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 20));

		label_9.setBounds(855, 358, 221, 25);
		panel.add(label_9);
		label_10.setFont(new Font("Tahoma", Font.PLAIN, 20));

		label_10.setBounds(1130, 7, 65, 34);
		panel.add(label_10);
		lblGeldKapital.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblGeldKapital.setBackground(Color.WHITE);

		lblGeldKapital.setBounds(855, 102, 391, 25);
		panel.add(lblGeldKapital);

		tabbedPane.addTab("Bank", null, panel_2, null);
		panel_2.setLayout(null);
		panel_2.setBorder(new EmptyBorder(5, 5, 5, 5));

		lblKapitalBank.setBounds(790, 310, 136, 14);
		panel_2.add(lblKapitalBank);

		txtKredit.setColumns(10);
		txtKredit.setBounds(198, 350, 161, 32);
		panel_2.add(txtKredit);
		lblKreditsumme.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblKreditsumme.setBounds(198, 285, 161, 32);
		panel_2.add(lblKreditsumme);

		lblMaxKredit.setBounds(198, 411, 294, 14);
		panel_2.add(lblMaxKredit);
		textStatis.setFont(new Font("Monospaced", Font.PLAIN, 20));
		textStatis.setEditable(false);

		textStatis.setText("Kredit:0.0\nZinsen:0.0\nGewinn:0.0\n");
		textStatis.setBounds(790, 368, 294, 270);
		panel_2.add(textStatis);
		chckbxlaufzeit1.setFont(new Font("Tahoma", Font.PLAIN, 20));

		chckbxlaufzeit1.setBounds(462, 396, 244, 36);
		panel_2.add(chckbxlaufzeit1);
		chckbxlaufzeit2.setFont(new Font("Tahoma", Font.PLAIN, 20));

		chckbxlaufzeit2.setBounds(462, 446, 244, 36);
		panel_2.add(chckbxlaufzeit2);
		btnRunde.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRunde.setEnabled(true);
		btnRunde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int LagerMenge = Integer.parseInt(txtLagern.getText())-Integer.parseInt(txtLagerVerkauf.getText());
				int VerkaufMenge = Integer.parseInt(txtVerkaufen.getText())+Integer.parseInt(txtLagerVerkauf.getText());

			    int dialogResult = JOptionPane.showConfirmDialog(null, "Willst du die Runde wirklich beenden?\nWerte:\nLagermenge: "+LagerMenge+"t\n"+"Verkaufsmenge: "+VerkaufMenge+"t\n", "Runde beenden?",JOptionPane.YES_NO_OPTION);
			    if (dialogResult==JOptionPane.YES_OPTION){
			    	btnRunde.setEnabled(false);
			    	VF.setAbbaumenge(Integer.parseInt(txtLagern.getText())+Integer.parseInt(txtVerkaufen.getText()));
			    	VF.setLagermenge(LagerMenge);
			    	VF.setVerkaufsmenge(VerkaufMenge);
			    	VF.confirmRound();
			    }
			}
		});
		btnRunde.setBounds(855, 276, 340, 34);
		panel.add(btnRunde);

		sliderLagern.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int Abbaumenge = Integer.parseInt(txtAbbaumenge.getText());
				int neuValue = 100;
				if(Abbaumenge<=freeLager) {
					neuValue = Abbaumenge * sliderLagern.getValue()/100;
					txtLagern.setText(neuValue+"");
					sliderVerkaufen.setValue(100-sliderLagern.getValue());
				}else {
					neuValue = freeLager * sliderLagern.getValue()/100;
					int Differenz = Abbaumenge - neuValue;
					txtLagern.setText(neuValue+"");
					sliderVerkaufen.setValue(100-sliderLagern.getValue());
					txtVerkaufen.setText(Differenz+"");
				}
				int VerkaufSumme = Integer.parseInt(txtVerkaufen.getText())+Integer.parseInt(txtLagerVerkauf.getText());
				txtVerkaufSumme.setText(VerkaufSumme+"");
			}
		});

		sliderVerkaufen.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int neuValue = Integer.parseInt(txtAbbaumenge.getText()) * sliderVerkaufen.getValue()/100;
				txtVerkaufen.setText(neuValue+"");
				sliderLagern.setValue(100-sliderVerkaufen.getValue());
			}
		});

		sliderLagerVerkauf.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int neuValue = Lager * sliderLagerVerkauf.getValue()/100;
				txtLagerVerkauf.setText(neuValue+"");
				int VerkaufSumme = Integer.parseInt(txtVerkaufen.getText())+Integer.parseInt(txtLagerVerkauf.getText());
				txtVerkaufSumme.setText(VerkaufSumme+"");
			}
		});

		sliderKredit.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int minKredit = (int)(maxKredit*0.2);
				int neuValue = maxKredit * sliderKredit.getValue()/100;
				txtKredit.setText(neuValue+"");
			}
		});

		sliderAbbau.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int neuValue = maxAbbau * sliderAbbau.getValue()/100;
				txtAbbaumenge.setText(neuValue+"");
				if(neuValue<=freeLager) {
					neuValue = Integer.parseInt(txtAbbaumenge.getText()) * sliderVerkaufen.getValue()/100;
					txtVerkaufen.setText(neuValue+"");
					neuValue = Integer.parseInt(txtAbbaumenge.getText()) * sliderLagern.getValue()/100;
					txtLagern.setText(neuValue+"");
				}else {
					int netValue = freeLager * sliderLagern.getValue()/100;
					int Differenz = neuValue - netValue;
					txtLagern.setText(netValue+"");
					sliderVerkaufen.setValue(100-sliderLagern.getValue());
					txtVerkaufen.setText(Differenz+"");
				}
			}
		});

		sliderLagern.setBounds(201, 524, 102, 30);
		panel.add(sliderLagern);

		sliderVerkaufen.setBounds(494, 450, 102, 25);
		panel.add(sliderVerkaufen);

		sliderAbbau.setValue(100);
		sliderAbbau.setBounds(181, 451, 156, 31);
		panel.add(sliderAbbau);
		lblMaxLager.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblMaxLager.setBounds(855, 174, 391, 25);
		panel.add(lblMaxLager);
		lblFreeLager.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblFreeLager.setBounds(855, 199, 391, 33);
		panel.add(lblFreeLager);

		lblLagerverkauf = new JLabel("Lagerverkauf");
		lblLagerverkauf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLagerverkauf.setBounds(385, 495, 182, 17);
		panel.add(lblLagerverkauf);

		txtLagerVerkauf = new JTextField();
		txtLagerVerkauf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtLagerVerkauf.setEditable(false);
		txtLagerVerkauf.setColumns(10);
		txtLagerVerkauf.setBounds(385, 524, 102, 30);
		panel.add(txtLagerVerkauf);



		lblVerkaufSumme = new JLabel("Verkauf Summe");
		lblVerkaufSumme.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblVerkaufSumme.setBounds(617, 421, 193, 20);
		panel.add(lblVerkaufSumme);
		btnKredit.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JTextArea textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setEditable(false);
		textArea.setBounds(41, 395, 727, 181);
		panel.add(textArea);
		textArea_1.setEnabled(false);
		textArea_1.setEditable(false);
		textArea_1.setBounds(51, 630, 340, 34);

		panel.add(textArea_1);
		lblAbbaukosten.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAbbaukosten.setBounds(855, 233, 391, 33);

		panel.add(lblAbbaukosten);

		btnKredit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kredit =Integer.parseInt(txtKredit.getText());
				if (chckbxlaufzeit1.isSelected()&&!chckbxlaufzeit2.isSelected())	{
					laufzeit=3;
					chckbxlaufzeit1.setEnabled(false);
					chckbxlaufzeit2.setEnabled(false);
					btnKredit.setEnabled(false);
					VF.setGenommenerKredit(kredit);
					VF.setKreditLaufzeit(laufzeit);
				}

				else if (chckbxlaufzeit2.isSelected()&&!chckbxlaufzeit1.isSelected())	{
					laufzeit=6;
					chckbxlaufzeit2.setEnabled(false);
					chckbxlaufzeit1.setEnabled(false);
					btnKredit.setEnabled(false);
					VF.setGenommenerKredit(kredit);
					VF.setKreditLaufzeit(laufzeit);

				}
				else		{
					JOptionPane.showMessageDialog(null,"Fehler bei laufzeitwahl", "fatal error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnKredit.setBounds(198, 465, 161, 33);
		panel_2.add(btnKredit);

		JLabel lblInfoBank = new JLabel("");
		lblInfoBank.setBounds(478, 277, 228, 40);
		panel_2.add(lblInfoBank);

		sliderKredit.setBounds(462, 321, 244, 40);
		panel_2.add(sliderKredit);

		JLabel lblBankBild = new JLabel(iconbank);
		lblBankBild.setBounds(10, -16, 1280, 800);
		panel_2.add(lblBankBild);
		lblBankBild.setBounds(0, -51, 1280, 800);

		JCheckBox chckbxLaufzeitRunden = new JCheckBox("Laufzeit 16 Runden");
		chckbxLaufzeitRunden.setFont(new Font("Tahoma", Font.PLAIN, 20));
		chckbxLaufzeitRunden.setBounds(462, 496, 244, 36);
		panel_2.add(chckbxLaufzeitRunden);

		JCheckBox checkBox_1 = new JCheckBox("Laufzeit 6 Runden");
		checkBox_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkBox_1.setBounds(462, 546, 244, 36);
		panel_2.add(checkBox_1);

		panel_1.setLayout(null);
		panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Forschung ", null, panel_1, null);

		chckbxlager1.setBounds(161, 172, 144, 23);
		panel_1.add(chckbxlager1);

		chckbxlager2.setEnabled(false);
		chckbxlager2.setBounds(161, 198, 144, 23);
		panel_1.add(chckbxlager2);

		chckbxlager3.setEnabled(false);
		chckbxlager3.setBounds(161, 224, 144, 23);
		panel_1.add(chckbxlager3);

		chckbxarbeiter1.setBounds(161, 250, 144, 23);
		panel_1.add(chckbxarbeiter1);

		chckbxarbeiter2.setEnabled(false);
		chckbxarbeiter2.setBounds(161, 276, 144, 23);
		panel_1.add(chckbxarbeiter2);

		chckbxarbeiter3.setEnabled(false);
		chckbxarbeiter3.setBounds(161, 302, 144, 23);
		panel_1.add(chckbxarbeiter3);

		chckbxpressluft.setBounds(161, 328, 144, 23);
		panel_1.add(chckbxpressluft);

		chckbxbagger.setBounds(161, 354, 144, 23);
		panel_1.add(chckbxbagger);

		chckbxkanarien.setBounds(161, 406, 144, 23);
		panel_1.add(chckbxkanarien);

		chckbxholzpfeiler.setBounds(161, 432, 144, 23);
		panel_1.add(chckbxholzpfeiler);

		chckbxstahlpfeiler.setEnabled(false);
		chckbxstahlpfeiler.setBounds(161, 458, 144, 23);
		panel_1.add(chckbxstahlpfeiler);

		chckbxwasserpumpe.setBounds(161, 484, 144, 23);
		panel_1.add(chckbxwasserpumpe);

		chckbxrauchverbot.setBounds(161, 510, 144, 23);
		panel_1.add(chckbxrauchverbot);

		chckbxstaubschutz.setBounds(161, 536, 144, 23);
		panel_1.add(chckbxstaubschutz);

		chckbxerstehilfe.setBounds(161, 562, 144, 23);
		panel_1.add(chckbxerstehilfe);

		chckbxkrankenversicherung.setBounds(476, 172, 144, 23);
		panel_1.add(chckbxkrankenversicherung);

		chckbxunfallversicherung.setBounds(476, 200, 144, 23);
		panel_1.add(chckbxunfallversicherung);

		chckbxstreikumsatzausfall.setBounds(476, 226, 144, 23);
		panel_1.add(chckbxstreikumsatzausfall);

		chckbxwerbung1.setBounds(476, 252, 144, 23);
		panel_1.add(chckbxwerbung1);

		chckbxwerbung2.setBounds(476, 278, 144, 23);
		panel_1.add(chckbxwerbung2);

		chckbxwerbung3.setBounds(476, 304, 144, 23);
		panel_1.add(chckbxwerbung3);

		lblLager1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLager1.setBounds(311, 176, 89, 19);
		panel_1.add(lblLager1);

		lblLager2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLager2.setBounds(311, 202, 89, 14);
		panel_1.add(lblLager2);

		lblLager3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLager3.setBounds(311, 228, 89, 14);
		panel_1.add(lblLager3);

		lblArbeiter1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblArbeiter1.setBounds(311, 254, 89, 14);
		panel_1.add(lblArbeiter1);

		lblArbeiter2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblArbeiter2.setBounds(311, 280, 89, 14);
		panel_1.add(lblArbeiter2);

		lblArbeiter3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblArbeiter3.setBounds(311, 306, 89, 14);
		panel_1.add(lblArbeiter3);

		lblPressluft.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPressluft.setBounds(311, 332, 89, 14);
		panel_1.add(lblPressluft);

		lblBagger.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBagger.setBounds(311, 358, 89, 14);
		panel_1.add(lblBagger);

		lblKanarien.setHorizontalAlignment(SwingConstants.TRAILING);
		lblKanarien.setBounds(311, 410, 89, 14);
		panel_1.add(lblKanarien);

		lblHolzpfeiler.setHorizontalAlignment(SwingConstants.TRAILING);
		lblHolzpfeiler.setBounds(311, 436, 89, 14);
		panel_1.add(lblHolzpfeiler);

		lblStahlpfeiler.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStahlpfeiler.setBounds(311, 462, 89, 14);
		panel_1.add(lblStahlpfeiler);

		lblWasserpumpe.setHorizontalAlignment(SwingConstants.TRAILING);
		lblWasserpumpe.setBounds(311, 488, 89, 14);
		panel_1.add(lblWasserpumpe);

		lblRauchverbot.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRauchverbot.setBounds(311, 514, 89, 14);
		panel_1.add(lblRauchverbot);

		lblStaubschutz.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStaubschutz.setBounds(311, 540, 89, 14);
		panel_1.add(lblStaubschutz);

		lblErstehilfekasten.setHorizontalAlignment(SwingConstants.TRAILING);
		lblErstehilfekasten.setBounds(311, 566, 89, 14);
		panel_1.add(lblErstehilfekasten);

		lblKrankenversicherung.setHorizontalAlignment(SwingConstants.TRAILING);
		lblKrankenversicherung.setBounds(626, 176, 89, 14);
		panel_1.add(lblKrankenversicherung);

		lblUnfallversicherung.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUnfallversicherung.setBounds(626, 204, 89, 14);
		panel_1.add(lblUnfallversicherung);

		lblStreikumsatzversicherung.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStreikumsatzversicherung.setBounds(626, 230, 89, 14);
		panel_1.add(lblStreikumsatzversicherung);

		lblWerbung1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblWerbung1.setBounds(626, 256, 89, 14);
		panel_1.add(lblWerbung1);

		lblWerbung2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblWerbung2.setBounds(626, 282, 89, 14);
		panel_1.add(lblWerbung2);

		lblWerbung3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblWerbung3.setBounds(626, 308, 89, 14);
		panel_1.add(lblWerbung3);
		lblKapitalForschung.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblKapitalForschung.setBounds(512, 393, 158, 40);
		panel_1.add(lblKapitalForschung);

		chckbxSbagger.setBounds(161, 380, 144, 23);
		panel_1.add(chckbxSbagger);

		lblSbagger.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSbagger.setBounds(311, 385, 89, 14);
		panel_1.add(lblSbagger);

		chckbxlager2.setEnabled(false);
		chckbxlager3.setEnabled(false);
		chckbxarbeiter2.setEnabled(false);
		chckbxarbeiter3.setEnabled(false);
		chckbxstahlpfeiler.setEnabled(false);
		chckbxkanarien.setEnabled(false);
		chckbxholzpfeiler.setEnabled(false);
		chckbxstahlpfeiler.setEnabled(false);
		chckbxkrankenversicherung.setEnabled(false);
		chckbxrauchverbot.setEnabled(false);
		chckbxerstehilfe.setEnabled(false);
		chckbxkrankenversicherung.setEnabled(false);
		chckbxunfallversicherung.setEnabled(false);
		chckbxwerbung1.setEnabled(false);
		chckbxwerbung2.setEnabled(false);
		chckbxwerbung3.setEnabled(false);
		chckbxstaubschutz.setEnabled(false);
		chckbxwasserpumpe.setEnabled(false);
		chckbxstreikumsatzausfall.setEnabled(false);
		JButton btnForschung = new JButton("Forschen");
		btnForschung.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnForschung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxlager1.isSelected() && (geldKapital-lager1)>=0)	{
					chckbxlager1.setSelected(false);
					chckbxlager1.setEnabled(false);
					chckbxlager2.setEnabled(true);
					Forschungslager=Forschungslager+5000;
					VF.getGeldKapital();
					VF.setForschungskosten(VF.getGeldKapital()-lager1);
				}
				if (chckbxlager2.isSelected() && (geldKapital-lager2)>=0)	{
					chckbxlager2.setSelected(false);
					chckbxlager2.setEnabled(false);
					chckbxlager3.setEnabled(true);
					Forschungslager=Forschungslager+7500;
					VF.getGeldKapital();
					VF.setForschungskosten(VF.getGeldKapital()-lager2);
				}
				if (chckbxlager3.isSelected() && (geldKapital-lager3)>=0)	{
					chckbxlager3.setSelected(false);
					chckbxlager3.setEnabled(false);
					Forschungslager=Forschungslager+12500;
					VF.getGeldKapital();
					VF.setForschungskosten(VF.getGeldKapital()-lager3);
				}
				if (chckbxarbeiter1.isSelected() && (geldKapital-arbeiter1)>=0)	{
					chckbxarbeiter1.setSelected(false);
					chckbxarbeiter1.setEnabled(false);
					chckbxarbeiter2.setEnabled(true);
					Forschungsabbau=Forschungsabbau+(maxAbbau/100*10);
					VF.getGeldKapital();
					VF.setForschungskosten(VF.getGeldKapital()-arbeiter1);
				}
				if (chckbxarbeiter2.isSelected() && (geldKapital-arbeiter2)>=0)	{
					chckbxarbeiter2.setSelected(false);
					chckbxarbeiter2.setEnabled(false);
					chckbxarbeiter3.setEnabled(true);
					Forschungsabbau=Forschungsabbau+(maxAbbau/100*10);
					VF.getGeldKapital();
					VF.setForschungskosten(VF.getGeldKapital()-arbeiter2);
				}
				if (chckbxarbeiter3.isSelected() && (geldKapital-arbeiter3)>=0)	{
					chckbxarbeiter3.setSelected(false);
					chckbxarbeiter3.setEnabled(false);
					Forschungsabbau=Forschungsabbau+(maxAbbau/100*10);
					VF.getGeldKapital();
					VF.setForschungskosten(VF.getGeldKapital()-arbeiter3);
				}
				if (chckbxpressluft.isSelected() && (geldKapital-pressluft)>=0)	{
					chckbxpressluft.setSelected(false);
					chckbxpressluft.setEnabled(false);
					Forschungsabbau=Forschungsabbau+(maxAbbau/100*10);
					VF.getGeldKapital();
					VF.setForschungskosten(VF.getGeldKapital()-pressluft);
				}
				if (chckbxbagger.isSelected() && (geldKapital-bagger)>=0)	{
					chckbxbagger.setSelected(false);
					chckbxbagger.setEnabled(false);
					Forschungsabbau=Forschungsabbau+(maxAbbau/100*25);
					VF.getGeldKapital();
					VF.setForschungskosten(VF.getGeldKapital()-bagger);
				}
				if (chckbxSbagger.isSelected() && (geldKapital-s_bagger)>=0)	{
					chckbxSbagger.setSelected(false);
					chckbxSbagger.setEnabled(false);
					Forschungsabbau=Forschungsabbau+(maxAbbau*2);
					VF.getGeldKapital();
					VF.setForschungskosten(VF.getGeldKapital()-s_bagger);
				}
				if (chckbxkanarien.isSelected() && (geldKapital-kanarien)>=0)	{
					chckbxkanarien.setSelected(false);
					chckbxkanarien.setEnabled(false);
					geldKapital= geldKapital-kanarien;
				}
				if (chckbxholzpfeiler.isSelected() && (geldKapital-holzpfeiler)>=0){
					chckbxholzpfeiler.setSelected(false);
					chckbxholzpfeiler.setEnabled(false);
					chckbxstahlpfeiler.setEnabled(true);
					geldKapital= geldKapital-holzpfeiler;
				}
				if (chckbxstahlpfeiler.isSelected() && (geldKapital-stahlpfeiler)>=0)	{
					chckbxstahlpfeiler.setSelected(false);
					chckbxstahlpfeiler.setEnabled(false);
					geldKapital= geldKapital-stahlpfeiler;
				}
				if (chckbxwasserpumpe.isSelected() && (geldKapital-wasserpumpen)>=0){
					chckbxwasserpumpe.setSelected(false);
					chckbxwasserpumpe.setEnabled(false);
					geldKapital= geldKapital-wasserpumpen;
				}
				if (chckbxrauchverbot.isSelected() && (geldKapital-rauchverbot)>=0)	{
					chckbxrauchverbot.setSelected(false);
					chckbxrauchverbot.setEnabled(false);
					geldKapital= geldKapital-rauchverbot;
				}
				if (chckbxstaubschutz.isSelected() && (geldKapital-staubschutz)>=0)	{
					chckbxstaubschutz.setSelected(false);
					chckbxstaubschutz.setEnabled(false);
					geldKapital= geldKapital-staubschutz;
				}
				if (chckbxerstehilfe.isSelected() && (geldKapital-erstehilfe)>=0)	{
					chckbxerstehilfe.setSelected(false);
					chckbxerstehilfe.setEnabled(false);
					geldKapital= geldKapital-erstehilfe;
				}
				if (chckbxkrankenversicherung.isSelected() && (geldKapital-krankenversicherung)>=0)	{
					chckbxkrankenversicherung.setSelected(false);
					chckbxkrankenversicherung.setEnabled(false);
					geldKapital= geldKapital-krankenversicherung;
				}
				if (chckbxunfallversicherung.isSelected() && ( geldKapital-unfallversicherung)>=0)	{
					chckbxunfallversicherung.setSelected(false);
					chckbxunfallversicherung.setEnabled(false);
					geldKapital= geldKapital-unfallversicherung;
				}
				if (chckbxstreikumsatzausfall.isSelected() && ( geldKapital-streik_umsatzausfall)>=0)	{
					chckbxstreikumsatzausfall.setSelected(false);
					chckbxstreikumsatzausfall.setEnabled(false);
					geldKapital=  geldKapital-streik_umsatzausfall;
				}
				if (chckbxwerbung1.isSelected() && ( geldKapital-werbung1)>=0)	{
					chckbxwerbung1.setEnabled(true);
					 geldKapital=  geldKapital-werbung1;
				}
				if (chckbxwerbung2.isSelected() && ( geldKapital-werbung2)>=0)	{
					chckbxwerbung2.setEnabled(true);
					 geldKapital=  geldKapital-werbung2;
				}
				if (chckbxwerbung3.isSelected() && ( geldKapital-werbung3)>=0)	{
					chckbxwerbung3.setEnabled(true);
					 geldKapital=  geldKapital-werbung3;
				}
				lblKapitalForschung.setText(geldKapital+"€");
			}
		});
		btnForschung.setBounds(476, 437, 239, 109);
		panel_1.add(btnForschung);

		JLabel lblForschungBild = new JLabel(iconforschung);
		lblForschungBild.setBounds(0, 0, 1280, 800);
		panel_1.add(lblForschungBild);
		lblForschungBild.setBounds(-5, -10, 1280, 800);
	}
}