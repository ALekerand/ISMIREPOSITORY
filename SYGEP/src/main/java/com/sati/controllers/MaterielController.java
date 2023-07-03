package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.dto.CaracteristiqueValeur;
import com.sati.model.Caracteristique;
import com.sati.model.Famille;
import com.sati.model.Fongible;
import com.sati.model.Magasin;
import com.sati.model.Materiel;
import com.sati.model.Nature;
import com.sati.model.Valeur;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class MaterielController {
	@Autowired
	Iservice service;
	private Materiel materiel = new Materiel();
	private Fongible fongible= new Fongible();
	@SuppressWarnings("unused")
	private List<Materiel> listTable = new ArrayList<Materiel>();
	private Materiel selectedObject = new Materiel();
	private int idFamille;
	private int idNature;
	private int idMagasin;
	private int stockActuel;
	private int stockAlerte;
	private String etatFongible;
	private List<Famille> listFamille = new ArrayList<Famille>();
	private List<Nature> listNature = new ArrayList<Nature>();
	private List<Magasin> listMagasin = new ArrayList<Magasin>();
	private List<CaracteristiqueValeur> listCaracteristiqueValeur = new ArrayList<CaracteristiqueValeur>();
	
	
//	private Famille choosedFamille = new Famille();
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		chargerListeCaracteristiqueValeur();
	}
	
	public void genererCode() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Materiel").size();
		if(nbEnregistrement < 10)
			prefix = "CM00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CM0" ;
		if (nbEnregistrement > 100) 
			prefix = "CM" ;
		this.materiel.setCodeMateriel(prefix+(nbEnregistrement+1));
	}
	
	
	public String genererCodeValeur() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Valeur").size();
		if(nbEnregistrement < 10)
			prefix = "CV00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CV0" ;
		if (nbEnregistrement > 100) 
			prefix = "CV" ;
		return new String(prefix+(nbEnregistrement+1));
	}
	
	@SuppressWarnings("unchecked")
	public void chargerListeCaracteristiqueValeur() {
		 for (Caracteristique caracteristique : (List<Caracteristique>)service.getObjects("Caracteristique")){
			 CaracteristiqueValeur  caracteristiqueValeur = new CaracteristiqueValeur();
			 caracteristiqueValeur.setCaracteristique(caracteristique);
			 listCaracteristiqueValeur.add(caracteristiqueValeur);
		}
	}
	

	public void enregistrer() {
		//Enregistrement dans la table Caracteristique
		Famille familleProduit = (Famille) service.getObjectById(idFamille, "Famille");
		Nature natureProduit = (Nature) service.getObjectById(idNature, "Nature");
		Magasin magasin = (Magasin)service.getObjectById(idMagasin,"Magasin");
		this.materiel.setFamille(familleProduit);
		this.materiel.setNature(natureProduit);
		this.materiel.setMagasin(magasin);
		this.service.addObject(this.materiel);
		
		
		this.fongible.setIdFamille(idFamille);
		this.fongible.setIdNature(idNature);
		this.fongible.setIdMagasin(idMagasin);
		this.fongible.setStockActuel(stockActuel);
		this.fongible.setStockAlerte(stockAlerte);
		this.fongible.setNomMateriel(materiel.getNomMateriel());
		this.fongible.setCodeMateriel(materiel.getCodeMateriel());
		this.fongible.setDescriptionMateriel(materiel.getDescriptionMateriel());
		this.fongible.setMateriel(materiel);
		this.service.addObject(this.fongible);
		
		//Enregistrement dans la table Valeur 
		for (CaracteristiqueValeur caracteristiqueValeur : listCaracteristiqueValeur) {
			if(caracteristiqueValeur.getValeurCaracteristique()!="") {
				Valeur valeur = new Valeur();
				valeur.setCode(genererCodeValeur());
				valeur.setValeurCaracteristique(caracteristiqueValeur.getValeurCaracteristique());
				valeur.setCaracteristique(caracteristiqueValeur.getCaracteristique());
				valeur.setMateriel(materiel);
				service.addObject(valeur);
			}
		}
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
	}

	public void selectionnerLigne() {
		this.materiel = this.selectedObject;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null, new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage,null ));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}

	public void annuler() {
		this.materiel.setCodeMateriel(null);
		this.materiel.setDescriptionMateriel(null);
		this.materiel.setFamille(null);
		this.materiel.setNature(null);
		this.materiel.setMagasin(null);
		this.materiel.setNomMateriel(null);
		this.materiel.setMagasin(null);
		this.setIdFamille(0);
		this.setIdMagasin(0);
		this.setIdNature(0);
		info("Annulation effectuée avec succès!");
		
		
		// vider la liste des valeurs des ^caracteristiques
		for (CaracteristiqueValeur caracteristiqueValeur : listCaracteristiqueValeur) {
			caracteristiqueValeur.setValeurCaracteristique("");
			
		}
		info("Annulation effectuée avec succès!");
	}

	public void modifier() {
		this.service.updateObject(this.materiel);
		this.annuler();
		this.info("Modification effectué avec succès!");
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
		this.btnAnnuler.setDisabled(false);
	}
	
	
	 public void onRowEdit(RowEditEvent event) {
	    info("Valeur de caractéristique éditée");
	    }
	 
	 
	 public void onRowCancel(RowEditEvent event) {	        
	        info("Edition terminée");
	    }
	
	
	//Accesseur & Mutateur
	
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

	public int getIdFamille() {
		return idFamille;
	}

	public void setIdFamille(int idFamille) {
		this.idFamille = idFamille;
	}

	@SuppressWarnings("unchecked")
	public List<Famille> getListFamille() {
		listFamille = service.getObjects("Famille");
		return listFamille;
	}

	public void setListFamille(List<Famille> listFamille) {
		this.listFamille = listFamille;
	}

	public Materiel getMateriel() {
		genererCode();
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	@SuppressWarnings("unchecked")
	public List<Materiel> getListTable() {
		 listTable = service.getObjects("Materiel");
		 return listTable;
	}

	public void setListTable(List<Materiel> listTable) {
		this.listTable = listTable;
	}

	public Materiel getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Materiel selectedObject) {
		this.selectedObject = selectedObject;
	}

	public int getIdNature() {
		return idNature;
	}

	public void setIdNature(int idNature) {
		this.idNature = idNature;
	}

	@SuppressWarnings("unchecked")
	public List<Nature> getListNature() {
		listNature = service.getObjects("Nature");
		return listNature;
	}

	public void setListNature(List<Nature> listNature) {
		this.listNature = listNature;
	}

	@SuppressWarnings("unchecked")
	public List<Magasin> getListMagasin() {
		 listMagasin = service.getObjects("Magasin");
		 return listMagasin;
	}

	public void setListMagasin(List<Magasin> listMagasin) {
		this.listMagasin = listMagasin;
	}

	public int getIdMagasin() {
		return idMagasin;
	}

	public void setIdMagasin(int idMagasin) {
		this.idMagasin = idMagasin;
	}

	public List<CaracteristiqueValeur> getListCaracteristiqueValeur() {
		return listCaracteristiqueValeur;
	}

	public void setListCaracteristiqueValeur(List<CaracteristiqueValeur> listCaracteristiqueValeur) {
		this.listCaracteristiqueValeur = listCaracteristiqueValeur;
	}

	public Fongible getFongible() {
		return fongible;
	}

	public void setFongible(Fongible fongible) {
		this.fongible = fongible;
	}

	public int getStockActuel() {
		return stockActuel;
	}

	public void setStockActuel(int stockActuel) {
		this.stockActuel = stockActuel;
	}

	public int getStockAlerte() {
		return stockAlerte;
	}

	public void setStockAlerte(int stockAlerte) {
		this.stockAlerte = stockAlerte;
	}

	public String getEtatFongible() {
		return etatFongible;
	}

	public void setEtatFongible(String etatFongible) {
		this.etatFongible = etatFongible;
	}

	
	
}