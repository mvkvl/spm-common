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

@Entity("ibms")
@Indexes(@Index(value = "archive_id", fields = @Field("archiveId")))
public class IBMS {
	@Property("region")
	public String region;				// 0
	@Property("regional_office")
	public String regionalOffice;		// 1
	@Property("branch")
	public String branch;				// 2
	@Property("country")
	public String country; 				// 3
	@Property("city")
	public String city; 				// 4
	@Property("account_number")
	public String accountNumber;		// 5
	@Property("account")
	public String account; 				// 6
	@Property("legal_entity_number")
	public String legalEntityNumber; 	// 7
	@Property("legal_entity")
	public String legalEntity;			// 8
	@Property("network_id")
	public String networkId;			// 9
	@Property("network_name")
	public String networkName; 			// 10
	@Id	@Property("archive_id")
	public String archiveId; 			// 11
	@Property("archive_name")	
	public String archiveName;			// 12

	@Property("ne_quantity")
	public String neQuantity; 			// 13
	
	
	@Property("product_line")
	public String productLine; 			// 14
	@Property("archive_status")
	public String archiveStatus; 		// 15
	@Property("archive_delete_reason")
	public String archiveDeleteReason; 	// 16
	@Property("archive_owner")
	public String archiveOwner; 		// 17
	@Property("data_provider")
	public String dataProvider;			// 18
	@Property("ne_update_date")
	public Date   neUpdateDate; 		// 19
	@Property("archive_register_staff")
	public String archiveRegisterStaff; // 20
	@Property("archive_register_date")
	public String archiveRegisterDate; 	// 21
	@Property("archive_update_staff")
	public String archiveUpdateStaff; 	// 22
	@Property("archive_update_date")
	public Date   archiveUpdateDate; 	// 23
	@Property("authorization_required")
	public Boolean authRequired; 		// 24
	@Property("authorization_obtained")
	public Boolean authObtained; 		// 25
	@Property("related_archive_id")
	public String relatedArchiveId; 	// 26
	@Property("related_archive_name")
	public String relatedArchiveName; 	// 27
	@Property("legal_entity_status")
	public String legalEntityStatus; 	// 28
	@Property("same_accounts")
	public Boolean sameAccounts; 		// 29
	@Property("auto_created")
	public Boolean autocreated; 		// 30
		
	public static Optional<IBMS> makeIBMS(String [] fields, String dateFormat) {
		//if (fields.length < 29 || fields.length > 32)
		//	throw new IllegalArgumentException("Incorrect input data for IBMS object creation: fields.length = " + fields.length);
		IBMS ibms = new IBMS();
		ibms.region              = fields[0]; 									// 0
		ibms.regionalOffice      = fields[1]; 									// 1
		ibms.branch              = fields[2]; 									// 2
		ibms.country             = fields[3]; 									// 3
		ibms.city                = fields[4]; 									// 4
		ibms.accountNumber       = fields[5]; 									// 5
		ibms.account             = fields[6]; 									// 6
		ibms.legalEntityNumber   = fields[7]; 									// 7
		ibms.legalEntity         = fields[8]; 									// 8
		ibms.networkId           = fields[9]; 									// 9
		ibms.networkName         = fields[10]; 									// 10
		ibms.archiveId           = fields[11]; 									// 11
		ibms.archiveName         = fields[12]; 									// 12
		
		ibms.neQuantity          = fields[13]; 									// 13
		
		ibms.productLine         = fields[14]; 									// 13
		ibms.archiveStatus       = fields[15]; 									// 14
		ibms.archiveDeleteReason = fields[16]; 									// 15
		ibms.archiveOwner        = fields[17]; 									// 16
		ibms.dataProvider        = fields[18]; 									// 17
		ibms.neUpdateDate        = DataTools.parseDate(fields[19], dateFormat); // 18
		ibms.archiveRegisterStaff= fields[20]; 									// 19
		ibms.archiveRegisterDate = fields[21]; 									// 20
		ibms.archiveUpdateStaff  = fields[22]; 									// 21
		ibms.archiveUpdateDate   = DataTools.parseDate(fields[23], dateFormat); // 22
		ibms.authRequired        = fields[24].equals("N") ? false : true; 		// 23
		ibms.authObtained        = fields[25].equals("N") ? false : true; 		// 24
		ibms.relatedArchiveId    = fields[26]; 									// 25
		ibms.relatedArchiveName  = fields[27]; 									// 26
		ibms.legalEntityStatus   = fields[28]; 									// 27
		ibms.sameAccounts        = fields[29].equals("N") ? false : true; 		// 28
		if (fields.length == 30)
			ibms.autocreated     = fields[30].equals("N") ? false : true; 		// 29
		return Optional.ofNullable(ibms);
	}
	
	public IBMS() {}
	
	public String toDetailedString() {
		String authR = (this.authRequired) ? "YES" : "NO";
		String authO = (this.authObtained) ? "YES" : "NO";
		String same  = (this.sameAccounts) ? "YES" : "NO";
		return 
				  "Arhive ID     : "   + archiveId 
				+ "\nArhive name   : " + archiveName
				+ "\nProduct line  : " + productLine 
				+ "\nArchive status: " + archiveStatus
				+ region  + "\n" +
				regionalOffice  + "\n" +
				branch  + "\n" +
				country  + "\n" +
				city + "\n" +
				accountNumber + "\n" +
				account + "\n" +
				legalEntityNumber + "\n" +
				legalEntity + "\n" +
				networkId + "\n" +
				networkName + "\n" +				
				archiveStatus + "\n" +
				archiveDeleteReason + "\n" +
				archiveOwner + "\n" +
				dataProvider + "\n" +
				neUpdateDate + "\n" +
				archiveRegisterStaff + "\n" +
				archiveRegisterDate + "\n" +
				archiveUpdateStaff + "\n" +
				archiveUpdateDate + "\n" +
				authR + "\n" +
				authO + "\n" +
				relatedArchiveId + "\n" +
				relatedArchiveName + "\n" +
				legalEntityStatus + "\n" +
				same;
	}	
	
}
