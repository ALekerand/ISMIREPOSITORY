package com.sati.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.LigneCommande;
import com.sati.requetes.RequeteBonCommande;

@Component
@Scope("session")
public class ConsulterBonCommandeController {
	
	@Autowired
	RequeteBonCommande requeteBonCommande;
	private List<LigneCommande> listLigneCommande = new ArrayList<LigneCommande>();
	String codeBonCommande;
	
	
	@SuppressWarnings("unchecked")
	public void ChargerLigneCommane() {
		listLigneCommande = requeteBonCommande.consulterBonCommande(codeBonCommande);
		
	}
	
	public  void annuler() {
		setListLigneCommande(null);
		setCodeBonCommande(null);
	}

	public String getCodeBonCommande() {
		return codeBonCommande;
	}

	public void setCodeBonCommande(String codeBonCommande) {
		this.codeBonCommande = codeBonCommande;
	}

	public List<LigneCommande> getListLigneCommande() {
		return listLigneCommande;
	}
	public void setListLigneCommande(List<LigneCommande> listLigneCommande) {
		this.listLigneCommande = listLigneCommande;
	}

}
