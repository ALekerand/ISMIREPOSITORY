package com.sati.reportBean;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Component
public class QRCodeReportBean {

	
	public void genererEtatQRCode(String chenin_QR, String nom_fichier) throws IOException {
		
		try { 
			
			JasperDesign jasperDesign = JRXmlLoader.load(chenin_QR+"/qr_code_repport.jrxml");
			//Compilation du fichier
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		
			BufferedImage image = ImageIO.read(getClass().getResource(chenin_QR));
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("image_QR", image);
				// Remplissage du rapport compil�
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, new JREmptyDataSource());
			// Visualisation, exportation ou impression 
		    JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/SYGEP/"+nom_fichier+".pdf");
			
			System.out.println("======== Toust est exécuté");//Clean after
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
