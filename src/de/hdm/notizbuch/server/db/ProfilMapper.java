package de.hdm.notizbuch.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.notizbuch.shared.bo.Profil;

/**
 * Die Mapper-Klasse <code>ProfilMapper</code> bildet <code>Profile
 * </code>-Objekte auf Datensätze in einer relationalen Datenbank ab.Durch die
 * Bereitstellung verschiedener Methoden können mit deren Hilfe beispielsweise
 * Objekte erzeugt, editiert, gelöscht oder gesucht werden. Das sogenannte
 * Mapping erfolgt bidirektional, d.h. Objekte können in DB-Strukturen und
 * DB-Strukturen in Objekte umgewandelt werden.
 * 
 */

public class ProfilMapper {

	// Grundlegendes Select-Statement
	private static final String BASE_SELECT = "SELECT id, firstName, lastName, eMail, notizName, rubrikName FROM profile ";

	/**
	 * Die Instantiierung der Klasse ProfilMapper erfolgt nur einmal. Dies wird
	 * auch als <b>Singleton</b> bezeichnet. Durch den Bezeichner
	 * <code>static</code> ist die Variable nur einmal für sämtliche eventuellen
	 * Instanzen dieser Klasse vorhanden. Sie speichert die einzige Instanz der
	 * Klasse.
	 * 
	 */
	private static ProfilMapper profilMapper = null;

	/**
	 * Dieser geschützte Konstruktor verhindert das Erzeugen von neuen Instanzen
	 * dieser Klasse mit dem Aufruf <code>new</code>.
	 */

	protected ProfilMapper() {

	}

	/**
	 * Durch <code>ProfilMapper.profilMapper()</code> kann folgende statische
	 * Methode aufgerufen werden. Durch sie wird die Singleton-Eigenschaft
	 * sichergestellt, in dem sie dafür sorgt, dass nur eine Instanz von
	 * <code>ProfilMapper</code> existiert. Die Instantiierung des ProfilMapper
	 * sollte immer durch den Aufruf dieser Methode erfolgen.
	 * 
	 * @return <code>ProfilMapper</code>-Objekt.
	 * 
	 * @see ProfilMapper#profilMapper()
	 */

	public static ProfilMapper profilMapper() {
		if (profilMapper == null) {
			profilMapper = new ProfilMapper();
		}
		return profilMapper;
	}

	/**
	 * Einfügen eines <code>Profil</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 * 
	 * @param profil
	 *            das zu speichernde Objekt
	 * @return das bereits übergebene Profil - Objekt, jedoch mit ggf.
	 *         korrigierter <code>id</code>.
	 */
	public Profil insert(Profil profil) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Momentan höchsten Primärschlüsselwert prüfen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM profile ");

			if (rs.next()) {
				/*
				 * profil erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				profil.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Einfügeoperation erfolgt
				String sql = "INSERT INTO profile (id, firstName, lastName, email, notizName, rubrikName) "
						+ "VALUES ("
						+ profil.getId()
						+ ",'"
						+ profil.getFirstName()
						+ "','"
						+ profil.getLastName()
						+ "','"
						+ profil.geteMail()
						+ "',"
						+ profil.getNotizName()
						+ ",'"
						+ profil.getRubrikName()
						+ "')";
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Rückgabe, des evtl. korrigierten Profiles.
		return profil;
	}

	/**
	 * Wiederholtes Schreiben eines Profil-Objekts in die Datenbank.
	 * 
	 * @param profil
	 *            , das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */

	public Profil update(Profil profil) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE profile " + "SET firstName=\""
					+ profil.getFirstName() + "\", " + "lastName=\""
					+ profil.getLastName() + "\", " + "email=\""
					+ profil.geteMail() + "\", " + "notizName=\""
					+ profil.getNotizName() + "\", " + "rubrikName=\""
					+ profil.getRubrikName() + "\"" + "WHERE id="
					+ profil.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		// Rückgabe, des evtl. korrigierten Profiles.
		return profil;
	}

	/**
	 * Löschen der Daten eines <code>Profil</code>-Objekts aus der Datenbank.
	 * 
	 * @param profil
	 *            das aus der DB zu löschende Objekt
	 */
	public void delete(Profil profil) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM profile " + "WHERE id="
					+ profil.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Auslesen aller Profile.
	 * 
	 * @return Eine ArrayList mit Profile-Objekten, die sämtliche Profile
	 *         repräsentieren. Bei evtl. Exceptions wird eine partiell gefüllte
	 *         oder ggf. auch leere ArrayList zurückgeliefert.
	 */
	public ArrayList<Profil> findAll() {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		// Vorbereitung der Ergebnis-ArrayList
		ArrayList<Profil> result = new ArrayList<Profil>();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(BASE_SELECT + "ORDER BY lastName");

			// Für jeden Eintrag im Suchergebnis wird nun ein Profil-Objekt
			// erstellt und zur Ergebnis-ArrayList hinzugefügt.
			while (rs.next()) {
				Profil profil = map(rs);

				result.add(profil);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnis-ArrayList zurückgeben
		return result;
	}

	/**
	 * Suchen eines Profils mit vorgegebener ID. Da diese eindeutig ist, wird
	 * genau ein Objekt zurückgegeben.
	 * 
	 * @param id
	 *            Primärschlüsselattribut in DB
	 * @return Profil-Objekt, das dem übergebenen Schlüssel entspricht, null bei
	 *         nicht vorhandenem DB-Tupel.
	 */
	public Profil findByKey(int id) {

		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(BASE_SELECT + "WHERE id=" + id
					+ " ORDER BY lastName");

			/*
			 * Da id der Primärschlüssel ist, kann maximal nur ein Tupel
			 * zurückgegeben werden. Prüfung, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts

				return map(rs);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Diese Methode bildet das Resultset auf ein Java - Objekt ab.
	 * 
	 * @param rs
	 *            , das Resultset das auf ein Java-Objekt abgebildet werden soll
	 * @return Profile-Objekt
	 */

	private Profil map(ResultSet rs) throws SQLException {
		Profil profil = new Profil();
		profil.setId(rs.getInt("id"));
		profil.setFirstName(rs.getString("firstName"));
		profil.setLastName(rs.getString("lastName"));
		profil.seteMail(rs.getString("email"));
		profil.setNotizName(rs.getString("notizName"));
		profil.setRubrikName(rs.getString("rubrikName"));

		return profil;
	}

}
