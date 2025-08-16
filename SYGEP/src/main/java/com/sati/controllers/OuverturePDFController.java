package com.sati.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.service.Iservice;

@SuppressWarnings({ "unchecked", "unchecked" })
@Component
@Scope("session")
public class OuverturePDFController {
	@Autowired
	Iservice service;

	public void ouvrirPDF(String nomFichier) throws IOException {
	    try {
			//String code =  "Ins_"+selectedObject.getEtudiants().getMle();
	    	
	      //  File source = new File(nomFichier);
	        File source = new File("C:/SYGEP/ETATS/"+ nomFichier);

	        //On copie vers un dossier du projet web accessible via HTTP
	        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
	        File destDir = new File(contextPath + "resources/pdf/");
	        if (!destDir.exists()) destDir.mkdirs();

	        File destFile = new File(destDir, nomFichier);

	        // Copie physique
	        Files.copy(source.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

	     // Stocker l'URL pour l'ouvrir côté JSF
	        String webPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	        String pdfUrl = webPath + "/resources/pdf/" + nomFichier;
	        
	     
	     // ✅ Utiliser RequestContext pour exécuter JavaScript dans PrimeFaces 6.x
          //  RequestContext.getCurrentInstance().execute("window.open('" + pdfUrl + "', '_blank');");
            PrimeFaces.current().executeScript("window.open('" + pdfUrl + "', '_blank');");
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Impossible d'ouvrir le fichier PDF."));
	    }catch (NullPointerException e) {
	    	 e.printStackTrace();
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez selectionner la ligne puis ouvrir le fichier."));
		}
	}
	
}