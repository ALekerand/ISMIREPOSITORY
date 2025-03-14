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

import com.sati.model.Famille;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class FamilleController {
	@Autowired
	Iservice service;
	private Famille famille = new Famille();
	private List<Famille> listFamille = new ArrayList<Famille>();
	private Famille selectedFamille = new Famille();
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	
	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
	}
	
	public void genererCode() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Famille").size();
		if(nbEnregistrement < 10)
			prefix = "FA00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "FA0" ;
		if (nbEnregistrement > 100) 
			prefix = "FA" ;
		this.famille.setCodeFamille(prefix+(nbEnregistrement+1));
	}

	public void enregistrer() {
		genererCode();
		this.service.addObject(this.famille);
		this.info("Eneregistrement effectué avec succès!");
		this.annuler();
		
	}

	public void selectionnerLigne() {
		this.famille = this.selectedFamille;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}

	public void annuler() {
		this.famille.setDescriptionFamille(null);
		this.famille.setLibFamille(null);
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
		
	}

	public void modifier() {
		this.service.updateObject(this.famille);
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

	public Famille getFamille() {
		
		return famille;
	}

	public void setFamille(Famille famille) {
		this.famille = famille;
	}

	@SuppressWarnings("unchecked")
	public List<Famille> getListFamille() {
		listFamille = service.getObjects("Famille");

		//=======Pour le rangement par ordre alphabétique======
				Collections.sort(listFamille, new Comparator<Famille>() {
			        @Override
			        public int compare(Famille ob1, Famille ob2)
			        {
			 
			            return  ob1.getLibFamille().compareTo(ob2.getLibFamille());
			        }
			    });
				//========================  Fin  =======================

		
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
	
	public Famille getSelectedFamille() {
		return selectedFamille;
	}

	public void setSelectedFamille(Famille selectedFamille) {
		this.selectedFamille = selectedFamille;
	}


	
}