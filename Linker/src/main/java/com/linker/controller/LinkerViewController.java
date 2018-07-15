package com.linker.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.linker.service.LinkerService;

@Controller
public class LinkerViewController {

	@Autowired
	private LinkerService linkerService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/{endPoint}", method = RequestMethod.GET)
	public ModelAndView method(@PathVariable("endPoint") String endPoint) {

		JSONObject payload = new JSONObject();
		try {
			payload.put("shortLink", endPoint.toLowerCase());
			JSONObject result = linkerService.getLinkByShortLink(payload);
			String originalLink = result.getString("originalLink");
			
			return new ModelAndView("redirect:" +originalLink);
		} catch (JSONException e) {
			
		}
		
		return new ModelAndView("index");	
	}

}
