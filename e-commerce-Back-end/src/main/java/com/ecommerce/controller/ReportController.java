package com.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.service.JasperReportService;

import net.sf.jasperreports.engine.JRException;

@RestController
public class ReportController {

	@Autowired
	JasperReportService jasperReportService;

	@PreAuthorize("hasRole('Admin')")
	@GetMapping("item-report")
	public ResponseEntity<ByteArrayResource> getItemReport() throws JRException, IOException, SQLException {
		
		String fileName = "ItemReport";
		String title = "Item Report";

		byte[] reportContent = jasperReportService.generatePDF(fileName, title);

		ByteArrayResource resource = new ByteArrayResource(reportContent);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(resource.contentLength())
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment().filename("item-report.pdf").build().toString())
				.body(resource);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@GetMapping("sale-report")
	public ResponseEntity<ByteArrayResource> getSaleReport() throws JRException, IOException, SQLException {
		
		String fileName = "SaleReport";
		String title = "Sales Report";

		byte[] reportContent = jasperReportService.generatePDF(fileName, title);

		ByteArrayResource resource = new ByteArrayResource(reportContent);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(resource.contentLength())
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment().filename("item-report.pdf").build().toString())
				.body(resource);
	}

}
