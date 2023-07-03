package com.sati.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.System.Logger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Boncommande;
import com.sati.model.Bonlivraison;
import com.sati.model.UserAuthentication;
import com.sati.requetes.RequeteBonCommande;
import com.sati.requetes.RequeteUtilisateur;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class BonLivraisonController {

	
	@Autowired
	Iservice service;
	@Autowired
	RequeteUtilisateur requeteUtilisateur;
	@Autowired
	RequeteBonCommande requeteBonCommande;
	private Bonlivraison bonlivraison = new Bonlivraison();
	private List<Bonlivraison> listObject = new ArrayList<Bonlivraison>();
	private Bonlivraison   selectedObject = new Bonlivraison();
	UserAuthentication userAuthentication = new UserAuthentication();
	private Date dateLivraison;
	private Boncommande bonCommande = new Boncommande();
	private List<Boncommande> listeBonCommande = new ArrayList<Boncommande>();
	private List<Boncommande> listBonCommande = new ArrayList<Boncommande>();
	private Boncommande selectedObjectBC = new Boncommande();
	private int idBonCommande;
	private String chemin = "C:\\Users\\HP\\Desktop\\fichierJSF\\";
	private UploadedFile fichier;
	
	
	
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();

	
	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		chagerUtilisateur();
		genererCodeBonlivraison();
	}
	
	public UserAuthentication chagerUtilisateur() {
		return userAuthentication = requeteUtilisateur.recuperUser();
	}
	
	public String genererCodeBonlivraison() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Bonlivraison").size();
		if(nbEnregistrement < 10)
			prefix = "BL00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "BL0" ;
		if (nbEnregistrement > 100) 
			prefix = "BL" ;
		return new String(prefix+(nbEnregistrement+1));
	}
	
	public void upload() {
		String extValidate;
		if(getFichier() != null) {
			String ext = getFichier().getFileName();
			if(ext != null) {
				extValidate = ext.substring(ext.indexOf(".")+1);
			}else {
				extValidate = "null";
			}
			if(extValidate.equals("docx") || extValidate.equals("pdf") || extValidate.equals("pptx") || extValidate.equals("xlsx")) {
				try {
					transfererFile(getFichier().getFileName(), getFichier().getInputstream());
				}catch (IOException ex) {
					System.out.println(ex);
				}
			}
		}
	}
	public void transfererFile(String fileName, InputStream in) {
		try {
			OutputStream out = new FileOutputStream(new File(chemin + fileName));
			int reader = 0;
			byte[] bytes = new byte[(int)getFichier().getSize()];
			while ((reader = in.read(bytes))!= -1) {
				out.write(bytes,0,reader);
			}
			in.close();
			out.flush();
			out.close();
		}catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void Enregistrer() {
		System.out.println("Lancement de la méthode!");
		bonlivraison.setCodeBonLivraison(genererCodeBonlivraison());
		bonlivraison.setDateEnregistrementLivraison(new Date());
		SimpleDateFormat formateurDate = new SimpleDateFormat("yyyy-MM-dd");
		String date = formateurDate.format(dateLivraison);
		bonlivraison.setDateLivraison(dateLivraison);
		bonlivraison.setFichier(chemin);
		bonlivraison.setPersonne(userAuthentication.getPersonne());
		bonlivraison.setBoncommande(bonCommande);
		this.service.addObject(bonlivraison);
		bonCommande.setBonlivraison(bonlivraison);
		this.service.updateObject(bonCommande);
	}
	
	public void selectionnerLigne() {
		this.bonCommande = this.selectedObjectBC;
	}
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage ,null ));
	}
	
	@SuppressWarnings("unchecked")
	public List<Bonlivraison> getListObject() {
		listObject = service.getObjects("Bonlivraison");
		System.out.println("=========Taille de la liste:"+listObject.size());
		return listObject;
	}
	public void setListObject(List<Bonlivraison> listObject) {
		this.listObject = listObject;
	}
	public Bonlivraison getSelectedObject() {
		return selectedObject;
	}
	public void setSelectedObject(Bonlivraison selectedObject) {
		this.selectedObject = selectedObject;
	}
	public CommandButton getBtnEnregistrer() {
		return btnEnregistrer;
	}
	public void setBtnEnregistrer(CommandButton btnEnregistrer) {
		this.btnEnregistrer = btnEnregistrer;
	}
	public CommandButton getBtnAnnuler() {
		return btnAnnuler;
	}
	public void setBtnAnnuler(CommandButton btnAnnuler) {
		this.btnAnnuler = btnAnnuler;
	}
	public CommandButton getBtnModifier() {
		return btnModifier;
	}
	public void setBtnModifier(CommandButton btnModifier) {
		this.btnModifier = btnModifier;
	}

	public Date getDateLivraison() {
		return dateLivraison;
	}

	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}

	public Boncommande getBonCommande() {
		return bonCommande;
	}

	public void setBonCommande(Boncommande bonCommande) {
		this.bonCommande = bonCommande;
	}

	public Bonlivraison getBonlivraison() {
		return bonlivraison;
	}

	public void setBonlivraison(Bonlivraison bonlivraison) {
		this.bonlivraison = bonlivraison;
	}

	
	public List<Boncommande> getListBonCommande() {
		return listBonCommande;
	}

	public void setListBonCommande(List<Boncommande> listBonCommande) {
		this.listBonCommande = listBonCommande;
	}

	@SuppressWarnings("unchecked")
	public List<Boncommande> getListeBonCommande() {
		listeBonCommande = requeteBonCommande.listBonCommande();
		return listeBonCommande;
	}

	public void setListeBonCommande(List<Boncommande> listeBonCommande) {
		this.listeBonCommande = listeBonCommande;
	}

	public Boncommande getSelectedObjectBC() {
		return selectedObjectBC;
	}

	public void setSelectedObjectBC(Boncommande selectedObjectBC) {
		this.selectedObjectBC = selectedObjectBC;
	}

	
	public UploadedFile getFichier() {
		return fichier;
	}

	public void setFichier(UploadedFile fichier) {
		this.fichier = fichier;
	}

	public int getIdBonCommande() {
		return idBonCommande;
	}

	public void setIdBonCommande(int idBonCommande) {
		this.idBonCommande = idBonCommande;
	}

	public String getDossier() {
		return chemin;
	}

	public void setDossier(String dossier) {
		this.chemin = dossier;
	}

	

}
