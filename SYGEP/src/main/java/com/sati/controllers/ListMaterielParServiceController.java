package com.sati.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Etat;
import com.sati.model.Materiel;
import com.sati.model.Parcours;
import com.sati.model.Personne;
import com.sati.model.Service;
import com.sati.model.UserAuthentication;
import com.sati.requetes.RequetePacours;
import com.sati.requetes.RequeteUtilisateur;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class ListMaterielParServiceController {

	
	@Autowired
	RequetePacours requetePacours;
	
	@Autowired
	Iservice service;
	
	private Service services = new Service();
	private Parcours parcours = new Parcours();
	private Materiel materiel = new Materiel();
	
	@Autowired
	RequeteUtilisateur requeteUtilisateur;
	
	UserAuthentication userAuthentication = new UserAuthentication();
	private List<Parcours> listMaterielParService = new ArrayList<Parcours>();
	private int idEntite;
	private List<Service> listService = new ArrayList<Service>();
	
	
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
	public void rechercher() {
		Personne personne = new Personne();
		personne = userAuthentication.getPersonne();
		services = (Service) service.getObjectById(idEntite, "Service");
		idEntite = services.getIdEntite();
		listMaterielParService = requetePacours.materielParService(idEntite);
		annuler();
	}

	public void annuler() {
		setIdEntite(0);
	}
	public UserAuthentication chagerUtilisateur() {
		return userAuthentication = requeteUtilisateur.recuperUser();
	}
	public List<Parcours> getListMaterielParService() {
		return listMaterielParService;
	}

	public void setListMaterielParService(List<Parcours> listMaterielParService) {
		this.listMaterielParService = listMaterielParService;
	}

	@SuppressWarnings("unchecked")
	public List<Service> getListService() {
		listService = service.getObjects("Service");
		
		Collections.sort(listService, new Comparator<Service>() {
	        @Override
	        public int compare(Service ob1, Service ob2)
	        {
	 
	            return  ob1.getNomService().compareTo(ob2.getNomService());
	        }
	    });
		return listService;
	}

	public void setListService(List<Service> listService) {
		this.listService = listService;
	}

	public Service getServices() {
		return services;
	}

	public void setServices(Service services) {
		this.services = services;
	}

	public int getIdEntite() {
		return idEntite;
	}

	public void setIdEntite(int idEntite) {
		this.idEntite = idEntite;
	}



}
