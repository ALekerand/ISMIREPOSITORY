package com.sati.controllers;

import java.io.IOException;
import java.nio.file.Paths;
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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.sati.model.Diagnostique;
import com.sati.model.Etat;
import com.sati.model.Materiel;
import com.sati.model.Parcours;
import com.sati.model.Service;
import com.sati.requetes.RequeteDiagnostique;
import com.sati.requetes.RequeteMateriel;
import com.sati.requetes.RequetePacours;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class ParcoursController {
	@Autowired
	Iservice service;
	
	private Parcours parcours = new Parcours();
	private Materiel materiel = new Materiel();
	private Materiel selectedObject = new Materiel();
	private Service leService = new Service();
	private Diagnostique diagnostique = new Diagnostique();
	private Etat etat = new Etat();
	private Date dateAffectation;
	private int idEntite;
	private int idEtat;
	
	private List<Materiel> listMateriel = new ArrayList<Materiel>();
	private List<Parcours> listParcours = new ArrayList<Parcours>();
	private List<Service> listService = new ArrayList<Service>();
	private List<Etat> listEtat = new ArrayList<Etat>();
	
	//Pour le QR code
		private String data;
		String path = "C:/SYGEP/QR_CODE";
	
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	
	
	@Autowired
	private RequeteMateriel requeteMateriel;
	@Autowired
	private RequetePacours requetePacours;
	@Autowired
	private RequeteDiagnostique  requeteDiagnostique;
	
	
	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		genererCodeDiagnostique();
		genererCodeParcours();
	}
	
	
	public void genererCodeParcours() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Parcours").size();
		if(nbEnregistrement < 10)
			prefix = "PC00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "PC0" ;
		if (nbEnregistrement > 100) 
			prefix = "PC" ;
		this.parcours.setCodeParcours(prefix+(nbEnregistrement+1));
	}
	
	public void genererCodeDiagnostique() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Diagnostique").size();
		if(nbEnregistrement < 10)
			prefix = "DG00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "DG0" ;
		if (nbEnregistrement > 100) 
			prefix = "DG" ;
		this.diagnostique.setCodeDiagnostique(prefix+(nbEnregistrement+1));	
	}
	
	public void selectionnerLigne() throws IndexOutOfBoundsException{                     
		this.materiel = this.selectedObject;
		//Charger la position du matériel
		parcours = requetePacours.recupererLastParcoursParMateriel(materiel.getIdMateriel());
		etat = requeteDiagnostique.recupererLastDiagnostiqueDuMateriel(materiel.getIdMateriel()).getEtat();
		dateAffectation = new Date();
		this.btnEnregistrer.setDisabled(false);
	}
	
	public void affecterMateriel() throws WriterException, IOException {
		//Enregistrer dans parcours
		leService = (Service) service.getObjectById(idEntite, "Service");
		parcours.setService(leService);
		parcours.setMateriel(materiel);
		genererCodeParcours();
		parcours.setDateEnregParcours(new Date());
		service.addObject(parcours);
		
		//Enregistrer dans Diagnostique
		diagnostique.setMateriel(materiel);
		diagnostique.setEtat((Etat) service.getObjectById(idEtat, "Etat"));
		genererCodeDiagnostique();
		diagnostique.setDateDiagnostique(parcours.getDateParcours());
		service.addObject(diagnostique);
		
		//Actualiser le QR code
		genererQRCode();
		
		annuler();
		info("Affectation de matériel effectuée!");
		listMateriel.clear();
		getListMateriel();
		
		genererCodeParcours();
		genererCodeDiagnostique();
	}
	
	
	public void genererQRCode() throws WriterException, IOException {
		path += "_"+materiel.getCodeMateriel();
		data = "Code: "+materiel.getCodeMateriel()+"\n"+
				"Désignation: " +materiel.getNomMateriel()+"\n"+
				"Magasin d'origine: " +materiel.getMagasin().getNomMagasin()+"\n"+
				"Position actuelle:"+leService.getNomService();
		BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
		MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
		
		//Reactualiser le chemin
		path = "C:/SYGEP/QR_CODE";
	}
	
	
	
	
	public void annuler() {
		this.materiel.setCodeMateriel(null);
		this.materiel.setNomMateriel(null);
		parcours.setService(null);
		diagnostique.setCommentaire(null);
		setDateAffectation(null);
		etat.setLibEtat(null);
		this.setIdEntite(0);
	}
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}
	

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}
	
	//Getters and Setters
	public Parcours getParcours() {
		return parcours;
	}

	public void setParcours(Parcours parcours) {
		this.parcours = parcours;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	@SuppressWarnings("unchecked")
	public List<Materiel> getListMateriel() {
		listMateriel = requeteMateriel.listerMaterielAvecQRCODE();
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



	public CommandButton getBtnAnnuler() {
		return btnAnnuler;
	}



	public void setBtnAnnuler(CommandButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
	}

	public Diagnostique getDiagnostique() {
		return diagnostique;
	}


	public void setDiagnostique(Diagnostique diagnostique) {
		this.diagnostique = diagnostique;
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

	public RequeteMateriel getRequeteMateriel() {
		return requeteMateriel;
	}

	public void setRequeteMateriel(RequeteMateriel requeteMateriel) {
		this.requeteMateriel = requeteMateriel;
	}

	public List<Parcours> getListParcours() {
		return listParcours;
	}

	public void setListParcours(List<Parcours> listParcours) {
		this.listParcours = listParcours;
	}

	public int getIdEntite() {
		return idEntite;
	}

	public void setIdEntite(int idEntite) {
		this.idEntite = idEntite;
	}

	public List<Service> getListService() {
		listService = service.getObjects("Service");
		return listService;
	}

	public void setListService(List<Service> listService) {
		this.listService = listService;
	}


	public List<Etat> getListEtat() {
		listEtat = service.getObjects("Etat");
		return listEtat;
	}


	public void setListEtat(List<Etat> listEtat) {
		this.listEtat = listEtat;
	}


	public int getIdEtat() {
		return idEtat;
	}


	public void setIdEtat(int idEtat) {
		this.idEtat = idEtat;
	}


	public Etat getEtat() {
		return etat;
	}


	public void setEtat(Etat etat) {
		this.etat = etat;
	}


	public Date getDateAffectation() {
		return dateAffectation;
	}


	public void setDateAffectation(Date dateAffectation) {
		this.dateAffectation = dateAffectation;
	}
}
