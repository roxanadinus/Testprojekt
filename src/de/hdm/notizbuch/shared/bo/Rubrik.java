package de.hdm.notizbuch.shared.bo;

import java.util.Date; 

public class Rubrik extends BusinessObject {

	private static final long serialVersionUID = 1L;

	// Stellt die Rubrik bzw. den Rubriknamen dar.
	private Rubrik rubrikName;

	// Stellt den Namen einer zur Rubrik zugehörigen Notiz dar.
	private String notizName = "";

	// Erstellungsdatum einer Rubrik.
	private Date erstellungsdatum;

	// Stellt eine Farbe einer Rubrik dar.
	private String farbe = "";
	
	//
	private Profil profil;
	

	// Auslesen eines Rubriknamens.
	public Rubrik getRubrikName() {
		return rubrikName;
	}

	// Setzen eins Rubriknamens.
	public void setRubrikName(Rubrik rubrikName) {
		this.rubrikName = rubrikName;
	}

	// Auslesen eines Notiznamens.
	public String getNotizName() {
		return notizName;
	}

	// Setzen eines Notiznamens.
	public void setNotizName(String notizName) {
		this.notizName = notizName;
	}

	// Auslesen eines Erstellungsdatum einer Rubrik.
	public Date getErstellungsdatum() {
		return erstellungsdatum;
	}

	// Setzen eines Erstellungsdatum einer Rubrik.
	public void setErstellungsdatum(Date erstellungsdatum) {
		this.erstellungsdatum = erstellungsdatum;
	}

	// Auslesen einer Farbe einer Rubrik.
	public String getFarbe() {
		return farbe;
	}

	// Setzen einer Farbe einer Rubrik.
	public void setFarbe(String farbe) {
		this.farbe = farbe;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

}
