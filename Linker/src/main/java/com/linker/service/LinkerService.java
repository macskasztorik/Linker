package com.linker.service;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONArray;
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

	// **This function makes sure to extract the shortLink, and then looks up the
	// original link*/
	public JSONObject generateNewLink(JSONObject requestBody) {
		JSONObject toReturn = new JSONObject();
		String incOriginalLink = extractStringFromJson(requestBody, "originalLink");
		
		if (incOriginalLink == null) {
			return toReturn;
		}
		
		//If there is already an encoded version for this, we won't bother creating a new
		Link checkUnique = linkRepository.findFirstByOriginalLink(incOriginalLink);
		
		if (checkUnique != null){
			putParameterToJson(toReturn, "originalLink", checkUnique.getOriginalLink());
			putParameterToJson(toReturn, "shortLink", checkUnique.getShortLink());
			return toReturn;
		}

		//Otherwise we will generate a new encoded version
		String newShortLink = RandomStringUtils.randomAlphanumeric(8); 
		checkUnique = linkRepository.findFirstByShortLink(newShortLink);
	
		while (checkUnique != null) {
			newShortLink = RandomStringUtils.randomAlphanumeric(8);
			checkUnique = linkRepository.findFirstByShortLink(newShortLink);
		}
		
		Link link = new Link(incOriginalLink, newShortLink);
		
		Link result = linkRepository.save(link);

		if (result == null) {
			return toReturn;
		}

		putParameterToJson(toReturn, "originalLink", result.getOriginalLink());
		putParameterToJson(toReturn, "shortLink", result.getShortLink());

		return toReturn;
	}

	// **This function makes sure to extract the shortLink, and then looks up the
	// original link*/
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

	// **This function makes sure to extract the shortLinks from an array, and then
	// looks up the
	// original links of them*/
	public JSONArray getLinksByShortLinks(JSONArray requestBodyJsonArr) {
		JSONArray toReturn = new JSONArray();

		for (int i = 0; i < requestBodyJsonArr.length(); i++) {

			JSONObject actual = extractJsonFromArray(requestBodyJsonArr, i);
			String incShortLink = extractStringFromJson(actual, "shortLink");
			if (incShortLink == null) {
				continue;
			}
			Link result = linkRepository.findFirstByShortLink(incShortLink);

			if (result == null) {
				continue;
			}

			JSONObject newJson = new JSONObject();
			putParameterToJson(newJson, "originalLink", result.getOriginalLink());
			putParameterToJson(newJson, "shortLink", result.getShortLink());

			toReturn.put(newJson);
		}

		return toReturn;
	}

	private JSONObject extractJsonFromArray(JSONArray jArr, int index) {
		JSONObject extractedJson = null;
		try {
			extractedJson = jArr.getJSONObject(index);
		} catch (JSONException e) {
			System.out.println("Couldn't extract " + index + "th Json from " + jArr.toString());
		}
		return extractedJson;
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

	private JSONObject putParameterToJson(JSONObject jObj, String key, String value) {
		try {
			jObj.put(key, value);
		} catch (JSONException e) {
			System.out.println("Couln't put " + key + " and " + value + " to " + jObj.toString());
		}
		return jObj;
	}

}
