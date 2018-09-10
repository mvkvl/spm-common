package ws.slink.spm.db;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import ws.slink.spm.model.SRNote;

public class SRNoteCodec implements Codec<SRNote> {

	@Override
	public void encode(BsonWriter writer, SRNote note, EncoderContext ec) {
		writer.writeString(note.toString());
	}

	@Override
	public Class<SRNote> getEncoderClass() {
		return SRNote.class;
	}

	@Override
	public SRNote decode(BsonReader reader, DecoderContext ec) {
		SRNote note = SRNote.parse(reader.readString());
		return note;
	}

}
