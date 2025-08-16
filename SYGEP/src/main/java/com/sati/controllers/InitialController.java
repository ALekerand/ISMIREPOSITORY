package com.sati.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sati.model.UserAuthentication;
import com.sati.requetes.RequeteUtilisateur;
import com.sati.service.Iservice;



@Component
@Scope("session")
public class InitialController {
	@Autowired
	private Iservice iservice;
	@Autowired
	private RequeteUtilisateur requeteUtilisateur;
	
	//private Responsable responsable = new Responsable();
//	private ServiceResponsable serviceResponsable = new ServiceResponsable();
	private UserAuthentication userAuthentication = new UserAuthentication();
	@PostConstruct
	public void recupererUtilisateur() {
		setUserAuthentication(requeteUtilisateur.recuperUser());
	}

	public UserAuthentication getUserAuthentication() {
		return userAuthentication;
	}

	public void setUserAuthentication(UserAuthentication userAuthentication) {
		this.userAuthentication = userAuthentication;
	}
}
