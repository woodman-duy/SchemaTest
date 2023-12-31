package modules.sales.ProcessLifeCycle;

import java.util.HashMap;
import java.util.Map;

import org.skyve.CORE;
import org.skyve.EXT;
import org.skyve.domain.Bean;
import org.skyve.metadata.SortDirection;
import org.skyve.persistence.DocumentQuery;
import org.skyve.persistence.Persistence;
import org.skyve.util.Binder;
import org.skyve.util.Util;

import modules.customers.domain.Account;
import modules.customers.domain.ContactDetail;
import modules.sales.Invoice.InvoiceExtension;
import modules.sales.Opportunity.OpportunityExtension;
import modules.sales.Order.OrderExtension;
import modules.sales.Quote.QuoteExtension;
import modules.sales.domain.Invoice;
import modules.sales.domain.Lead;
import modules.sales.domain.Opportunity;
import modules.sales.domain.Order;
import modules.sales.domain.ProcessLifeCycle;
import modules.sales.domain.Quote;

public class ProcessLifeCycleExtension extends ProcessLifeCycle {

	private static final long serialVersionUID = -5362551133619242186L;
	
	@Override
	public OpportunityExtension getOpportunity() {
		if (getAccount() != null) {
			Persistence persistence = CORE.getPersistence();
			DocumentQuery query = persistence.newDocumentQuery(Opportunity.MODULE_NAME, Opportunity.DOCUMENT_NAME);
			query.getFilter().addEquals(Binder.createCompoundBinding(Opportunity.accountPropertyName, Bean.DOCUMENT_ID), this.getAccount().getBizId());
			query.addBoundOrdering(Opportunity.LOCK_NAME, SortDirection.descending);
			
			return query.beanResult();
		}
		else {
			return null;
		}
	}
	
	@Override
	public QuoteExtension getQuote() {
		if (getAccount() != null) {
			Persistence persistence = CORE.getPersistence();
			DocumentQuery query = persistence.newDocumentQuery(Quote.MODULE_NAME, Quote.DOCUMENT_NAME);
			query.getFilter().addEquals(Binder.createCompoundBinding(Quote.accountPropertyName, Bean.DOCUMENT_ID), this.getAccount().getBizId());				
			query.addBoundOrdering(Quote.LOCK_NAME, SortDirection.descending);
	
			return query.beanResult();
		}
		else {
			return null;
		}
	}
	
	@Override
	public OrderExtension getOrder() {
		if (getAccount() != null) {
			Persistence persistence = CORE.getPersistence();
			DocumentQuery query = persistence.newDocumentQuery(Order.MODULE_NAME, Order.DOCUMENT_NAME);
			query.getFilter().addEquals(Binder.createCompoundBinding(Order.accountPropertyName, Bean.DOCUMENT_ID), this.getAccount().getBizId());
			query.addBoundOrdering(Order.LOCK_NAME, SortDirection.descending);
			
			return query.beanResult();
		}
		else {
			return null;
		}
	}
	
	@Override
	public InvoiceExtension getInvoice() {
		if (getAccount() != null) {
			Persistence persistence = CORE.getPersistence();
			DocumentQuery query = persistence.newDocumentQuery(Invoice.MODULE_NAME, Invoice.DOCUMENT_NAME);
			query.getFilter().addEquals(Binder.createCompoundBinding(Invoice.accountPropertyName, Bean.DOCUMENT_ID), this.getAccount().getBizId());
			query.addBoundOrdering(Invoice.LOCK_NAME, SortDirection.descending);
			
			return query.beanResult();
		}
		else {
			return null;
		}
	}
	
	@Override
	public String getFlowbar() {

		String contactClass, leadClass, accountClass, opportunityClass, quoteClass, orderClass, invoiceClass = null;
		contactClass = leadClass = accountClass = opportunityClass = quoteClass = orderClass = invoiceClass = "notCurrent";
		
		String contactUrl = Util.getDocumentUrl(ContactDetail.MODULE_NAME, ContactDetail.DOCUMENT_NAME);
		String leadUrl = Util.getDocumentUrl(Lead.MODULE_NAME, Lead.DOCUMENT_NAME);
		String accountUrl = Util.getDocumentUrl(Account.MODULE_NAME, Account.DOCUMENT_NAME);
		String opportunityUrl = Util.getDocumentUrl(Opportunity.MODULE_NAME, Opportunity.DOCUMENT_NAME);
		String quoteUrl = Util.getDocumentUrl(Quote.MODULE_NAME, Quote.DOCUMENT_NAME);
		String orderUrl = Util.getDocumentUrl(Order.MODULE_NAME, Order.DOCUMENT_NAME);
		String invoiceUrl = Util.getDocumentUrl(Invoice.MODULE_NAME, Invoice.DOCUMENT_NAME);
		
		if (getContact() != null) {
			contactUrl = Util.getDocumentUrl(ContactDetail.MODULE_NAME, ContactDetail.DOCUMENT_NAME, getContact().getBizId());
			contactClass = "current";
		}
		if (getLead() != null) {
			leadUrl = Util.getDocumentUrl(Lead.MODULE_NAME, Lead.DOCUMENT_NAME, getLead().getBizId());
			leadClass = "current";
		}
		if (getAccount() != null) {
			accountUrl = Util.getDocumentUrl(Account.MODULE_NAME, Account.DOCUMENT_NAME, getAccount().getBizId());
			accountClass = "current";
			
			if (getOpportunity() != null) {
				opportunityUrl = Util.getDocumentUrl(Opportunity.MODULE_NAME, Opportunity.DOCUMENT_NAME, getOpportunity().getBizId());
				opportunityClass = "current";
			}
			if (getQuote() != null) {
				quoteUrl = Util.getDocumentUrl(Quote.MODULE_NAME, Quote.DOCUMENT_NAME, getQuote().getBizId());
				quoteClass = "current";
			}
			if (getOrder() != null) {
				orderUrl = Util.getDocumentUrl(Order.MODULE_NAME, Order.DOCUMENT_NAME, getOrder().getBizId());
				orderClass = "current";
			}
			if (getInvoice() != null) {
				invoiceUrl = Util.getDocumentUrl(Invoice.MODULE_NAME, Invoice.DOCUMENT_NAME, getInvoice().getBizId());
				invoiceClass = "current";
			}
		}
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("contactClass", contactClass);
		parameters.put("leadClass", leadClass);
		parameters.put("accountClass", accountClass);
		parameters.put("opportunityClass", opportunityClass);
		parameters.put("quoteClass", quoteClass);
		parameters.put("orderClass", orderClass);
		parameters.put("invoiceClass", invoiceClass);

		parameters.put("contactUrl", contactUrl);
		parameters.put("leadUrl", leadUrl);
		parameters.put("accountUrl", accountUrl);
		parameters.put("opportunityUrl", opportunityUrl);
		parameters.put("quoteUrl", quoteUrl);
		parameters.put("orderUrl", orderUrl);
		parameters.put("invoiceUrl", invoiceUrl);
		try {
			return EXT.getReporting().createFreemarkerBeanReport(this, "flowbar.html", parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
