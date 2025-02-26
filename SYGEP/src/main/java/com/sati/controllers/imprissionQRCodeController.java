package com.sati.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.primefaces.component.commandbutton.CommandButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.Etat;
import com.sati.model.EtatDemande;
import com.sati.model.Famille;
import com.sati.model.Fongible;
import com.sati.model.NonFongible;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class imprissionQRCodeController {
	@Autowired
	Iservice service;
	private NonFongible selectedObject = new NonFongible();
	private NonFongible nonFongible = new NonFongible();
	private List<NonFongible> listeNonFongible = new ArrayList<NonFongible>();
	
	
	public void selectionnerLigne() {
		System.out.println("==== Selecgtion OK");
		nonFongible = selectedObject;
		}
	
	public void doGet()throws ServletException, IOException {
        // Chemin du fichier PDF
		
		//if ((selectedObject == null) ||(selectedObject.getCodeMateriel().equals("")) ) {
		//	error("Veuillez selectionner le matériel avnt l'impression du QR CODE");
	//	}else {
			try {
				String pdfFilePath = "C:\\SYGEP\\"+selectedObject.getCodeMateriel()+".pdf";
				File pdfFile = new File(pdfFilePath);
				
				// Configuration de la réponse
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				response.setContentType("application/pdf");
				response.setContentLength((int) pdfFile.length());
				response.setHeader("Content-Disposition", "inline; filename=\"" + pdfFile.getName() + "\"");

				// Lecture et envoi du fichier PDF
				FileInputStream fis = new FileInputStream(pdfFile);
				OutputStream os = response.getOutputStream(); 
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = fis.read(buffer)) != -1) {
				        os.write(buffer, 0, bytesRead);
				    }
				vider();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				error("Veuillez selectionner le matériel avnt l'impression du QR CODE");
			} 
        }
	
	
	public void vider() {
		selectedObject.setCodeMateriel(null);
	}

    
	public void info(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", monMessage));
	}

	public void error(String monMessage) {
		FacesContext.getCurrentInstance().addMessage((String) null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", monMessage));
	}

	public NonFongible getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(NonFongible selectedObject) {
		this.selectedObject = selectedObject;
	}

	public NonFongible getNonFongible() {
		return nonFongible;
	}

	public void setNonFongible(NonFongible nonFongible) {
		this.nonFongible = nonFongible;
	}

	public List<NonFongible> getListeNonFongible() {
		return listeNonFongible = service.getObjects("NonFongible");
	}

	public void setListeNonFongible(List<NonFongible> listeNonFongible) {
		this.listeNonFongible = listeNonFongible;
	}
}