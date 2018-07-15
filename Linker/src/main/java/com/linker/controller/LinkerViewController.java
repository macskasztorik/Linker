package com.linker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LinkerViewController {

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		System.out.println("index page loading...");
		return "index";
	}

}
