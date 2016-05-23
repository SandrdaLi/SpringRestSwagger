package com.spring.rest.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.rest.model.Issuer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Handles requests for Issuer.
 */
@Api(value = "Issuer", produces = "application/json")
@RestController
public class IssuerController {
	private static final Logger logger = LoggerFactory.getLogger(IssuerController.class);
	private Map<String, Issuer> issuers = new HashMap<String, Issuer>();

	public IssuerController() {
		// pre-initialize the list of issuers available ...

		issuers.put("ATEN", new Issuer("ATEN", "A10 Networks Inc", "corp", "USA"));
		issuers.put("AAPL", new Issuer("AAPL", "Apple Inc", "corp", "USA"));
		issuers.put("T", new Issuer("T", "AT&T", "corp", "USA"));
		issuers.put("CSCO", new Issuer("CSCO", "Cisco Systems, Inc.", "corp", "USA"));
		issuers.put("CTXS", new Issuer("CTXS", "Citrix Systems, Inc.", "corp", "USA"));
		issuers.put("GOOGL", new Issuer("GOOGL", "Google Inc", "corp", "USA"));
		issuers.put("IBM", new Issuer("IBM", "IBM", "corp", "USA"));
		issuers.put("JNPR", new Issuer("JNPR", "Juniper Networks, Inc.", "corp", "USA"));
		issuers.put("MSFT", new Issuer("MSFT", "Microsoft Corporation", "corp", "USA"));
		issuers.put("ORCL", new Issuer("ORCL", "Oracle Corporation", "corp", "USA"));
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	//@RequestMapping(value = "/", method = RequestMethod.GET)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		//return "status";
		return new ModelAndView("redirect:swagger-ui.html");
	}

	@RequestMapping(value = "/issuers", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "getAllIssuers", notes = "Get all issuers", code = 200, produces = "application/json")
	public Map<String, Issuer> getAllIssuers() {
		logger.info("Inside getAllIssuers() method...");

		return issuers;
	}

	@RequestMapping(value = "/issuer/{ticker}", method = RequestMethod.GET)
	@ApiOperation(value = "getIssuerByTicker", notes = "Get Issuer By Ticker", code = 200, produces = "application/json")
	public Issuer getIssuerByTicker(@PathVariable("ticker") String ticker) {
		Issuer myIssuer = issuers.get(ticker);

		if (myIssuer != null) {
			logger.info("Inside getIssuerByTicker, returned: " + myIssuer.toString());
		} else {
			logger.info("Inside getIssuerByTicker, ticker: " + ticker + ", NOT FOUND!");
		}
		return myIssuer;
	}

	@RequestMapping(value = "/issuer/delete/{ticker}", method = RequestMethod.DELETE)
	@ApiOperation(value = "deleteIssuerByTicker", notes = "Delete Issuer By Ticker", code = 200, produces = "application/json")
	public Issuer deleteIssuerByTicker(@PathVariable("ticker") String ticker) {
		Issuer myIssuer = issuers.remove(ticker);

		if (myIssuer != null) {
			logger.info("Inside deleteIssuerByTicker, deleted: " + myIssuer.toString());
		} else {
			logger.info("Inside deleteIssuerByTicker, ticker: " + ticker + ", NOT FOUND!");
		}
		return myIssuer;
	}

	@RequestMapping(value = "/issuer/create", method = RequestMethod.GET)
	public ModelAndView addIssuer() {

		return new ModelAndView("addIssuer", "command", new Issuer());
	}

	@RequestMapping(value = "/issuer/addIssuer", method = RequestMethod.POST)
	@ApiOperation(value = "addIssuer", notes = "Add Issuer", code = 200, produces = "application/json")
	public Issuer addIssuer(@ModelAttribute("issuer") Issuer issuer) {

		if (issuer != null) {
			logger.info("Inside addIssuer, adding: " + issuer.toString());
		} else {
			logger.info("Inside addIssuer...");
		}
		issuers.put(issuer.getTicker(), issuer);
		return issuer;
	}
}
