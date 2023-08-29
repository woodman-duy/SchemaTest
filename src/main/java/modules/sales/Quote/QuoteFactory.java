package modules.sales.Quote;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.DataMap;
import org.skyve.util.test.SkyveFactory;
import org.skyve.util.test.SkyveFixture;
import org.skyve.util.test.SkyveFixture.FixtureType;

import modules.sales.domain.Quote;

@SkyveFactory(testAction = false, value = {
		@DataMap(attributeName = Quote.streetBillPropertyName, fileName = "addressLine1.txt"),
		@DataMap(attributeName = Quote.streetShipPropertyName, fileName = "addressLine1.txt"),
		@DataMap(attributeName = Quote.stateBillPropertyName, fileName = "state.txt"),
		@DataMap(attributeName = Quote.stateShipPropertyName, fileName = "state.txt"),
		@DataMap(attributeName = Quote.cityBillPropertyName, fileName = "suburb.txt"),
		@DataMap(attributeName = Quote.cityShipPropertyName, fileName = "suburb.txt"),
		@DataMap(attributeName = Quote.postCodeBillPropertyName, fileName = "postCode.txt"),
		@DataMap(attributeName = Quote.postCodeShipPropertyName, fileName = "postCode.txt")
})
public class QuoteFactory {
	@SkyveFixture(types = FixtureType.crud)
	public QuoteExtension crudInstance() {
		QuoteExtension bean = new DataBuilder()
				.fixture(FixtureType.crud)
				.optional(true, true)
				.required(true, true)
				.factoryBuild(Quote.MODULE_NAME, Quote.DOCUMENT_NAME);
		bean.setSelectedTab(0);
		bean.setName(bean.getAccount().getAccountName());
		bean.setStatusReason(null);
		bean.setDescription(null);
		bean.setPaymentTerms(null);
		bean.setFreightTerms(null);
		bean.setCountryBill("Australia");
		bean.setCountryShip("Australia");
		bean.setShippingMethod(null);
		
		return bean;
	}
}
