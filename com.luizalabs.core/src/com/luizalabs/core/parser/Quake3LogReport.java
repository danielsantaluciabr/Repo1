package com.luizalabs.core.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.luizalabs.domain.parser.LogReport;

/**
 * Implementacao de {@link LogReport} 
 * 
 * @author danielsantalucia
 */
final class Quake3LogReport implements LogReport {

	private List<QuakeGameStat> games = new ArrayList<QuakeGameStat>();

	public String getTextReport() {
		return getJSonReport().toString(); 
	}

	public JSONObject getJSonReport() {
		JSONObject jRootObj = new JSONObject();
		
		int count = 1;
		for(QuakeGameStat game: games){
			JSONObject jObj = new JSONObject();
			jObj.put("total_kills", game.getTotal_kills());
			jObj.put("players", game.getPlayers());
			jObj.put("kills", game.getKills());

			jRootObj.put("game_" + count, jObj);
			count++;
		}
		
		return jRootObj;
	}

	void setGames(List<QuakeGameStat> games) {
		this.games = games;
	}	
	
	List<QuakeGameStat> getGames() {
		return this.games;
	}
}
