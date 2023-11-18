package com.sati.controllers;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.sati.model.Materiel;
import com.sati.model.Parcours;
import com.sati.requetes.RequetePacours;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class DetailDuQrCodeController {

	
	@Autowired
	Iservice service;
	
	@Autowired
	RequetePacours requetePacours;
	
	private Parcours parcours = new Parcours();
	
	private Materiel materiel = new Materiel();
	
	
	
	//Pour le QR code
	private String data;
	
	@PostConstruct
	public void initialiser() {
		
		parcours = requetePacours.recupererLastParcoursParMateriel(parcours.getMateriel().getIdMateriel());
		
		System.out.println("Code: "+parcours.getMateriel().getCodeMateriel());
		System.out.println("Désignation: " +parcours.getMateriel().getNomMateriel());
		System.out.println("Magasin d'origine: " +parcours.getMateriel().getMagasin().getNomMagasin());
		System.out.println("Position actuelle:"+parcours.getService());
	/*	data = "Code: "+parcours.getMateriel().getCodeMateriel()+"\n"+
				"Désignation: " +parcours.getMateriel().getNomMateriel()+"\n"+
				"Magasin d'origine: " +parcours.getMateriel().getMagasin().getNomMagasin()+"\n"+
				"Position actuelle:"+parcours.getService();
				*/
		
	}

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
}
