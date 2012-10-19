package org.zkoss.zkmvc.core;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CommandResponse extends HttpServletResponseWrapper{
	
	private int error = -1; 
	private String errorMessage;		
	
	public CommandResponse(HttpServletResponse response) {
		super(response);
	}
	
	public int getError(){
		return error;
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		error = sc;
		errorMessage = msg;
		super.sendError(sc, msg);
	}

	@Override
	public void sendError(int sc) throws IOException {
		error = sc;
		errorMessage = "";
		super.sendError(sc);
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	
}
