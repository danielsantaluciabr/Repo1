package com.luizalabs.domain.parser;


/**
 * Factory para criacao de parsers de log files
 * 
 * @author danielsantalucia
 */
public interface LogParserFactory {
				
	LogParser createParser(LogParserType type);	
}
