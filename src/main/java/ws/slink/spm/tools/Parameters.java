package ws.slink.spm.tools;

import ws.slink.spm.tools.Configuration;

public class Parameters {

	static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(Parameters.class);

	private static final String VAR_DBHOST  = "APP_MONGODB_HOST"; 
	private static final String PRP_DBHOST  = "app.mongodb.host";
	
	private static final String VAR_DBPORT  = "APP_MONGODB_PORT"; 
	private static final String PRP_DBPORT  = "app.mongodb.port";

	public static String getDBHost() {
		String s = null;
		try {
			s = Configuration.instance().getValue(PRP_DBHOST, VAR_DBHOST);
		} catch (IllegalArgumentException ex) {}
		if (s == null || s.isEmpty()) 
			s = "127.0.0.1";
		logger.trace("dbhost = " + s);
		return s;
	}
	
	public static int getDBPort() {
		String   s = null;
		int result = 27017;
		try {
			s = Configuration.instance().getValue(PRP_DBPORT, VAR_DBPORT);
		} catch (IllegalArgumentException ex) {}
		if (s != null && !s.isEmpty()) {
			try {
				result = Integer.parseInt(s);
			} catch (NumberFormatException ex) {
			}
		} else {
		}
		logger.trace("dbport = " + result);
		return result;
	}
}
