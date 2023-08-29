package modules.customers.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractTransientBean;

/**
 * Account Map
 * 
 * @stereotype "transient"
 */
@XmlType
@XmlRootElement
public class AccountMap extends AbstractTransientBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "customers";

	/** @hidden */
	public static final String DOCUMENT_NAME = "AccountMap";

	@Override
	@XmlTransient
	public String getBizModule() {
		return AccountMap.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return AccountMap.DOCUMENT_NAME;
	}

	public static AccountMap newInstance() {
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
		return toString();

	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof AccountMap) && 
					this.getBizId().equals(((AccountMap) o).getBizId()));
	}
}
