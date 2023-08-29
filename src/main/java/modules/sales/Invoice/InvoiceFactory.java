package modules.sales.Invoice;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture;
import org.skyve.util.test.SkyveFixture.FixtureType;

import modules.sales.domain.Invoice;

public class InvoiceFactory {
	
	@SkyveFixture(types = FixtureType.crud)
	public InvoiceExtension crudInstance() {
		InvoiceExtension bean = new DataBuilder()
				.fixture(FixtureType.crud)
				.optional(true, false)
				.required(true, true)
				.factoryBuild(Invoice.MODULE_NAME, Invoice.DOCUMENT_NAME);
		bean.setSelectedTab(0);
		return bean;
	}
}
