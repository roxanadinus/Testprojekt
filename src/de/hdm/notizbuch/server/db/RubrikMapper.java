package de.hdm.notizbuch.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdm.notizbuch.shared.bo.Profil;
import de.hdm.notizbuch.shared.bo.Rubrik;


/**
 * Die Mapper-Klasse <code>RubrikMapper</code> bildet <code>Rubrik
 * </code>-Objekte auf Datens��tze in einer relationalen Datenbank ab.Durch die
 * Bereitstellung verschiedener Methoden k��nnen mit deren Hilfe beispielsweise
 * Objekte erzeugt, editiert, gel��scht oder gesucht werden. Das sogenannte
 * Mapping erfolgt bidirektional, d.h. Objekte k��nnen in DB-Strukturen und
 * DB-Strukturen in Objekte umgewandelt werden.
 * 
 */


public class RubrikMapper {

	// Grundlegendes Select-Statement
	private static final String BASE_SELECT = "SELECT rubrikName, notizName, erstellungsdatum, farbe, profil FROM rubriken ";

	/**
	 * Die Instantiierung der Klasse RubrikMapper erfolgt nur einmal. Dies wird
	 * auch als <b>Singleton</b> bezeichnet. Durch den Bezeichner
	 * <code>static</code> ist die Variable nur einmal f��r s��mtliche eventuellen
	 * Instanzen dieser Klasse vorhanden. Sie speichert die einzige Instanz der
	 * Klasse.
	 * 
	 */
	private static RubrikMapper rubrikMapper = null;

	/**
	 * Dieser gesch��tzte Konstruktor verhindert das Erzeugen von neuen Instanzen
	 * dieser Klasse mit dem Aufruf <code>new</code>.
	 */

	protected RubrikMapper() {

	}

	/**
	 * Durch <code>RubrikMapper.rubrikMapper()</code> kann folgende statische
	 * Methode aufgerufen werden. Durch sie wird die Singleton-Eigenschaft
	 * sichergestellt, in dem sie daf��r sorgt, dass nur eine Instanz von
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
	 * Einf��gen eines <code>Rubrik</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Prim��rschl��ssel des ��bergebenen Objekts gepr��ft und ggf.
	 * berichtigt.
	 * 
	 * @param rubrik
	 *            das zu speichernde Objekt
	 * @return das bereits ��bergebene Profil - Objekt, jedoch mit ggf.
	 *         korrigierter <code>id</code>.
	 */
	public Rubrik insert(Rubrik rubrik) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Momentan h��chsten Prim��rschl��sselwert pr��fen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM rubriken ");

			if (rs.next()) {
				/*
				 * notiz erh��lt den bisher maximalen, nun um 1 inkrementierten
				 * Prim��rschl��ssel.
				 */
				rubrik.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");

				// Einf��geoperation erfolgt
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

		// R��ckgabe, der evtl. korrigierten Rubrik.
		return rubrik;
	}

	/**
	 * Wiederholtes Schreiben eines Rubrik-Objekts in die Datenbank.
	 * 
	 * @param rubrik
	 *            , das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter ��bergebene Objekt
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
		// R��ckgabe, der evtl. korrigierten Rubrik.
		return rubrik;
	}

	/**
	 * L��schen der Daten eines <code>Rubrik</code>-Objekts aus der Datenbank.
	 * 
	 * @param rubrik
	 *            das aus der DB zu l��schende Objekt
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
	 * @return Eine ArrayList mit Rubrik-Objekten, die s��mtliche Rubriken
	 *         repr��sentieren. Bei evtl. Exceptions wird eine partiell gef��llte
	 *         oder ggf. auch leere ArrayList zur��ckgeliefert.
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

			// F��r jeden Eintrag im Suchergebnis wird nun ein Rubrik-Objekt
			// erstellt und zur Ergebnis-ArrayList hinzugef��gt.
			while (rs.next()) {
				Rubrik rubrik = map(rs);

				result.add(rubrik);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnis-ArrayList zur��ckgeben
		return result;
	}

	/**
	 * Suchen einer Rubrik mit vorgegebener ID. Da diese eindeutig ist, wird
	 * genau ein Objekt zur��ckgegeben.
	 * 
	 * @param id
	 *            Prim��rschl��sselattribut in DB
	 * @return Rubrik-Objekt, das dem ��bergebenen Schl��ssel entspricht, null bei
	 *         nicht vorhandenem DB-Tupel.
	 */
	public Rubrik findByKey(int id) {

		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausf��llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(BASE_SELECT + "WHERE id=" + id
					+ " ORDER BY rubrikName");

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


	//TODO
	
	public ArrayList<Rubrik> findByProfil(Profil profil) {
		return findByProfil(profil);


	}

}

