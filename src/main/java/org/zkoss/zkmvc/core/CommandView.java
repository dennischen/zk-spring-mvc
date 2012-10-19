package org.zkoss.zkmvc.core;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

public class CommandView implements View {

	String commandName;
	public CommandView(String commandName) {
		this.commandName = commandName;
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommandRequest cmdreq = (CommandRequest)request.getAttribute(CommandRequest.ATTR_CMD_REQUEST);
		if(cmdreq!=null){
			cmdreq.setPayload(CommandRequest.PAYLOAD_MODEL_MAP, model);
		}
		//render nothing
	}

}
