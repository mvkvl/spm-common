package ws.slink.spm.model;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Transient;

import ws.slink.spm.tools.DataTools;

@Entity("dates_unresolved")
public class SRDates {
	
	static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(SRDates.class);
	
	@Property("report_date")
	public Date    reportDate;
	@Property("response_date_expected")
	public Date    responseDateExpected;
	@Property("response_date_actual")
	public Date    responseDateActual;
	@Property("restore_date_expected")
	public Date    restoreDateExpected;
	@Property("restore_date_expected_with_travel")
	public Date    restoreDateExpectedTravel;
	@Property("restore_date_actual")
	public Date    restoreDateActual;
	@Property("workaround_date_expected")
	public Date    waDateExpected;
	@Property("workaround_date_expected_with_suspend")
	public Date    waDateExpectedWithSuspend;
	@Property("workaround_date_actual")
	public Date    waDateActual;
	@Property("closure_date_expected")
	public Date    closeDateExpected;
	@Property("closure_date_actual")
	public Date    closeDateActual;
	@Property("closed_date")
	public Date    closedDate;
	@Property("closure_date_expected_with_suspend")
	public Date    closeDateExpectedSuspend;
	@Property("solution_provided_date")
	public Date    solutionProvidedDate;
	@Property("total_suspend_duration")
	public int     totalSuspendDuration;
	@Property("total_open_duration")
	public int     totalOpenDuration;
	@Property("last_update_date")
	public Date    lastUpdateDate;
	@Property("escalate_date_actual")
	public Date    escalateDateActual;		 
	@Property("escalate_date_expected")
	public Date    escalateDateExpected;	 

	// calculated fields
	@Transient public Integer daysToAOFR;	 // + Days to AOFR (11 - today)	[calculated field]
	@Transient public Integer unresolvedDays;// 38. Unresolved Duration(day)[calculated field]
	@Transient public boolean overdue;		 // 39. Overdue? 				[calculated field]
	@Transient public Integer overdueDays;	 // 40. Overdue Duration(day)	[calculated field]
	
	
	
	// default constructor for automatic mapping (Morphia)
	public SRDates() {}
	
	// builder methods
	public static SRDates makeActive(String [] fields, String dateFormat) {
		logger.trace("sr.makeActive: ");
		SRDates srd = new SRDates();
		srd.reportDate 					= DataTools.parseExcelDate(fields[2]);//DataTools.parseDate(fields[2], dateFormat);
		srd.responseDateExpected		= DataTools.parseExcelDate(fields[3]);//DataTools.parseDate(fields[3], dateFormat);
		srd.responseDateActual			= DataTools.parseExcelDate(fields[4]);//DataTools.parseDate(fields[4], dateFormat);
		srd.restoreDateExpected			= DataTools.parseExcelDate(fields[5]);//DataTools.parseDate(fields[5], dateFormat);
		srd.restoreDateExpectedTravel 	= DataTools.parseExcelDate(fields[6]);//DataTools.parseDate(fields[6], dateFormat);
		srd.restoreDateActual			= DataTools.parseExcelDate(fields[7]);//DataTools.parseDate(fields[7], dateFormat);
		srd.waDateExpected				= DataTools.parseExcelDate(fields[8]);//DataTools.parseDate(fields[8], dateFormat);
		srd.waDateActual				= DataTools.parseExcelDate(fields[9]);//DataTools.parseDate(fields[9], dateFormat);
		srd.closeDateExpected			= DataTools.parseExcelDate(fields[10]);//DataTools.parseDate(fields[10], dateFormat);
		srd.closeDateExpectedSuspend	= DataTools.parseExcelDate(fields[11]);//DataTools.parseDate(fields[11], dateFormat);
		srd.totalSuspendDuration		= DataTools.parseIntValue(fields[12]);
		srd.lastUpdateDate				= DataTools.parseExcelDate(fields[13]);//DataTools.parseDate(fields[13], dateFormat);
		srd.escalateDateActual			= DataTools.parseExcelDate(fields[21]);//DataTools.parseDate(fields[21], dateFormat);
		srd.escalateDateExpected		= DataTools.parseExcelDate(fields[22]);//DataTools.parseDate(fields[22], dateFormat);

		
		
		logger.trace("           report_date: '" + fields[2] + "' -> '" + srd.reportDate + "'");
		logger.trace("      response_date_ex: '" + fields[3] + "' -> '" + srd.responseDateExpected + "'");
		logger.trace("         response_date: '" + fields[4] + "' -> '" + srd.responseDateActual + "'");
		logger.trace("       restore_date_ex: '" + fields[5] + "' -> '" + srd.restoreDateExpected + "'");

		logger.trace("          restore_date: '" + fields[7] + "' -> '" + srd.restoreDateActual + "'");
		logger.trace("            wa_date_ex: '" + fields[8] + "' -> '" + srd.waDateExpected + "'");
		logger.trace("               wa_date: '" + fields[9] + "' -> '" + srd.waDateActual + "'");
		logger.trace("         close_date_ex: '" + fields[10] + "' -> '" + srd.closeDateExpected + "'");
		logger.trace("    close_date_ex_susp: '" + fields[11] + "' -> '" + srd.closeDateExpectedSuspend + "'");
		
		return srd;
	}
	public static SRDates makeHistoric(String [] fields, String dateFormat) {
		logger.trace("sr.makeHistoric: ");
		SRDates srd = new SRDates();
		srd.reportDate           = DataTools.parseExcelDate(fields[6]);//DataTools.parseDate(fields[6], dateFormat);	// 07. Report Date
		srd.closeDateActual      = DataTools.parseExcelDate(fields[7]);//DataTools.parseDate(fields[7], dateFormat);	// 02. Actual Close Date
		srd.totalSuspendDuration = DataTools.parseIntValue(fields[8]);
		logger.trace("       report_date: '" + fields[6] + "' -> '" + srd.reportDate + "'");
		logger.trace("      closure_date: '" + fields[7] + "' -> '" + srd.closeDateActual + "'");
		logger.trace("  suspend_duration: '" + fields[8] + "' -> '" + srd.totalSuspendDuration + "'");
		return srd;
	}
	public static SRDates makeCommon(String [] fields, String dateFormat) {
		SRDates srd = new SRDates();
		srd.reportDate 					= DataTools.parseExcelDate(fields[2]);//DataTools.parseDate(fields[2], dateFormat);
		srd.responseDateExpected		= DataTools.parseExcelDate(fields[3]);//DataTools.parseDate(fields[3], dateFormat);
		srd.responseDateActual			= DataTools.parseExcelDate(fields[4]);//DataTools.parseDate(fields[4], dateFormat);
		srd.restoreDateExpected			= DataTools.parseExcelDate(fields[5]);//DataTools.parseDate(fields[5], dateFormat);
		srd.restoreDateExpectedTravel 	= DataTools.parseExcelDate(fields[6]);//DataTools.parseDate(fields[6], dateFormat);
		srd.restoreDateActual			= DataTools.parseExcelDate(fields[7]);//DataTools.parseDate(fields[7], dateFormat);
		srd.waDateExpected				= DataTools.parseExcelDate(fields[8]);//DataTools.parseDate(fields[8], dateFormat);
		srd.waDateActual				= DataTools.parseExcelDate(fields[9]);//DataTools.parseDate(fields[9], dateFormat);
		srd.closeDateExpected			= DataTools.parseExcelDate(fields[10]);//DataTools.parseDate(fields[10], dateFormat);
		srd.closeDateExpectedSuspend	= DataTools.parseExcelDate(fields[11]);//DataTools.parseDate(fields[11], dateFormat);
		srd.totalSuspendDuration		= DataTools.parseIntValue(fields[12]);
		srd.lastUpdateDate				= DataTools.parseExcelDate(fields[13]);//DataTools.parseDate(fields[13], dateFormat);
		srd.escalateDateActual			= DataTools.parseExcelDate(fields[21]);//DataTools.parseDate(fields[21], dateFormat);
		srd.escalateDateExpected		= DataTools.parseExcelDate(fields[22]);//DataTools.parseDate(fields[22], dateFormat);
		srd.closeDateActual             = DataTools.parseExcelDate(fields[40]);//DataTools.parseDate(fields[40], dateFormat);
		return srd;
	}
	public static SRDates makeComplete(String [] fields, String dateFormat) {
		SRDates srd = new SRDates();
		srd.reportDate 					= DataTools.parseExcelDate(fields[3]);//DataTools.parseDate(fields[2], dateFormat);
		srd.responseDateExpected		= DataTools.parseExcelDate(fields[4]);//DataTools.parseDate(fields[3], dateFormat);
		srd.responseDateActual			= DataTools.parseExcelDate(fields[5]);//DataTools.parseDate(fields[4], dateFormat);
		srd.restoreDateExpected			= DataTools.parseExcelDate(fields[6]);//DataTools.parseDate(fields[5], dateFormat);
		srd.restoreDateExpectedTravel 	= DataTools.parseExcelDate(fields[7]);//DataTools.parseDate(fields[6], dateFormat);
		srd.restoreDateActual			= DataTools.parseExcelDate(fields[8]);//DataTools.parseDate(fields[7], dateFormat);
		srd.waDateExpected				= DataTools.parseExcelDate(fields[9]);//DataTools.parseDate(fields[8], dateFormat);
		srd.waDateExpectedWithSuspend   = DataTools.parseExcelDate(fields[10]);//DataTools.parseDate(fields[8], dateFormat);
		srd.waDateActual				= DataTools.parseExcelDate(fields[11]);//DataTools.parseDate(fields[9], dateFormat);
		srd.closeDateExpected			= DataTools.parseExcelDate(fields[12]);//DataTools.parseDate(fields[10], dateFormat);
		srd.closeDateExpectedSuspend	= DataTools.parseExcelDate(fields[13]);//DataTools.parseDate(fields[11], dateFormat);
		srd.solutionProvidedDate        = DataTools.parseExcelDate(fields[14]);//DataTools.parseDate(fields[11], dateFormat);
		srd.closeDateActual             = DataTools.parseExcelDate(fields[15]);//DataTools.parseDate(fields[40], dateFormat);
		srd.closedDate                  = DataTools.parseExcelDate(fields[16]);//DataTools.parseDate(fields[40], dateFormat);
		srd.lastUpdateDate				= DataTools.parseExcelDate(fields[17]);//DataTools.parseDate(fields[13], dateFormat);
		srd.totalSuspendDuration		= DataTools.parseIntValue (fields[18]);
		srd.totalOpenDuration           = DataTools.parseIntValue (fields[19]);
		srd.escalateDateActual			= DataTools.parseExcelDate(fields[28]);//DataTools.parseDate(fields[21], dateFormat);
		srd.escalateDateExpected		= DataTools.parseExcelDate(fields[29]);//DataTools.parseDate(fields[22], dateFormat);
		return srd;		
	}
}
