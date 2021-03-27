package com.example.demo.shell.commands;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.example.demo.accounts.WebAccountsService;
import com.example.demo.shell.config.ShellHelper;

@ShellComponent
public class AccountCommand {

	@Autowired
	ShellHelper shellHelper;

	@Resource(name="accountsService")
	WebAccountsService webAccountService;

	@ShellMethod("Retrieve account by number")
	public String getAccount(@ShellOption({ "-N", "--nnumber" }) String number) {
		String message = String.format("Hello %s!", number);

		webAccountService.findByNumber("123456789");

		shellHelper.print(message.concat(" (Default style message)"));
		shellHelper.printError(message.concat(" (Error style message)"));
		shellHelper.printWarning(message.concat(" (Warning style message)"));
		shellHelper.printInfo(message.concat(" (Info style message)"));
		shellHelper.printSuccess(message.concat(" (Success style message)"));

		String output = shellHelper.getSuccessMessage(message);
		return output.concat(" You are running spring shell cli-demo.");
	}

}
