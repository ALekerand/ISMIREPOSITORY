package com.sati.controllers;

import java.io.File;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sati.dto.CaracteristiqueValeur;
import com.sati.model.Caracteristique;
import com.sati.model.Diagnostique;
import com.sati.model.Entree;
import com.sati.model.Etat;
import com.sati.model.Famille;
import com.sati.model.Fongible;
import com.sati.model.Fournisseur;
import com.sati.model.Magasin;
import com.sati.model.Marque;
import com.sati.model.Materiel;
import com.sati.model.Nature;
import com.sati.model.NonFongible;
import com.sati.model.SourceFinancement;
import com.sati.model.UserAuthentication;
import com.sati.model.Valeur;
import com.sati.reportBean.QRCodeReportBean;
import com.sati.requetes.RequeteNature;
import com.sati.requetes.RequeteUtilisateur;
import com.sati.requetes.RequeteUtilitaire;
import com.sati.service.Iservice;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@SuppressWarnings({ "unchecked", "unchecked" })
@Component
@Scope("session")
public class MaterielWithQRController {
	
	@Autowired
	Iservice service;
	
	//Injection de controle
	@Autowired
	private RequeteNature requeteNature;
	@Autowired
	RequeteUtilisateur requeteUtilisateur;
	@Autowired
	QRCodeReportBean qrCodeReportBean;
	@Autowired
	RequeteUtilitaire requeteUtilitaire;
	
	
	private Materiel materiel = new Materiel();
	private Fongible fongible= new Fongible();
	private NonFongible nonfongible= new NonFongible();
	private Entree entree = new Entree();
	private UserAuthentication userAuthentication = new UserAuthentication();
	@SuppressWarnings("unused")
	private List<Materiel> listTable = new ArrayList<Materiel>();
	private Materiel selectedObject = new Materiel();
	private Diagnostique diagnostique = new Diagnostique();
	private int idFamille;
	private int idNature;
	private int idMagasin;
	private int idFournisseur;
	private int idSource;
	private int idMarque;
	private int stockActuel;
	private int stockAlerte;
	private int idEtat;
	private String etatFongible;
	private List<Famille> listFamille = new ArrayList<Famille>();
	private List<Nature> listNature = new ArrayList<Nature>();
	private List<Magasin> listMagasin = new ArrayList<Magasin>();
	private List<Marque> listMarque = new ArrayList<Marque>();
	private List<SourceFinancement> listSourceFinance = new ArrayList<SourceFinancement>();
	private List<CaracteristiqueValeur> listCaracteristiqueValeur = new ArrayList<CaracteristiqueValeur>();
	private List<Fournisseur> listFournisseur = new ArrayList<Fournisseur>();
	private List<Etat> listEtat = new ArrayList<Etat>();
	private String typeMateriel="";
	
	//Pour le QR code
	private String data;
	private String path = "C:\\SYGEP\\";
	private CommandButton btnEnregistrer = new CommandButton();
	private CommandButton btnAnnuler = new CommandButton();
	private CommandButton btnModifier = new CommandButton();
	private SelectOneMenu cbNature = new SelectOneMenu();
	
	

	@PostConstruct
	public void initialiser() {
		this.btnModifier.setDisabled(true);
		this.cbNature.setDisabled(true);
		chargerListeCaracteristiqueValeur();
		chagerUtilisateur();
	}
	
	public UserAuthentication chagerUtilisateur() {
		return userAuthentication = requeteUtilisateur.recuperUser();
	}
	
	public void genererCode(){
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Materiel").size();
		if(nbEnregistrement < 10)
			prefix = "MTQR00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "MTQR0" ;
		if (nbEnregistrement > 100) 
			prefix = "MTQR" ;
		this.materiel.setCodeMateriel(prefix+(nbEnregistrement+1));
	}
	
	public String genererCodeEntree() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Entree").size();
		if(nbEnregistrement < 10)
			prefix = "CE00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CE0" ;
		if (nbEnregistrement > 100) 
			prefix = "CE" ;
		return new String(prefix+(nbEnregistrement+1));
	}
	
	
	public String genererCodeValeur() {
		String prefix="";
		int nbEnregistrement = this.service.getObjects("Valeur").size();
		if(nbEnregistrement < 10)
			prefix = "CV00" ;
		if ((nbEnregistrement >= 10) && (nbEnregistrement < 100)) 
			prefix = "CV0" ;
		if (nbEnregistrement > 100) 
			prefix = "CV" ;
		return new String(prefix+(nbEnregistrement+1));
	}
	
	@SuppressWarnings("unchecked")
	public void chargerListeCaracteristiqueValeur() {
		 for (Caracteristique caracteristique : (List<Caracteristique>)service.getObjects("Caracteristique")){
			 CaracteristiqueValeur  caracteristiqueValeur = new CaracteristiqueValeur();
			 caracteristiqueValeur.setCaracteristique(caracteristique);
			 listCaracteristiqueValeur.add(caracteristiqueValeur);
		}
	}
	
	
	public void genererQRCode() throws WriterException, IOException, JRException {
		String nom_fichier = materiel.getCodeMateriel()+".png";
		data =  "===== INFORMATION MATERIEL ====="+"\n"+
				" " +"\n"+
				"Code: "+materiel.getCodeMateriel()+"\n"+
				"Désignation: " +materiel.getNomMateriel()+"\n"+
				"Magasin d'origine: " +materiel.getMagasin().getNomMagasin()+"\n"+
				"Position actuelle: lien en dessous" +"\n"+
				" "+"\n"+
				"Localisation: "+ "http://154.0.30.233/SYGEP/localisation.xhtml"+"\n"+
				"================================";
		
		QRCodeWriter qr = new QRCodeWriter();
		BitMatrix matrix = qr.encode(data, BarcodeFormat.QR_CODE, 150, 150);
		MatrixToImageWriter.writeToPath(matrix, "PNG", Paths.get(path+nom_fichier));
		//Génération du QR CODE		
		JasperDesign jasperDesign = JRXmlLoader.load("C:/SYGEP/qr_code_repport.jrxml");
		
		//Compilation du fichier
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		
		InputStream is = new FileInputStream("C:/SYGEP/"+nom_fichier);
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("image_QR", is);
			
		// Remplissage du rapport compilé
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, new JREmptyDataSource());
		
		// Visualisation, exportation ou impression 
	    JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/SYGEP/"+materiel.getCodeMateriel()+".pdf");
	}
	
	
	// Ouvrir un fichier PDF dans le navigateur
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        File pdfFile = new File("C:/chemin/vers/votre-fichier.pdf");
	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "inline; filename=" + pdfFile.getName());
	        
	        try (FileInputStream fis = new FileInputStream(pdfFile);
	             OutputStream os = response.getOutputStream()) {
	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = fis.read(buffer)) != -1) {
	                os.write(buffer, 0, bytesRead);
	            }
	        }
	    }
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public void chargerNature() {
		listNature = requeteNature.listerNatureParFamille(idFamille);
		this.cbNature.setDisabled(false);
	}

	public void enregistrer() throws WriterException, IOException, JRException {
		//Enregistrement dans la table Caracteristique
		Famille familleProduit = (Famille) service.getObjectById(idFamille, "Famille");
		Nature natureProduit = (Nature) service.getObjectById(idNature, "Nature");
		Magasin magasin = (Magasin)service.getObjectById(idMagasin,"Magasin");
		Marque marque = (Marque) service.getObjectById(idMarque, "Marque");
		this.materiel.setNature(natureProduit);
		this.materiel.setMagasin(magasin);
		this.materiel.setMarque(marque);
		this.materiel.setRetrait(false);
		this.service.addObject(this.materiel);
		
		//Donner le diqgnostique
		diagnostique.setMateriel(materiel);
		diagnostique.setEtat((Etat) service.getObjectById(idEtat, "Etat"));
		diagnostique.setDateDiagnostique(new Date());
		diagnostique.setCommentaire("Etat du materiel lors de l'acquisition");
		service.addObject(diagnostique);
		
		//Enregistrement dans fongible et non fongible
		
		
			//Enregistrer dans non fongible
			this.nonfongible.setIdNature(idNature);
			this.nonfongible.setIdMagasin(idMagasin);
			this.nonfongible.setIdMarque(idMarque);
			this.nonfongible.setNomMateriel(materiel.getNomMateriel());
			this.nonfongible.setCodeMateriel(materiel.getCodeMateriel());
			this.nonfongible.setRetrait(materiel.getRetrait());
			this.nonfongible.setDescriptionMateriel(materiel.getDescriptionMateriel());
			this.nonfongible.setReferenceMateriel(materiel.getReferenceMateriel());
			this.nonfongible.setMateriel(materiel);
			this.service.addObject(this.nonfongible);
			
			//Générer le QR code
			genererQRCode();
		
		//Enregistrement dans la table Valeur 
		for (CaracteristiqueValeur caracteristiqueValeur : listCaracteristiqueValeur) {
			if(caracteristiqueValeur.getValeurCaracteristique()!="") {
				Valeur valeur = new Valeur();
				valeur.setCode(genererCodeValeur());
				valeur.setValeurCaracteristique(caracteristiqueValeur.getValeurCaracteristique());
				valeur.setCaracteristique(caracteristiqueValeur.getCaracteristique());
				valeur.setMateriel(materiel);
				service.addObject(valeur);
			}
		}
		this.info("Eneregistrement effectué avec succès!");
		
		this.enregistrerEtree();
		
		this.annuler();
	}
	
	
	public void enregistrerEtree() {
		this.entree.setMateriel(materiel);
		this.entree.setQteEntree(1);
		
		this.entree.setDateEnregistrement(new Date());
		this.entree.setSourceFinancement((SourceFinancement)service.getObjectById(idSource, "SourceFinancement"));
		this.entree.setPersonne(userAuthentication.getPersonne());
		
		if(idFournisseur !=0) {
		this.entree.setFournisseur((Fournisseur)service.getObjectById(idFournisseur, "Fournisseur"));
		}
		
		this.entree.setCodeEntre(genererCodeEntree());
		this.service.addObject(this.entree);
		
		if (typeMateriel.equals("MATERIEL FONGIBLE")) {
			fongible.setStockActuel(this.fongible.getStockActuel()+ this.entree.getQteEntree());
			service.updateObject(materiel);
		}
	}

	public void selectionnerLigne() {
		this.materiel = this.selectedObject;
		this.btnEnregistrer.setDisabled(true);
		this.btnModifier.setDisabled(false);
	}

	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null, new FacesMessage(FacesMessage.SEVERITY_INFO, monMessage,null ));
	}

	public void error() {
		FacesContext.getCurrentInstance().addMessage((String) null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
	}

	public void annuler() {
		this.materiel.setCodeMateriel(null);
		this.materiel.setReferenceMateriel(null);
		this.materiel.setDescriptionMateriel(null);
		this.materiel.setNature(null);
		this.materiel.setMagasin(null);
		this.materiel.setNomMateriel(null);
		this.materiel.setMagasin(null);
		this.setIdFamille(0);
		this.setIdMagasin(0);
		this.setIdNature(0);
		this.setIdMarque(0);
		this.setIdSource(0);
		this.setIdFournisseur(0);
		//this.rdbFondible.setItemIndex(2);
		
		
		// vider la liste des valeurs des ^caracteristiques
		for (CaracteristiqueValeur caracteristiqueValeur : listCaracteristiqueValeur) {
			caracteristiqueValeur.setValeurCaracteristique("");
			
		}
		//info("Annulation effectuée avec succès!");
	}

	public void modifier() {
		this.service.updateObject(this.materiel);
		this.annuler();
		this.info("Modification effectué avec succès!");
		this.btnModifier.setDisabled(true);
		this.btnEnregistrer.setDisabled(false);
		this.btnAnnuler.setDisabled(false);
	}
	
	
	 public void onRowEdit(RowEditEvent event) {
	    info("Valeur de caractéristique éditée");
	    }
	 
	 
	 public void onRowCancel(RowEditEvent event) {	        
	        info("Edition terminée");
	    }
	
	
	//Accesseur & Mutateur
	
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

	public int getIdFamille() {
		return idFamille;
	}

	public void setIdFamille(int idFamille) {
		this.idFamille = idFamille;
	}

	@SuppressWarnings("unchecked")
	public List<Famille> getListFamille() {
		listFamille = service.getObjects("Famille");
		
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

	public Materiel getMateriel() {
		genererCode();
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	@SuppressWarnings("unchecked")
	public List<Materiel> getListTable() {
		 listTable = service.getObjects("Materiel");
		 
		 Collections.sort(listTable, new Comparator<Materiel>() {
		        @Override
		        public int compare(Materiel ob1, Materiel ob2)
		        {
		 
		            return  ob1.getNomMateriel().compareTo(ob2.getNomMateriel());
		        }
		    });
		 return listTable;
	}

	public void setListTable(List<Materiel> listTable) {
		this.listTable = listTable;
	}

	public Materiel getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Materiel selectedObject) {
		this.selectedObject = selectedObject;
	}

	public int getIdNature() {
		return idNature;
	}

	public void setIdNature(int idNature) {
		this.idNature = idNature;
	}

	@SuppressWarnings("unchecked")
	public List<Nature> getListNature() {
		return listNature;
	}

	public void setListNature(List<Nature> listNature) {
		this.listNature = listNature;
	}

	@SuppressWarnings("unchecked")
	public List<Magasin> getListMagasin() {
		 listMagasin = service.getObjects("Magasin");
		 
		 Collections.sort(listMagasin, new Comparator<Magasin>() {
		        @Override
		        public int compare(Magasin ob1, Magasin ob2)
		        {
		 
		            return  ob1.getNomMagasin().compareTo(ob2.getNomMagasin());
		        }
		    });
		 return listMagasin;
	}

	public void setListMagasin(List<Magasin> listMagasin) {
		this.listMagasin = listMagasin;
	}

	public int getIdMagasin() {
		return idMagasin;
	}

	public void setIdMagasin(int idMagasin) {
		this.idMagasin = idMagasin;
	}

	public List<CaracteristiqueValeur> getListCaracteristiqueValeur() {
		return listCaracteristiqueValeur;
	}

	public void setListCaracteristiqueValeur(List<CaracteristiqueValeur> listCaracteristiqueValeur) {
		this.listCaracteristiqueValeur = listCaracteristiqueValeur;
	}

	public Fongible getFongible() {
		return fongible;
	}

	public void setFongible(Fongible fongible) {
		this.fongible = fongible;
	}

	public int getStockActuel() {
		return stockActuel;
	}

	public void setStockActuel(int stockActuel) {
		this.stockActuel = stockActuel;
	}

	public int getStockAlerte() {
		return stockAlerte;
	}

	public void setStockAlerte(int stockAlerte) {
		this.stockAlerte = stockAlerte;
	}

	public String getEtatFongible() {
		return etatFongible;
	}

	public void setEtatFongible(String etatFongible) {
		this.etatFongible = etatFongible;
	}

	public SelectOneMenu getCbNature() {
		return cbNature;
	}

	public void setCbNature(SelectOneMenu cbNature) {
		this.cbNature = cbNature;
	}

	public int getIdFournisseur() {
		return idFournisseur;
	}

	public void setIdFournisseur(int idFournisseur) {
		this.idFournisseur = idFournisseur;
	}

	public int getIdSource() {
		return idSource;
	}

	public void setIdSource(int idSource) {
		this.idSource = idSource;
	}

	public List<SourceFinancement> getListSourceFinance() {
		listSourceFinance = service.getObjects("SourceFinancement");
		
		Collections.sort(listSourceFinance, new Comparator<SourceFinancement>() {
	        @Override
	        public int compare(SourceFinancement ob1, SourceFinancement ob2)
	        {
	 
	            return  ob1.getLibSource().compareTo(ob2.getLibSource());
	        }
	    });
		return listSourceFinance;
	}

	public void setListSourceFinance(List<SourceFinancement> listSourceFinance) {
		this.listSourceFinance = listSourceFinance;
	}

	public List<Fournisseur> getListFournisseur() {
		listFournisseur = service.getObjects("Fournisseur");
		
		Collections.sort(listFournisseur, new Comparator<Fournisseur>() {
	        @Override
	        public int compare(Fournisseur ob1, Fournisseur ob2)
	        {
	 
	            return  ob1.getNomFournisseur().compareTo(ob2.getNomFournisseur());
	        }
	    });
		return listFournisseur;
	}

	public void setListFournisseur(List<Fournisseur> listFournisseur) {
		this.listFournisseur = listFournisseur;
	}

	public Entree getEntree() {
		return entree;
	}

	public void setEntree(Entree entree) {
		this.entree = entree;
	}

	public String getTypeMateriel() {
		return typeMateriel;
	}

	public void setTypeMateriel(String typeMateriel) {
		this.typeMateriel = typeMateriel;
	}

	@SuppressWarnings("unchecked")
	public List<Marque> getListMarque() {
		 listMarque = service.getObjects("Marque");
		
		Collections.sort(listMarque, new Comparator<Marque>() {
	        @Override
	        public int compare(Marque ob1, Marque ob2)
	        {
	 
	            return  ob1.getLibelleMarque().compareTo(ob2.getLibelleMarque());
	        }
	    });
		return listMarque;
	}

	public void setListMarque(List<Marque> listMarque) {
		this.listMarque = listMarque;
	}

	public int getIdMarque() {
		return idMarque;
	}

	public void setIdMarque(int idMarque) {
		this.idMarque = idMarque;
	}

	public int getIdEtat() {
		return idEtat;
	}

	public void setIdEtat(int idEtat) {
		this.idEtat = idEtat;
	}

	public List<Etat> getListEtat() {
		 listEtat = service.getObjects("Etat");
		
		Collections.sort(listEtat, new Comparator<Etat>() {
	        @Override
	        public int compare(Etat ob1, Etat ob2)
	        {
	 
	            return  ob1.getLibEtat().compareTo(ob2.getLibEtat());
	        }
	    });
		return listEtat;
	}

	public void setListEtat(List<Etat> listEtat) {
		this.listEtat = listEtat;
	}

	public NonFongible getNonfongible() {
		return nonfongible;
	}

	public void setNonfongible(NonFongible nonfongible) {
		this.nonfongible = nonfongible;
	}
}