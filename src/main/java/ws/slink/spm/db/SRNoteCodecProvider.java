package ws.slink.spm.db;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import ws.slink.spm.model.SRNote;

public class SRNoteCodecProvider implements CodecProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <T> Codec<T> get(Class<T> type, CodecRegistry cr) {
		if (type == SRNote.class) {
			return (Codec<T>) new SRNoteCodec();
		}
		return null;
	}

}
