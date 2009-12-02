package ipipan.ispejd.client;

public class ISpejdServiceException extends Exception {
	private static final long serialVersionUID = -6074912499750290636L;

	public ISpejdServiceException() {
		super();
	}

	public ISpejdServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ISpejdServiceException(String message) {
		super(message);
	}

	public ISpejdServiceException(Throwable cause) {
		super(cause.getMessage());
	}
}
