package com.sati.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Etat;
import com.sati.model.EtatInventaire;
import com.sati.model.Inventaire;
import com.sati.model.Materiel;
import com.sati.model.Point;
import com.sati.requetes.RequeteInventaire;
import com.sati.requetes.RequeteMateriel;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class PointController {

	
	@Autowired
	Iservice service;
	
	@Autowired
	RequeteInventaire requeteInventaire;
	
	private Point point = new Point();
	private List<Point> listPoint = new ArrayList<>();
	private Point selectedPoint;
	private List<EtatInventaire> listEtatInventaire = new ArrayList<>();
	private int idEtatInventaire;
	
	private List<Inventaire> listInventaire = new ArrayList<>();
	private Inventaire inventaire = new Inventaire();
	private int idInventaire;
	
	private List<Materiel> listMateriel = new ArrayList<>();
	private Materiel materiel = new Materiel();
	private Materiel selectedMateriel;
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	
	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
	
	}

	public void enregistrer() {
		point.setEtatInventaire((EtatInventaire) service.getObjectById(idEtatInventaire, "EtatInventaire"));
		point.setInventaire(inventaire);
		point.setMateriel(selectedMateriel);
		point.setDateEnregPoint(new Date());
		service.addObject(point);
		this.info("Enregistrement effectué avec succès!");
		annuler();
	}
	
	public void annuler() {
		setIdEtatInventaire(0);
		point.setManquantStock(null);
		point.setObservationPoint(null);
		setSelectedMateriel(null);
	}
	
	public void choisirLigneMateriel() {
		this.materiel = this.selectedMateriel;
	}
	
	public void selectionnerLigne() {
		this.point  = this.selectedPoint;
	}
	
	public void modifier() {
		service.updateObject(point);
		this.info("Modification effectuée avec succès!");
	}
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}
	
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	
	@SuppressWarnings("unchecked")
	public List<Point> getListPoint() {
		listPoint = service.getObjects("Point");
		return listPoint;
	}
	public void setListPoint(List<Point> listPoint) {
		this.listPoint = listPoint;
	}
	
	@SuppressWarnings("unchecked")
	public List<EtatInventaire> getListEtatInventaire() {
		listEtatInventaire = service.getObjects("EtatInventaire");
		
		return listEtatInventaire;
	}
	public void setListEtatInventaire(List<EtatInventaire> listEtatInventaire) {
		this.listEtatInventaire = listEtatInventaire;
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

	@SuppressWarnings("unchecked")
	public List<Inventaire> getListInventaire() {
		listInventaire = requeteInventaire.recupererLastInventaire();
		return listInventaire;
	}

	public void setListInventaire(List<Inventaire> listInventaire) {
		this.listInventaire = listInventaire;
	}

	public int getIdEtatInventaire() {
		return idEtatInventaire;
	}

	public void setIdEtatInventaire(int idEtatInventaire) {
		this.idEtatInventaire = idEtatInventaire;
	}

	public Inventaire getInventaire() {
		return inventaire = requeteInventaire.recupInventaireOuverte();
	}

	public void setInventaire(Inventaire inventaire) {
		this.inventaire = inventaire;
	}

	@SuppressWarnings("unchecked")
	public List<Materiel> getListMateriel() {
		listMateriel = service.getObjects("Materiel");
		
		Collections.sort(listMateriel, new Comparator<Materiel>() {
	        @Override
	        public int compare(Materiel ob1, Materiel ob2)
	        {
	 
	            return  ob1.getNomMateriel().compareTo(ob2.getNomMateriel());
	        }
	    });
		return listMateriel;
	}

	public void setListMateriel(List<Materiel> listMateriel) {
		this.listMateriel = listMateriel;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public Point getSelectedPoint() {
		return selectedPoint;
	}

	public void setSelectedPoint(Point selectedPoint) {
		this.selectedPoint = selectedPoint;
	}

	public Materiel getSelectedMateriel() {
		return selectedMateriel;
	}

	public void setSelectedMateriel(Materiel selectedMateriel) {
		this.selectedMateriel = selectedMateriel;
	}

	public int getIdInventaire() {
		return idInventaire;
	}

	public void setIdInventaire(int idInventaire) {
		this.idInventaire = idInventaire;
	}
}
