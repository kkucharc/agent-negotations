package dao.domain;

public class Client {
	private int id;
	private String name;
	private String surname;
	private String password;
	
	public Client(String name, String surname, String pass){
		this.name = name;
		this.surname = surname;
		this.password = pass;
	}
	public Client(){
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
