package program;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import datenbank.DbConnection;
import interfaces.IStore;

public class Window extends JFrame{
	private int width;
	private int height;

	 
	private final CardLayout cardlayout = new CardLayout();
	private final JPanel cardPanel = new JPanel(cardlayout);
	private final JPanel cardA = new JPanel();
	private final JPanel cardB = new JPanel();
	
	
	// CardPanel soll durch Auswahl der ID ( Person oder Project ) verändert werden
	// wenn nötig
	private int idAuswahl = 0;
	private final JButton button1 = new JButton("Project ID");
	private final JButton button2 = new JButton("Person ID");
	private final JButton button3 = new JButton("Kategorie wechseln");
	private final JTextField txtpersonid = new JTextField();
	private final JLabel lblNewLabel_Katgoriewechsel = new JLabel("oder eingabe einer ID");
	private ArrayList<String> projektDaten = new ArrayList<String>();
	private ArrayList<String> personenDaten = new ArrayList<String>();
	private boolean new_delete = false;
	
	//// CardA
	private final JLabel lblNewLabel_A = new JLabel("personid");
	private final JLabel lblNewLabel_A_ID = new JLabel("");
	private final JLabel lblNewLabel_A1 = new JLabel("Name");
	private final JTextField txtName = new JTextField();
	private final JLabel lblNewLabel_A2 = new JLabel("Vorname");
	private final JTextField txtVorname = new JTextField();
	private final JLabel lblNewLabel_A3 = new JLabel("Password");
	private final JPasswordField passwordField = new JPasswordField();
	private final JLabel lblNewLabel_A4 = new JLabel("email");
	private final JTextField txtemail = new JTextField();
	private final JLabel lblNewLabel_A5 = new JLabel("phone");
	private final JTextField txtphone = new JTextField();
	private final JLabel lblNewLabel_A6 = new JLabel("fax");
	private final JTextField txtfax = new JTextField();
	private final JLabel lblNewLabel_A7 = new JLabel("sex");
	private final JTextField txtsex = new JTextField();
	private final JLabel lblNewLabel_A8 = new JLabel("username");
	private final JTextField txtusername = new JTextField();
	
	//// CardB
	private final JLabel lblNewLabel_B = new JLabel("projectid");
	private final JLabel lblNewLabel_B_ID = new JLabel("");
	private final JLabel lblNewLabel_B1 = new JLabel("acronym");
	private final JTextField txtacronym = new JTextField();
	private final JLabel lblNewLabel_B2 = new JLabel("title");
	private JTextField txttitle = new JTextField();
	private final JLabel lblNewLabel_B3 = new JLabel("description");
	private final JTextField txtdescription = new JTextField();
	private final JLabel lblNewLabel_B4 = new JLabel("startdate");
	private final JTextField txtstartdate = new JTextField();
	private final JLabel lblNewLabel_B5 = new JLabel("enddate");
	private final JTextField txtenddate = new JTextField();
	
	private final JPanel panel = new JPanel();
	private final JPanel panel_idAuswahl = new JPanel();
	private JPanel panel_1 = new JPanel();
	private final JButton btnNew = new JButton("new");
	private final JButton btnSave = new JButton("save");
	private final JButton btnCancel = new JButton("cancel");
	private final JButton btnDelete = new JButton("delete");
	private final JSplitPane splitPane = new JSplitPane();
	private final JSplitPane splitPane_1 = new JSplitPane();
	private final JSplitPane splitPane_2 = new JSplitPane();
	private final JTable table = new JTable();
	private final JScrollPane scrollPane = new JScrollPane(table);
	private JPanel ausgabeDaten;

	
	public Window(String title) {
		this.width = 1820;
		this.height = 980;
		
		
		eingabefelderLaenge();
		
		// splitPane Einstellungen
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		// Label und Textfelder werden zusammengefasst
		labelFuerPersonen();		
		labelFuerProjekte();
		
		// Karten werden dem CardPanel zugefügt
		cardA.setName("person");
		cardB.setName("projekt");
		cardPanel.add(cardA);
		cardPanel.add(cardB);
		
		////MS JFrame erstellen und konfigurieren
		this.setSize(1820, 980);	
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		// splitPane der ContentPane zufügen
		getContentPane().add(splitPane_2, BorderLayout.NORTH);
		
		
		// Buttons dem Panel hinzufügen
		panel.add(btnNew);
		panel.add(btnSave);
		panel.add(btnCancel);
		panel.add(btnDelete);
		panel_idAuswahl.add(button3);	
		panel_idAuswahl.add(lblNewLabel_Katgoriewechsel);	
		panel_idAuswahl.add(txtpersonid);
		panel_idAuswahl.add(button1);
		panel_idAuswahl.add(button2);	
		
		// splitPane Komponenten zuordnen
		
		splitPane_2.setLeftComponent(panel_idAuswahl);
		splitPane_2.setRightComponent(splitPane);
		splitPane.setLeftComponent(cardPanel);
		splitPane.setRightComponent(splitPane_1);
		splitPane_1.setLeftComponent(panel);
		splitPane_1.setRightComponent(panel_1);
		panel_1.add(scrollPane);
		
		// die Daten aus der Datenbank holen
		IStore dbcon = new DbConnection();
		dbcon.createConnection(this);
		
		// den Buttons ActionListener zuweisen
		btnNew.addActionListener(e ->{
			
			
			if(!wurdeDatensatzVeraendert() || new_delete) {
				System.out.println("new_delete steht auf = " + new_delete);
				if(this.idAuswahl==1) {
					dbcon.eintragProjekt(	0,
											txtacronym.getText(), 
											txttitle.getText(), 
											txtdescription.getText(), 
											txtstartdate.getText(), 
											txtenddate.getText()
											);
					dbcon.updateTable(this);
				}
				else if(!wurdeDatensatzVeraendert() || new_delete){
					dbcon.eintragPerson(	0,
											txtName.getText(), 
											txtVorname.getText(), 
											txtemail.getText(), 
											txtphone.getText(), 
											txtfax.getText(), 
											txtsex.getText(), 
											txtusername.getText(), 
											passwordField.getText()
											);
					dbcon.updateTable(this);
				}
			}
		});
		
		btnSave.addActionListener(f ->{
			if(wurdeDatensatzVeraendert()) {
				// Prüfung welche Card gerade Angezeigt wird
				if(this.idAuswahl==1) {
	
					dbcon.eintragProjekt(	Integer.parseInt( lblNewLabel_B_ID.getText().substring(2)),
											txtacronym.getText(), 
											txttitle.getText(), 
											txtdescription.getText(), 
											txtstartdate.getText(), 
											txtenddate.getText()
											);
					dbcon.updateTable(this);
				}
				else
				{
					dbcon.eintragPerson(	Integer.parseInt( lblNewLabel_A_ID.getText().substring(2)),
											txtName.getText(), 
											txtVorname.getText(), 
											txtemail.getText(), 
											txtphone.getText(), 
											txtfax.getText(), 
											txtsex.getText(), 
											txtusername.getText(), 
											passwordField.getText()
											);
					dbcon.updateTable(this);
				}
				new_delete = true;
			}
		});
		
		btnCancel.addActionListener(g ->{
			if(wurdeDatensatzVeraendert()) {
				if(this.idAuswahl==1) {
					dbcon.getProject(this, Integer.parseInt(lblNewLabel_B_ID.getText().substring(2)));			
				}
				else {
					dbcon.getProject(this, Integer.parseInt(lblNewLabel_A_ID.getText().substring(2)));			
				}
				new_delete = true;
			}
			
		});
		
		btnDelete.addActionListener(h ->{

			
			if(!wurdeDatensatzVeraendert() || new_delete) {
				int input =JOptionPane.showConfirmDialog(null, "Moechten sie wirklich loeschen?");			
				// Bei Ja löschen
				if(input == 0)
				{
					if(this.idAuswahl==1) {
						dbcon.eintragLoeschen(Integer.parseInt(lblNewLabel_B_ID.getText().substring(2)), 1);
						dbcon.updateTable(this);
					}
					else {
						dbcon.eintragLoeschen(Integer.parseInt(lblNewLabel_A_ID.getText().substring(2)), 0);
						dbcon.updateTable(this);
					}
				    
				}
				
				// bei Nein nichts machen
				if(input == 1)
				{
				}
	
				// Bei Abbrechen eventuelle Aenderungen entfernen
				if(input == 2)
				{
					if(this.idAuswahl==1) {
						dbcon.getProject(this, Integer.parseInt(lblNewLabel_B_ID.getText().substring(2)));			
					}
					else {
						dbcon.getProject(this, Integer.parseInt(lblNewLabel_A_ID.getText().substring(2)));			
					}
				}
			}
		});
		
		button1.addActionListener(e1 ->{
			if(!wurdeDatensatzVeraendert() || new_delete) {
				dbcon.getProject(this, Integer.valueOf(txtpersonid.getText()));
				// Prüfen welche Card gerade angezeigt wird und gegebenenfalls ändern
				if(this.idAuswahl == 0) {			
					cardlayout.next(cardPanel);
					this.idAuswahl++;
					
				}
				lblNewLabel_B.setText("projectid");
				new_delete = false;
			}
		});
		button2.addActionListener(e1 ->{
			if(!wurdeDatensatzVeraendert() || new_delete) {
				dbcon.getPerson(this, Integer.valueOf(txtpersonid.getText()));
				// Prüfen welche Card gerade angezeigt wird und gegebenenfalls ändern
				if(this.idAuswahl == 1) {			
						cardlayout.next(cardPanel);
						this.idAuswahl--;
				}
				lblNewLabel_A.setText("personid");
				new_delete = false;
			}
		});
		
		button3.addActionListener(e1 ->{
			if(!wurdeDatensatzVeraendert() || new_delete) {
				if(this.idAuswahl == 0) {			
					cardlayout.next(cardPanel);
					this.idAuswahl++;
					lblNewLabel_B.setText("");
					lblNewLabel_B_ID.setText(" Neueintrag Project");
				}
				else{
					cardlayout.next(cardPanel);
					this.idAuswahl--;
					lblNewLabel_A.setText("");
					lblNewLabel_A_ID.setText(" Neueintrag Person");
				}
			}
		});
		
	}//END Konstruktor
	
	/**
	 * Hier werden alle Label und Textfelder für Personen der CardA zugewiesen
	 * Auch der Button mit ActionListener
	 */
	private void labelFuerPersonen() {
		
		cardA.add(lblNewLabel_A); 
		cardA.add(lblNewLabel_A_ID);
		
		cardA.add(lblNewLabel_A1);
		cardA.add(txtName);
		
		cardA.add(lblNewLabel_A2);
		cardA.add(txtVorname);
		
		cardA.add(lblNewLabel_A3);
		cardA.add(passwordField);
		
		cardA.add(lblNewLabel_A4); 
		cardA.add(txtemail); 
		
		cardA.add(lblNewLabel_A5); 
		cardA.add(txtphone);
		
		cardA.add(lblNewLabel_A6);
		cardA.add(txtfax); 
		
		cardA.add(lblNewLabel_A7); 
		cardA.add(txtsex); 
		
		cardA.add(lblNewLabel_A8); 
		cardA.add(txtusername);
	}
	
	/**
	 * Hier werden alle Label und Textfelder für Projekte der CardB zugewiesen
	 * Auch der Button mit ActionListener
	 */
	private void labelFuerProjekte() {
		
		
		cardB.add(lblNewLabel_B);
		cardB.add(lblNewLabel_B_ID);
		
		cardB.add(lblNewLabel_B1);
		cardB.add(txtacronym);
		
		cardB.add(lblNewLabel_B2);
		cardB.add(txttitle);
		
		cardB.add(lblNewLabel_B3);
		cardB.add(txtdescription);
		
		cardB.add(lblNewLabel_B4);
		cardB.add(txtstartdate);
		
		cardB.add(lblNewLabel_B5);
		cardB.add(txtenddate);
	}
	
	/**
	 * Setzt die Länge der Eingabefelder
	 */
	private void eingabefelderLaenge() {
		// CardA
		txtpersonid.setColumns(10); 
		txtName.setColumns(10);
		txtVorname.setColumns(10);
		passwordField.setColumns(10);
		txtemail.setColumns(10);
		txtphone.setColumns(10);
		txtfax.setColumns(10); 
		txtsex.setColumns(10); 
		txtusername.setColumns(10);
		
		// CardB
		txtacronym.setColumns(10);
		txttitle.setColumns(10);
		txtdescription.setColumns(10);
		txtstartdate.setColumns(10);
		txtenddate.setColumns(10);
	}
	
	/**
	 * Ändert die Tabelle mit neuen Werten
	 * @param model
	 */
	public void tabelleSchreiben(DefaultTableModel model) {
		this.table.setModel(model); 
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.table.setFillsViewportHeight(true);
			
	}
	

	
	/**
	 * Aendert den Text in den JTextField von Project
	 * @param title
	 * @param startdate
	 * @param enddate
	 * @param acronym
	 * @param description
	 */
	public void txtAendernProjekt(	String id,	
									String title,
									String startdate,
									String enddate,
									String acronym,
									String description
									) {
		lblNewLabel_B_ID.setText("= " +id);
		txttitle.setText(title);
		txtstartdate.setText(startdate);
		txtenddate.setText(enddate);
		txtacronym.setText(acronym);
		txtdescription.setText(description);
		
		// ArrayList zur überprüfung der Daten
		// zuerst leeren falls noch Daten vorhenden 
		for(int i = projektDaten.size(); i > 0; i-- ) {
			projektDaten.remove(i-1);
		}
	
		projektDaten.add(title);
		projektDaten.add(startdate);
		projektDaten.add(enddate);
		projektDaten.add(acronym);
		projektDaten.add(description);
	}

	/**
	 * Aendert den Text in den JTextField von Person
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param phone
	 * @param fax
	 * @param sex
	 * @param username
	 * @param password
	 */
	public void txtAendernPerson(	String id,
									String firstname,
									String lastname,
									String email,
									String phone,
									String fax,
									String sex,
									String username,
									String password
									) {

			lblNewLabel_A_ID.setText("= " +id);
			txtVorname.setText(firstname);
			txtName.setText(lastname);
			txtemail.setText(email);
			txtphone.setText(phone);
			txtfax.setText(fax);
			txtsex.setText(sex);
			txtusername.setText(username);
			passwordField.setText(password);
			
			// ArrayList zur überprüfung der Daten
			// zuerst leeren falls noch Daten vorhenden 
			for(int i = personenDaten.size(); i > 0; i-- ) {
				personenDaten.remove(i-1);
			}
			personenDaten.add(firstname); 
			personenDaten.add(lastname); 
			personenDaten.add(email); 
			personenDaten.add(phone); 
			personenDaten.add(fax); 
			personenDaten.add(sex); 
			personenDaten.add(username); 
			personenDaten.add(password); 
													
	}

	/**
	 * Gibt false zurück wenn die Daten nicht verändert wurden
	 * @return
	 */
	public boolean wurdeDatensatzVeraendert() {
		if(this.idAuswahl == 0 && personenDaten.size() == 8 ) {
			if(	personenDaten.get(0).equals(txtVorname.getText()) &&
				personenDaten.get(1).equals(txtName.getText()) &&
				personenDaten.get(2).equals(txtemail.getText()) &&
				personenDaten.get(3).equals(txtphone.getText()) &&
				personenDaten.get(4).equals(txtfax.getText()) &&
				personenDaten.get(5).equals(txtsex.getText()) &&
				personenDaten.get(6).equals(txtusername.getText()) &&
				personenDaten.get(7).equals(passwordField.getText())) {
				return false;
			}
			return true;
		}
		else if(this.idAuswahl == 1 && projektDaten.size() == 5 ){
			if(	projektDaten.get(0).equals(txttitle.getText()) &&      
				projektDaten.get(1).equals(txtstartdate.getText()) &&      
				projektDaten.get(2).equals(txtenddate.getText()) &&      
				projektDaten.get(3).equals(txtacronym.getText()) &&      
				projektDaten.get(4).equals(txtdescription.getText())) {
				return false;
			}
			return true;
		}
		return false;
	}
		

	
}//END class Window
