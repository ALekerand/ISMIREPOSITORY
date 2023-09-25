package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Diagnostique;
import com.sati.model.Materiel;
import com.sati.requetes.RequeteDiagnostique;
import com.sati.requetes.RequeteMateriel;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class ConsulterListDiagnosticController {

	
	@Autowired
	Iservice service;
	@Autowired
	RequeteDiagnostique requeteDiagnostique;
	@Autowired
	RequeteMateriel requeteMateriel;
	private List<Materiel> listeMateriels = new ArrayList<Materiel>();
	private	List<Diagnostique> listeDiagnostique = new ArrayList<Diagnostique>();
	private	Diagnostique diagnostique = new  Diagnostique();

	public List<Materiel> getListeMateriels() {
		return listeMateriels;
	}

	public void setListeMateriels(List<Materiel> listeMateriels) {
		this.listeMateriels = listeMateriels;
	}

	@SuppressWarnings("unchecked")
	public List<Diagnostique> getListeDiagnostique() {
		   System.out.println(" \n\n ######################################### \n\n");
		    System.out.println("  LISTE DES MATÉRIELS AVEC LEURS ÉTATS ");
		    System.out.println(" \n\n ######################################### \n\n");

		    System.out.println(" === AFFICHAGE DE LA LISTE DES MATERIELS AVEC QRCODE  ===");
		    
		    listeMateriels = requeteMateriel.listerMaterielAvecQRCODE();
		    
		    System.out.println("==================="+listeMateriels.size());
		    
		    listeDiagnostique.clear();
		    
		    for (Materiel materiel : listeMateriels ) {
		       try {
				diagnostique = requeteDiagnostique.recupererLastDiagnostiqueDuMateriel(materiel.getIdMateriel());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				continue;
			}
		       listeDiagnostique.add(diagnostique);
		    }

		    System.out.println(" === AFFICHAGE DE LA LISTE DU DIAGNOSTIQUE  === \n\n");
		    
		    System.out.println(listeDiagnostique);
		return listeDiagnostique;
	}

	public void setListeDiagnostique(List<Diagnostique> listeDiagnostique) {
		this.listeDiagnostique = listeDiagnostique;
	}

	public Diagnostique getDiagnostique() {
		return diagnostique;
	}

	public void setDiagnostique(Diagnostique diagnostique) {
		this.diagnostique = diagnostique;
	}

	
	
}

