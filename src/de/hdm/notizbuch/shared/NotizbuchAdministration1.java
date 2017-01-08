package de.hdm.notizbuch.shared;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.notizbuch.shared.bo.Notiz;
import de.hdm.notizbuch.shared.bo.Profil;
import de.hdm.notizbuch.shared.bo.Rubrik;




/**
 * Die Klasse <code>NotizbuchAdministration</code> ist die synchrone
 * Schnittstelle fuer eine RPC-faehige Klasse zur Verwaltung des Notizbuchs.
 * 
 * @author roxanadinus
 *
 *         Das Marker-Interface RemoteService markiert ein Interface
 *         (NotizbuchAdministration) als RPC-fähig.
 *
 *
 *         In der Hauptverwaltungsklasse werden die im System verfügbaren
 *         Funktionen deklariert.
 */

@RemoteServiceRelativePath("administration")
public interface NotizbuchAdministration1 extends RemoteService {

	/**
	 * Initialisierung des Objekts. Diese Methode ist ebenso notwendig für GWT
	 * RPC, sowie der No Argument Constructor der Implementations-Klasse
	 * {@link NotizbuchAdministrationImpl}
	 * 
	 * @throws IllegalArgumentException
	 */

	public void init() throws IllegalArgumentException;
	
	//create-Methoden
	

	/**
	 * Ein neues Nutzer-Profil wird angelegt.
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param notizName
	 * @param rubrikName
	 * @return
	 */
	public Profil createProfil(int id, String firstName, String lastName,
			String email, String notizName, String rubrikName);

	/**
	 * Eine neue Rubrik wird angelegt.
	 * 
	 * @param id
	 * @param rubrikName
	 * @param notizName
	 * @param erstellungsdatum
	 * @param farbe
	 * @param profil
	 * @return
	 */

	public Rubrik createRubrik(int id, String rubrikName, String notizName,
			Date erstellungsdatum, String farbe, Profil profil);

	/**
	 * Eine neue Notiz wird angelegt.
	 * @param id
	 * @param notizName
	 * @param rubrikName
	 * @param textInhalt
	 * @param erstellungsdatum
	 * @param profil
	 * @return
	 */
	
	public Notiz createNotiz(int id, String notizName, String rubrikName,
			String textInhalt, Date erstellungsdatum, Profil profil);

	
	// delete-Methoden
	
	/**
	 * Löschen eines Profils.
	 * @param profil
	 * @throws IllegalArgumentException
	 */
	public void delete(Profil profil) throws IllegalArgumentException;
	
	/**
	 * Löschen einer Rubrik.
	 * @param rubrik
	 * @throws IllegalArgumentException
	 */
	public void delete(Rubrik rubrik) throws IllegalArgumentException;
	
	/**
	 * Löschen einer Notiz.
	 * @param notiz
	 * @throws IllegalArgumentException
	 */
	public void delete(Notiz notiz) throws IllegalArgumentException;
	
	
	// Auslesen
	
	/**
	 * Auslesen aller Profile.
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public ArrayList<Profil> getAllProfile() throws IllegalArgumentException;
	
	/**
	 * Auslesen eines Profils anhand der Profil Id.
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Profil getProfilByKey(int id) throws IllegalArgumentException;
	
	/**
	 * Auslesen aller Rubriken.
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public ArrayList<Rubrik> getAllRubriken() throws IllegalArgumentException;
	
	/**
	 * Auslesen einer Rubrik anhand der Rubrik Id.
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public Rubrik getRubrikByKey(int id) throws IllegalArgumentException;
	
	/**
	 * Auslesen aller Notizen.
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public ArrayList<Notiz> getAllNotizen() throws IllegalArgumentException;
	
	/**
	 * Auslesen einer Notiz anhand der Notiz Id.
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public Notiz getNotizByKey(int id) throws IllegalArgumentException;
	
	
	// Speichern
	
	
	public void save(Profil profil) throws IllegalArgumentException;
	
	public void save(Rubrik rubrik) throws IllegalArgumentException;
	
	public void save(Notiz notiz) throws IllegalArgumentException;
	
	
	// Auslesen einer Rubrik eines Profils, einer Notiz eines Profils
	// Auslesen einer Notiz einer Rubrik, einer Rubrik einer Notiz
	
	/**
	 * Auslesen einer Rubrik eines Profils.
	 * @param profil
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	ArrayList<Rubrik> getRubrikOf(Profil profil)
			throws IllegalArgumentException;
	
	/**
	 * Auslesen einer Notiz eines Profils.
	 * @param profil
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	ArrayList<Notiz> getNotizOf(Profil profil)
			throws IllegalArgumentException;
	
	/**
	 * Auslesen einer Notiz einer Rubrik.
	 * @param rubrik
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	ArrayList<Notiz> getNotizOf(Rubrik rubrik)
			throws IllegalArgumentException;
	
	/**
	 * Auslesen einer Rubrik einer Notiz.
	 * @param notiz
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	ArrayList<Rubrik> getNotizOf(Notiz notiz)
			throws IllegalArgumentException;
	
	
}