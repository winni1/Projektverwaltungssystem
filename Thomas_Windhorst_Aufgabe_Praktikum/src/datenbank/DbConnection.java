package datenbank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import interfaces.IStore;
import program.Window;

public class DbConnection implements IStore{

	private Connection con;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/fuerbalance";
	private static final String USER = "root";
	private static final String PASS = "";
	private ArrayList<String> ausgabe = new ArrayList<String>();
	private String[] columnNames = {"Id", "Name","Startdatum","Enddatum"};
	private DefaultTableModel model = new DefaultTableModel();
	
	/**
	 * Erstellt Verbindung zur Datenbank und startet mit Ergebnis die Methode
	 * tabelleSchreiben() um es anzeigen zu können
	 */
	@Override
	public void createConnection(Window win) {
		
		model.setColumnIdentifiers(columnNames);
			
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			String QUERY = "SELECT * FROM project";
						
			Statement stmt = con.prepareStatement(QUERY);
	        ResultSet rs = stmt.executeQuery(QUERY);
			
	         while (rs.next()) {           
	        	 model.addRow(new Object[]{	rs.getInt("projectid"), 
	        			 					rs.getString("title"), 
	        			 					rs.getString("startdate"),
	        			 					rs.getString("enddate")
	        			 					});        	 
	         }
	         
	         // Werte aus DB in Tabelle eintragen
	         win.tabelleSchreiben(model);
	         
	         con.close();
	         
		} catch (SQLException e) {
			System.err.println("Verbindung zur Datenbank ist fehlgeschlagen.");
			e.printStackTrace();
		}	
	}

	/**
	 * Aendert die Eintraege in den JTextFields bei Projekten 
	 * mit den Datenbankeintraegen
	 */
	@Override
	public void getProject(Window win, int id) {
		
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			String QUERY = "SELECT * FROM project WHERE projectid=?";
			PreparedStatement pstmt = con.prepareStatement(QUERY);
			pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
			
	        
	        while (rs.next()) {
		        win.txtAendernProjekt(	rs.getString("projectid").toString(),
		        						rs.getString("title").toString(),
				        				rs.getString("startdate").toString(),
				        				rs.getString("enddate").toString(),
				        				rs.getString("acronym").toString(),
				        				rs.getString("description").toString()
				        				);
	        } 
	         con.close();
			
			
		} catch (SQLException e) {
			System.err.println("Verbindung zur Datenbank ist fehlgeschlagen.");
			e.printStackTrace();		
		}	
	}

	
	/**
	 * Aendert die Eintraege in den JTextFields bei Personen 
	 * mit den Datenbankeintraegen
	 */
	@Override
	public void getPerson(Window win, int id) {
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			String QUERY = "SELECT * FROM person WHERE personid=?";
			PreparedStatement pstmt = con.prepareStatement(QUERY);
			pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
			
	        while (rs.next()) {
		        win.txtAendernPerson(	rs.getString("personid").toString(),
		        						rs.getString("firstname").toString(),
		        						rs.getString("lastname").toString(),
		        						rs.getString("email").toString(),
		        						rs.getString("phone").toString(),
		        						rs.getString("fax").toString(),
		        						rs.getString("sex").toString(),
		        						rs.getString("username").toString(),
		        						rs.getString("password").toString()
		        					);
	        } 
	         con.close();
			
			
		} catch (SQLException e) {
			System.err.println("Verbindung zur Datenbank ist fehlgeschlagen.");
			e.printStackTrace();		
		}	
		
	}

	/**
	 * Erstellt oder Ändert ein Projekt in der Tabelle Project
	 */
	@Override
	public void eintragProjekt(	int id,
								String acronym, 
								String title, 
								String description, 
								String startdate, 
								String enddate) {
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			if(id==0) {
				String QUERY = "INSERT INTO project (acronym, title, description, startdate, enddate) VALUES (?, ?, ?, ?, ?);";
				
				PreparedStatement pstmt = con.prepareStatement(QUERY);
				
				pstmt.setString(1, acronym);
				pstmt.setString(2, title);
				pstmt.setString(3, description);
				pstmt.setString(4, startdate);
				pstmt.setString(5, enddate);
				
				pstmt.executeUpdate();
			}
			else {
				String QUERY = "UPDATE project SET acronym = ?, title = ?, description = ?, startdate = ?, enddate = ? WHERE project.projectid =?;";
//				String QUERY = "UPDATE project SET acronym = ?, title = ?, description = ?, startdate = ?, enddate = ? WHERE project.projectid =" + id +";";
				
				PreparedStatement pstmt = con.prepareStatement(QUERY);
				
				pstmt.setString(1, acronym);
				pstmt.setString(2, title);
				pstmt.setString(3, description);
				pstmt.setString(4, startdate);
				pstmt.setString(5, enddate);
				pstmt.setInt(5, id);
				
				pstmt.executeUpdate();
			}
			
		
			con.close();
		} catch (SQLException e) {
			System.err.println("Verbindung zur Datenbank ist fehlgeschlagen.\nDas neue Projekt wurde nicht in der Datenbank gespeichert");
			e.printStackTrace();
		}
	}

	/**
	 * Erstellt oder Ändert eine Person in der Tabelle Person
	 */
	@Override
	public void eintragPerson(	int id,
								String firstname,  
								String lastname, 
								String email, 
								String phone, 
								String fax, 
								String sex, 
								String username, 
								String password) {

		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			if(id==0) {
			String QUERY = "INSERT INTO person (firstname, lastname, email, phone, fax, sex, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
			
			PreparedStatement pstmt = con.prepareStatement(QUERY);			
			pstmt.setString(1, firstname);
			pstmt.setString(2, lastname);
			pstmt.setString(3, email);
			pstmt.setString(4, phone);
			pstmt.setString(5, fax);	
			pstmt.setString(6, sex);
			pstmt.setString(7, username);
			pstmt.setString(8, password);		
			pstmt.executeUpdate();
			}
			else {
				String QUERY = "UPDATE person SET firstname = ?, lastname = ?, email = ?, phone = ?, fax = ?, sex = ?, username = ?, password = ? WHERE person.personid =?;";
				 		
				PreparedStatement pstmt = con.prepareStatement(QUERY);			
				pstmt.setString(1, firstname);
				pstmt.setString(2, lastname);
				pstmt.setString(3, email);
				pstmt.setString(4, phone);
				pstmt.setString(5, fax);	
				pstmt.setString(6, sex);
				pstmt.setString(7, username);
				pstmt.setString(8, password);		
				pstmt.setInt(8, id);		
				pstmt.executeUpdate();
			}
			con.close();
		} catch (SQLException e) {
			System.err.println("Verbindung zur Datenbank ist fehlgeschlagen.");
			e.printStackTrace();
		}
		
	}

	/**
	 * Löscht einen Eintrag nach ID in einer der Tabellen
	 * (Person oder Project)
	 */
	@Override
	public void eintragLoeschen(int id, int tabelle) {
		
		try {
			if(tabelle == 1) {
				con = DriverManager.getConnection(DB_URL, USER, PASS);
				String QUERY = "DELETE FROM project WHERE project.projectid =?;";
				PreparedStatement pstmt = con.prepareStatement(QUERY);
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
				con.close();
			}
			else {
				con = DriverManager.getConnection(DB_URL, USER, PASS);
				String QUERY = "DELETE FROM person WHERE person.personid =?;";
				PreparedStatement pstmt = con.prepareStatement(QUERY);
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
				con.close();
			}
		} catch (SQLException e) {
			System.err.println("Verbindung zur Datenbank ist fehlgeschlagen.");
			e.printStackTrace();
		}
	}

	/**
	 * Erneuert die Daten in der Tabelle
	 */
	@Override
	public void updateTable(Window win) {
		
		// alte Einträge entfernen
		for(int i = model.getRowCount(); i > 0;i--) {
			model.removeRow(i-1);
		}
		
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			String QUERY = "SELECT * FROM project";
						
			Statement stmt = con.prepareStatement(QUERY);
	        ResultSet rs = stmt.executeQuery(QUERY);
			
	        // neue Einträge hinzufügen
	         while (rs.next()) {           
	        	 model.addRow(new Object[]{	rs.getInt("projectid"), 
	        			 					rs.getString("title"), 
	        			 					rs.getString("startdate"),
	        			 					rs.getString("enddate")
	        			 					});        	 
	         }
	         
	         // Werte aus DB in Tabelle eintragen
	         win.tabelleSchreiben(model);
	         
	         con.close();
		} catch (SQLException e) {
			System.err.println("Verbindung zur Datenbank ist fehlgeschlagen.");
			e.printStackTrace();
		}
	}
	
	

}
