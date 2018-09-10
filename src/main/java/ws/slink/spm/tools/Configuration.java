package ws.slink.spm.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Configuration implements Serializable {

	private static final String CONFIGURATION_FILENAME_PROPERTY = "app.conf";

	private Properties properties = new Properties();

	/**
	 * get single environment variable value
	 *
	 * @param environmentVariable
	 * @return
	 */
	public String getValue(String property, String environmentVariable) {
		String result = properties.getProperty(property);
		if (result == null || result.isEmpty())
			result = System.getenv(environmentVariable);
		if (result == null || result.isEmpty())
			throw new IllegalArgumentException("neither configuration property '" + property
					+ "' nor environment variable '" + environmentVariable + "' set");
		return result;
	}

	/**
	 * get a list of comma-separated integers from environment variable
	 *
	 * @param environmentVariable
	 * @return
	 */
	public List<Integer> getIntegerValues(String property, String environmentVariable) {
		String result = properties.getProperty(property);
		if (result == null || result.isEmpty())
			result = System.getenv(environmentVariable);
		if (result == null || result.isEmpty())
			throw new IllegalArgumentException("neither configuration property '" + property
					+ "' nor environment variable '" + environmentVariable + "' set");
		List<Integer> list = new ArrayList<Integer>();
		for (String s : result.replaceAll(",", " ").replaceAll(" +", " ").split(" "))
			list.add(Integer.parseInt(s));
		return list;
	}

	/**
	 * get a list of comma-separated doubles from environment variable
	 *
	 * @param environmentVariable
	 * @return
	 */
	public List<Double> getDoubleValues(String property, String environmentVariable) {
		String result = properties.getProperty(property);
		if (result == null || result.isEmpty())
			result = System.getenv(environmentVariable);

		if (result == null || result.isEmpty())
			throw new IllegalArgumentException("neither configuration property '" + property
					+ "' nor environment variable '" + environmentVariable + "' set");
		List<Double> list = new ArrayList<Double>();
		for (String s : result.replaceAll(",", " ").replaceAll(" +", " ").split(" "))
			list.add(Double.parseDouble(s));
		return list;
	}

	/**
	 * get a list of comma-separated strings from environment variable
	 *
	 * @param environmentVariable
	 * @return
	 */
	public List<String> getStringValues(String property, String environmentVariable, String tpl) {
		String result = properties.getProperty(property);
		if ((result == null || result.isEmpty()) && environmentVariable != null)
			result = System.getenv(environmentVariable);
		if (result == null || result.isEmpty())
			throw new IllegalArgumentException("neither configuration property '" + property
					+ "' nor environment variable '" + environmentVariable + "' set");
		List<String> list = new ArrayList<String>();
		for (String s : result./*replaceAll(",", tpl).replaceAll(" +", tpl).*/split(",")) {
			list.add(s.trim());
		}
		return list;
	}

	/*
	 * SINGLETON PATTERN FOR CONFIGURATION OBJECT
	 */
	// deny constructor
	private static final long serialVersionUID = 1L;

	private Configuration() {
		String configFile = System.getProperty(CONFIGURATION_FILENAME_PROPERTY);
		if (configFile != null) {
			FileInputStream fis = null;
			try {
				File f = new File(configFile);
				if (f.exists()) {
					fis = new FileInputStream(f);
					properties.load(fis);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static class LazyHolder {
		private static final Configuration INSTANCE = new Configuration();
	}

	protected Object readResolve() {
		return instance();
	}

	public static Configuration instance() {
		return LazyHolder.INSTANCE;
	}
}
