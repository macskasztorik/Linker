package com.linker.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linker.domain.Link;
import com.linker.repository.LinkRepository;

@Service
public class LinkerService {

	@Autowired
	LinkRepository linkRepository;

	//**This function makes sure to extract the shortLink, and then looks up the original link*/
	public JSONObject getLinkByShortLink(JSONObject requestBody) {
		JSONObject toReturn = new JSONObject();
		String incShortLink = extractStringFromJson(requestBody, "shortLink");

		if (incShortLink == null) {
			return toReturn;
		}
			

		Link result = linkRepository.findFirstByShortLink(incShortLink);

		if (result == null) {
			return toReturn;
		}

		putParameterToJson(toReturn, "originalLink", result.getOriginalLink());
		putParameterToJson(toReturn, "shortLink", result.getShortLink());
		
		return toReturn;
	}

	private String extractStringFromJson(JSONObject jObj, String name) {
		String extractedValue = null;
		try {
			extractedValue = jObj.getString(name);
		} catch (JSONException e) {
			System.out.println("Couldn't extract " + name + " from " + jObj.toString());
		}
		return extractedValue;
	}

	private JSONObject putParameterToJson(JSONObject jObj, String key, String value){
		try {
			jObj.put(key, value);
		} catch (JSONException e) {
			System.out.println("Couln't put " + key + " and " + value + " to " + jObj.toString());
		}
		return jObj;
	}

}
