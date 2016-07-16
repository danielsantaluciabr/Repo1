package com.luizalabs.core.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.luizalabs.core.exception.UnexpectedError;
import com.luizalabs.core.property.PropertyLoader;
import com.luizalabs.domain.parser.LogParser;
import com.luizalabs.domain.parser.LogReport;

/**
 * Implementacao de @see(LogParser) para tratar e extrair um relatorio de jogo Quake3 baseado no arquivo log do Jogo.
 * 
 * @author danielsantalucia
 *
 */
final class Quake3LogParser implements LogParser {

	private static final String KEY_WORD_WORLD = "<world>";

	private static final String KEY_WORD_CLIENT_USERINFO_CHANGED = "ClientUserinfoChanged";

	private static final String KEY_WORD_KILLED = "killed";

	private static final String KEY_WORD_INITGAME = "InitGame";

	static final String QUAKE_3_LOG_FILE_PATH = "games.quake.log.path";
	
	static final String QUAKE_3_LOG_FILE_NAME = "games.quake.log.filename";
	
	/**
	 * Retorna um relatorio de jogo  
	 */
	public LogReport getLogReport() {
		Properties props = getCoreProperties();
	
		String quakeFilePath = (String)props.get(QUAKE_3_LOG_FILE_PATH);
		String quakeFilename = (String)props.get(QUAKE_3_LOG_FILE_NAME);
		
		LogReport logReport = null;
		File quakeLogFile = new File(quakeFilePath + quakeFilename); 
		try {
			logReport = createLogReport(quakeLogFile);
			
		} catch (Exception e) {
			throw new UnexpectedError(e, "Não foi possível gerar report:" + e.getMessage());
		}
	
		return logReport;
	}

	LogReport createLogReport(File quakeLogFile) throws Exception {
		Quake3LogReport logReport = new Quake3LogReport();
		Scanner scanner = new Scanner(quakeLogFile);
		try {
			// Encontra e itera sobre os games
			scanner.useDelimiter(Pattern.compile("\\d{1,2}\\:\\d{1,2} "+KEY_WORD_INITGAME+"\\:"));
			while(scanner.hasNext()){
				String game = scanner.next();
				
				if(isValidGame(game)){
					logReport.getGames().add(createGameStat(game));
				}
			}
		
		} catch (Exception e) {
			throw e;

		}finally{
			scanner.close();
		}
		
		return logReport;
	}

	private boolean isValidGame(String game) {
		if(game.indexOf(KEY_WORD_KILLED) <0 && game.indexOf(KEY_WORD_CLIENT_USERINFO_CHANGED) < 0){
			return false;
		}
		
		return true;
	}

	
	QuakeGameStat createGameStat(String game) {
		QuakeGameStat gameStat = new QuakeGameStat();

		// players
		List<String> players = extractPlayers(game);
		gameStat.setPlayers(players);
		
		// kills
		List<String> rawKillsEntries = extractRawKills(game);
		gameStat.setTotal_kills(rawKillsEntries.size());
		
		gameStat.setKills(createKillsMap(rawKillsEntries));
				
		return gameStat;
	}

	/*
	 * Transforma as entradas de mortes em um mapa de estatística
	 */
	private Map<String, Integer> createKillsMap(List<String> rawKillsEntries) {
		Map<String, Integer> killMap = new HashMap<String, Integer>();
		for(String rawKill: rawKillsEntries){
			String[] assassinAndKilled = findAssassinAndKilled(rawKill);
			String assassin = assassinAndKilled[0];
			String killed = assassinAndKilled[1];
			
			if(assassin.contains(KEY_WORD_WORLD)){
				if (killMap.get(killed) == null){
					killMap.put(killed, 0);
				}
				killMap.put(killed, killMap.get(killed) - 1 );
				continue;
			}
			if (killMap.get(assassin) == null){
				killMap.put(assassin, 0);
			}
			
			killMap.put(assassin, killMap.get(assassin) + 1 );
		}
		
		return killMap;
	}

	/*
	 * Encontra por players dentro do escopo de um game
	 */
	private List<String> extractPlayers(String game) {

		// Encontra players nos logs de ClientUserinfoChanged 
		Matcher matcher = Pattern.compile(" \\d{1,2}\\:\\d{1,2} "+KEY_WORD_CLIENT_USERINFO_CHANGED+"\\: \\d n\\\\.*\\\\t\\\\").matcher(game);
		Set<String> players = new HashSet<String>();
		while (matcher.find()){
			String match = matcher.group();
			String player = match.substring(match.indexOf("n\\") + 2, match.indexOf("\\t\\"));
			
			players.add(player);
		}

		// Encontra players nos logs de kills 
		List<String> rawKillsEntries = extractRawKills(game);
		for(String rawKill:rawKillsEntries){
			String[] playersArray = findAssassinAndKilled(rawKill);
			players.add(playersArray[0]);
			players.add(playersArray[1]);
		}
		players.remove(KEY_WORD_WORLD);
		
		return new ArrayList<String>(players);
	}

	/*
	 * Retorna assassino (posicao zero do array) e assassinado (posicao um do array)
	 */
	private String[] findAssassinAndKilled(String rawKill) {
		String playersStr = rawKill.replaceFirst(" \\d+ \\d+ \\d+\\: ", "");
		playersStr = playersStr.substring(0, playersStr.lastIndexOf(" by"));
		String[] playersArray = playersStr.split(" "+KEY_WORD_KILLED+" ");
		return playersArray;
	}

	/*
	 * Find for kills entries
	 */
	private List<String> extractRawKills(String game) {
		Matcher matcher = Pattern.compile(" \\d+ \\d+ \\d+\\: .+ "+KEY_WORD_KILLED+" .+ by").matcher(game);
		
		List<String> rawKills = new ArrayList<String>();
		while (matcher.find()){
			rawKills.add(matcher.group());
		}
		
		return rawKills;
	}

	Properties getCoreProperties() {
		Properties props = PropertyLoader.load(PropertyLoader.CORE_PROPERTY_FILE_NAME, PropertyLoader.PROPERTIES_FILE_PATH);
		if(props == null){
			throw new UnexpectedError(null, "Nao foi possível carregar arquivo de propriedades: " + PropertyLoader.CORE_PROPERTY_FILE_NAME);
		}
		return props;
	}

}
