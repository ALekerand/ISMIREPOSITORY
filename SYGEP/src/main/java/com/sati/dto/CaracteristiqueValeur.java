package com.sati.dto;

import com.sati.model.Caracteristique;

public class CaracteristiqueValeur {
	private Caracteristique caracteristique;
	private String valeurCaracteristique;
	
	
	//Accesseur Muttateurs
	
	public Caracteristique getCaracteristique() {
		return caracteristique;
	}
	public void setCaracteristique(Caracteristique caracteristique) {
		this.caracteristique = caracteristique;
	}
	
	public String getValeurCaracteristique() {
		return valeurCaracteristique;
	}
	public void setValeurCaracteristique(String valeurCaracteristique) {
		this.valeurCaracteristique = valeurCaracteristique;
	}
	

}
