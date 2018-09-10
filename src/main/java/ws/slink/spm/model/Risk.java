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

@Entity("risks")
@Indexes(@Index(value = "task_id", fields = @Field("taskId")))
public class Risk {

	@Id @Property("task_id")
	public String taskId;				// 0
	@Property("risk_title")
	public String riskTitle;			// 1
	@Property("risk_status")
	public String riskStatus;			// 2
	@Property("create_time")
	public Date createTime;				// 3
	@Property("creator")
	public String creator;				// 4
	@Property("current_handler")
	public String currentHandler;		// 5
	@Property("current_handler_name")
	public String currentHandlerName;	// 5 -------
	@Property("project_name")
	public String projectName;			// 6
	@Property("project_level")
	public String projectLevel;			// 7
	@Property("project_code")
	public String projectCode;			// 8
	@Property("project_category")
	public String projectCategory; 		// 9
	@Property("sub_region_name")
	public String subRegionName;		// 10
	@Property("rep_office_name")
	public String repOfficeName;		// 11
	@Property("gnoc_name")
	public String gnocName;				// 12
	@Property("domain")
	public String domain;				// 13
	@Property("vendor")
	public String vendor;				// 14
	@Property("product_level_1")
	public String productLevel1;		// 15
	@Property("product_level_2")
	public String productLevel2;		// 16
	@Property("product_level_3")
	public String productLevel3;		// 17
	@Property("product")
	public String product;				// 18
	@Property("type")
	public String type;					// 19
	@Property("technical_risk_id")
	public String technicalRiskId;		// 20
	@Property("risk_item")
	public String riskItem;				// 21
	@Property("source")
	public String source;				// 22
	@Property("severity")
	public String severity;				// 23
	@Property("start_date")
	public Date startDate;				// 24
	@Property("due_date")
	public Date dueDate;				// 25
	@Property("response_bu")
	public String responseBu;			// 26
	@Property("icare_number")
	public String icareNumber;			// 27
	@Property("risk_description")
	public String riskDescription;		// 28
	@Property("description_file")
	public String descriptionFile;		// 29
	@Property("improvement_measure")
	public String improvementMeasure;	// 30
	@Property("approver")
	public String approver;				// 31
	@Property("copy_to")
	public String copyTo;				// 32
	@Property("contingency_plan_status")
	public String contingencyPlanStatus;// 33
	@Property("review_sr_number")
	public String reviewSrNumber;		// 34
	@Property("not_required_reason")
	public String notRequireReason;		// 35
	@Property("confirm_comment")
	public String confirmComment;		// 36
	@Property("appoint_owner")
	public String appointOwner;			// 37
	@Property("plan_develop_comment")
	public String planDevelopComment;	// 38
	@Property("response_plan")
	public String responsePlan;			// 39
	@Property("review_comment")
	public String reviewComment;		// 40
	@Property("implement_comment")
	public String implementComment;		// 41
	@Property("implement_file")
	public String implementFile;		// 42
	@Property("result_comment")
	public String resultComment;		// 43
	@Property("operate")
	public String operate;				// 44
	@Property("country")
	public String country;				// 45
	@Property("customer_group")
	public String customerGroup;		// 46
	@Property("remark")
	public String remark;				// 47
	@Property("involved_equipment")
	public String involvedEquipment;	// 48
	@Property("closure_time")
	public Date closureTime;			// 49
	@Property("accept_time")
	public Date acceptTime;				// 50


	public static Optional<Risk> makeRisk(String [] fields, String dateFormat, String timeFormat) {
		if (fields.length > 51 || fields.length < 47)// && fields.length != 50)
			throw new IllegalArgumentException("Incorrect input data for RiskObject creation: fields.length = " + fields.length);
		Risk risk = new Risk();

		risk.taskId					= fields[0];
		risk.riskTitle				= fields[1];
		risk.riskStatus				= fields[2];
		risk.createTime				= DataTools.parseDate(fields[3], timeFormat);
		risk.creator				= fields[4].replaceAll("[^\\x00-\\x7F]", ",").replaceAll(",$", "");
		risk.currentHandler			= fields[5].replaceAll("[^\\x00-\\x7F]", ",").replaceAll("undefined", "").replaceAll(",$", "").replaceAll("^,", "");
		risk.currentHandlerName		= fields[6].replaceAll("[^\\x00-\\x7F]", ",").replaceAll("undefined", "").replaceAll(",$", "").replaceAll("^,", "");
		risk.projectName			= fields[7];
		risk.projectLevel			= fields[8];
		risk.projectCode			= fields[9];
		risk.projectCategory		= fields[10];
		risk.subRegionName			= fields[11];
		risk.repOfficeName			= fields[12];
		risk.gnocName				= fields[13];
		risk.domain					= fields[14];
		risk.vendor					= fields[15];
		risk.productLevel1			= fields[16];
		risk.productLevel2			= fields[17];
		risk.productLevel3			= fields[18];
		risk.product				= fields[19];
		risk.type					= getTypeString(fields[20]);
		risk.technicalRiskId		= fields[21];
		risk.riskItem				= fields[22];
		risk.source					= fields[23];
		risk.severity				= fields[24];
		risk.startDate				= DataTools.parseDate(fields[25], dateFormat);
		risk.dueDate				= DataTools.parseDate(fields[26], dateFormat);
		risk.responseBu				= fields[27];
		risk.icareNumber			= fields[28];
		risk.riskDescription		= fields[29];
		risk.descriptionFile		= fields[30];
		risk.improvementMeasure		= fields[31];
		risk.approver				= fields[32].replaceAll("[^\\x00-\\x7F]", ",").replaceAll(",$", "");
		risk.copyTo					= fields[33];
		risk.contingencyPlanStatus	= fields[34];
		risk.reviewSrNumber			= fields[35];
		risk.notRequireReason		= fields[36];
		risk.confirmComment			= fields[37];
		risk.appointOwner			= fields[38].replaceAll("[^\\x00-\\x7F]", ",").replaceAll(",$", "");
		risk.planDevelopComment		= fields[39];
		risk.responsePlan			= fields[40];
		risk.reviewComment			= fields[41];
		risk.implementComment		= fields[42];
		risk.implementFile			= fields[43];
		risk.resultComment			= fields[44];
		risk.operate				= fields[45];
		risk.country				= fields[46];
		risk.customerGroup			= fields[47];
		if (fields.length > 48) risk.remark			   = fields[48];
		if (fields.length > 49) risk.involvedEquipment = fields[49];
		if (fields.length > 50) risk.closureTime 	   = DataTools.parseDate(fields[50], timeFormat);
		if (fields.length > 51) risk.acceptTime 	   = DataTools.parseDate(fields[51], timeFormat);

		return Optional.ofNullable(risk);
	}

	// default constructor [for automatic mapping (Morphia)]
	public Risk() {}

	public String toDetailedString() {
		return
				"Task Id                      : " + this.taskId +
				"\nRisk Title                   : " + this.riskTitle +
				"\nRisk Status                  : " + this.riskStatus +
				"\nCreate Time                  : " + DataTools.dateToStr(this.createTime) +
				"\nCreator                      : " + this.creator +
				"\nCurrent Handler              : " + this.currentHandler +
				"\nProject Name                 : " + this.projectName +
				"\nProject Level                : " + this.projectLevel +
				"\nProject Code                 : " + this.projectCode +
				"\nProject Category             : " + this.projectCategory +
				"\nsub Region Name              : " + this.subRegionName +
				"\nRep_office_name              : " + this.repOfficeName +
				"\nGNOC Name                    : " + this.gnocName +
				"\nDomain                       : " + this.domain +
				"\nVendor                       : " + this.vendor +
				"\nProduct_level_l1             : " + this.productLevel1 +
				"\nProduct_level_l2             : " + this.productLevel2 +
				"\nProduct_level_l3             : " + this.productLevel3 +
				"\nProduct                      : " + this.product +
				"\nType                         : " + this.type +
				"\nTechnical Risk ID            : " + this.technicalRiskId +
				"\nRisk Item                    : " + this.riskItem +
				"\nSource                       : " + this.source +
				"\nSeverity                     : " + this.severity +
				"\nStart Date                   : " + DataTools.dateToStr(this.startDate, "yyyy.MM.dd") +
				"\nDue Date                     : " + DataTools.dateToStr(this.dueDate, "yyyy.MM.dd") +
				"\nResponse Bu                  : " + this.responseBu +
				"\nIcare No                     : " + this.icareNumber +
				"\nImprovement Measure          : " + this.improvementMeasure +
				"\nApprover                     : " + this.approver +
				"\nCopy to                      : " + this.copyTo +
				"\nContingency plan status      : " + this.contingencyPlanStatus +
				"\nReview sr no                 : " + this.reviewSrNumber +
				"\nnot_require_reason           : " + this.notRequireReason +
				"\nConfirm Comment              : " + this.confirmComment +
				"\nAppoint Owner                : " + this.appointOwner +
				"\nPlan develop comment         : " + this.planDevelopComment +
				"\nResponse Plan                : " + this.responsePlan +
				"\nReview Comment               : " + this.reviewComment +
				"\nImplement Comment            : " + this.implementComment +
				"\nImplement File               : " + this.implementFile +
				"\nResult Comment               : " + this.resultComment +
				"\nOperate                      : " + this.operate +
				"\nCountry                      : " + this.country +
				"\nCustomer Group               : " + this.customerGroup +
				"\nRemark                       : " + this.remark  +
				"\nInvolved Equipment           : " + this.involvedEquipment +
				"\nClose Time                   : " + DataTools.dateToStr(this.closureTime) +
				"\nRisk Description             : " + this.riskDescription +
				"\nDescription File             : " + this.descriptionFile;
	}

	public String toString() {
		return String.format("%-20s  %-20s  %-10s  %-10s  %-20s  %-30s %-30s %-10s %-20s %-10s",
								this.taskId,
								DataTools.dateToStr(this.createTime),
								DataTools.dateToStr(this.startDate, "yyyy.MM.dd"),
								DataTools.dateToStr(this.dueDate, "yyyy.MM.dd"),
								DataTools.dateToStr(this.closureTime),
								String.format("%s", this.currentHandler),
								String.format("%s", this.appointOwner),
								this.severity,
								this.customerGroup,
								this.type
							);
	}

	private static String getTypeString(String typeStr) {
		String [] split = typeStr.split("/");
		return (split.length > 1) ? split[1] : split[0];
	}
}
