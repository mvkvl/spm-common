package ws.slink.spm.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

import ws.slink.spm.tools.DataTools;

@Entity("sr_note")
public class SRNote {

	static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(SRNote.class);

	@Property
	public String text;
	@Property
	public Date   date;
	@Property
	public String type;
	@Property
	public String visibility;
	
	private SRNote() {}
	public SRNote(String text, Date date, String type, String visibility) {
		this.text       = text;
		this.date       = date;
		this.type       = type;
		this.visibility = visibility;
	}
	public SRNote(String [] fields) {
		this.text       = fields[1].replaceAll("\"", "'").replaceAll("\n", " ");
		this.date       = DataTools.parseExcelDate(fields[2]);	
		this.type       = fields[4].replaceAll("\"", "'");
		this.visibility = fields[5].replaceAll("\"", "'");
	}
	@Override
	public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + text.hashCode();
        result = prime * result + date.hashCode();
        result = prime * result + type.hashCode();
        result = prime * result + visibility.hashCode();
        return result;	
	}
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) 
        	return false;
        SRNote other = (SRNote) obj;
        if (!text.equals(other.text)) return false;
        if (!date.equals(other.date)) return false;
        if (!type.equals(other.type)) return false;
        if (!visibility.equals(other.visibility)) return false;
        return true;
    }
    
    public String toString() {
    	return "text: " + text + "\ndate: " + date + "\ntype: " + type + "\nvisibility: " + visibility;
    }
    
	public static SRNote parse(String string) {
    	try {
        	String [] strings = string.split("\n");
        	SRNote note = new SRNote();
        	note.text = strings[0].replaceFirst("text: ", "");
        	note.date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(strings[1].replaceFirst("date: ", ""));
        	note.type = strings[2].replaceFirst("type: ", "");
        	note.visibility = strings[3].replaceFirst("visibility: ", "");
        	return note;
    	} catch (ParseException ex) {
    		return null;
    	}
    }
    
    public static void main(String[] args) {
		SRNote note1 = new SRNote("text", new Date(), "type", "visibility");
		String s = note1.toString();
		SRNote note2 = SRNote.parse(s);
		System.out.println(note1 + "\n" + note2);
	}
}
