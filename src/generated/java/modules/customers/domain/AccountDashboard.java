package modules.customers.domain;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import modules.customers.Account.AccountExtension;
import modules.customers.AccountDashboard.AccountDashboardExtension;
import modules.customers.Interaction.InteractionExtension;
import org.locationtech.jts.geom.Geometry;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractTransientBean;
import org.skyve.impl.domain.ChangeTrackingArrayList;
import org.skyve.impl.domain.types.jaxb.GeometryMapper;

/**
 * Account Dashboard
 * 
 * @navhas n account 0..1 Account
 * @navhas n interactions 0..n Interaction
 * @stereotype "transient"
 */
@XmlType
@XmlRootElement
public abstract class AccountDashboard extends AbstractTransientBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "customers";

	/** @hidden */
	public static final String DOCUMENT_NAME = "AccountDashboard";

	/** @hidden */
	public static final String accountPropertyName = "account";

	/** @hidden */
	public static final String interactionsPropertyName = "interactions";

	/** @hidden */
	public static final String noOpportunityPropertyName = "noOpportunity";

	/** @hidden */
	public static final String noQuotePropertyName = "noQuote";

	/** @hidden */
	public static final String noOrderPropertyName = "noOrder";

	/** @hidden */
	public static final String noInvoicePropertyName = "noInvoice";

	/** @hidden */
	public static final String dateOpportunityPropertyName = "dateOpportunity";

	/** @hidden */
	public static final String dateQuotePropertyName = "dateQuote";

	/** @hidden */
	public static final String dateOrderPropertyName = "dateOrder";

	/** @hidden */
	public static final String dateInvoicePropertyName = "dateInvoice";

	/** @hidden */
	public static final String flowbarPropertyName = "flowbar";

	/** @hidden */
	public static final String actionTemplatePropertyName = "actionTemplate";

	/** @hidden */
	public static final String accountLocationPropertyName = "accountLocation";

	/**
	 * Account
	 **/
	private AccountExtension account = null;

	/**
	 * Interactions
	 **/
	private List<InteractionExtension> interactions = new ChangeTrackingArrayList<>("interactions", this);

	/**
	 * Number of Opportunities
	 **/
	private transient Integer noOpportunity = Integer.valueOf(0);

	/**
	 * Number of Quotes
	 **/
	private transient Integer noQuote = Integer.valueOf(0);

	/**
	 * Number of Orders
	 **/
	private transient Integer noOrder = Integer.valueOf(0);

	/**
	 * Number of Invoices
	 **/
	private transient Integer noInvoice = Integer.valueOf(0);

	/**
	 * Date Opportunity Updated
	 **/
	private String dateOpportunity;

	/**
	 * Date Quote Updated
	 **/
	private String dateQuote;

	/**
	 * Date Order Updated
	 **/
	private String dateOrder;

	/**
	 * Date Invoice Updated
	 **/
	private String dateInvoice;

	/**
	 * Flowbar
	 **/
	private String flowbar;

	/**
	 * Suggested Actions
	 **/
	private String actionTemplate;

	/**
	 * Account Location
	 **/
	private Geometry accountLocation;

	@Override
	@XmlTransient
	public String getBizModule() {
		return AccountDashboard.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return AccountDashboard.DOCUMENT_NAME;
	}

	public static AccountDashboardExtension newInstance() {
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
			return org.skyve.util.Binder.formatMessage("{account.accountName}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof AccountDashboard) && 
					this.getBizId().equals(((AccountDashboard) o).getBizId()));
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
	 * {@link #interactions} accessor.
	 * @return	The value.
	 **/
	@XmlElement
	public List<InteractionExtension> getInteractions() {
		return interactions;
	}

	/**
	 * {@link #interactions} accessor.
	 * @param bizId	The bizId of the element in the list.
	 * @return	The value of the element in the list.
	 **/
	public InteractionExtension getInteractionsElementById(String bizId) {
		return getElementById(interactions, bizId);
	}

	/**
	 * {@link #interactions} mutator.
	 * @param bizId	The bizId of the element in the list.
	 * @param element	The new value of the element in the list.
	 **/
	public void setInteractionsElementById(String bizId, InteractionExtension element) {
		setElementById(interactions, element);
	}

	/**
	 * {@link #interactions} add.
	 * @param element	The element to add.
	 **/
	public boolean addInteractionsElement(InteractionExtension element) {
		return interactions.add(element);
	}

	/**
	 * {@link #interactions} add.
	 * @param index	The index in the list to add the element to.
	 * @param element	The element to add.
	 **/
	public void addInteractionsElement(int index, InteractionExtension element) {
		interactions.add(index, element);
	}

	/**
	 * {@link #interactions} remove.
	 * @param element	The element to remove.
	 **/
	public boolean removeInteractionsElement(InteractionExtension element) {
		return interactions.remove(element);
	}

	/**
	 * {@link #interactions} remove.
	 * @param index	The index in the list to remove the element from.
	 **/
	public InteractionExtension removeInteractionsElement(int index) {
		return interactions.remove(index);
	}

	/**
	 * {@link #noOpportunity} accessor.
	 * @return	The value.
	 **/
	public Integer getNoOpportunity() {
		return noOpportunity;
	}

	/**
	 * {@link #noOpportunity} mutator.
	 * @param noOpportunity	The new value.
	 **/
	@XmlElement
	public void setNoOpportunity(Integer noOpportunity) {
		preset(noOpportunityPropertyName, noOpportunity);
		this.noOpportunity = noOpportunity;
	}

	/**
	 * {@link #noQuote} accessor.
	 * @return	The value.
	 **/
	public Integer getNoQuote() {
		return noQuote;
	}

	/**
	 * {@link #noQuote} mutator.
	 * @param noQuote	The new value.
	 **/
	@XmlElement
	public void setNoQuote(Integer noQuote) {
		preset(noQuotePropertyName, noQuote);
		this.noQuote = noQuote;
	}

	/**
	 * {@link #noOrder} accessor.
	 * @return	The value.
	 **/
	public Integer getNoOrder() {
		return noOrder;
	}

	/**
	 * {@link #noOrder} mutator.
	 * @param noOrder	The new value.
	 **/
	@XmlElement
	public void setNoOrder(Integer noOrder) {
		preset(noOrderPropertyName, noOrder);
		this.noOrder = noOrder;
	}

	/**
	 * {@link #noInvoice} accessor.
	 * @return	The value.
	 **/
	public Integer getNoInvoice() {
		return noInvoice;
	}

	/**
	 * {@link #noInvoice} mutator.
	 * @param noInvoice	The new value.
	 **/
	@XmlElement
	public void setNoInvoice(Integer noInvoice) {
		preset(noInvoicePropertyName, noInvoice);
		this.noInvoice = noInvoice;
	}

	/**
	 * {@link #dateOpportunity} accessor.
	 * @return	The value.
	 **/
	public String getDateOpportunity() {
		return dateOpportunity;
	}

	/**
	 * {@link #dateOpportunity} mutator.
	 * @param dateOpportunity	The new value.
	 **/
	@XmlElement
	public void setDateOpportunity(String dateOpportunity) {
		preset(dateOpportunityPropertyName, dateOpportunity);
		this.dateOpportunity = dateOpportunity;
	}

	/**
	 * {@link #dateQuote} accessor.
	 * @return	The value.
	 **/
	public String getDateQuote() {
		return dateQuote;
	}

	/**
	 * {@link #dateQuote} mutator.
	 * @param dateQuote	The new value.
	 **/
	@XmlElement
	public void setDateQuote(String dateQuote) {
		preset(dateQuotePropertyName, dateQuote);
		this.dateQuote = dateQuote;
	}

	/**
	 * {@link #dateOrder} accessor.
	 * @return	The value.
	 **/
	public String getDateOrder() {
		return dateOrder;
	}

	/**
	 * {@link #dateOrder} mutator.
	 * @param dateOrder	The new value.
	 **/
	@XmlElement
	public void setDateOrder(String dateOrder) {
		preset(dateOrderPropertyName, dateOrder);
		this.dateOrder = dateOrder;
	}

	/**
	 * {@link #dateInvoice} accessor.
	 * @return	The value.
	 **/
	public String getDateInvoice() {
		return dateInvoice;
	}

	/**
	 * {@link #dateInvoice} mutator.
	 * @param dateInvoice	The new value.
	 **/
	@XmlElement
	public void setDateInvoice(String dateInvoice) {
		preset(dateInvoicePropertyName, dateInvoice);
		this.dateInvoice = dateInvoice;
	}

	/**
	 * {@link #flowbar} accessor.
	 * @return	The value.
	 **/
	public String getFlowbar() {
		return flowbar;
	}

	/**
	 * {@link #flowbar} mutator.
	 * @param flowbar	The new value.
	 **/
	@XmlElement
	public void setFlowbar(String flowbar) {
		this.flowbar = flowbar;
	}

	/**
	 * {@link #actionTemplate} accessor.
	 * @return	The value.
	 **/
	public String getActionTemplate() {
		return actionTemplate;
	}

	/**
	 * {@link #actionTemplate} mutator.
	 * @param actionTemplate	The new value.
	 **/
	@XmlElement
	public void setActionTemplate(String actionTemplate) {
		this.actionTemplate = actionTemplate;
	}

	/**
	 * {@link #accountLocation} accessor.
	 * @return	The value.
	 **/
	public Geometry getAccountLocation() {
		return accountLocation;
	}

	/**
	 * {@link #accountLocation} mutator.
	 * @param accountLocation	The new value.
	 **/
	@XmlElement
	@XmlJavaTypeAdapter(GeometryMapper.class)
	public void setAccountLocation(Geometry accountLocation) {
		this.accountLocation = accountLocation;
	}

	/**
	 * accountSelected
	 *
	 * @return The condition
	 */
	@XmlTransient
	public boolean isAccountSelected() {
		return (account != null);
	}

	/**
	 * {@link #isAccountSelected} negation.
	 *
	 * @return The negated condition
	 */
	public boolean isNotAccountSelected() {
		return (! isAccountSelected());
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
