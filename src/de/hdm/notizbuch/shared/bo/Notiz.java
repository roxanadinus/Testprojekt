package de.hdm.notizbuch.shared.bo;

import java.util.Date;

public class Notiz extends BusinessObject {

	private static final long serialVersionUID = 1L;

	// Stellt die Rubrik bzw. den Rubriknamen einer Notiz dar.
	private Rubrik rubrikName;

	// Stellt den Namen einer Notiz dar.
	private String notizName = "";

	// Stellt den Textinhalt einer Notiz dar.
	private String textInhalt = "";

	// Erstellungsdatum einer Notiz.
	private Date erstellungsdatum;
	
	//
	private Profil profil;

	// Auslesen eines Rubriknamens.
	public Rubrik getRubrikName() {
		return rubrikName;
	}

	// Setzen eines Rubriknamens.
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

	// Auslesen eines Textinhaltes einer Notiz.
	public String getTextInhalt() {
		return textInhalt;
	}

	// Setzen eines Textinhaltes einer Notiz.
	public void setTextInhalt(String textInhalt) {
		this.textInhalt = textInhalt;
	}

	// Auslesen eines Erstellungsdatum einer Notiz.
	public Date getErstellungsdatum() {
		return erstellungsdatum;
	}

	// Setzen eines Erstellungsdatum einer Notiz.
	public void setErstellungsdatum(Date erstellungsdatum) {
		this.erstellungsdatum = erstellungsdatum;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

}
