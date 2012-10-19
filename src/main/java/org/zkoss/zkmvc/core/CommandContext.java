package org.zkoss.zkmvc.core;

public class CommandContext {

	private static final ThreadLocal<CommandContext> instance = new ThreadLocal<CommandContext>();

	public static CommandContext init() {
		if (instance.get() != null) {
			throw new RuntimeException("previous ctx is not clean or in nested ctx");
		}
		CommandContext ctx = new CommandContext();
		instance.set(ctx);
		return ctx;
	}

	public static boolean available() {
		return instance.get() != null;
	}

	public static CommandContext instance() {
		if (!available()) {
			throw new RuntimeException("ctx not found");
		}
		return instance.get();
	}

	public static void clean() {
		instance.set(null);
	}

	String commandName;
	String redirect;
	String popup;

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getPopup() {
		return popup;
	}

	public void setPopup(String popup) {
		this.popup = popup;
	}

}
