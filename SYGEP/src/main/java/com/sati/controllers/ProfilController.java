package com.sati.controllers;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.linkbutton.LinkButton;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ProfilController implements Serializable{
	private static final long serialVersionUID = 1L;
	private String profil;
	private LinkButton linkButton = new LinkButton();
	
	public void router() {
		
		switch (profil) {
		
		case "utilisateur_simple": {
			linkButton.setOutcome("/templates/templateUser.xhtml");
			break;
		}
		
	case "adminstrateur": {
		linkButton.setOutcome("/templates/template.xhtml");
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


	public LinkButton getLinkButton() {
		return linkButton;
	}

	public void setLinkButton(LinkButton linkButton) {
		this.linkButton = linkButton;
	}
}
