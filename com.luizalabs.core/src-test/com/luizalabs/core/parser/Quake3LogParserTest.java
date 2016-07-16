package com.luizalabs.core.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.luizalabs.domain.parser.LogReport;

public class Quake3LogParserTest extends TestCase{

	@Test
	public void testCreateLogReport(){
		Quake3LogParser parser = new Quake3LogParser();
		
		LogReport logReport = null;
		try {
			File file = getFile("quakeTestLogFile1.txt");
			logReport = parser.createLogReport(file);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Erro inesperado no teste.");
		}
		
		JSONObject jSonReport = logReport.getJSonReport();
		JSONObject jsonGame1 = jSonReport.getJSONObject("game_1");
		
		JSONObject jsonGame2 = null;
		try {
			jsonGame2 = jSonReport.getJSONObject("game_2");
		} catch (JSONException e) {
			// do nothing
		}
		
		assertNotNull(jsonGame1);
		assertNull(jsonGame2);
		
		int totalKills = jsonGame1.getInt("total_kills");
		assertEquals(11, totalKills);
		
		JSONArray players = jsonGame1.getJSONArray("players");
		List<String> expectedPlayers = new ArrayList<String>();
		expectedPlayers.add("Mocinha" );
		expectedPlayers.add("Dono da Bola");
		expectedPlayers.add("Isgalamido");
		assertEquals(3, players.length());
		for(Object player:players){
			assertTrue(expectedPlayers.contains(player));
		} 

		JSONObject kills = jsonGame1.getJSONObject("kills");
		assertEquals("{\"Mocinha\":0,\"Dono da Bola\":0,\"Isgalamido\":-5}", kills.toString());
		
	}

	private File getFile(String fileName) {
		return new File(this.getClass().getResource(fileName).getPath());
	}
	
}
