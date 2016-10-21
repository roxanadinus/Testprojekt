package de.hdm.notizbuch.shared.bo;

public class Profil extends BusinessObject {

	private static final long serialVersionUID = 1L;
	
	private String firstName = "";
	
	private String lastName = ""; 
	
	private String eMail = ""; 
	
	private String notizName = "";
	
	private String rubrikName = "";

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getNotizName() {
		return notizName;
	}

	public void setNotizName(String notizName) {
		this.notizName = notizName;
	}

	public String getRubrikName() {
		return rubrikName;
	}

	public void setRubrikName(String rubrikName) {
		this.rubrikName = rubrikName;
	}
	
}
