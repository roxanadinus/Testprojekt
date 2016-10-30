
package de.hdm.notizbuch.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdm.notizbuch.shared.bo.Notiz;
import de.hdm.notizbuch.shared.bo.Profil;
import de.hdm.notizbuch.shared.bo.Rubrik;

/**
 * Die Mapper-Klasse <code>NotizMapper</code> bildet <code>Notiz
 * </code>-Objekte auf Datens��tze in einer relationalen Datenbank ab.Durch die
 * Bereitstellung verschiedener Methoden k��nnen mit deren Hilfe beispielsweise
 * Objekte erzeugt, editiert, gel��scht oder gesucht werden. Das sogenannte
 * Mapping erfolgt bidirektional, d.h. Objekte k��nnen in DB-Strukturen und
 * DB-Strukturen in Objekte umgewandelt werden.
 * 
 */

public class NotizMapper {

	// Grundlegendes Select-Statement
	private static final String BASE_SELECT = "SELECT rubrikName, notizName, textInhalt, erstellungsdatum, profil FROM notizen ";

	/**
	 * Die Instantiierung der Klasse NotizMapper erfolgt nur einmal. Dies wird
	 * auch als <b>Singleton</b> bezeichnet. Durch den Bezeichner
	 * <code>static</code> ist die Variable nur einmal f��r s��mtliche eventuellen
	 * Instanzen dieser Klasse vorhanden. Sie speichert die einzige Instanz der
	 * Klasse.
	 * 
	 */
	private static NotizMapper notizMapper = null;

	/**
	 * Dieser gesch��tzte Konstruktor verhindert das Erzeugen von neuen Instanzen
	 * dieser Klasse mit dem Aufruf <code>new</code>.
	 */

	protected NotizMapper() {

	}

	/**
	 * Durch <code>NotizMapper.notizMapper()</code> kann folgende statische
	 * Methode aufgerufen werden. Durch sie wird die Singleton-Eigenschaft
	 * sichergestellt, in dem sie daf��r sorgt, dass nur eine Instanz von
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
	 * Einf��gen eines <code>Notiz</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Prim��rschl��ssel des ��bergebenen Objekts gepr��ft und ggf.
	 * berichtigt.
	 * 
	 * @param notiz
	 *            das zu speichernde Objekt
	 * @return das bereits ��bergebene Profil - Objekt, jedoch mit ggf.
	 *         korrigierter <code>id</code>.
	 */
	public Notiz insert(Notiz notiz) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Momentan h��chsten Prim��rschl��sselwert pr��fen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM notizen ");

			if (rs.next()) {
				/*
				 * notiz erh��lt den bisher maximalen, nun um 1 inkrementierten
				 * Prim��rschl��ssel.
				 */
				notiz.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");

				// Einf��geoperation erfolgt
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

		// R��ckgabe, der evtl. korrigierten Notiz.
		return notiz;
	}

	/**
	 * Wiederholtes Schreiben eines Notiz-Objekts in die Datenbank.
	 * 
	 * @param notiz
	 *            , das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter ��bergebene Objekt
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
		// R��ckgabe, des evtl. korrigierten Profiles.
		return notiz;
	}

	/**
	 * L��schen der Daten eines <code>Notiz</code>-Objekts aus der Datenbank.
	 * 
	 * @param notiz
	 *            das aus der DB zu l��schende Objekt
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
	 * @return Eine ArrayList mit Notiz-Objekten, die s��mtliche Notizen
	 *         repr��sentieren. Bei evtl. Exceptions wird eine partiell gef��llte
	 *         oder ggf. auch leere ArrayList zur��ckgeliefert.
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

			// F��r jeden Eintrag im Suchergebnis wird nun ein Notiz-Objekt
			// erstellt und zur Ergebnis-ArrayList hinzugef��gt.
			while (rs.next()) {
				Notiz notiz = map(rs);

				result.add(notiz);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnis-ArrayList zur��ckgeben
		return result;
	}

	/**
	 * Suchen einer Notiz mit vorgegebener ID. Da diese eindeutig ist, wird
	 * genau ein Objekt zur��ckgegeben.
	 * 
	 * @param id
	 *            Prim��rschl��sselattribut in DB
	 * @return Notiz-Objekt, das dem ��bergebenen Schl��ssel entspricht, null bei
	 *         nicht vorhandenem DB-Tupel.
	 */
	public Notiz findByKey(int id) {

		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausf��llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(BASE_SELECT + "WHERE id=" + id
					+ " ORDER BY notizName");

			/*
			 * Da id der Prim��rschl��ssel ist, kann maximal nur ein Tupel
			 * zur��ckgegeben werden. Pr��fung, ob ein Ergebnis vorliegt.
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

	//TODO
	public ArrayList<Notiz> findByProfil(int id) {
		return findByProfil(id);
	}

	
	//TODO
	public ArrayList<Notiz> findByRubrik(int id) {
		return findByRubrik(id);
	}

	public ArrayList<Notiz> findByProfil(Profil profil) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Notiz> findByRubrik(Rubrik rubrik) {
		// TODO Auto-generated method stub
		return null;
	}

}
