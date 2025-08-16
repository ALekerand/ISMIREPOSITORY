package com.sati.controllers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.UserAuthentication;
import com.sati.requetes.RequeteUtilisateur;
import com.sati.service.Iservice;


@Component
@Scope("session")
public class ChangementMDPController {
	
	@Autowired
	Iservice service;
	
	@Autowired
	private RequeteUtilisateur requeteUtilisateur;	
	private String mdp_actuel;
	private String nouveau_mdp;
	private String mdp_conf;
	private UserAuthentication userAuthentication = new UserAuthentication();
	
//Methodes		
	public void enregistrer(){
		userAuthentication = requeteUtilisateur.recuperUser();
		//Verifier le mot de passe
		if (userAuthentication.getPassword().equals(mdp_actuel)) {
			//Verifier la conformité du nouveau MDP
			if(nouveau_mdp.equals(mdp_conf)) {
				//Faire l'enregistrement
				userAuthentication.setPassword(nouveau_mdp);
				service.updateObject(userAuthentication);
				info("Modification de mot de passe effectué avec succès");
				annuler();
			}else {
				error("Echec de confirmation du nouveau mot de passe. Veuillez le ressaisir");
			}
		}else {
			error("Le mot de passe entré ne correspond pas à celui présent dans le système. Veuillez contacter l'admmistrateur");
		}
		
	}
	
	
	public void annuler() {
		nouveau_mdp ="";
		mdp_actuel="";
		mdp_conf ="";
	}
	

	public void info(String message){
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,message,null));	
	}
	
	
	public void error(String message){
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,message,null));	
	}
		
//Getters and setters
	public UserAuthentication getUserAuthentication() {
		return userAuthentication;
	}

	public void setUserAuthentication(UserAuthentication userAuthentication) {
		this.userAuthentication = userAuthentication;
	}

	public String getMdp_actuel() {
		return mdp_actuel;
	}

	public void setMdp_actuel(String mdp_actuel) {
		this.mdp_actuel = mdp_actuel;
	}

	public String getNouveau_mdp() {
		return nouveau_mdp;
	}

	public void setNouveau_mdp(String nouveau_mdp) {
		this.nouveau_mdp = nouveau_mdp;
	}

	public String getMdp_conf() {
		return mdp_conf;
	}

	public void setMdp_conf(String mdp_conf) {
		this.mdp_conf = mdp_conf;
	}
}
