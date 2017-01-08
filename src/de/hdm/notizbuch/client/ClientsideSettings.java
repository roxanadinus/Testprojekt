package de.hdm.notizbuch.client;

import com.google.gwt.core.shared.GWT;

import de.hdm.notizbuch.shared.NotizbuchAdministration1;
import de.hdm.notizbuch.shared.NotizbuchAdministration1Async;

public class ClientsideSettings {

	private static NotizbuchAdministration1Async notizbuchAdministration = null; 
	
	public static NotizbuchAdministration1Async getNotizbuchAdministration() {
		
		if (notizbuchAdministration == null){
			notizbuchAdministration = GWT.create(NotizbuchAdministration1.class);
		}
		return notizbuchAdministration;
		
	}
}
