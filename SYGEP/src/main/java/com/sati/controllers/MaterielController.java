package com.sati.controllers;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.radiobutton.RadioButton;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.sati.dto.CaracteristiqueValeur;
import com.sati.model.Caracteristique;
import com.sati.model.Diagnostique;
import com.sati.model.Etat;
import com.sati.model.Famille;
import com.sati.model.Fongible;
import com.sati.model.Magasin;
import com.sati.model.Marque;
import com.sati.model.Materiel;
import com.sati.model.Nature;
import com.sati.model.NonFongible;
import com.sati.model.Valeur;
import com.sati.requetes.RequeteMateriel;
import com.sati.requetes.RequeteNature;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class MaterielController {
	@Autowired
	Iservice service;
	private Materiel materiel = new Materiel();
	private Fongible fongible= new Fongible();
	private NonFongible Nonfongible= new NonFongible();
	@SuppressWarnings("unused")
	private List<Materiel> listTable = new ArrayList<Materiel>();
	private Materiel selectedObject = new Materiel();
	private int idFamille;
	private int idNature;
	private int idMagasin;
	private int idMarque;
	private int stockActuel;
	private int stockAlerte;
//	private String etatFongible;
	private List<Fongible> listFongible = new ArrayList<Fongible>();
	private List<Famille> listFamille = new ArrayList<Famille>();
	private List<Nature> listNature = new ArrayList<Nature>();
	private List<Magasin> listMagasin = new ArrayList<Magasin>();
	private List<Marque> listMarque = new ArrayList<Marque>();
	private List<CaracteristiqueValeur> listCaracteristiqueValeur = new ArrayList<CaracteristiqueValeur>();
	
	//Pour le QR code
	private String data;
	String path = "C:\\SYGEP\\QR_CODE";
	
//	private Famille choosedFamille = new Famille();
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	//private RadioButton rdbFondible = new  RadioButton();
	private SelectOneMenu cbNature = new SelectOneMenu();
	
	//Injection de controle
	@Autowired
	private RequeteNature requeteNature;
	private RequeteMateriel requeteMateriel;
	
	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		this.cbNature.setDisabled(true);
		chargerListeCaracteristiqueValeur();
	}
	
	public void genererCode() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Materiel").size();
		if(nbEnregistrement < 10)
			prefix = "MT00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "MT0" ;
		if (nbEnregistrement > 100) 
			prefix = "MT" ;
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
		 
		 //Trie de la liste
		 Collections.sort(listCaracteristiqueValeur, new Comparator<CaracteristiqueValeur>() {
		        @Override
		        public int compare(CaracteristiqueValeur ob1, CaracteristiqueValeur ob2)
		        {
		 
		            return  ob1.getCaracteristique().getLibCaracteristique().compareTo(ob2.getCaracteristique().getLibCaracteristique());
		        }
		    });
	}
	

	@SuppressWarnings("unchecked")
	public void chargerNature() {
		listNature = requeteNature.listerNatureParFamille(idFamille);
		this.cbNature.setDisabled(false);
	}

	public void enregistrer() throws WriterException, IOException {
		//Enregistrement dans la table Caracteristique
		//Famille familleProduit = (Famille) service.getObjectById(idFamille, "Famille");
		Nature natureProduit = (Nature) service.getObjectById(idNature, "Nature");
		Magasin magasin = (Magasin)service.getObjectById(idMagasin,"Magasin");
		Marque marque = (Marque) service.getObjectById(idMarque, "Marque");
		//this.materiel.setFamille(familleProduit);
		this.materiel.setNature(natureProduit);
		this.materiel.setMagasin(magasin);
		this.materiel.setMarque(marque);
		this.materiel.setRetrait(false);
		service.addObject(materiel);
		
			//Enregistrer dans fongible
			//this.fongible.setIdFamille(idFamille);
			this.fongible.setIdNature(idNature);
			this.fongible.setIdMagasin(idMagasin);
			this.fongible.setIdMarque(idMarque);
			this.fongible.setStockActuel(stockActuel);
			this.fongible.setStockAlerte(stockAlerte);
			this.fongible.setNomMateriel(materiel.getNomMateriel());
			this.fongible.setCodeMateriel(materiel.getCodeMateriel());
			this.fongible.setStockActuel(0);
			this.fongible.setRetrait(materiel.getRetrait());
			this.fongible.setDescriptionMateriel(materiel.getDescriptionMateriel());
			this.fongible.setMateriel(materiel);
			service.addObject(fongible);

		//Enregistrement dans la table Valeur 
		for (CaracteristiqueValeur caracteristiqueValeur : listCaracteristiqueValeur) {
			if(!(caracteristiqueValeur.getValeurCaracteristique().equals(""))) {
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
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage,null ));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}

	public void annuler() {
		this.materiel.setCodeMateriel(null);
		this.materiel.setDescriptionMateriel(null);
	//	this.materiel.setFamille(null);
		this.materiel.setNature(null);
		this.materiel.setMagasin(null);
		this.materiel.setNomMateriel(null);
		this.materiel.setMagasin(null);
		this.setIdFamille(0);
		this.setIdMagasin(0);
		this.setIdNature(0);
		this.setIdMarque(0);
		this.setStockActuel(0);
		this.setStockAlerte(0);
	//	this.rdbFondible.setItemIndex(2);
		
		
		
		// vider la liste des valeurs des ^caracteristiques
		for (CaracteristiqueValeur caracteristiqueValeur : listCaracteristiqueValeur) {
			caracteristiqueValeur.setValeurCaracteristique("");
			
		}
		//info("Annulation effectuée avec succès!");
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
		
		Collections.sort(listFamille, new Comparator<Famille>() {
	        @Override
	        public int compare(Famille ob1, Famille ob2)
	        {
	 
	            return  ob1.getLibFamille().compareTo(ob2.getLibFamille());
	        }
	    });
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
		 
		 Collections.sort(listTable, new Comparator<Materiel>() {
		        @Override
		        public int compare(Materiel ob1, Materiel ob2)
		        {
		 
		            return  ob1.getNomMateriel().compareTo(ob2.getNomMateriel());
		        }
		    });
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

	
	public List<Nature> getListNature() {
		return listNature;
	}

	public void setListNature(List<Nature> listNature) {
		this.listNature = listNature;
	}

	@SuppressWarnings("unchecked")
	public List<Magasin> getListMagasin() {
		 listMagasin = service.getObjects("Magasin");
		 
		 Collections.sort(listMagasin, new Comparator<Magasin>() {
		        @Override
		        public int compare(Magasin ob1, Magasin ob2)
		        {
		 
		            return  ob1.getNomMagasin().compareTo(ob2.getNomMagasin());
		        }
		    });
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

	/*
	 * public String getEtatFongible() { return etatFongible; }
	 * 
	 * public void setEtatFongible(String etatFongible) { this.etatFongible =
	 * etatFongible; }
	 */

	public NonFongible getNonfongible() {
		return Nonfongible;
	}

	public void setNonfongible(NonFongible nonfongible) {
		Nonfongible = nonfongible;
	}

	public SelectOneMenu getCbNature() {
		return cbNature;
	}

	public void setCbNature(SelectOneMenu cbNature) {
		this.cbNature = cbNature;
	}

	public int getIdMarque() {
		return idMarque;
	}

	public void setIdMarque(int idMarque) {
		this.idMarque = idMarque;
	}

	@SuppressWarnings("unchecked")
	public List<Marque> getListMarque() {
		listMarque = service.getObjects("Marque");
		
		Collections.sort(listMarque, new Comparator<Marque>() {
	        @Override
	        public int compare(Marque ob1, Marque ob2)
	        {
	 
	            return  ob1.getLibelleMarque().compareTo(ob2.getLibelleMarque());
	        }
	    });
		return listMarque;
	}

	public void setListMarque(List<Marque> listMarque) {
		this.listMarque = listMarque;
	}

	@SuppressWarnings("unchecked")
	public List<Fongible> getListFongible() {
		listFongible = service.getObjects("Fongible");
		
		  Collections.sort(listFongible, new Comparator<Fongible>() {
		        @Override
		        public int compare(Fongible ob1, Fongible ob2)
		        {
		 
		            return  ob1.getMateriel().getNomMateriel().compareTo(ob2.getMateriel().getNomMateriel());
		        }
		    });
		return listFongible;
	}

	public void setListFongible(List<Fongible> listFongible) {
		this.listFongible = listFongible;
	}
}