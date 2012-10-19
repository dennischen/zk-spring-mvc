package org.zkoss.zkmvc.core;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class CommandViewResolver extends InternalResourceViewResolver {
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		if (CommandContext.available() && CommandContext.instance().getCommandName() != null) {
			if (viewName.startsWith("redirect:")) {
				String redirect = viewName.substring("redirect:".length());
				// TODO here is page id, i should resolve to uri
				CommandContext.instance().setRedirect(redirect);
				return new NullView();
			} else if (viewName.startsWith("popup:")) {
				String popup = viewName.substring("popup:".length());
				CommandContext.instance().setPopup(popup);
			}
			String commandName = CommandContext.instance().getCommandName();
			return new CommandView(commandName);
		}
		return null;
	}

}
