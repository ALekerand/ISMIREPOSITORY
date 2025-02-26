package com.sati.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.service.Iservice;


@Component
@Scope("session")
public class TestRapportController {
	@Autowired
	Iservice service;

	// public void doGet(HttpServletRequest request, HttpServletResponse response)
		public void doGet()throws ServletException, IOException {
			System.out.println("======= DEBUT D'EXECUTION ===================");
	        // Chemin du fichier PDF
	        String pdfFilePath = "C:\\SYGEP\\MTQR149.pdf";
	        File pdfFile = new File(pdfFilePath);
	        
	        // Configuration de la réponse
	        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	        response.setContentType("application/pdf");
	        response.setContentLength((int) pdfFile.length());
	        response.setHeader("Content-Disposition", "inline; filename=\"" + pdfFile.getName() + "\"");

	        // Lecture et envoi du fichier PDF
	        try (FileInputStream fis = new FileInputStream(pdfFile);
	             OutputStream os = response.getOutputStream()) {

	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = fis.read(buffer)) != -1) {
	                os.write(buffer, 0, bytesRead);
	            }
	        }
	        
	        System.out.println("======= FIN D'EXECUTION ===================");
	    }

	
	
    /*
	    protected void genererPDF(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        try {
	            // Chemin vers votre fichier JRXML (dans WEB-INF par exemple)
	            String reportPath = ServletContext.getRealPath("/WEB-INF/reports/qr_code_repport.jrxml");
	            
	            // Compilation du rapport (si vous n'avez pas encore compilé le .jrxml en .jasper)
	            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
	            
	            // Préparation des données pour le rapport
	            // Remplacez MyBean par votre classe de données et récupérez votre liste de données
	            //List<MyBean> dataList = getDataForReport(); 
	            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
	            
	            // Paramètres éventuels à passer au rapport
	            Map<String, Object> parameters = new HashMap<>();
	            parameters.put("param1", "valeur1");
	            
	            // Remplissage du rapport
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
	            
	            // Configuration de la réponse HTTP pour afficher le PDF dans le navigateur
	            response.setContentType("application/pdf");
	            response.setHeader("Content-Disposition", "inline; filename=monrapport.pdf");
	            
	            // Export du rapport vers le flux de sortie de la réponse
	            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	        } catch (Exception e) {
	            throw new ServletException("Erreur lors de la génération du rapport", e);
	        }
	    }*/
	    
	    // Méthode factice pour récupérer les données du rapport
	    
}