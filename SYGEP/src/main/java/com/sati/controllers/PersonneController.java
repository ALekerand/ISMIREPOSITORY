package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Entite;
import com.sati.model.Fonction;
import com.sati.model.Personne;
import com.sati.model.UserAuthentication;
import com.sati.model.UserAuthorization;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class PersonneController {
	@Autowired
	Iservice service;
	private int idFonction;
	private String codeEntite;
	private String userRole;
	private Personne personne = new Personne();
	private Entite entite = new Entite();
	private Fonction fonction = new Fonction();
	private UserAuthentication userAuthentication = new UserAuthentication();
	private UserAuthorization userauthorization = new UserAuthorization();
	private List<Personne> listTable = new ArrayList<Personne>();
	private List<Fonction> listFonction = new ArrayList<Fonction>();
	private Personne selectedObject = new Personne();
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		//codePersonne();
		genererCodeEntite();
	}
	public void genererCodeEntite() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Entite").size();
		if(nbEnregistrement < 10)
			prefix = "ET00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "ET0" ;
		if (nbEnregistrement > 100) 
			prefix = "ET" ;
		codeEntite = prefix+(nbEnregistrement+1);
		System.out.println("======="+codeEntite);
	}

	public void enregistrer() {
		//Recuperaion de la fonction
		this.fonction = (Fonction) this.service.getObjectById(idFonction, "Fonction");
		this.personne.setEntite(entite);
		this.personne.setFonction(fonction);
		
		//Enregistrement de l'entité et de la personne
		this.entite.setCodeEntite(codeEntite);
		this.service.addObject(entite);
		personne.setCodeEntite(entite.getCodeEntite());
		personne.setTelephone(entite.getTelephone());
		personne.setEmail(entite.getEmail());
		personne.setEmail(entite.getEmail());
		this.service.addObject(this.personne);
		
		//Enregistrement du user authorization
		this.userauthorization.setRole(userRole);
		this.service.addObject(userauthorization);
		
		//Enregistrement de userAuthentification
		this.userAuthentication.setPersonne(personne);
		this.userAuthentication.setEnabled(true);
		this.service.addObject(this.userAuthentication);
		
		//Mise jour de userAuthorization
		userauthorization.setUserAuthentication(userAuthentication);
		this.service.updateObject(userauthorization);
		
		//Mise jour de personne
		personne.setUserAuthentication(userAuthentication);
		service.updateObject(personne);
		
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
	}

	public void selectionnerLigne() {
		this.personne = this.selectedObject;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,monMessage,null));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}

	public void annuler() {
		entite.setCodeEntite(null);
		entite.setTelephone(null);
		entite.setEmail(null);
		
		this.personne.setNomPersonne(null);
		this.personne.setPrenomsPersonne(null);
		this.personne.setCodeEntite(null);
		this.personne.setTelephone(null);
		this.personne.setEmail(null);
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
		
		this.userAuthentication.setUsername(null);
		this.userAuthentication.setPassword(null);
		
		setIdFonction(0);
		info("Annulation effectuée avec succès!");
	}

	public void modifier() {
		this.service.updateObject(this.personne);
		this.info("Modification effectué avec succès!");
		this.annuler();
	}

	public CommandButton getBtnEnregistrer() {
		return this.btnEnregistrer;
	}

	public void setBtnEnregistrer(CommandButton btnEnregistrer) {
		this.btnEnregistrer = btnEnregistrer;
	}

	public CommandButton getBtnAnnuler() {
		return this.btnAnnuler;
	}

	public void setBtnAnnuler(CommandButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
	}

	public CommandButton getBtnModifier() {
		return this.btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	@SuppressWarnings("unchecked")
	public List<Personne> getListTable() {
		listTable = this.service.getObjects("Personne");
		return listTable;
	}

	public void setListTable(List<Personne> listTable) {
		this.listTable = listTable;
	}

	public Personne getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Personne selectedObject) {
		this.selectedObject = selectedObject;
	}

	public Entite getEntite() {
		return entite;
	}

	public void setEntite(Entite entite) {
		this.entite = entite;
	}

	@SuppressWarnings("unchecked")
	public List<Fonction> getListFonction() {
	       listFonction = this.service.getObjects("Fonction");
	       return listFonction;
	}

	public void setListFonction(List<Fonction> listFonction) {
		this.listFonction = listFonction;
	}

	public int getIdFonction() {
		return idFonction;
	}

	public void setIdFonction(int idFonction) {
		this.idFonction = idFonction;
	}

	public Fonction getFonction() {
		return fonction;
	}

	public void setFonction(Fonction fonction) {
		this.fonction = fonction;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public UserAuthentication getUserAuthentication() {
		return userAuthentication;
	}

	public void setUserAuthentication(UserAuthentication userAuthentication) {
		this.userAuthentication = userAuthentication;
	}

	public String getCodeEntite() {
		return codeEntite;
	}

	public void setCodeEntite(String codeEntite) {
		this.codeEntite = codeEntite;
	}
}