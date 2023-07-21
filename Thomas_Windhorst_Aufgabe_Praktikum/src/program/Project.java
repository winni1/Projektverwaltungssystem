package program;

public class Project {

	public int projectid;
	public String acronym;
	public String title;
	public String description;
	public String startdate;
	public String enddate;
	

	public Project() {};
	
	public Project(	
					String acronym,
					String title,
					String description,
					String startdate,
					String enddate) 
	{
		this.acronym = acronym;
		this.title = title;
		this.description = description;
		this.startdate = startdate;
		this.enddate = enddate;
	}//END Konstruktor
}//END class Project
