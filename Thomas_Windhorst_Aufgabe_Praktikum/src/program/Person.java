package program;

public class Person {

	public int personid;
	public String firstname;
	public String lastname;
	public String email;
	public String phone;
	public String fax;
	public String sex;
	public String username;
	public String password;

	
	public Person() {};
	
	public Person(	
					String firstname,
					String lastname,
					String email,
					String phone,
					String fax,
					String sex,
					String username,
					String password) 
	{
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.fax = fax;
		this.sex = sex;
		this.username = username;
		this.password = password;
	}//END Konstruktor
}//END class Person
