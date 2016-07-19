package com.luizalabs.web.restful.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.luizalabs.core.parser.LogParserFactoryImpl;
import com.luizalabs.domain.parser.LogParser;
import com.luizalabs.domain.parser.LogParserType;
import com.luizalabs.domain.parser.LogReport;

@Path("quakeLogService") 
public class QuakeLogService extends Application{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getGameReport() throws JSONException {
		LogParser parser = LogParserFactoryImpl.getInstance().createParser(LogParserType.QUAKE_3);
		
		String result = "";
		LogReport logReport = parser.getLogReport();
		if(logReport != null){
			JSONObject jSonReport = parser.getLogReport().getJSonReport();
		
			result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jSonReport;
		}
		
		return result;
	}

}
