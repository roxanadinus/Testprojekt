package de.hdm.notizbuch.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Verwaltung der Datenbankverbindung
 * 
 * @author roxanadinus
 *
 */

public class DBConnection {

	/**
	 * Die Instantiierung der Klasse DBConnection erfolgt nur einmal. Dies wird
	 * auch als <b>Singleton</b> bezeichnet. Durch den Bezeichner
	 * <code>static</code> ist die Variable nur einmal für sämtliche eventuellen
	 * Instanzen dieser Klasse vorhanden. Sie speichert die einzige Instanz der
	 * Klasse.
	 */

	private static Connection con = null;

	/**
	 * Mit Hilfe folgender URL wird die Datenbank angesprochen.
	 */

	private static String googleUrl = "";
	private static String localUrl = "";

	/**
	 * Durch <code>DBConnection.connection()</code> kann folgende statische
	 * Methode aufgerufen werden. Durch sie wird die Singleton-Eigenschaft
	 * sichergestellt, indem sie dafür sorgt, dass nur eine Instanz von
	 * <code>DBConnection</code> existiert. Die Instantiierung der DBConnection
	 * sollte immer durch den Aufruf dieser Methode erfolgen.
	 * 
	 * @return DAS <code>DBConncetion</code>-Objekt.
	 * 
	 * @see DBConnection#con
	 */
	public static Connection connection() {

		// Wenn es bisher keine Verbindung zur DB gab
		if (con == null) {
			String url = null;
			try {
				if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {

					Class.forName("com.mysql.jdbc.GoogleDriver");
					url = googleUrl;
				} else {

					Class.forName("com.mysql.jdbc.Driver");
					url = localUrl;
				}

				con = DriverManager.getConnection(url);
			} catch (Exception e) {
				con = null;
				e.printStackTrace();
			}
		}

		// Verbindung zurückgeben
		return con;
	}

}