package com.luizalabs.core.parser;

import com.luizalabs.core.exception.UnexpectedError;
import com.luizalabs.domain.parser.LogParser;
import com.luizalabs.domain.parser.LogParserFactory;
import com.luizalabs.domain.parser.LogParserType;

/**
 * Implementacao padrao para {@link LogParserFactory}
 * 
 * @author danielsantalucia
 */
public final class LogParserFactoryImpl implements LogParserFactory{

	public LogParser createParser(LogParserType type) {
		if(type == null){
			throw new UnexpectedError("Operation not supported, type:" + type);			
		}
				
		switch (type) {
			case QUAKE_3:
				return new Quake3LogParser();

			default:
				throw new UnexpectedError("Operation not supported yet!");
		}
	}
	
}
