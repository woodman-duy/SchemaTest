package modules.sales.Opportunity;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFactory;
import org.skyve.util.test.SkyveFixture;
import org.skyve.util.test.SkyveFixture.FixtureType;

import modules.sales.domain.Opportunity;

@SkyveFactory(testAction = false)
public class OpportunityFactory {

	@SkyveFixture(types = FixtureType.crud)
	public OpportunityExtension crudInstance() {
		OpportunityExtension bean = new DataBuilder()
				.fixture(FixtureType.crud)
				.optional(true, true)
				.required(true, true)
				.factoryBuild(Opportunity.MODULE_NAME, Opportunity.DOCUMENT_NAME);
		bean.setDescription(null);
		bean.setSelectedTab(0);
		return bean;
	}
}
