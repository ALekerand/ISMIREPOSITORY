package com.sati.controllers;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Materiel;
import com.sati.model.NonFongible;
import com.sati.requetes.RequeteNonFongible;
import com.sati.service.Iservice;

@Component
@Scope("session")

public class RetraitMaterielController {
		@Autowired
		Iservice service;
		
		@Autowired
		RequeteNonFongible requeteNonFongible;
		//RequeteMateriel requeteMateriel;
		
		private List<NonFongible> listNonFongible = new ArrayList<NonFongible>();
		private List<NonFongible> listNonfongibleRetires = new ArrayList<NonFongible>();
		private NonFongible selectedNonFongible = new NonFongible();
		private NonFongible nonFongible  = new NonFongible();
		private Materiel materiel = new Materiel();

		
		
		public void choisirLigne() {
			this.nonFongible = this.selectedNonFongible; 
			System.out.println("Selection faite");
			}
		 
		
		public void retirer() {
			try {
				selectedNonFongible.setRetraitMateriel(true);
				service.updateObject(selectedNonFongible);
				//materiel = (Materiel) service.getObjectById(nonFongible.getIdMateriel(), "Materiel");
				//materiel.setRetrait(true);
				//service.updateObject(materiel);
				info("Retrait effectué avec succès!");
			} catch (java.lang.NullPointerException e) {
				// TODO Auto-generated catch block
				erreur("Vous devez au préalable selectioner le materiel concerné" );
			}
		}
		
		
		
		public void info(String monMessage) {
			FacesContext.getCurrentInstance().addMessage((String) null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage,null ));
		}
		
		public void erreur(String monMessage) {
			FacesContext.getCurrentInstance().addMessage((String) null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, monMessage,null ));
		}
		
		
		public List<NonFongible> getListNonFongible() {
			
			listNonFongible = requeteNonFongible.listerNonfongibleNonretires();
			
			Collections.sort(listNonFongible, new Comparator<NonFongible>() {
		        @Override
		        public int compare(NonFongible ob1, NonFongible ob2)
		        {
		 
		            return  ob1.getNomMateriel().compareTo(ob2.getNomMateriel());
		        }
		    });
			return listNonFongible;
		}



		public void setListNonFongible(List<NonFongible> listNonFongible) {
			this.listNonFongible = listNonFongible;
		}



		public List<NonFongible> getListNonfongibleRetires() {
			return listNonfongibleRetires = requeteNonFongible.listerNonFongibleRetires();
		}



		public void setListNonfongibleRetires(List<NonFongible> listNonfongibleRetires) {
			this.listNonfongibleRetires = listNonfongibleRetires;
		}



		public NonFongible getSelectedNonFongible() {
			return selectedNonFongible;
		}



		public void setSelectedNonFongible(NonFongible selectedNonFongible) {
			this.selectedNonFongible = selectedNonFongible;
		}
	}


