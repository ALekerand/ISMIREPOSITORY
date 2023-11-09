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
@Scope("session")
public class QRCodeReportBean {

	
	public void genererEtatQRCode() throws IOException {
		
		try { 
			
			JasperDesign jasperDesign = JRXmlLoader.load("C:/SYGEP/REPORTS/qr_code_repport.jrxml");
			//Compilation du fichier
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		
			BufferedImage image = ImageIO.read(getClass().getResource("C:/SYGEP/QR_CODE_MTQR024.png"));
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("image_QR", image);
			
			
			System.out.println("======== Parametre setter=======");//Clean after
				// Remplissage du rapport compil�
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, new JREmptyDataSource());
			System.out.println("======== Remplissage =======");//Clean after
			
			// Visualisation, exportation ou impression 
		    JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\SYGEP\\ETATS\\ETAT_QR"+".pdf");
			
			System.out.println("======== Toust est ex�cut�");//Clean after
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
