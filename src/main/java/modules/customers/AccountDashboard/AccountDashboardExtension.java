package modules.customers.AccountDashboard;

import java.util.stream.Collectors;

import org.skyve.CORE;
import org.skyve.EXT;
import org.skyve.domain.Bean;
import org.skyve.domain.types.DateTime;
import org.skyve.metadata.SortDirection;
import org.skyve.metadata.customer.Customer;
import org.skyve.metadata.model.document.Document;
import org.skyve.metadata.module.Module;
import org.skyve.persistence.DocumentQuery;
import org.skyve.persistence.Persistence;
import org.skyve.util.Binder;
import org.skyve.util.Binder.TargetMetaData;
import org.skyve.util.Util;

import modules.admin.ModulesUtil;
import modules.customers.Account.AccountExtension;
import modules.customers.Interaction.InteractionExtension;
import modules.customers.domain.Account;
import modules.customers.domain.AccountDashboard;
import modules.customers.domain.ContactDetail;
import modules.customers.domain.Interaction;
import modules.customers.domain.Interaction.Type;
import modules.sales.domain.Invoice;
import modules.sales.domain.Lead;
import modules.sales.domain.Opportunity;
import modules.sales.domain.Order;
import modules.sales.domain.Quote;

public class AccountDashboardExtension extends AccountDashboard {

	private static final long serialVersionUID = 2026179169670513211L;

	private static String formatBooleanHTML(final boolean value) {
		final String template = "<i style='color: %1$s;' class='fa %2$s'></i>";

		if (value == true) {
			return String.format(template, "#4bc0c0", "fa-check");
		}
		return String.format(template, "#ff6385", "fa-times");
	}

	public void setDates() {
//		if (getRecentOpportunity() != null && lastCreated(Opportunity.DOCUMENT_NAME) != null) {
//			setDateOpportunity(formatBooleanHTML(true));
//		}
//		else {
//			setDateOpportunity(formatBooleanHTML(false));
//		}
//		if (getRecentQuote() != null && lastCreated(Quote.DOCUMENT_NAME) != null) {
//			setDateQuote(formatBooleanHTML(true));
//		}
//		else {
//			setDateQuote(formatBooleanHTML(false));
//		}
//		if (getRecentOrder() != null && lastCreated(Order.DOCUMENT_NAME) != null) {
//			setDateOrder(formatBooleanHTML(true));
//		}
//		else {
//			setDateOrder(formatBooleanHTML(false));
//		}
//		if (getRecentInvoice() != null && lastCreated(Invoice.DOCUMENT_NAME) != null) {
//			setDateInvoice(formatBooleanHTML(true));
//		}
//		else {
			setDateInvoice(formatBooleanHTML(false));
//		}
	}
	
	public void setNumbers() {
		setNoOpportunity(numberDocuments(Opportunity.MODULE_NAME, Opportunity.DOCUMENT_NAME));
		setNoQuote(numberDocuments(Quote.MODULE_NAME, Quote.DOCUMENT_NAME));
		setNoOrder(numberDocuments(Order.MODULE_NAME, Order.DOCUMENT_NAME));
		setNoInvoice(numberDocuments(Invoice.MODULE_NAME, Invoice.DOCUMENT_NAME));
	}
	
	// returns number of documents for this account
	public int numberDocuments(String moduleName, String documentName) {
		Persistence persistence = CORE.getPersistence();
		DocumentQuery query = persistence.newDocumentQuery(moduleName, documentName);
		switch (documentName) {
		case "Opportunity":
			query.getFilter().addEquals(Binder.createCompoundBinding(Opportunity.accountPropertyName, Bean.DOCUMENT_ID), this.getAccount().getBizId());
			break;
		case "Quote":
			query.getFilter().addEquals(Binder.createCompoundBinding(Quote.accountPropertyName, Bean.DOCUMENT_ID), this.getAccount().getBizId());
			break;
		case "Order":
			query.getFilter().addEquals(Binder.createCompoundBinding(Order.accountPropertyName, Bean.DOCUMENT_ID), this.getAccount().getBizId());
			break;
		case "Invoice":
			query.getFilter().addEquals(Binder.createCompoundBinding(Invoice.accountPropertyName, Bean.DOCUMENT_ID), this.getAccount().getBizId());
			break;
		default:
			break;
		}
		return query.beanResults().size();
	}

	// returns the last interaction made for a document for this account
	public Interaction lastCreated(String documentName) {
		Persistence persistence = CORE.getPersistence();
		DocumentQuery query = persistence.newDocumentQuery(Interaction.MODULE_NAME, Interaction.DOCUMENT_NAME);
		query.getFilter().addIn(Interaction.DOCUMENT_ID, getAccount().getInteractions().stream()
				.map(i -> i.getBizId())
				.collect(Collectors.toList()));
		query.getFilter().addEquals(Interaction.typePropertyName, "Other");
		query.getFilter().addEquals(Interaction.titlePropertyName, "Created New " + documentName);
		query.addBoundOrdering(Interaction.interactionTimePropertyName, SortDirection.descending);
		return query.beanResult();
	}
	
	// returns the last auto update interaction
	public Interaction lastUpdated() {
		Persistence persistence = CORE.getPersistence();
		DocumentQuery query = persistence.newDocumentQuery(Interaction.MODULE_NAME, Interaction.DOCUMENT_NAME);
		query.getFilter().addIn(Interaction.DOCUMENT_ID, getAccount().getInteractions().stream()
				.map(i -> i.getBizId())
				.collect(Collectors.toList()));
		
		query.getFilter().addNotEquals(Interaction.typePropertyName, "Other");
		query.addBoundOrdering(Interaction.interactionTimePropertyName, SortDirection.descending);
		return query.beanResult();
	}
	
	// returns the days since an interaction was made
	public long daysSinceInteraction(Interaction interaction) {
		DateTime latest = interaction.getInteractionTime();
		DateTime current = new DateTime();
		return (current.getTime() - latest.getTime())/(24 * 60 * 60 * 1000);
	}
	
	// returns the most recently edited lead
//	public LeadExtension getRecentLead() {
//		Persistence persistence = CORE.getPersistence();
//		DocumentQuery query = persistence.newDocumentQuery(Lead.MODULE_NAME, Lead.DOCUMENT_NAME);
//		query.getFilter().addEquals(Binder.createCompoundBinding(Lead.contactDetailsPropertyName, Bean.DOCUMENT_ID), this.getAccount().getPrimaryContact().getBizId());
//		query.addBoundOrdering(Lead.LOCK_NAME, SortDirection.descending);
//		return query.beanResult();
//	}

	// returns the most recently edited account
	public AccountExtension getRecentAccount() {
		Persistence persistence = CORE.getPersistence();
		DocumentQuery query = persistence.newDocumentQuery(Account.MODULE_NAME, Account.DOCUMENT_NAME);
		query.getFilter().addEquals(Account.accountManagerPropertyName,
				ModulesUtil.currentAdminUser().getContact().getName());
		query.addBoundOrdering(Account.LOCK_NAME, SortDirection.descending);
		return query.beanResult();
	}
	
	// returns the most recently edited opportunity
//	public OpportunityExtension getRecentOpportunity() {
//		Persistence persistence = CORE.getPersistence();
//		DocumentQuery query = persistence.newDocumentQuery(Opportunity.MODULE_NAME, Opportunity.DOCUMENT_NAME);
//		query.getFilter().addEquals(Binder.createCompoundBinding(Opportunity.accountPropertyName, Bean.DOCUMENT_ID), this.getAccount().getBizId());
//		query.addBoundOrdering(Opportunity.LOCK_NAME, SortDirection.descending);
//		return query.beanResult();
//	}

	// returns the most recently edited quote
//	public QuoteExtension getRecentQuote() {
//		Persistence persistence = CORE.getPersistence();
//		DocumentQuery query = persistence.newDocumentQuery(Quote.MODULE_NAME, Quote.DOCUMENT_NAME);
//		query.getFilter().addEquals(Binder.createCompoundBinding(Quote.accountPropertyName, Bean.DOCUMENT_ID), this.getAccount().getBizId());
//		query.addBoundOrdering(Quote.LOCK_NAME, SortDirection.descending);
//		return query.beanResult();		
//	}
	
	// returns the most recently edited order
//	public OrderExtension getRecentOrder() {
//		Persistence persistence = CORE.getPersistence();
//		DocumentQuery query = persistence.newDocumentQuery(Order.MODULE_NAME, Order.DOCUMENT_NAME);
//		query.getFilter().addEquals(Binder.createCompoundBinding(Order.accountPropertyName, Bean.DOCUMENT_ID), this.getAccount().getBizId());
//		query.addBoundOrdering(Order.LOCK_NAME, SortDirection.descending);
//		return query.beanResult();	
//	}
	
	// returns the most recently edited invoice
//	public Invoice getRecentInvoice() {
//		Persistence persistence = CORE.getPersistence();
//		DocumentQuery query = persistence.newDocumentQuery(Invoice.MODULE_NAME, Invoice.DOCUMENT_NAME);
//		query.getFilter().addEquals(Binder.createCompoundBinding(Invoice.accountPropertyName, Bean.DOCUMENT_ID), this.getAccount().getBizId());
//		query.addBoundOrdering(Invoice.LOCK_NAME, SortDirection.descending);
//		return query.beanResult();
//	}
	
	public void createInteraction(final Type type, final String description, final String document) {
		InteractionExtension interaction = Interaction.newInstance();
		interaction.setTitle(String.format("New %s", type.toLocalisedDescription()));
		interaction.setType(type);
		interaction.setDescription(description);
		if (document != null) {
			interaction.setDocument(document);
		}
		getAccount().getInteractions().add(interaction);
	}
	
	/**
	 * Returns the most recent document instance edited for the document specified.
	 * 
	 * @param <T>
	 * @param moduleName
	 * @param documentName
	 * @param accountBinding
	 * @return
	 */
	public <T extends Bean> T getRecentDocument(String moduleName, String documentName, String accountBinding) {
		Customer c = CORE.getCustomer();
		Module m = c.getModule(moduleName);
		Document d = m.getDocument(c, documentName);
		TargetMetaData accountMetaData = Binder.getMetaDataForBinding(c, m, d, accountBinding);
		if (accountMetaData != null) {
			DocumentQuery q = CORE.getPersistence().newDocumentQuery(moduleName, documentName);
			if (accountBinding != null) {
				q.getFilter().addEquals(
						Binder.createCompoundBinding(accountMetaData.getAttribute().getName(), Bean.DOCUMENT_ID),
						getAccount().getBizId());
			}
			q.addBoundOrdering("bizLock", SortDirection.descending);
			if (q.beanResult() != null) {
				return q.beanResult();
			}
		}

		return null;
	}
	
	@Override
	public String getFlowbar() {
		if (getAccount() == null) {
			return null;
		}
		try {
			return EXT.getReporting().createFreemarkerBeanReport(this, "flowbar.html", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getActionTemplate() {
//		StringBuilder markup = new StringBuilder();
//		
//		if (lastUpdated() != null && daysSinceInteraction(lastUpdated()) > 14 ) {
//			markup.append(makeCommunicationTemplate(getAccount().getAccountName()));
//		}
//		if (getRecentOpportunity() == null) {
//			markup.append(makeNewTemplate(Opportunity.DOCUMENT_NAME));
//		}
//		if (getRecentQuote() == null) {
//			markup.append(makeNewTemplate(Quote.DOCUMENT_NAME));
//		}
//		if (getRecentOrder() == null) {
//			markup.append(makeNewTemplate(Order.DOCUMENT_NAME));
//		}
//		if (getRecentInvoice() == null) {
//			markup.append(makeNewTemplate(Invoice.DOCUMENT_NAME));
//		}
//		
//		if (getRecentOpportunity() != null) {
//			if (lastCreated(Opportunity.DOCUMENT_NAME) == null || daysSinceInteraction(lastCreated(Opportunity.DOCUMENT_NAME)) > 30) {
//				markup.append(makeReuseTemplate(Opportunity.DOCUMENT_NAME));
//			}
//			if (getRecentQuote() != null) {
//				if (lastCreated(Quote.DOCUMENT_NAME) == null || daysSinceInteraction(lastCreated(Quote.DOCUMENT_NAME)) > 30) {
//					markup.append(makeReuseTemplate(Quote.DOCUMENT_NAME));
//				}
//				if (getRecentOrder() != null) {
//					if (lastCreated(Order.DOCUMENT_NAME) == null || daysSinceInteraction(lastCreated(Order.DOCUMENT_NAME)) > 30) {
//						markup.append(makeReuseTemplate(Order.DOCUMENT_NAME));
//					}
//					if (getRecentInvoice() != null) {
//						if (lastCreated(Invoice.DOCUMENT_NAME) == null || daysSinceInteraction(lastCreated(Invoice.DOCUMENT_NAME)) > 30) {
//							markup.append(makeReuseTemplate(Invoice.DOCUMENT_NAME));
//						}
//						else {
//							markup.append(makeNoTemplate());
//						}
//					}
//				}
//			}
//		}		
//		return markup.toString();

		try {
			return EXT.getReporting().createFreemarkerBeanReport(this, "action.html", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// helper method to return the communication action markup for an account name
//	public String makeCommunicationTemplate(String accountName) {
//		StringBuilder markup = new StringBuilder();
//		markup.append(ACTION_STYLE);
//		markup.append("<div class='actionContainer'>");
//		markup.append("<div class='actionIcon'>");
//		markup.append("<span class='fa fa-plus'></span></div>");
//		markup.append("<div class='actionInfo'>");    
//		markup.append("<span class='actionTitle'>Make New Communication</span>");
//		markup.append("<span></br><p class='updateDescription'> You haven't recorded "
//				+ "an interaction with " + accountName + " for over two weeks, make a new communication "
//				+ "and record it as an interaction. </p>");    
//		markup.append("</div></div>");
//		return markup.toString();
//	}
	
	// helper method to return the new action markup for a document name
//	public String makeNewTemplate(String documentName) {
//		String vowels = "aeiou";
//		StringBuilder markup = new StringBuilder();
//		markup.append(ACTION_STYLE);
//		markup.append("<div class='actionContainer' onclick=\"location.href='"+ makeActionLink(documentName) + "';\">");
//		markup.append("<div class='actionIcon'>");
//		markup.append("<span class='" + makeIcon(documentName) + "'></span></div>");
//		markup.append("<div class='actionInfo'>");    
//		markup.append("<span class='actionTitle'>Make New "+ documentName +"</span>");
//		if (vowels.indexOf(Character.toLowerCase(documentName.charAt(0))) != -1 ) {
//			markup.append("<span></br><p class='actionDescription'> This account does not yet have an "+ 
//						documentName +", click here to make a new " + documentName + ".</p>");  
//		}
//		else {
//			markup.append("<span></br><p class='actionDescription'> This account does not yet have a "+
//						documentName +", click here to make a new " + documentName + ".</p>");  
//		}
//		markup.append("</div></div>");
//		return markup.toString();
//	}
	
	// helper method to return the reuse action markup for a document name
//	public String makeReuseTemplate(String documentName) {
//		StringBuilder markup = new StringBuilder();
//		markup.append(ACTION_STYLE);
//		markup.append("<div class='actionContainer' onclick=\"location.href='"+ makeActionLink(documentName) + "';\">");
//		markup.append("<div class='actionIcon'>");
//		markup.append("<span class='" + makeIcon(documentName) + "'></span></div>");
//		markup.append("<div class='actionInfo'>");    
//		markup.append("<span class='actionTitle'>Create A New "+ documentName +"</span>");
//		markup.append("<span></br><p class='actionDescription'>You have not created a new "+ documentName +""
//							+ " for "+ getAccount().getAccountName() +" recently, click here to create a new "+ documentName +""
//							+ " and save it to this account.</p>");  
//		markup.append("</div></div>");
//		return markup.toString();
//	}	
	
	//helper method to return no action markup
//	public String makeNoTemplate() {
//		StringBuilder markup = new StringBuilder();
//		markup.append(ACTION_STYLE);
//		markup.append("<div class='actionContainer'>");
//		markup.append("<div class='actionIcon'>");
//		markup.append("<span class='fa fa-info-circle'></span></div>");
//		markup.append("<div class='actionInfo'>");    
//		markup.append("<span class='actionTitle'>No Suggested Actions</span>");
//		markup.append("<span></br><p class='actionDescription'>There are no suggested actions for " + 
//							getAccount().getAccountName() + " at this time.</p>");  
//		markup.append("</div></div>");
//		return markup.toString();
//	}
	
	// helper method to return an icon for a document
	public String makeIcon(String documentName) {
		String icon = "fa fa-info-circle";
		switch (documentName) {
		case "ContactDetail":
			icon = "fa fa-users";
			break;
		case "Lead":
			icon = "fa fa-phone-square";
			break;
		case "Account":
			icon = "fa fa-book";
			break;
		case "Opportunity":
			icon = "fa fa-fax";
			break;
		case "Quote":
			icon = "fa fa-files-o";
			break;
		case "Order":
			icon = "fa fa-file-text";
			break;
		case "Invoice":
			icon = "fa fa-envelope";
			break;
		default:
			break;
		}
		return icon;
	}
	
	// helper function to return action link
	public String makeActionLink(String documentName) {
		String link = "";
		switch (documentName) {
		case "ContactDetail":
			link = Util.getDocumentUrl(ContactDetail.MODULE_NAME, ContactDetail.DOCUMENT_NAME);
			break;
		case "Lead": 
			link = Util.getDocumentUrl(Lead.MODULE_NAME, Lead.DOCUMENT_NAME);
			break;
		case "Account":
			link = Util.getDocumentUrl(Account.MODULE_NAME, Account.DOCUMENT_NAME);
			break;
		case "Opportunity":
			link = Util.getDocumentUrl(Opportunity.MODULE_NAME, Opportunity.DOCUMENT_NAME);
			break;
		case "Quote":
			link = Util.getDocumentUrl(Quote.MODULE_NAME, Quote.DOCUMENT_NAME);
			break;
		case "Order":
			link = Util.getDocumentUrl(Order.MODULE_NAME, Order.DOCUMENT_NAME);
			break;
		case "Invoice":
			link = Util.getDocumentUrl(Invoice.MODULE_NAME, Invoice.DOCUMENT_NAME);
			break;
		default:
			break;
		}
		return link;
	}

}