package com.luizalabs.core.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.luizalabs.core.parser.Quake3LogReport;
import com.luizalabs.core.parser.QuakeGameStat;

import junit.framework.TestCase;

public class Quake3LogReportTest extends TestCase {

	@Test
	public void testJSonReport(){
		Quake3LogReport quakeLogReport = new Quake3LogReport();
		
		ArrayList<QuakeGameStat> games = new ArrayList<QuakeGameStat>(); 
		
		QuakeGameStat gameStat1 = new QuakeGameStat();
		gameStat1.setTotal_kills(20);
		
		ArrayList<String> players = new ArrayList<String>();
		players.add("Player 1");
		players.add("Player 2");
		gameStat1.setPlayers(players);
		
		Map<String, Integer> killsMap = new HashMap<String, Integer>();
		killsMap.put("player1", 3);
		killsMap.put("player2", 1);
		gameStat1.setKills(killsMap);
		
		games.add(gameStat1);
		quakeLogReport.setGames(games);
		
		assertEquals("[{\"total_kills\":20,\"players\":[\"Player 1\",\"Player 2\"],\"kills\":{\"player1\":3,\"player2\":1}}]", quakeLogReport.getJSonReport().toString());
		
	}
	
}
