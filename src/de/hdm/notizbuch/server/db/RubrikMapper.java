package de.hdm.notizbuch.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdm.notizbuch.shared.bo.Profil;
import de.hdm.notizbuch.shared.bo.Rubrik;


/**
 * Die Mapper-Klasse <code>RubrikMapper</code> bildet <code>Rubrik
 * </code>-Objekte auf Datensätze in einer relationalen Datenbank ab.Durch die
 * Bereitstellung verschiedener Methoden können mit deren Hilfe beispielsweise
 * Objekte erzeugt, editiert, gelöscht oder gesucht werden. Das sogenannte
 * Mapping erfolgt bidirektional, d.h. Objekte können in DB-Strukturen und
 * DB-Strukturen in Objekte umgewandelt werden.
 * 
 */


public class RubrikMapper {

	// Grundlegendes Select-Statement
	private static final String BASE_SELECT = "SELECT rubrikName, notizName, erstellungsdatum, farbe, profil FROM rubriken ";

	/**
	 * Die Instantiierung der Klasse RubrikMapper erfolgt nur einmal. Dies wird
	 * auch als <b>Singleton</b> bezeichnet. Durch den Bezeichner
	 * <code>static</code> ist die Variable nur einmal für sämtliche eventuellen
	 * Instanzen dieser Klasse vorhanden. Sie speichert die einzige Instanz der
	 * Klasse.
	 * 
	 */
	private static RubrikMapper rubrikMapper = null;

	/**
	 * Dieser geschützte Konstruktor verhindert das Erzeugen von neuen Instanzen
	 * dieser Klasse mit dem Aufruf <code>new</code>.
	 */

	protected RubrikMapper() {

	}

	/**
	 * Durch <code>RubrikMapper.rubrikMapper()</code> kann folgende statische
	 * Methode aufgerufen werden. Durch sie wird die Singleton-Eigenschaft
	 * sichergestellt, in dem sie dafür sorgt, dass nur eine Instanz von
	 * <code>RubrikMapper</code> existiert. Die Instantiierung des RubrikMapper
	 * sollte immer durch den Aufruf dieser Methode erfolgen.
	 * 
	 * @return <code>RubrikMapper</code>-Objekt.
	 * 
	 * @see RubrikMapper#rubrikMapper()
	 */

	public static RubrikMapper rubrikMapper() {
		if (rubrikMapper == null) {
			rubrikMapper = new RubrikMapper();
		}
		return rubrikMapper;
	}

	/**
	 * Einfügen eines <code>Rubrik</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 * 
	 * @param rubrik
	 *            das zu speichernde Objekt
	 * @return das bereits übergebene Profil - Objekt, jedoch mit ggf.
	 *         korrigierter <code>id</code>.
	 */
	public Rubrik insert(Rubrik rubrik) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Momentan höchsten Primärschlüsselwert prüfen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM rubriken ");

			if (rs.next()) {
				/*
				 * notiz erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				rubrik.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");

				// Einfügeoperation erfolgt
				String sql = "INSERT INTO rubriken (id, rubrikName, notizName, erstellungsdatum, farbe, profil) "
						+ "VALUES ("
						+ rubrik.getId()
						+ ",'"
						+ rubrik.getRubrikName()
						+ "','"
						+ rubrik.getNotizName()
						+ "','"
						+ simpleDateFormat.format(rubrik.getErstellungsdatum())
						+ ",'"
						+ rubrik.getFarbe()
						+ ",'"
						+ rubrik.getProfil() + "')";
				
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Rückgabe, der evtl. korrigierten Rubrik.
		return rubrik;
	}

	/**
	 * Wiederholtes Schreiben eines Rubrik-Objekts in die Datenbank.
	 * 
	 * @param rubrik
	 *            , das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */

	public Rubrik update(Rubrik rubrik) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");

			stmt.executeUpdate("UPDATE rubriken " + "SET rubrikName=\""
					+ rubrik.getRubrikName() + "\", " + "notizName=\""
					+ rubrik.getNotizName() + "\", " + "erstellungsdatum=\""
					+ simpleDateFormat.format(rubrik.getErstellungsdatum()) + "\", " + "farbe=\""
							+ rubrik.getFarbe() +"\", " + "profil=\""
					+ rubrik.getProfil() + "\"" + "WHERE id=" + rubrik.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		// Rückgabe, der evtl. korrigierten Rubrik.
		return rubrik;
	}

	/**
	 * Löschen der Daten eines <code>Rubrik</code>-Objekts aus der Datenbank.
	 * 
	 * @param rubrik
	 *            das aus der DB zu löschende Objekt
	 */
	public void delete(Rubrik rubrik) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM rubriken " + "WHERE id="
					+ rubrik.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Auslesen aller Rubriken.
	 * 
	 * @return Eine ArrayList mit Rubrik-Objekten, die sämtliche Rubriken
	 *         repräsentieren. Bei evtl. Exceptions wird eine partiell gefüllte
	 *         oder ggf. auch leere ArrayList zurückgeliefert.
	 */
	public ArrayList<Rubrik> findAll() {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		// Vorbereitung der Ergebnis-ArrayList
		ArrayList<Rubrik> result = new ArrayList<Rubrik>();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery(BASE_SELECT + "ORDER BY rubrikName");

			// Für jeden Eintrag im Suchergebnis wird nun ein Rubrik-Objekt
			// erstellt und zur Ergebnis-ArrayList hinzugefügt.
			while (rs.next()) {
				Rubrik rubrik = map(rs);

				result.add(rubrik);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnis-ArrayList zurückgeben
		return result;
	}

	/**
	 * Suchen einer Rubrik mit vorgegebener ID. Da diese eindeutig ist, wird
	 * genau ein Objekt zurückgegeben.
	 * 
	 * @param id
	 *            Primärschlüsselattribut in DB
	 * @return Rubrik-Objekt, das dem übergebenen Schlüssel entspricht, null bei
	 *         nicht vorhandenem DB-Tupel.
	 */
	public Rubrik findByKey(int id) {

		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(BASE_SELECT + "WHERE id=" + id
					+ " ORDER BY rubrikName");

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
	 * @return Rubrik-Objekt
	 */

	private Rubrik map(ResultSet rs) throws SQLException {
		Rubrik rubrik = new Rubrik();

		rubrik.setId(rs.getInt("id"));
		rubrik.setNotizName(rs.getString("notizName"));
		rubrik.setRubrikName(rs.getString("rubrikName"));
		rubrik.setErstellungsdatum(rs.getDate("erstellungsdatum"));
		rubrik.setFarbe(rs.getString("farbe"));
		
		Profil profil = new Profil ();
		
		profil.setFirstName(rs.getString("firstName"));
		profil.setLastName(rs.getString("lastName"));
	
		
		return rubrik;
	}

}

