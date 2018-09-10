package ws.slink.spm.tools;

public class MethodNotImplementedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MethodNotImplementedException() {
		super("method not implemented!");
	}

	public MethodNotImplementedException(String msg) {
		super(msg);
	}

}
