package de.hdm.notizbuch.server;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.notizbuch.server.db.NotizMapper;
import de.hdm.notizbuch.server.db.ProfilMapper;
import de.hdm.notizbuch.server.db.RubrikMapper;
import de.hdm.notizbuch.shared.NotizbuchAdministration1;
import de.hdm.notizbuch.shared.bo.Notiz;
import de.hdm.notizbuch.shared.bo.Profil;
import de.hdm.notizbuch.shared.bo.Rubrik;

public class NotizbuchAdministrationImpl extends RemoteServiceServlet implements
		NotizbuchAdministration1 {

	private static final long serialVersionUID = 1L;

	/**
	 * Referenz auf den ProfilMapper. Dieser ermöglicht den Datenbankzugriff auf
	 * die Profile-Tabelle.
	 */

	private ProfilMapper profilMapper = null;

	/**
	 * Referenz auf den RubrikMapper. Dieser ermöglicht den Datenbankzugriff auf
	 * die Rubriken-Tabelle.
	 */

	private RubrikMapper rubrikMapper = null;

	/**
	 * Referenz auf den NotizMapper. Dieser ermöglicht den Datenbankzugriff auf
	 * die Notizen-Tabelle.
	 */

	private NotizMapper notizMapper = null;

	/**
	 * No-Argument Constructor muss vorhanden sein.
	 * 
	 * @throws IllegalArgumentException
	 */

	public NotizbuchAdministrationImpl() throws IllegalArgumentException {

	}

	/**
	 * Instanzenmethode zur Initialisierung der jeweiligen Instanzen. Für jede
	 * Instanz der <code>NotizbuchAdministrationImpl</code> muss diese Methode
	 * aufgerufen werden.
	 * 
	 */
	public void init() throws IllegalArgumentException {
		this.profilMapper = ProfilMapper.profilMapper();
		this.rubrikMapper = RubrikMapper.rubrikMapper();
		this.notizMapper = NotizMapper.notizMapper();

	}

	/**
	 * Ein neues Nutzer-Profil wird angelegt. Dies führt zu einer Speicherung
	 * des neuen Profils in der Datenbank.
	 */

	@Override
	public Profil createProfil(int id, String firstName, String lastName,
			String email, String notizName, String rubrikName) {

		Profil p = new Profil();
		p.setId(id);
		p.setFirstName(firstName);
		p.setLastName(lastName);
		p.seteMail(email);
		p.setNotizName(notizName);
		p.setRubrikName(rubrikName);

		return this.profilMapper.insert(p);

	}

	/**
	 * Eine neue Rubrik wird angelegt. Dies führt zu einer Speicherung der neuen
	 * Rubrik in der Datenbank.
	 */

	@Override
	public Rubrik createRubrik(int id, String rubrikName, String notizName,
			Date erstellungsdatum, String farbe, Profil profil) {

		Rubrik r = new Rubrik();
		r.setId(id);
		r.setRubrikName(rubrikName);
		r.setNotizName(notizName);
		r.setErstellungsdatum(erstellungsdatum);
		r.setFarbe(farbe);
		r.setProfil(profil);

		return this.rubrikMapper.insert(r);
	}

	/**
	 * Eine neue Notiz wird angelegt. Dies führt zu einer Speicherung der neuen
	 * Notiz in der Datenbank.
	 */

	@Override
	public Notiz createNotiz(int id, String notizName, String rubrikName,
			String textInhalt, Date erstellungsdatum, Profil profil) {

		Notiz n = new Notiz();
		n.setId(id);
		n.setNotizName(notizName);
		n.setRubrikName(rubrikName);
		n.setTextInhalt(textInhalt);
		n.setErstellungsdatum(erstellungsdatum);
		n.setProfil(profil);

		return this.notizMapper.insert(n);
	}

	/**
	 * Löschen eines Profils aus der Datenbank.
	 */

	// Erst notiz löschen, dann rubrik löschen

	@Override
	public void delete(Profil profil) throws IllegalArgumentException {

		ArrayList<Notiz> notiz = this.getNotizOf(profil);
		ArrayList<Rubrik> rubrik = this.getRubrikOf(profil);

		if (notiz != null) {
			for (Notiz n : notiz) {
				this.delete(n);
			}

			if (rubrik != null) {
				for (Rubrik r : rubrik) {
					this.delete(r);
				}

				this.profilMapper.delete(profil);

			}
		}
	}

	/**
	 * Löschen einer Rubrik aus der Datenbank.
	 */

	// TODO erst notiz löschen

	@Override
	public void delete(Rubrik rubrik) throws IllegalArgumentException {

		ArrayList<Notiz> notiz = this.getNotizOf(rubrik);

		if (notiz != null) {
			for (Notiz n : notiz) {
				this.delete(n);
			}

			this.rubrikMapper.delete(rubrik);
		}
	}

	/**
	 * Löschen einer Notiz aus der Datenbank.
	 */

	@Override
	public void delete(Notiz notiz) throws IllegalArgumentException {

		this.notizMapper.delete(notiz);
	}

	/**
	 * Auslesen aller Profile aus der Datenbank.
	 */

	@Override
	public ArrayList<Profil> getAllProfile() throws IllegalArgumentException {
		return this.profilMapper.findAll();
	}

	/**
	 * Auslesen eines Profils aus der Datenbank anhand dessen Profil-Id.
	 */
	@Override
	public Profil getProfilByKey(int id) throws IllegalArgumentException {
		return this.profilMapper.findByKey(id);

	}

	/**
	 * Auslesen aller Rubriken aus der Datenbank.
	 */

	@Override
	public ArrayList<Rubrik> getAllRubriken() throws IllegalArgumentException {
		return this.rubrikMapper.findAll();
	}

	/**
	 * Auslesen einer Rubrik aus der Datenbank anhand dessen Rubrik-Id.
	 */
	@Override
	public Rubrik getRubrikByKey(int id) throws IllegalArgumentException {
		return this.rubrikMapper.findByKey(id);

	}

	/**
	 * Auslesen aller Notizen aus der Datenbank.
	 */

	@Override
	public ArrayList<Notiz> getAllNotizen() throws IllegalArgumentException {
		return this.notizMapper.findAll();
	}

	/**
	 * Auslesen einer Notiz aus der Datenbank anhand dessen Notiz-Id.
	 */
	@Override
	public Notiz getNotizByKey(int id) throws IllegalArgumentException {
		return this.notizMapper.findByKey(id);

	}

	/**
	 * Speichern eines Profils in der Datenbank.
	 */
	@Override
	public void save(Profil profil) throws IllegalArgumentException {
		if (profil.getId() != 0) {
			profilMapper.update(profil);
		} else {
			profilMapper.insert(profil);
		}

	}

	/**
	 * Speichern einer Rubrik in der Datenbank.
	 */
	@Override
	public void save(Rubrik rubrik) throws IllegalArgumentException {
		if (rubrik.getId() != 0) {
			rubrikMapper.update(rubrik);
		} else {
			rubrikMapper.insert(rubrik);
		}

	}

	/**
	 * Speichern einer Notiz in der Datenbank.
	 */
	@Override
	public void save(Notiz notiz) throws IllegalArgumentException {
		if (notiz.getId() != 0) {
			notizMapper.update(notiz);
		} else {
			notizMapper.insert(notiz);
		}

	}

	/**
	 * Auslesen einer Rubrik eines Profils aus der Datenbank.
	 * 
	 */
	@Override
	public ArrayList<Rubrik> getRubrikOf(Profil profil)
			throws IllegalArgumentException {

		return this.rubrikMapper.findByProfil(profil);
	}

	/**
	 * Auslesen einer Notiz eines Profils aus der Datenbank.
	 * 
	 */
	@Override
	public ArrayList<Notiz> getNotizOf(Profil profil)
			throws IllegalArgumentException {

		return this.notizMapper.findByProfil(profil);
	}

	/**
	 * Auslesen einer Notiz einer Rubrik aus der Datenbank.
	 * 
	 */
	@Override
	public ArrayList<Notiz> getNotizOf(Rubrik rubrik)
			throws IllegalArgumentException {

		return this.notizMapper.findByRubrik(rubrik);
	}

	/**
	 * Auslesen einer Rubrik einer Notiz aus der Datenbank.
	 */
	
	@Override
	public ArrayList<Rubrik> getNotizOf(Notiz notiz)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
