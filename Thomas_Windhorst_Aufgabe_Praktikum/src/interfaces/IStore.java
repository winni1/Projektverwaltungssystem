package interfaces;

import program.Window;

public interface IStore {

	void createConnection(Window win);
	void updateTable(Window win);
	void getProject(Window win, int id);
	void getPerson(Window win, int id);
	void eintragProjekt(	int id,
							String acronym, 
							String title, 
							String description, 
							String startdate, 
							String enddate
							);
	void eintragPerson(	int id,
						String firstname,  
						String lastname, 
						String email, 
						String phone, 
						String fax, 
						String sex, 
						String username, 
						String password
						);
	void eintragLoeschen(int id, int tabelle);
	
}
