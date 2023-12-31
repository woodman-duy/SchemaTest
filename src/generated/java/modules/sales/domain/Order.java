package modules.sales.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import modules.customers.Account.AccountExtension;
import modules.products.domain.ProductInfo;
import modules.sales.Opportunity.OpportunityExtension;
import modules.sales.Order.OrderExtension;
import modules.sales.Quote.QuoteExtension;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.DateOnly;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.types.jaxb.DateOnlyMapper;

/**
 * Order
 * 
 * @navhas n product 0..1 ProductInfo
 * @navhas n quote 1 Quote
 * @navhas n opportunity 1 Opportunity
 * @navhas n account 1 Account
 * @navhas n status 1 Configuration
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public abstract class Order extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "sales";

	/** @hidden */
	public static final String DOCUMENT_NAME = "Order";

	/** @hidden */
	public static final String orderIdPropertyName = "orderId";

	/** @hidden */
	public static final String namePropertyName = "name";

	/** @hidden */
	public static final String descriptionPropertyName = "description";

	/** @hidden */
	public static final String statusPropertyName = "status";

	/** @hidden */
	public static final String requestedPropertyName = "requested";

	/** @hidden */
	public static final String fulfilledPropertyName = "fulfilled";

	/** @hidden */
	public static final String productPropertyName = "product";

	/** @hidden */
	public static final String totalPropertyName = "total";

	/** @hidden */
	public static final String accountPropertyName = "account";

	/** @hidden */
	public static final String opportunityPropertyName = "opportunity";

	/** @hidden */
	public static final String quotePropertyName = "quote";

	/** @hidden */
	public static final String selectedTabPropertyName = "selectedTab";

	/**
	 * Order ID
	 * <br/>
	 * The ID for this order
	 **/
	private String orderId;

	/**
	 * Order Name
	 * <br/>
	 * The name of the order
	 **/
	private String name;

	/**
	 * Description
	 * <br/>
	 * A description of the order
	 **/
	private String description;

	/**
	 * Order Status
	 * <br/>
	 * The current status of this order
	 **/
	private Configuration status = null;

	/**
	 * Requested Delivery Date
	 * <br/>
	 * The requested date of delivery for the order
	 **/
	private DateOnly requested;

	/**
	 * Fulfillment Date
	 * <br/>
	 * The date the order is fulfilled
	 **/
	private DateOnly fulfilled;

	/**
	 * Product
	 * <br/>
	 * The product of the order
	 **/
	private ProductInfo product = null;

	/**
	 * Provisional Total
	 * <br/>
	 * The provisional total for this order
	 **/
	private Long total;

	/**
	 * Account
	 * <br/>
	 * The account for the order
	 **/
	private AccountExtension account = null;

	/**
	 * Opportunity
	 * <br/>
	 * The opportunity for the order
	 **/
	private OpportunityExtension opportunity = null;

	/**
	 * Quote
	 * <br/>
	 * The quote for the order
	 **/
	private QuoteExtension quote = null;

	/**
	 * Selected Tab
	 **/
	private Integer selectedTab;

	@Override
	@XmlTransient
	public String getBizModule() {
		return Order.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return Order.DOCUMENT_NAME;
	}

	public static OrderExtension newInstance() {
		try {
			return CORE.getUser().getCustomer().getModule(MODULE_NAME).getDocument(CORE.getUser().getCustomer(), DOCUMENT_NAME).newInstance(CORE.getUser());
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new DomainException(e);
		}
	}

	@Override
	@XmlTransient
	public String getBizKey() {
		try {
			return org.skyve.util.Binder.formatMessage("{orderId}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Order) && 
					this.getBizId().equals(((Order) o).getBizId()));
	}

	/**
	 * {@link #orderId} accessor.
	 * @return	The value.
	 **/
	public String getOrderId() {
		return orderId;
	}

	/**
	 * {@link #orderId} mutator.
	 * @param orderId	The new value.
	 **/
	@XmlElement
	public void setOrderId(String orderId) {
		preset(orderIdPropertyName, orderId);
		this.orderId = orderId;
	}

	/**
	 * {@link #name} accessor.
	 * @return	The value.
	 **/
	public String getName() {
		return name;
	}

	/**
	 * {@link #name} mutator.
	 * @param name	The new value.
	 **/
	@XmlElement
	public void setName(String name) {
		preset(namePropertyName, name);
		this.name = name;
	}

	/**
	 * {@link #description} accessor.
	 * @return	The value.
	 **/
	public String getDescription() {
		return description;
	}

	/**
	 * {@link #description} mutator.
	 * @param description	The new value.
	 **/
	@XmlElement
	public void setDescription(String description) {
		preset(descriptionPropertyName, description);
		this.description = description;
	}

	/**
	 * {@link #status} accessor.
	 * @return	The value.
	 **/
	public Configuration getStatus() {
		return status;
	}

	/**
	 * {@link #status} mutator.
	 * @param status	The new value.
	 **/
	@XmlElement
	public void setStatus(Configuration status) {
		if (this.status != status) {
			preset(statusPropertyName, status);
			this.status = status;
		}
	}

	/**
	 * {@link #requested} accessor.
	 * @return	The value.
	 **/
	public DateOnly getRequested() {
		return requested;
	}

	/**
	 * {@link #requested} mutator.
	 * @param requested	The new value.
	 **/
	@XmlElement
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(DateOnlyMapper.class)
	public void setRequested(DateOnly requested) {
		preset(requestedPropertyName, requested);
		this.requested = requested;
	}

	/**
	 * {@link #fulfilled} accessor.
	 * @return	The value.
	 **/
	public DateOnly getFulfilled() {
		return fulfilled;
	}

	/**
	 * {@link #fulfilled} mutator.
	 * @param fulfilled	The new value.
	 **/
	@XmlElement
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(DateOnlyMapper.class)
	public void setFulfilled(DateOnly fulfilled) {
		preset(fulfilledPropertyName, fulfilled);
		this.fulfilled = fulfilled;
	}

	/**
	 * {@link #product} accessor.
	 * @return	The value.
	 **/
	public ProductInfo getProduct() {
		return product;
	}

	/**
	 * {@link #product} mutator.
	 * @param product	The new value.
	 **/
	@XmlElement
	public void setProduct(ProductInfo product) {
		if (this.product != product) {
			preset(productPropertyName, product);
			this.product = product;
		}
	}

	/**
	 * {@link #total} accessor.
	 * @return	The value.
	 **/
	public Long getTotal() {
		return total;
	}

	/**
	 * {@link #total} mutator.
	 * @param total	The new value.
	 **/
	@XmlElement
	public void setTotal(Long total) {
		preset(totalPropertyName, total);
		this.total = total;
	}

	/**
	 * {@link #account} accessor.
	 * @return	The value.
	 **/
	public AccountExtension getAccount() {
		return account;
	}

	/**
	 * {@link #account} mutator.
	 * @param account	The new value.
	 **/
	@XmlElement
	public void setAccount(AccountExtension account) {
		if (this.account != account) {
			preset(accountPropertyName, account);
			this.account = account;
		}
	}

	/**
	 * {@link #opportunity} accessor.
	 * @return	The value.
	 **/
	public OpportunityExtension getOpportunity() {
		return opportunity;
	}

	/**
	 * {@link #opportunity} mutator.
	 * @param opportunity	The new value.
	 **/
	@XmlElement
	public void setOpportunity(OpportunityExtension opportunity) {
		if (this.opportunity != opportunity) {
			preset(opportunityPropertyName, opportunity);
			this.opportunity = opportunity;
		}
	}

	/**
	 * {@link #quote} accessor.
	 * @return	The value.
	 **/
	public QuoteExtension getQuote() {
		return quote;
	}

	/**
	 * {@link #quote} mutator.
	 * @param quote	The new value.
	 **/
	@XmlElement
	public void setQuote(QuoteExtension quote) {
		if (this.quote != quote) {
			preset(quotePropertyName, quote);
			this.quote = quote;
		}
	}

	/**
	 * {@link #selectedTab} accessor.
	 * @return	The value.
	 **/
	public Integer getSelectedTab() {
		return selectedTab;
	}

	/**
	 * {@link #selectedTab} mutator.
	 * @param selectedTab	The new value.
	 **/
	@XmlElement
	public void setSelectedTab(Integer selectedTab) {
		this.selectedTab = selectedTab;
	}

	/**
	 * opportunitySelected
	 *
	 * @return The condition
	 */
	@XmlTransient
	public boolean isOpportunitySelected() {
		return (opportunity != null);
	}

	/**
	 * {@link #isOpportunitySelected} negation.
	 *
	 * @return The negated condition
	 */
	public boolean isNotOpportunitySelected() {
		return (! isOpportunitySelected());
	}

	/**
	 * quoteSelected
	 *
	 * @return The condition
	 */
	@XmlTransient
	public boolean isQuoteSelected() {
		return (quote != null);
	}

	/**
	 * {@link #isQuoteSelected} negation.
	 *
	 * @return The negated condition
	 */
	public boolean isNotQuoteSelected() {
		return (! isQuoteSelected());
	}

	/**
	 * uploadSelected
	 *
	 * @return The condition
	 */
	@XmlTransient
	public boolean isUploadSelected() {
		return (getAccount() != null && getAccount().getInteractionType() != null && getAccount().getInteractionType().name() == "upload");
	}

	/**
	 * {@link #isUploadSelected} negation.
	 *
	 * @return The negated condition
	 */
	public boolean isNotUploadSelected() {
		return (! isUploadSelected());
	}
}
