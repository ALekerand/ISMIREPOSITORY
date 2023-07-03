package com.sati.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Boncommande;
import com.sati.model.LigneCommande;
import com.sati.model.Materiel;
import com.sati.model.UserAuthentication;
import com.sati.requetes.RequeteUtilisateur;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class BonCommandeController {
	
	@Autowired
	Iservice service;
	@Autowired
	RequeteUtilisateur requeteUtilisateur;
	private LigneCommande ligneCommande = new LigneCommande();
	private List<LigneCommande> listObject = new ArrayList<LigneCommande>();
	private Materiel materiel = new Materiel();
	private List<Materiel> listMateriel = new ArrayList<Materiel>();
	private Materiel selectedObject;
	private Integer qteLigneCommande;
	private int idMateriel;
	private Boncommande bonCommande = new Boncommande();
	private List<Boncommande> listBonCommande = new ArrayList<Boncommande>();
	private UserAuthentication userAuthentication = new UserAuthentication();
	
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnAjouter = new CommandButton();
	
	
	@PostConstruct
	public void initialiser() {
		btnModifier.setDisabled(true);
		genererCodeBoncommande();
		chagerUtilisateur();
	}
	
	public void ajouter() {
		System.out.println("=========Lancement de la methode============");
		LigneCommande ligneCommande = new LigneCommande();
		ligneCommande.setQteLigneCommande(qteLigneCommande);
		ligneCommande.setMateriel(selectedObject);
		listObject.add(ligneCommande);
		info("Ajout effectu� avec succ�s");
		annulerLigneCommande();
	}
	
	public void annulerLigneCommande() {
		setQteLigneCommande(null);
		materiel.setCodeMateriel(null);
		materiel.setNomMateriel(null);
		
	}
	public UserAuthentication chagerUtilisateur() {
		return userAuthentication = requeteUtilisateur.recuperUser();
	}
	
	public String genererCodeBoncommande() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Boncommande").size();
		if(nbEnregistrement < 10)
			prefix = "BC00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "BC0" ;
		if (nbEnregistrement > 100) 
			prefix = "BC" ;
		return new String(prefix+(nbEnregistrement+1));
	}
	
	public String genererCodeLgneCommande() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("LigneCommande").size();
		if(nbEnregistrement < 10)
			prefix = "LC00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "LC0" ;
		if (nbEnregistrement > 100) 
			prefix = "LC" ;
		return new String(prefix+(nbEnregistrement+1));
	}
	
	 public void enregistrer() {
		
		bonCommande.setCodeBonCommande(genererCodeBoncommande());
		bonCommande.setCommentaireBonCommande(null);
		bonCommande.setDateBonCommande(new Date());
		bonCommande.setPersonne(userAuthentication.getPersonne());
		service.addObject(bonCommande);
		for (LigneCommande objetLigneCommande:listObject) {
			System.out.println("=============Quantité:"+objetLigneCommande.getQteLigneCommande());
			System.out.println("=============Materiel:"+objetLigneCommande.getMateriel());
			objetLigneCommande.setBoncommande(bonCommande);
			objetLigneCommande.setCodeLigneCommande(genererCodeLgneCommande());
			service.addObject(objetLigneCommande);
		}
		this.info("Enregistrement effectuée avec succès!");
		annuler();
		
	}

	public void annuler() {
		setListObject(null);
		bonCommande.setCommentaireBonCommande(null);	
		
	}
	

	public void choisirLigne() {
	this.materiel = this.selectedObject;
	}
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}
	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}
	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}
	public List<LigneCommande> getListObject() {
		return listObject;
	}
	public void setListObject(List<LigneCommande> listObject) {
		this.listObject = listObject;
	}
	public Materiel getMateriel() {
		return materiel;
	}
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}
	@SuppressWarnings("unchecked")
	public List<Materiel> getListMateriel() {
		listMateriel = service.getObjects("Materiel");
		System.out.println("===========Taille de la liste est:"+listMateriel.size());
		return listMateriel;
	}
	public void setListMateriel(List<Materiel> listMateriel) {
		this.listMateriel = listMateriel;
	}
	public Materiel getSelectedObject() {
		return selectedObject;
	}
	public void setSelectedObject(Materiel selectedObject) {
		this.selectedObject = selectedObject;
	}

	public Integer getQteLigneCommande() {
		return qteLigneCommande;
	}

	public void setQteLigneCommande(Integer qteLigneCommande) {
		this.qteLigneCommande = qteLigneCommande;
	}

	public int getIdMateriel() {
		return idMateriel;
	}

	public void setIdMateriel(int idMateriel) {
		this.idMateriel = idMateriel;
	}

	public Boncommande getBonCommande() {
		return bonCommande;
	}

	public void setBonCommande(Boncommande bonCommande) {
		this.bonCommande = bonCommande;
	}

	public List<Boncommande> getListBonCommande() {
		return listBonCommande;
	}

	public void setListBonCommande(List<Boncommande> listBonCommande) {
		this.listBonCommande = listBonCommande;
	}

	public CommandButton getBtnEnregistrer() {
		return btnEnregistrer;
	}

	public void setBtnEnregistrer(CommandButton btnEnregistrer) {
		this.btnEnregistrer = btnEnregistrer;
	}

	public CommandButton getBtnModifier() {
		return btnModifier;
	}

	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public CommandButton getBtnAnnuler() {
		return btnAnnuler;
	}

	public void setBtnAnnuler(CommandButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
	}

	public UserAuthentication getUserAuthentication() {
		return userAuthentication;
	}

	public void setUserAuthentication(UserAuthentication userAuthentication) {
		this.userAuthentication = userAuthentication;
	}

	public CommandButton getBtnAjouter() {
		return btnAjouter;
	}

	public void setBtnAjouter(CommandButton btnAjouter) {
		this.btnAjouter = btnAjouter;
	}


}
