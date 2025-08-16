package com.sati.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.NonFongible;
import com.sati.service.Iservice;

@Component
@Scope("session")
public class imprissionQRCodeController {
	@Autowired
	Iservice service;
	
	@Autowired
	OuverturePDFController ouverturePDFController;
	
	private NonFongible selectedObject = new NonFongible();
	private NonFongible nonFongible = new NonFongible();
	private List<NonFongible> listeNonFongible = new ArrayList<NonFongible>();
	
	private String pdfUrl;
	
	
	public void selectionnerLigne() {
		System.out.println("==== Selecgtion OK");
		nonFongible = selectedObject;
		}
	
	public void doGet()throws ServletException, IOException {
       
			try {
				//String pdfFilePath = ;
				File pdfFile = new File("C:\\SYGEP\\ETATS\\"+selectedObject.getCodeMateriel()+".pdf");
				
				
				if (!pdfFile.exists()) {
		            error("Le fichier PDF n'existe pas.");
		            return;
		        }
				
				
				// Configuration de la réponse
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline; filename=\"" + pdfFile.getName() + "\"");
				response.setContentLength((int) pdfFile.length());
				

				// Lecture et envoi du fichier PDF
				
				FileInputStream fis = new FileInputStream(pdfFile);
	            OutputStream os = response.getOutputStream(); 

	            byte[] buffer = new byte[4096];
	            int length;

	            while ((length = fis.read(buffer)) > 0) {
	                os.write(buffer, 0, length);
	            }
	            fis.close();
	            os.flush();
	            os.close();
	             
	            context.responseComplete(); // 🔴 Très important : empêche JSF de continuer
				vider();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				error("Veuillez selectionner le matériel avnt l'impression du QR CODE");
			} 
        }
	
	
		/*
		 * public void ouvrirPDF() { try { String code =
		 * selectedObject.getCodeMateriel();
		 * System.out.println("=============== Code materiel:"+
		 * selectedObject.getCodeMateriel()); File source = new File("C:/SYGEP/ETATS/" +
		 * code + ".pdf");
		 * 
		 * System.out.println("=============== Code materiel:"+
		 * selectedObject.getCodeMateriel());
		 * 
		 * // On copie vers un dossier du projet web accessible via HTTP String
		 * contextPath =
		 * FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"); File
		 * destDir = new File(contextPath + "resources/pdf/"); if (!destDir.exists())
		 * destDir.mkdirs();
		 * 
		 * File destFile = new File(destDir, code + ".pdf");
		 * 
		 * // Copie physique Files.copy(source.toPath(), destFile.toPath(),
		 * StandardCopyOption.REPLACE_EXISTING);
		 * 
		 * // Stocker l'URL pour l'ouvrir côté JSF String webPath =
		 * FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath(
		 * ); pdfUrl = webPath + "/resources/pdf/" + code + ".pdf";
		 * 
		 * vider();
		 * 
		 * } catch (IOException e) { e.printStackTrace();
		 * FacesContext.getCurrentInstance().addMessage(null, new
		 * FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
		 * "Impossible d'ouvrir le fichier PDF.")); } }
		 */
	
	
	public void afficherPDF() throws IOException{
		ouverturePDFController.ouvrirPDF(selectedObject.getCodeMateriel()+".pdf");
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

	public String getPdfUrl() {
		return pdfUrl;
	}

	
}