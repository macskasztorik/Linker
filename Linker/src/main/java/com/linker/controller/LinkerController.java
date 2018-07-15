package com.linker.controller;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linker.service.LinkerService;

@RestController
public class LinkerController {
	
	@Autowired
	private LinkerService linkerService;
	
	@RequestMapping(value = "/findLink", method = RequestMethod.POST)
	public String findLink(@RequestBody String payload) {
		
        JSONObject requestBody = new JSONObject();
        JSONObject responseBody = new JSONObject();
        
        
        try{
        	requestBody = new JSONObject(payload);
        }catch(Exception e){
        	System.out.println("Request's body has wrong format - JSON is expected");
        }
        
        if (!requestBody.has("shortlink")){
        	try {
				responseBody.put("status", "error");
				responseBody.put("error", "Request's body has wrong format - JSON is expected");  
			} catch (JSONException e) {
				System.out.println("Issue with putting values to the JSON object"); //will change to logger
			}
        }
        
              return linkerService.getLinkByShortLink(requestBody).toString();
	}

}
