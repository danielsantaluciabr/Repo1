package com.luizalabs.core.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utilitário para leitura de arquivos de propriedades.
 *  
 * @author danielsantalucia
 */
public final class PropertyLoader {

	public static final String PROPERTIES_FILE_PATH= "c:\\luizalabs_home\\props";
	
	public static final String CORE_PROPERTY_FILE_NAME= "core.properties";
	
	
	/**
	 * Retorna propriedades para o path e resource informado. Retorna nulo caso algum problema ocorra.
	 * 
	 * @param resourceName
	 * @param path
	 * @return
	 */
	public static Properties load(String resourceName, String path) {
		Properties props = null;
		try {

			File file = new File(path + File.separator + resourceName);
			if (!file.exists()) {
				return null;
			}

			InputStream is = new FileInputStream(file);

			if (is != null) {
				props = new Properties();
				props.load(is);
			}

		} catch (Exception e) {
			// FIXME: change to Logger approach over syso
			System.out.println(e.getMessage());
		}

		return props;
	}
	
}
