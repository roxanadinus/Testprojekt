package de.hdm.notizbuch.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.core.java.util.Arrays;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.notizbuch.shared.FieldVerifier;
import de.hdm.notizbuch.shared.NotizbuchAdministration1Async;
import de.hdm.notizbuch.shared.bo.Profil;
import de.hdm.notizbuch.shared.bo.Rubrik;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Notizbuch implements EntryPoint {

	private NotizbuchAdministration1Async notizbuchAdministration = ClientsideSettings
			.getNotizbuchAdministration();

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		
		//Menu-Tab anlegen
		
		MenuBar profilMenu = new MenuBar();
		MenuBar notizMenu = new MenuBar();
		MenuBar rubrikMenu = new MenuBar();
		
		//allg. Menu anglegen
		
		MenuBar menu = new MenuBar();
		
		// Hinzufügen der Tabs zum allg. Menu
				
	menu.addItem("Mein Profil", profilMenu);
	menu.addItem("Meine Notizen", notizMenu);
	menu.addItem("Meine Rubriken", rubrikMenu);
	
		// das Menu dem Navigator-Bereich zuordnen und dem RootPanel hinzufügen.
	
	RootPanel.get("Navigator").add(menu);
	
	
		
		
		
	
	


//		// create buttons
//		Button redButton = new Button("Red");
	Button greenButton = new Button("Green");
//		Button blueButton = new Button("Blue");
//
//		// use UIObject methods to set button properties.
//		redButton.setWidth("100px");
	greenButton.setWidth("100px");
//		blueButton.setWidth("100px");
greenButton.addStyleName("gwt-Green-Button");
//		blueButton.addStyleName("gwt-Blue-Button");
//
//		// add a clickListener to the button
//		redButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				Window.alert("Red Button clicked!");
//			}
//		});
//
		// add a clickListener to the button
		greenButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
			Window.alert("Green Button clicked!");
				notizbuchAdministration
						.getAllRubriken(new AsyncCallback<ArrayList<Rubrik>>() {
							public void onSuccess(ArrayList<Rubrik> result) {
								for (int i = 0; i < result.size(); i++) {
									String temp = result.get(i).getRubrikName();
									Window.alert(temp);
								}
							}

							public void onFailure(Throwable caught) {
								Window.alert("Fehler");
							}
						});
			}

		});
		}
		}

//		// add a clickListener to the button
//		// blueButton.addClickHandler(new ClickHandler() {
//		// @Override
//		// public void onClick(ClickEvent event) {
//		// Window.alert("Blue Button clicked!");
//		// }
//		// });
//		// }
//
//		// Add button to the root panel.
//		VerticalPanel panel = new VerticalPanel();
//		panel.setSpacing(10);
//		panel.add(redButton);
//		panel.add(greenButton);
//		panel.add(blueButton);
//
//		RootPanel.get("gwtContainer").add(panel);
//
//		// Create a three-item tab panel, with the tab area 1.5em tall.
//		TabLayoutPanel p = new TabLayoutPanel(1.5, Unit.EM);
//
//	}
//	
//	
//	
//}

//
// final Button sendButton = new Button("Send");
// final TextBox nameField = new TextBox();
// nameField.setText("GWT User");
// final Label errorLabel = new Label();
//
// // We can add style names to widgets
// sendButton.addStyleName("sendButton");
//
// // Add the nameField and sendButton to the RootPanel
// // Use RootPanel.get() to get the entire body element
// RootPanel.get("nameFieldContainer").add(nameField);
// RootPanel.get("sendButtonContainer").add(sendButton);
// RootPanel.get("errorLabelContainer").add(errorLabel);
//
// // Focus the cursor on the name field when the app loads
// nameField.setFocus(true);
// nameField.selectAll();
//
// // Create the popup dialog box
// final DialogBox dialogBox = new DialogBox();
// dialogBox.setText("Remote Procedure Call");
// dialogBox.setAnimationEnabled(true);
// final Button closeButton = new Button("Close");
// // We can set the id of a widget by accessing its Element
// closeButton.getElement().setId("closeButton");
// final Label textToServerLabel = new Label();
// final HTML serverResponseLabel = new HTML();
// VerticalPanel dialogVPanel = new VerticalPanel();
// dialogVPanel.addStyleName("dialogVPanel");
// dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
// dialogVPanel.add(textToServerLabel);
// dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
// dialogVPanel.add(serverResponseLabel);
// dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
// dialogVPanel.add(closeButton);
// dialogBox.setWidget(dialogVPanel);
//
// // Add a handler to close the DialogBox
// closeButton.addClickHandler(new ClickHandler() {
// public void onClick(ClickEvent event) {
// dialogBox.hide();
// sendButton.setEnabled(true);
// sendButton.setFocus(true);
// }
// });
//
// // Create a handler for the sendButton and nameField
// class MyHandler implements ClickHandler, KeyUpHandler {
// /**
// * Fired when the user clicks on the sendButton.
// */
// public void onClick(ClickEvent event) {
// sendNameToServer();
// }
//
// /**
// * Fired when the user types in the nameField.
// */
// public void onKeyUp(KeyUpEvent event) {
// if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
// sendNameToServer();
// }
// }
//
// /**
// * Send the name from the nameField to the server and wait for a response.
// */
// private void sendNameToServer() {
// // First, we validate the input.
// errorLabel.setText("");
// String textToServer = nameField.getText();
// if (!FieldVerifier.isValidName(textToServer)) {
// errorLabel.setText("Please enter at least four characters");
// return;
// }
//
// // Then, we send the input to the server.
// sendButton.setEnabled(false);
// textToServerLabel.setText(textToServer);
// serverResponseLabel.setText("");
// greetingService.greetServer(textToServer,
// new AsyncCallback<String>() {
// public void onFailure(Throwable caught) {
// // Show the RPC error message to the user
// dialogBox
// .setText("Remote Procedure Call - Failure");
// serverResponseLabel
// .addStyleName("serverResponseLabelError");
// serverResponseLabel.setHTML(SERVER_ERROR);
// dialogBox.center();
// closeButton.setFocus(true);
// }
//
// public void onSuccess(String result) {
// dialogBox.setText("Remote Procedure Call");
// serverResponseLabel
// .removeStyleName("serverResponseLabelError");
// serverResponseLabel.setHTML(result);
// dialogBox.center();
// closeButton.setFocus(true);
// }
// });
// }
// }
//
// // Add a handler to send the name to the server
// MyHandler handler = new MyHandler();
// sendButton.addClickHandler(handler);
// nameField.addKeyUpHandler(handler);

