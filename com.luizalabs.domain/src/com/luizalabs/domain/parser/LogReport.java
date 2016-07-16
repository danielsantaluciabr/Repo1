package com.luizalabs.domain.parser;

import org.json.JSONObject;

/**
 * Prove informacoes de um log file parseado {@link LogParser}  
 * 
 * @author danielsantalucia
 */
public interface LogReport {

	/**
	 * Retorna report no formato texto
	 * @return  
	 */
	String getTextReport();

	/**
	 * Retorna report no formato Json
	 * @return
	 */
	JSONObject getJSonReport();
	
}
