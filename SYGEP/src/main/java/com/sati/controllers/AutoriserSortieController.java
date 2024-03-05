package com.sati.controllers;

import java.text.SimpleDateFormat;
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

import com.sati.model.Demande;
import com.sati.model.Sortie;
import com.sati.model.UserAuthentication;
import com.sati.requetes.RequeteDemande;
import com.sati.requetes.RequeteUtilisateur;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class AutoriserSortieController {
	
	@Autowired
	RequeteDemande requeteDemande;
	@Autowired
	Iservice service;
	@Autowired
	RequeteUtilisateur requeteUtilisateur;
	private Demande demande = new Demande();
	private List<Demande> listObject = new ArrayList<>();
	private Sortie sortie = new Sortie();
	private int idSortie;
	private UserAuthentication userAuthentication = new UserAuthentication();
	private List<Sortie> listSortie = new ArrayList<Sortie>();
	private Demande selectedDemande = new Demande();
	private int idDemande;
	private Date dateEnregSortie;
	
	
	private CommandButton btnEnregistrer  = new CommandButton();
	

	@PostConstruct
	public void initialiser() {
		chagerUtilisateur();
		genererCode();
	}
	
	public UserAuthentication chagerUtilisateur() {
		return userAuthentication = requeteUtilisateur.recuperUser();
	}
	
	public void genererCode() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Sortie").size();
		if(nbEnregistrement < 10)
			prefix = "ST00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "ST0" ;
		if (nbEnregistrement > 100) 
			prefix = "ST" ;
		this.sortie.setCodeSortie(prefix+(nbEnregistrement+1));
	}

	
	public void enregistrer() {
		sortie.setDateSortie(new Date());
		SimpleDateFormat formateurDate = new SimpleDateFormat("yyyy-MM-dd");
		String date = formateurDate.format(dateEnregSortie);
		sortie.setDateEnregSortie(dateEnregSortie);
		sortie.setPersonne(userAuthentication.getPersonne());
		sortie.setDemande(demande);
		this.service.addObject(this.sortie);
		demande.setSortie(sortie);
		service.updateObject(demande);
		sortie.setDemande(demande);
		service.updateObject(sortie);
		info("Enregistrement effectué avec succès!");
	}
	
	
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage, null));
	}

	public void selectionnerLigne() {
		this.demande = this.selectedDemande;
	}

	public Demande getDemande() {
		return demande;
	}
	public void setDemande(Demande demande) {
		this.demande = demande;
	}
	
	@SuppressWarnings("unchecked")
	public List<Demande> getListObject() {
		listObject = requeteDemande.afficherDemandeReceptionner();
		
		 Collections.sort(listObject, new Comparator<Demande>() {
		        @Override
		        public int compare(Demande ob1, Demande ob2)
		        {
		 
		            return  ob1.getEntite().getPersonne().getNomPersonne().compareTo(ob2.getEntite().getPersonne().getNomPersonne());
		        }
		    });
		return listObject;
	}
	public void setListObject(List<Demande> listObject) {
		this.listObject = listObject;
	}
	public Sortie getSortie() {
		genererCode();
		return sortie;
	}
	public void setSortie(Sortie sortie) {
		this.sortie = sortie;
	}
	@SuppressWarnings("unchecked")
	public List<Sortie> getListSortie() {
		listSortie = service.getObjects("Sortie");
		return listSortie;
	}

	public void setListSortie(List<Sortie> listSortie) {
		this.listSortie = listSortie;
	}


	public UserAuthentication getUserAuthentication() {
		return userAuthentication;
	}


	public void setUserAuthentication(UserAuthentication userAuthentication) {
		this.userAuthentication = userAuthentication;
	}
	

	public int getIdDemande() {
		return idDemande;
	}

	public void setIdDemande(int idDemande) {
		this.idDemande = idDemande;
	}

	public int getIdSortie() {
		return idSortie;
	}

	public void setIdSortie(int idSortie) {
		this.idSortie = idSortie;
	}

	public Demande getSelectedDemande() {
		return selectedDemande;
	}

	public void setSelectedDemande(Demande selectedDemande) {
		this.selectedDemande = selectedDemande;
	}

	public Date getDateEnregSortie() {
		return dateEnregSortie;
	}

	public void setDateEnregSortie(Date dateEnregSortie) {
		this.dateEnregSortie = dateEnregSortie;
	}

	public CommandButton getBtnEnregistrer() {
		return btnEnregistrer;
	}

	public void setBtnEnregistrer(CommandButton btnEnregistrer) {
		this.btnEnregistrer = btnEnregistrer;
	}
}
