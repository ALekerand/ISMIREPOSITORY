package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Demande;
import com.sati.model.Diagnostique;
import com.sati.model.Entite;
import com.sati.model.EtatDemande;
import com.sati.model.Materiel;
import com.sati.model.Personne;
import com.sati.model.UserAuthentication;
import com.sati.requetes.RequeteDemande;
import com.sati.requetes.RequeteDiagnostique;
import com.sati.requetes.RequeteMateriel;
import com.sati.requetes.RequeteUtilisateur;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class TableauDeBordController {
	
	
	@Autowired
	Iservice service;
	

	@Autowired
	RequeteDemande requeteDemande;
	
	@Autowired
	RequeteDiagnostique requeteDiagnostique;
	
	@Autowired
	RequeteUtilisateur requeteUtilisateur;
	
	@Autowired
	RequeteMateriel requeteMateriel;
	private List<EtatDemande> listEtatDemande = new ArrayList<EtatDemande>();
	private List<Demande> listDemandeValider = new ArrayList<Demande>();
	private List<Diagnostique> listeMaterielMauvaisEtat = new ArrayList<Diagnostique>();
	private List<Materiel> listStockAlerteMateriel = new ArrayList<>();
	private UserAuthentication userAuthentication = new UserAuthentication();

	private int nbreDemandeAttente;
	private int nbreDemandeValider;
	private int nbreMaterielMauvaisEtat;
	private int nbreMaterielStockAlertAtteint;
	private int idEntite;
	
	public List<EtatDemande> getListEtatDemande() {
		
		
		return listEtatDemande;
	}

	public void setListEtatDemande(List<EtatDemande> listEtatDemande) {
		this.listEtatDemande = listEtatDemande;
	}
	
	@SuppressWarnings("unchecked")
	public int getNbreDemandeAttente() 
	{
		listEtatDemande = requeteDemande.traiterEtatDemande();
		nbreDemandeAttente = listEtatDemande.size();
		return nbreDemandeAttente;
	}

	public void setNbreDemandeAttente(int nbreDemandeAttente) {
		this.nbreDemandeAttente = nbreDemandeAttente;
	}

	public List<Demande> getListDemandeValider() {
		return listDemandeValider;
	}

	public void setListDemandeValider(List<Demande> listDemandeValider) {
		this.listDemandeValider = listDemandeValider;
	}

	@SuppressWarnings("unchecked")
	public int getNbreDemandeValider() {
		Personne personne = new Personne();
		Entite entite = new Entite();
		userAuthentication = requeteUtilisateur.recuperUser();
		personne = userAuthentication.getPersonne();
		entite = (Entite) service.getObjectById(personne.getIdEntite(), "Entite");
		setIdEntite(entite.getIdEntite());
		listDemandeValider = requeteDemande.afficherDemandeAccepte(idEntite);
		nbreDemandeValider = listDemandeValider.size();
		return nbreDemandeValider;
	}
	
	

	public void setNbreDemandeValider(int nbreDemandeValider) {
		this.nbreDemandeValider = nbreDemandeValider;
	}

	public int getIdEntite() {
		return idEntite;
	}

	public void setIdEntite(int idEntite) {
		this.idEntite = idEntite;
	}

	public UserAuthentication getUserAuthentication() {
		return userAuthentication;
	}

	public void setUserAuthentication(UserAuthentication userAuthentication) {
		this.userAuthentication = userAuthentication;
	}



	@SuppressWarnings("unchecked")
	public int getNbreMaterielMauvaisEtat() {
		listeMaterielMauvaisEtat = requeteDiagnostique.recupererMaterielMauvaisEtat();
		nbreMaterielMauvaisEtat = listeMaterielMauvaisEtat.size();
		return nbreMaterielMauvaisEtat;
	}

	public void setNbreMaterielMauvaisEtat(int nbreMaterielMauvaisEtat) {
		this.nbreMaterielMauvaisEtat = nbreMaterielMauvaisEtat;
	}

	public List<Diagnostique> getListeMaterielMauvaisEtat() {
		return listeMaterielMauvaisEtat;
	}

	public void setListeMaterielMauvaisEtat(List<Diagnostique> listeMaterielMauvaisEtat) {
		this.listeMaterielMauvaisEtat = listeMaterielMauvaisEtat;
	}

	@SuppressWarnings("unchecked")
	public List<Materiel> getListStockAlerteMateriel() {
		listStockAlerteMateriel = requeteMateriel.stockAlertMateriel();
		
		return listStockAlerteMateriel;
	}

	public void setListStockAlerteMateriel(List<Materiel> listStockAlerteMateriel) {
		this.listStockAlerteMateriel = listStockAlerteMateriel;
	}

	@SuppressWarnings("unchecked")
	public int getNbreMaterielStockAlertAtteint() {
		listStockAlerteMateriel = requeteMateriel.stockAlertMateriel();
		nbreMaterielStockAlertAtteint = listStockAlerteMateriel.size();
		return nbreMaterielStockAlertAtteint;
	}

	public void setNbreMaterielStockAlertAtteint(int nbreMaterielStockAlertAtteint) {
		this.nbreMaterielStockAlertAtteint = nbreMaterielStockAlertAtteint;
	}

}
