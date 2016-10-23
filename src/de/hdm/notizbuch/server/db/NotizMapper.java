
package de.hdm.notizbuch.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdm.notizbuch.shared.bo.Notiz;
import de.hdm.notizbuch.shared.bo.Profil;

/**
 * Die Mapper-Klasse <code>NotizMapper</code> bildet <code>Notiz
 * </code>-Objekte auf Datensätze in einer relationalen Datenbank ab.Durch die
 * Bereitstellung verschiedener Methoden können mit deren Hilfe beispielsweise
 * Objekte erzeugt, editiert, gelöscht oder gesucht werden. Das sogenannte
 * Mapping erfolgt bidirektional, d.h. Objekte können in DB-Strukturen und
 * DB-Strukturen in Objekte umgewandelt werden.
 * 
 */

public class NotizMapper {

	// Grundlegendes Select-Statement
	private static final String BASE_SELECT = "SELECT rubrikName, notizName, textInhalt, erstellungsdatum, profil FROM notizen ";

	/**
	 * Die Instantiierung der Klasse NotizMapper erfolgt nur einmal. Dies wird
	 * auch als <b>Singleton</b> bezeichnet. Durch den Bezeichner
	 * <code>static</code> ist die Variable nur einmal für sämtliche eventuellen
	 * Instanzen dieser Klasse vorhanden. Sie speichert die einzige Instanz der
	 * Klasse.
	 * 
	 */
	private static NotizMapper notizMapper = null;

	/**
	 * Dieser geschützte Konstruktor verhindert das Erzeugen von neuen Instanzen
	 * dieser Klasse mit dem Aufruf <code>new</code>.
	 */

	protected NotizMapper() {

	}

	/**
	 * Durch <code>NotizMapper.notizMapper()</code> kann folgende statische
	 * Methode aufgerufen werden. Durch sie wird die Singleton-Eigenschaft
	 * sichergestellt, in dem sie dafür sorgt, dass nur eine Instanz von
	 * <code>NotizMapper</code> existiert. Die Instantiierung des NotizMapper
	 * sollte immer durch den Aufruf dieser Methode erfolgen.
	 * 
	 * @return <code>NotizMapper</code>-Objekt.
	 * 
	 * @see NotizMapper#notizMapper()
	 */

	public static NotizMapper notizMapper() {
		if (notizMapper == null) {
			notizMapper = new NotizMapper();
		}
		return notizMapper;
	}

	/**
	 * Einfügen eines <code>Notiz</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 * 
	 * @param notiz
	 *            das zu speichernde Objekt
	 * @return das bereits übergebene Profil - Objekt, jedoch mit ggf.
	 *         korrigierter <code>id</code>.
	 */
	public Notiz insert(Notiz notiz) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Momentan höchsten Primärschlüsselwert prüfen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM notizen ");

			if (rs.next()) {
				/*
				 * notiz erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				notiz.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");

				// Einfügeoperation erfolgt
				String sql = "INSERT INTO notizen (id, rubrikName, notizName, textInhalt, erstellungsdatum, profil) "
						+ "VALUES ("
						+ notiz.getId()
						+ ",'"
						+ notiz.getRubrikName()
						+ "','"
						+ notiz.getNotizName()
						+ "','"
						+ notiz.getTextInhalt()
						+ "',"
						+ simpleDateFormat.format(notiz.getErstellungsdatum())
						+ ",'"
						+ notiz.getProfil() + "')";
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Rückgabe, der evtl. korrigierten Notiz.
		return notiz;
	}

	/**
	 * Wiederholtes Schreiben eines Notiz-Objekts in die Datenbank.
	 * 
	 * @param notiz
	 *            , das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */

	public Notiz update(Notiz notiz) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");

			stmt.executeUpdate("UPDATE notizen " + "SET rubrikName=\""
					+ notiz.getRubrikName() + "\", " + "notizName=\""
					+ notiz.getNotizName() + "\", " + "textInhalt=\""
					+ notiz.getTextInhalt() + "\", " + "erstellungsdatum=\""
					+ simpleDateFormat.format(notiz.getErstellungsdatum()) + "\", " + "profil=\""
					+ notiz.getProfil() + "\"" + "WHERE id=" + notiz.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		// Rückgabe, des evtl. korrigierten Profiles.
		return notiz;
	}

	/**
	 * Löschen der Daten eines <code>Notiz</code>-Objekts aus der Datenbank.
	 * 
	 * @param notiz
	 *            das aus der DB zu löschende Objekt
	 */
	public void delete(Notiz notiz) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM notizen " + "WHERE id="
					+ notiz.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Auslesen aller Notizen.
	 * 
	 * @return Eine ArrayList mit Notiz-Objekten, die sämtliche Notizen
	 *         repräsentieren. Bei evtl. Exceptions wird eine partiell gefüllte
	 *         oder ggf. auch leere ArrayList zurückgeliefert.
	 */
	public ArrayList<Notiz> findAll() {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		// Vorbereitung der Ergebnis-ArrayList
		ArrayList<Notiz> result = new ArrayList<Notiz>();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery(BASE_SELECT + "ORDER BY notizName");

			// Für jeden Eintrag im Suchergebnis wird nun ein Notiz-Objekt
			// erstellt und zur Ergebnis-ArrayList hinzugefügt.
			while (rs.next()) {
				Notiz notiz = map(rs);

				result.add(notiz);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnis-ArrayList zurückgeben
		return result;
	}

	/**
	 * Suchen einer Notiz mit vorgegebener ID. Da diese eindeutig ist, wird
	 * genau ein Objekt zurückgegeben.
	 * 
	 * @param id
	 *            Primärschlüsselattribut in DB
	 * @return Notiz-Objekt, das dem übergebenen Schlüssel entspricht, null bei
	 *         nicht vorhandenem DB-Tupel.
	 */
	public Notiz findByKey(int id) {

		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(BASE_SELECT + "WHERE id=" + id
					+ " ORDER BY notizName");

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
	 * @return Notiz-Objekt
	 */

	private Notiz map(ResultSet rs) throws SQLException {
		Notiz notiz = new Notiz();

		notiz.setId(rs.getInt("id"));
		notiz.setNotizName(rs.getString("notizName"));
		notiz.setRubrikName(rs.getString("rubrikName"));
		notiz.setTextInhalt(rs.getString("textInhalt"));
		notiz.setErstellungsdatum(rs.getDate("erstellungsdatum"));
		
		Profil profil = new Profil ();
		
		profil.setFirstName(rs.getString("firstName"));
		profil.setLastName(rs.getString("lastName"));
	
		
		return notiz;
	}

}
