package com.luizalabs.web.restful.resource;

import junit.framework.TestCase;

import org.junit.Test;


public class QuakeLogServiceTest extends TestCase {

	@Test
	public void testHas() {
		QuakeLogService srv = new QuakeLogService();
		assertNotNull(srv.getGameReport().contains("game_18") );
	}
}
