package com.sati.controllers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.linkbutton.LinkButton;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ProfilController {
	private String profil;
	//private String lien;
	private LinkButton linkButton = new LinkButton();
	
	public void router() {
	//	lien ="";
		
		switch (profil) {
		
		case "utilisateur_simple": {
			linkButton.setOutcome("/templates/templateUser.xhtml");
			System.out.println("======== lien :"+ linkButton.getOutcome());
			break;
		}
		
	case "adminstrateur": {
		linkButton.setOutcome("/templates/template.xhtml");
		System.out.println("======== lien :"+ linkButton.getOutcome());
		break;
	}
	
		default:
	}
		
	}
	
	
	
	public void info(String message){
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,message,null));	
	}
	
	
	
	
	// GETTERS AND SETTERS

	public String getProfil() {
		return profil;
	}

	public void setProfil(String profil) {
		this.profil = profil;
	}




	/*
	 * public String getLien() { return lien; }
	 * 
	 * 
	 * 
	 * 
	 * public void setLien(String lien) { this.lien = lien; }
	 */



	public LinkButton getLinkButton() {
		return linkButton;
	}




	public void setLinkButton(LinkButton linkButton) {
		this.linkButton = linkButton;
	}
}
