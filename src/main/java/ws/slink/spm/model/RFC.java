package ws.slink.spm.model;

import java.util.Date;
import java.util.Optional;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;

import ws.slink.spm.tools.DataTools;

@Entity("rfc")
@Indexes(@Index(value = "rfc_number", fields = @Field("rfcNumber")))
public class RFC extends DataItem {

	@Id @Property("sr")
	public String 		srNumber;           	// 00. Service Request Number

	@Property("severity")
	public String		severity;				// 07. Severity

	@Property("type")
	public String		type;					// 03. Type

	@Property("quarter")
	public String 		quarter;				// 17. Quarter (e.g "2016 Q1")

	@Property("report_date")
	public Date			reportDate;				// 01. Report Date

	@Property("planned_implement_date")
	public Date			planImplementDate;		// 10. Planned Implementation Date
	
	@Property("closed_date")
	public Date			actualCloseDate;		// 02. Actual Close Date

	@Property("state")
	public String 		state;					// 05. State

	@Property("on_site")
	public boolean		onSite;					// 06. On Site Flag

	@Property("planned_amount")
	public int			planElementAmount;		// 08. Planned Element Amount

	@Property("actual_amount")
	public int			actualElementAmount;	// 09. Actual Element Amount

	@Property("customer_number")
	public String		customerNumber;			// 18. Customer Number

	@Property("customer_name")
	public String 		customerName;			// 20. Customer Name

	@Property("product_line")
	public String		productLine;			// 12. Product Line

	@Property("product_class")
	public String		productClass;			// 13. Product Class

	@Property("product")
	public String		product;				// 14. Product

	@Property("employee")
	public String		employee;				// 11. Employee Name

	@Property("employee_org_name")
	public String		employeeOrgName;		// 15. Employee Organization Name

	@Property("employee_tac_name")
	public String		employeeTACName;		// 16. Employee TAC Name

	@Property("country")
	public String		country;				// 04. Country

	@Property("summary")
	public String		problemSummary;			// 19. Problem Summary

	@Property("status")
	public String		status;					// 21. Status

	@Property("checked")
	public Boolean		checked;				

	@Property("project_type")
	public String		projectType;			

	// builder methods
	/**
	 * create an RFC from fields of "rfc_xxxx" report 
	 */
	
	public static Optional<RFC> makeRFC(String [] fields, String dateFormat) {
//		System.out.println("   ---> " + dateFormat);
//		System.out.println("   ---> '" + fields[1] + "', '" + fields[10] + "', '" + fields[2] + "'");
		if (fields.length != 23 && fields.length != 22)
			throw new IllegalArgumentException("Incorrect input data for RFC object creation: fields.length = " + fields.length);
		RFC rfc = new RFC();
		rfc.srNumber			= fields[0]; 	// 00. Service Request Number
		rfc.severity			= fields[7];	// 07. Severity
		rfc.type				= fields[3];	// 03. Type
		rfc.quarter				= fields[17];	// 17. Quarter (e.g "2016 Q1")
		rfc.reportDate			= DataTools.parseExcelDate(fields[1]);//DataTools.parseDate(fields[1],  dateFormat);	// 01. Report Date
		rfc.planImplementDate	= /*DataTools.parseExcelDate(fields[10]);*/DataTools.parseDate(fields[10], dateFormat);	// 10. Planned Implementation Date
		rfc.actualCloseDate		= DataTools.parseExcelDate(fields[2]);//DataTools.parseDate(fields[2],  dateFormat);	// 02. Actual Close Date
		rfc.state				= fields[5];	// 05. State
		rfc.planElementAmount	= DataTools.parseIntValue(fields[8]);	// 08. Planned Element Amount
		rfc.actualElementAmount	= DataTools.parseIntValue(fields[9]);	// 09. Actual Element Amount
		rfc.customerNumber		= fields[18];	// 18. Customer Number
		rfc.customerName		= fields[20];	// 20. Customer Name
		rfc.productLine			= fields[12];	// 12. Product Line
		rfc.productClass		= fields[13];	// 13. Product Class
		rfc.product				= fields[14];	// 14. Product
		rfc.employee			= fields[11];	// 11. Employee Name
		rfc.employeeOrgName		= fields[15];	// 15. Employee Organization Name
		rfc.employeeTACName		= fields[16];	// 16. Employee TAC Name
		rfc.country				= fields[4];	// 04. Country
		rfc.problemSummary		= fields[19];	// 19. Problem Summary
		rfc.status      		= fields[21];	// 21. Status
		if (fields.length == 23) rfc.projectType = fields[22];	// 22. projectType
		else					 rfc.projectType = null;
		rfc.onSite 				= false;
		if (fields[6] != null && fields[6].isEmpty() && fields[6].equalsIgnoreCase("yes")) 
			rfc.onSite = true;
		
		if (rfc.state == null || rfc.state.isEmpty()) {
			String s = rfc.problemSummary.toLowerCase();
			if (s.contains("far east") || s.contains("far-east") || s.contains("fareast") || s.contains(" fe "))
				rfc.state = "FE";
		}
		
		return Optional.ofNullable(rfc);
	}

	// default constructor [for automatic mapping (Morphia)]
	public RFC() {}

	// toString methods
	public String toDetailedString() {
		String onSite   = (this.onSite) ? "YES" : "NO";
		return 
				"SR Number                    : "   + this.srNumber 								+
				"\nSeverity                     : " + this.severity	     							+
				"\nType                         : " + this.type										+
				"\nQuarter                      : " + this.quarter									+
				"\nReport Date                  : " + DataTools.dateToStr(this.reportDate) 			+
				"\nPlanned Implementation Date  : " + DataTools.dateToStr(this.planImplementDate)	+
				"\nActual Closure Date          : " + DataTools.dateToStr(this.actualCloseDate)		+
				"\nState                        : " + this.state									+
				"\nPlanned Element Amount       : " + this.planElementAmount						+
				"\nActual Element Amount        : " + this.actualElementAmount						+
				"\nCustomer Number              : " + this.customerNumber							+
				"\nCustomer Name                : " + this.customerName								+
				"\nProduct Line                 : " + this.productLine								+
				"\nProduct Class                : " + this.productClass								+
				"\nProduct                      : " + this.product									+
				"\nEmployee Name                : " + this.employee									+
				"\nEmployee Organization Name   : " + this.employeeOrgName							+
				"\nEmployee TAC Name            : " + this.employeeTACName							+
				"\nCountry                      : " + this.country									+
				"\nProblem Summary              : " + this.problemSummary							+
				"\nStatus                       : " + this.status   							    +
				"\nProject Type                 : " + this.projectType							    +
				"\nOn-Site                      : " + onSite;
	}
	public String toString() {
		return String.format("%-10s  %-10s  %-5s  %-50s  %-40s  %-40s",
								this.srNumber,
								this.status,
								this.severity,
								this.state,
								this.customerName,
								this.employee,
								this.problemSummary
			   ); 
	}
}
