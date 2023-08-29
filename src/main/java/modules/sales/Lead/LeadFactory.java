package modules.sales.Lead;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.DataMap;
import org.skyve.util.test.SkyveFactory;
import org.skyve.util.test.SkyveFixture;
import org.skyve.util.test.SkyveFixture.FixtureType;

import modules.sales.domain.Lead;

@SkyveFactory(testAction = false, value = {
		@DataMap(attributeName = Lead.websitePropertyName, fileName = "url.txt"),
		@DataMap(attributeName = Lead.line1PropertyName, fileName = "addressLine1.txt")
})
public class LeadFactory {
	
	@SkyveFixture(types = FixtureType.crud)
	public LeadExtension crudInstance() {
		LeadExtension bean = new DataBuilder()
				.fixture(FixtureType.crud)
				.optional(true, true)
				.required(true, true)
				.factoryBuild(Lead.MODULE_NAME, Lead.DOCUMENT_NAME);
		bean.setSelectedTab(0);
		bean.setName(bean.getContactDetails().getLastName());
		bean.setLine2(null);
		return bean;
	}
}
