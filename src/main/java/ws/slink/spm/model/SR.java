package ws.slink.spm.model;

import java.util.List;
import java.util.Optional;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.PostLoad;
import org.mongodb.morphia.annotations.Property;

import ws.slink.spm.tools.DataTools;

@Entity("srs")
@Indexes(@Index(value = "sr_number", fields = @Field("srNumber")))
public class SR extends DataItem {

	@Id @Property("sr")
	public String 		 srNumber;           	// 01. Service Request Number
    @Embedded
	public SRDates 		 dates;					// 03 - 14, 21 - 23. all dates + AOFR
	@Property("customer_ticket")
	public String 		 ticketNumber;			// 02. Customer Ticket Number
	@Property("type")
	public String 		 type;					// 15. Type
	@Property("severity")
	public String 		 severity;				// 16. Severity
	@Property("customer_severity")
	public String 		 customerSeverity;		// 17. Customer Severity
	@Property("customer_severity_original")
	public String 		 customerSeverityOriginal;
	@Property("status")
	public String 		 status; 				// 18. Status
	@Property("channel")
	public String 		 channel;				// 19. Channel
	@Property("contract")
	public String 		 contract; 				// 20. Contract Number
	@Property("escalate_flag")
	public boolean		 escalateFlag;			// 21. Escalate to PSE Flag
	@Property("customer_region")
	public String 		 customerRegion;		// 24. Customer Region
	@Property("customer_office")
	public String 		 customerOffice;		// 25. Customer Office
	@Property("country")
	public String 		 country;				// 26. Country
	@Property("state")
	public String 		 state;					// 27. State (location)
	@Property("customer_name")
	public String 		 customerName;			// 28. Customer Name
	@Property("contact_type")
	public String 		 contactType;			// 29. Contact Type
	@Property("employee_organization_tac")
	public String 		 employeeOrgTAC;		// 30. Employee Organization TAC
	@Property("employee_organization_name")
	public String 		 employeeOrgName;		// 31. Employee Organization Name
	@Property("employee_name")
	public String 		 employeeName;			// 32. Employee Name
	@Property("product_line")
	public String 		 productLine;			// 33. Product Line
	@Property("product_class")
	public String 		 productClass;			// 34. Product Class
	@Property("product")
	public String 		 product;				// 35. Product
	@Property("problem_code")
	public String 		 problemCode;
	@Property("problem_summary")
	public String		 problemSummary;		// 36. Problem Summary
	@Property("problem_details")
	public String		 problemDetails;
	@Property("logged_by")
	public String		 loggedBy;				// 37. Logged By
	@Property("resolution_details")
	public String		 resolutionDetails;
	@Property("resolution_summary")
	public String		 resolutionSummary;		// xx. (for historic SRs)
	@Property("resolution_code")
	public String		 resolutionCode;		// xx. (for historic SRs)
	@Property("initiator_phone")
	public String		 initiatorPhoneNumber;
	@Property("initiator_email")
	public String		 initiatorEmail;
	@Property("initiator_name")
	public String		 initiatorName;
	@Property("notes")
	public List<SRNote>  notes;
	
	@Property
	public boolean critical; // if this SR is in a list of critical SRs (from RDS site)
	@Property("subregion")
	public String        subregion;
	
	// builder methods
	/**
	 * create an SR from fields of "Unresolved" report 
	 */
	public static Optional<SR> makeActiveSR(String [] fields, String dateFormat) {
		if (fields.length != 39 && fields.length != 40)
			throw new IllegalArgumentException("Incorrect input data for SRObject creation: fields.length = " + fields.length);
		SR sr = new SR();
		sr.srNumber     	= fields[0];
		sr.ticketNumber 	= fields[1];
		sr.type         	= fields[14];
		sr.severity     	= fields[15];
		sr.customerSeverity = fields[16];
		sr.status			= fields[17];
		sr.channel			= fields[18];
		sr.contract			= fields[19];
		sr.escalateFlag		= (fields[20].equalsIgnoreCase("y")) ? true : false;
		sr.customerRegion	= fields[23];
		sr.customerOffice	= fields[24];
		sr.country			= fields[25];
		sr.state 			= fields[26];
		sr.customerName		= fields[27];
		sr.contactType		= fields[28];
		sr.employeeOrgTAC	= fields[29];
		sr.employeeOrgName	= fields[30];
		sr.employeeName		= fields[31];
		sr.productLine		= fields[32];
		sr.productClass		= fields[33];
		sr.product			= fields[34];
		sr.problemSummary	= fields[35];
		sr.loggedBy			= fields[36];
		sr.dates            = SRDates.makeActive(fields, dateFormat);
		return Optional.ofNullable(sr);
	}

	/**
	 * make a 'historic' SR from fields of "Opened" report
	 * @param fields
	 * @return
	 */
	public static Optional<SR> makeHistoricSR(String [] fields, String dateFormat) {
		if (fields.length != 21) {
			String srN = "n/a";
			if (fields.length > 4) srN = fields[5];
			throw new IllegalArgumentException("Incorrect input data for SRObject creation (#fields: " + fields.length + ", sr#: " + srN + ")");
//			System.out.println(" --> (#fields: " + fields.length + ", sr#: " + srN + ")");
//			return Optional.ofNullable(null);
		} else {
			SR sr = new SR();
			sr.customerRegion    = fields[0];  // 00. Customer Region
			sr.country           = fields[1];  // 01. Country
			sr.customerOffice    = fields[2];  // 02. Customer Office
			sr.customerName      = fields[3];  // 03. Customer Name
			sr.customerSeverity  = fields[4];  // 04. Customer Severity
			sr.srNumber          = fields[5];  // 05. Service Request Number
			sr.dates             = SRDates.makeHistoric(fields, dateFormat); // 06, 07, 08: actualClosureDate, reportDate, totalSuspendDuration
			sr.type              = fields[9];  // 09. Type		
			sr.state             = fields[10];  // 10. State
			sr.status            = fields[11]; // 11. Status
			sr.problemSummary    = fields[12]; // 12. Problem Summary
			sr.resolutionCode    = fields[13]; // 13. Resolution Code
			sr.resolutionSummary = fields[14]; // 14. Resolution Summary
			sr.loggedBy          = fields[15]; // 15. Logged By
			sr.contract          = fields[16]; // 16. Contract Number
			sr.product           = fields[17]; // 17. Product
			sr.productLine       = fields[18]; // 18. Product Line
			sr.employeeOrgTAC    = fields[19]; // 19. Employee Organization TAC
			sr.employeeName      = fields[20]; // 20. Employee Name
//			System.out.println(sr);
			return Optional.ofNullable(sr);
		}
	}
	
	public static Optional<SR> makeCommonSR(String [] fields, String dateFormat) {
//		if (fields.length != 39 && fields.length != 40)
//			throw new IllegalArgumentException("Incorrect input data for SRObject creation: fields.length = " + fields.length);
		SR sr = new SR();
		sr.srNumber     	 = fields[0];
		sr.ticketNumber 	 = fields[1];
		sr.type         	 = fields[14];
		sr.severity     	 = fields[15];
		sr.customerSeverity  = fields[16];
		sr.status			 = fields[17];
		sr.channel			 = fields[18];
		sr.contract			 = fields[19];
		sr.escalateFlag		 = (fields[20].equalsIgnoreCase("y")) ? true : false;
		sr.customerRegion	 = fields[23];
		sr.customerOffice	 = fields[24];
		sr.country			 = fields[25];
		sr.state 			 = fields[26];
		sr.customerName		 = fields[27];
		sr.contactType		 = fields[28];
		sr.employeeOrgTAC	 = fields[29];
		sr.employeeOrgName	 = fields[30];
		sr.employeeName		 = fields[31];
		sr.productLine		 = fields[32];
		sr.productClass		 = fields[33];
		sr.product			 = fields[34];
		sr.problemSummary	 = fields[35];
		sr.loggedBy			 = fields[36];		
		sr.resolutionCode    = fields[41];
		if (fields.length > 42)
			sr.resolutionSummary = fields[42];
		sr.dates             = SRDates.makeCommon(fields, dateFormat);
		return Optional.ofNullable(sr);
	}
	
	public static Optional<SR> makeCompleteSR(String [] fields, String dateFormat) {
		if (fields.length != 54)
			throw new IllegalArgumentException("makeCompleteSR: Incorrect input data for SRObject creation: fields.length = " + fields.length);
		SR sr = new SR();
		
//		sr.notes                    = notes;
		sr.srNumber     	        = fields[0];
		sr.ticketNumber 	        = fields[1];
		sr.type         	        = fields[20];
		sr.severity     	        = fields[21];
		sr.customerSeverity         = fields[22];
		sr.customerSeverityOriginal = fields[23];
		sr.status			        = fields[24];
		sr.channel			        = fields[25];
		sr.contract			        = fields[26];
		sr.escalateFlag		        =(fields[27].equalsIgnoreCase("y")) ? true : false;
		sr.country			        = fields[30];
		sr.state 			        = fields[31];
		sr.loggedBy			        = fields[32];
		sr.resolutionCode           = fields[35];
		sr.resolutionSummary        = fields[36];
		sr.resolutionDetails        = fields[37];
		sr.problemCode              = fields[38];
		sr.problemSummary	        = fields[39];
		sr.problemDetails           = fields[40];
		sr.contactType		        = fields[41];
		sr.initiatorPhoneNumber     = fields[42];
		sr.initiatorEmail           = fields[43];
		sr.initiatorName            = fields[44];
		sr.productLine		        = fields[45];
		sr.productClass		        = fields[46];
		sr.product			        = fields[47];
		sr.employeeOrgTAC	        = fields[48];
		sr.employeeOrgName	        = fields[49];
		sr.employeeName		        = fields[50];
		sr.customerRegion	        = fields[51];
		sr.customerOffice	        = fields[52];
		sr.customerName		        = fields[53];
		sr.dates                    = SRDates.makeComplete(fields, dateFormat);
		return Optional.ofNullable(sr);
	}
	
	public static Optional<SR> makeNotesSR(String [] fields, List<SRNote> notes, String dateFormat) {
		if (notes == null || notes.isEmpty())
			throw new IllegalArgumentException("'notes' list is empty or not initialized");
		SR sr       = new SR();
		sr.notes    = notes;
		sr.notes.sort((a, b) -> b.date.compareTo(a.date));
		sr.srNumber = fields[0];
		return Optional.ofNullable(sr);
	}
	// default constructor [for automatic mapping (Morphia)]
	public SR() {}

	// toString methods
	public String toStringActive() {
		return "";
	}
	public String toStringHistoric() {
		return String.format("%-10s  %-20s  %-25s  %-2s  %-25s",
							  	this.srNumber,
							  	this.customerSeverity,
								this.status,
								this.state,
								this.customerName
				            );
	}
	public String toDetailedString() {
		String escalatedStr = (this.escalateFlag)  ? "YES" : "NO";
		String overdueStr   = (this.dates != null && this.dates.overdue) ? "YES" : "NO";
		
		String notesStr = "";
		if (notes != null)
			for (SRNote n : notes) {
				notesStr += "\n    " + DataTools.dateToStr(n.date);
				notesStr += "\n        " + n.type;
				notesStr += "\n        " + n.text;						
				notesStr += "\n        " + n.visibility;						
			}

		String datesStr = "";
		if (dates != null) {
			datesStr += 
			"\n    Report                    : " + DataTools.dateToStr(dates.reportDate) +
			"\n    Response (expected)       : " + DataTools.dateToStr(dates.responseDateExpected) +
			"\n    Response (actual)         : " + DataTools.dateToStr(dates.responseDateActual) + 
			"\n    Restore  (expected)       : " + DataTools.dateToStr(dates.restoreDateExpected) +
			"\n    Restore (expected travel) : " + DataTools.dateToStr(dates.restoreDateExpectedTravel) +
			"\n    Restore (actual)          : " + DataTools.dateToStr(dates.restoreDateActual) + 
			"\n    Workaround (expected)     : " + DataTools.dateToStr(dates.waDateExpected) +
			"\n    Workaround (expected susp): " + DataTools.dateToStr(dates.waDateExpectedWithSuspend) +
			"\n    Workaround (actual)       : " + DataTools.dateToStr(dates.waDateActual) +
			"\n    Closed                    : " + DataTools.dateToStr(dates.closedDate) +
			"\n    Closure (actual)          : " + DataTools.dateToStr(dates.closeDateActual) +
			"\n    Closure (expected)        : " + DataTools.dateToStr(dates.closeDateExpected) +
			"\n    Closure (expected suspend): " + DataTools.dateToStr(dates.closeDateExpectedSuspend) +
			"\n    Solution provided         : " + DataTools.dateToStr(dates.solutionProvidedDate) +
			"\n    Last update               : " + DataTools.dateToStr(dates.lastUpdateDate) +
			"\n    Escalate (expected)       : " + DataTools.dateToStr(dates.escalateDateExpected) +
			"\n    Escalate (actual)         : " + DataTools.dateToStr(dates.escalateDateActual) +
			"\n    Days to AOFR              : " + DataTools.nullableToString(this.dates.daysToAOFR) +				
			"\n    Total suspend duration    : " + dates.totalSuspendDuration +
			"\n    Total open duration       : " + dates.totalOpenDuration +
			"\n    Unresolved Days           : " + DataTools.nullableToString(this.dates.unresolvedDays) +
			"\n    Overdue?                  : " + overdueStr +
			"\n    Overdue Days              : " + this.dates.overdueDays;
		}
		
		
		return 
				"SR Number                    : "   + this.srNumber +
				"\nCustomer Ticket              : " + this.ticketNumber +
				"\nType                         : " + this.type +
				"\nSeverity                     : " + this.severity +
				"\nCustomer Severity            : " + this.customerSeverity +
				"\nCustomer Severity Original   : " + this.customerSeverityOriginal +				
				"\nStatus                       : " + this.status +
				"\nChannel                      : " + this.channel +
				"\ncontract                     : " + this.contract +	
				"\nCountry                      : " + this.country +
				"\nState (location)             : " + this.state +
				"\nLogged By                    : " + this.loggedBy +
				"\nProduct Line                 : " + this.productLine +
				"\nProduct Class                : " + this.productClass +
				"\nProduct                      : " + this.product +
				"\nProblem Code                 : " + this.problemCode +
				"\nProblem Summary              : " + this.problemSummary +
				"\nProblem Details              : " + this.problemDetails +
				"\nResolution Code              : " + this.resolutionCode +
				"\nResolution Summary           : " + this.resolutionSummary +
				"\nResolution Details           : " + this.resolutionDetails +
				"\nContact Type                 : " + this.contactType +
				"\nInitiator Name               : " + this.initiatorName +
				"\nInitiator Phone              : " + this.initiatorPhoneNumber +
				"\nInitiator Email              : " + this.initiatorEmail +
				"\nEmployee Organization TAC    : " + this.employeeOrgTAC +
				"\nEmployee Organization Name   : " + this.employeeOrgName +
				"\nEmployee Name                : " + this.employeeName +
				"\nCustomer Region              : " + this.customerRegion +
				"\nCustomer Office              : " + this.customerOffice +
				"\nCustomer Name                : " + this.customerName +
				"\nEscalated                    : " + escalatedStr +
				"\nDates:" + datesStr + 
				"\nNotes:" + notesStr + 				
				"\n------------------------------------------------------------------------"
				;
	}
	public String toString() {
		return String.format("%-10s  %-20s  %-20s  %-25s  %-2s  %-25s",
								this.srNumber,
								this.severity,
								this.customerSeverity,
								this.status,
								this.state,
								this.customerName
			   ); 
	}

	@PostLoad private void updateCalculatedFields() {
		// TODO: rethink calculatable fields update procedure
		Integer v; 
		
		v = DataTools.dateToNow(dates.reportDate);
		this.dates.unresolvedDays = (v!=null) ? Math.abs(v) : null;

		v = DataTools.dateToNow(dates.closeDateExpected);
		this.dates.daysToAOFR = (v!=null) ? Math.abs(v) : null;
		
		v = DataTools.dateToNow(dates.closeDateExpectedSuspend);
		this.dates.overdueDays = (v!=null) ? (-v) : null;
		
//		v = dateToNow(dates.closeDateExpectedSuspend);
//		this.dates.overdue = (v < 0) ? true : false;
	}
}
