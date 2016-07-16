package com.luizalabs.core.parser;

import java.util.List;
import java.util.Map;

/**
 * Representacao de estatisticas de um game {@link Quake3LogReport}
 * 
 * @author danielsantalucia
 */
final class QuakeGameStat {

	private Integer total_kills;
	
	private List<String> players;
	
	private Map<String, Integer> kills;

	
	Integer getTotal_kills() {
		return total_kills;
	}

	void setTotal_kills(Integer total_kills) {
		this.total_kills = total_kills;
	}

	List<String> getPlayers() {
		return players;
	}

	void setPlayers(List<String> players) {
		this.players = players;
	}

	Map<String, Integer> getKills() {
		return kills;
	}

	void setKills(Map<String, Integer> kills) {
		this.kills = kills;
	}

	@Override
	public String toString() {
		return "Quake Game Stat [total_kills=" + total_kills + ", players="
				+ players + ", kills=" + kills + "]";
	}
	
	
	
}
