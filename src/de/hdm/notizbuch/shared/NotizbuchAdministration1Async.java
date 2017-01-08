package de.hdm.notizbuch.shared;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.notizbuch.shared.bo.Notiz;
import de.hdm.notizbuch.shared.bo.Profil;
import de.hdm.notizbuch.shared.bo.Rubrik;

public interface NotizbuchAdministration1Async {

	void createNotiz(int id, String notizName, String rubrikName,
			String textInhalt, Date erstellungsdatum, Profil profil,
			AsyncCallback<Notiz> callback);

	void createProfil(int id, String firstName, String lastName, String email,
			String notizName, String rubrikName, AsyncCallback<Profil> callback);

	void createRubrik(int id, String rubrikName, String notizName,
			Date erstellungsdatum, String farbe, Profil profil,
			AsyncCallback<Rubrik> callback);

	void delete(Profil profil, AsyncCallback<Void> callback);

	void delete(Rubrik rubrik, AsyncCallback<Void> callback);

	void delete(Notiz notiz, AsyncCallback<Void> callback);

	void getAllNotizen(AsyncCallback<ArrayList<Notiz>> callback);

	void getAllProfile(AsyncCallback<ArrayList<Profil>> callback);

	void getAllRubriken(AsyncCallback<ArrayList<Rubrik>> callback);

	void getNotizByKey(int id, AsyncCallback<Notiz> callback);

	void getNotizOf(Profil profil, AsyncCallback<ArrayList<Notiz>> callback);

	void getNotizOf(Rubrik rubrik, AsyncCallback<ArrayList<Notiz>> callback);

	void getNotizOf(Notiz notiz, AsyncCallback<ArrayList<Rubrik>> callback);

	void getProfilByKey(int id, AsyncCallback<Profil> callback);

	void getRubrikByKey(int id, AsyncCallback<Rubrik> callback);

	void getRubrikOf(Profil profil, AsyncCallback<ArrayList<Rubrik>> callback);

	void init(AsyncCallback<Void> callback);

	void save(Profil profil, AsyncCallback<Void> callback);

	void save(Rubrik rubrik, AsyncCallback<Void> callback);

	void save(Notiz notiz, AsyncCallback<Void> callback);

}
