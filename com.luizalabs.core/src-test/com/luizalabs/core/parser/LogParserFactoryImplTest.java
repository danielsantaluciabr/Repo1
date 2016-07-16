package com.luizalabs.core.parser;

import org.junit.Test;

import com.luizalabs.core.exception.UnexpectedError;
import com.luizalabs.domain.parser.LogParserType;

import junit.framework.TestCase;

public class LogParserFactoryImplTest extends TestCase {

	@Test
	public void testQuakeParserCreation(){
		LogParserFactoryImpl logFactory = LogParserFactoryImpl.getInstance();
		
		assertTrue(logFactory.createParser(LogParserType.QUAKE_3).getClass().equals(Quake3LogParser.class));
	}
	
	@Test
	public void testUnexpectedType(){	
		LogParserFactoryImpl logFactory = LogParserFactoryImpl.getInstance();
		boolean testPass = false;
		try {
			logFactory.createParser(null);
			
		} catch (UnexpectedError e) {
			testPass = true;
		}
		
		assertTrue(testPass);
	}

	@Test
	public void testUnsupportedType(){	
		LogParserFactoryImpl logFactory = LogParserFactoryImpl.getInstance();
		boolean testPass = false;
		try {
			logFactory.createParser(LogParserType.UNREAL_TOURNAMENT);
			
		} catch (UnexpectedError e) {
			testPass = true;
		}
		
		assertTrue(testPass);
	}

}
