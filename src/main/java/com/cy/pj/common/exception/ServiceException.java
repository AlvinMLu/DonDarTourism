package com.cy.pj.common.exception;
/**
 * 自定义业务异常
 * @author Administrator
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -5598865415547474216L;

	public ServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
