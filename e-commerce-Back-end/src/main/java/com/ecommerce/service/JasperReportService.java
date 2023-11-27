package com.ecommerce.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.zaxxer.hikari.HikariDataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;

@Service
public class JasperReportService {
	
	@Autowired
	private HikariDataSource dataSource;
	
	public byte[] generatePDF(String fileName, String title) throws SQLException {
		
		JasperReport jasperReport;
		
		try {
			File file = ResourceUtils.getFile("classpath:reports/"+fileName+".jrxml");
			jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRSaver.saveObject(jasperReport, fileName+".jasper");
					  
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("TITLE", title);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
			
			byte[] reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
			
			return reportContent;
			
		} catch (FileNotFoundException | JRException ex) {
		  throw new RuntimeException(ex);
		}

	}

}
