package com.sati.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Marque;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class MarqueController {
	
	@Autowired
	Iservice service;
	private Marque marque = new Marque();
	private List<Marque> listObject = new ArrayList<Marque>();
	private Marque selectedMarque;
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	
	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		marque.setCodeMarque(genererCodeMarque());
	}
	
	public String genererCodeMarque() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Marque").size();
		if(nbEnregistrement < 10)
			prefix = "MQ00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "MQ0" ;
		if (nbEnregistrement > 100) 
			prefix = "MQ" ;
		return new String(prefix+(nbEnregistrement+1));
	}
	
	public void enregistrer() {
		this.service.addObject(this.marque);
		annuler();
		this.info("Enregistrement effectué avec succès!");
		marque.setCodeMarque(genererCodeMarque());
	}
	
	public void modifier() {
		this.service.updateObject(marque);
		annuler();
		this.info("Modification effectuée avec succès!");
	}
	
	public void annuler() {
		marque.setCodeMarque(null);
		marque.setLibelleMarque(null);
		marque.setCodeMarque(genererCodeMarque());
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
	}
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage,null ));
	}

	public void selectionnerLigne() {
		this.marque = this.selectedMarque;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
		
	}
	@SuppressWarnings("unchecked")
	public List<Marque> getListObject() {
		listObject = service.getObjects("Marque");
		
		//=======Pour le rangement par ordre alphabétique======
				Collections.sort(listObject, new Comparator<Marque>() {
			        @Override
			        public int compare(Marque ob1, Marque ob2)
			        {
			            return  ob1.getLibelleMarque().compareTo(ob2.getLibelleMarque());
			        }
			    });
		//========================  Fin  =======================

		Collections.sort(listObject, new Comparator<Marque>() {
	        @Override
	        public int compare(Marque ob1, Marque ob2)
	        {
	 
	            return  ob1.getLibelleMarque().compareTo(ob2.getLibelleMarque());
	        }
	    });
		System.out.println("=========Taille de la liste:"+listObject.size());
		return listObject;
	}
	public void setListObject(List<Marque> listObject) {
		this.listObject = listObject;
	}
	public Marque getMarque() {
		return marque;
	}
	public void setMarque(Marque marque) {
		this.marque = marque;
	}
	public Marque getSelectedMarque() {
		return selectedMarque;
	}
	public void setSelectedMarque(Marque selectedMarque) {
		this.selectedMarque = selectedMarque;
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

}
