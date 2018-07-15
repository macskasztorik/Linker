package com.linker.controller;

import java.util.List;

import org.json.JSONArray;
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

	@RequestMapping(value = "/generateLink", method = RequestMethod.POST)
	public String generateLink(@RequestBody String payload) {

		JSONObject requestBodyJson = requestJsonValidation(payload);

		return linkerService.generateNewLink(requestBodyJson).toString();
	}

	@RequestMapping(value = "/findLink", method = RequestMethod.POST)
	public String findLink(@RequestBody String payload) {
		
		JSONObject jsonPayload = requestJsonValidation(payload);
		
		if (jsonPayload.has("shortLink")) {
			return linkerService.getLinkByShortLink(jsonPayload).toString();
		}

		return jsonPayload.toString();
	}

	@RequestMapping(value = "/findLinks", method = RequestMethod.POST)
	public String findLinks(@RequestBody String payload) {

		JSONArray jsonPayload = requestJsonArrayValidation(payload);
		
		if (jsonPayload.length() > 0) {
			return linkerService.getLinksByShortLinks(jsonPayload).toString();
		}
		
		return jsonPayload.toString();
	}

	
	
	//**Support functions for Controllers*/
	public static JSONObject requestJsonValidation(String payload) {
		JSONObject requestBodyJson = new JSONObject();
		
		try {
			requestBodyJson = new JSONObject(payload);
		} catch (Exception e) {
			System.out.println("Request's body has wrong format - JSON is expected");
		}

		if (!requestBodyJson.has("shortlink")) {
			try {
				requestBodyJson.put("status", "error");
				requestBodyJson.put("error", "Request's body has wrong format - JSON is expected");
			} catch (JSONException e) {
				System.out.println("Issue with putting values to the JSON object"); // will change to logger
			}
		}
		return requestBodyJson;

	}
	
	public static JSONArray requestJsonArrayValidation(String payload) {
		
		JSONArray requestBodyJsonArr = new JSONArray();
		
		try {
			requestBodyJsonArr = new JSONArray(payload);
		} catch (Exception e) {
			System.out.println("Request's body has wrong format - JSON is expected");
		}

		if (requestBodyJsonArr.length() < 1) {
			try {
				JSONObject error = new JSONObject();
				error.put("status", "error");
				error.put("error", "Request's body has wrong format - JSONArray is expected");
				requestBodyJsonArr.put(error);
			} catch (JSONException e) {
				System.out.println("Issue with putting values to the JSON object"); // will change to logger
			}
		}
		
		return requestBodyJsonArr;
	}

}
