package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Certificate;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CertificateRepository;

@RestController
@RequestMapping("/api/v1/")

public class CertificateController {
	@Autowired
	CertificateRepository repository;
	
	@GetMapping("/certificate")
	public List<Certificate> getAllEmployees(){
		return repository.findAll();
	}	
	
	@PostMapping("/certificate")
	public Certificate createEmployee(@RequestBody Certificate certificate) {
		return repository.save(certificate);
	}
	
	@GetMapping("/certificate/{id}")
	public ResponseEntity<Certificate> getEmployeeById(@PathVariable Long id) {
		Certificate certificate = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Certificate is not available with id :" + id));
		return ResponseEntity.ok(certificate);
	}
	
	@PutMapping("/certificate/{id}")
	public ResponseEntity<Certificate> updateCertificate(@PathVariable Long id, @RequestBody Certificate certificateDetails){
		Certificate certificate = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Certificate is not available with id :" + id));
		
		certificate.setYear(certificateDetails.getYear());
		certificate.setCollege(certificateDetails.getCollege());
		
		
		Certificate updatedCertificate = repository.save(certificate);
		return ResponseEntity.ok(updatedCertificate);
	}

	@DeleteMapping("/certificate/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteCertificate(@PathVariable Long id){
		Certificate certificate = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Certificate is not available with id :" + id));
		
		repository.delete(certificate);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
