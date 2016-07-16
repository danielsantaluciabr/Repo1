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
	public void testCreateGameStat(){
		Quake3LogParser parser = new Quake3LogParser();
		QuakeGameStat gameStat = parser.createGameStat(getAGame());
		
		List<String> expectedPlayers = new ArrayList<String>();
		expectedPlayers.add("Isgalamido");
		assertEquals(expectedPlayers, gameStat.getPlayers());
		
		assertEquals(Integer.valueOf(0), gameStat.getTotal_kills());
		
		assertEquals(1, gameStat.getKills().size());
		assertEquals(Integer.valueOf(0), gameStat.getKills().get("Isgalamido"));
	}
	
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
		expectedPlayers.add("Mocinha");
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
	
	private String getAGame() {
		return "0:00 ------------------------------------------------------------"
				+ "  0:00 InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0"
				+ "  15:00 Exit: Timelimit hit."
				+ "  20:34 ClientConnect: 2"
				+ "  20:34 ClientUserinfoChanged: 2 n\\Isgalamido\\t\\0\\model\\xian/default\\hmodel\\xian/default\\g_redteam\\\\\\g_blueteam\\\\\\c1\\4\\c2\\5\\hc\\100\\w\\0\\l\\0\\tt\\0\\tl\\0"
				+ "  20:37 ClientUserinfoChanged: 2 n\\Isgalamido\\t\\0\\model\\uriel/zael\\hmodel\\uriel/zael\\g_redteam\\\\g_blueteam\\\\c1\\5\\c2\\5\\hc\\100\\w\\0\\l\\0\\tt\\0\\tl\\0"
				+ "  20:37 ClientBegin: 2"
				+ "  20:37 ShutdownGame:"
				+ "  20:37 ------------------------------------------------------------"
				+ "  20:37 ------------------------------------------------------------"
				+ ""
				+ "";
	}
}
