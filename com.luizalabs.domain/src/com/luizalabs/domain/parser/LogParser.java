package com.luizalabs.domain.parser;


/**
 * Interpretador de arquivos logs. Pode ser implementado para atender qualquer tipo de log file. Prove o resultado da interpretacao em {@link LogReport}
 * 
 * @author danielsantalucia
 */
public interface LogParser {

	LogReport getLogReport();

}
