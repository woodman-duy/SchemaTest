package modules.sales.Order;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFactory;
import org.skyve.util.test.SkyveFixture;
import org.skyve.util.test.SkyveFixture.FixtureType;

import modules.sales.domain.Order;

@SkyveFactory(testAction = false)
public class OrderFactory {

	@SkyveFixture(types = FixtureType.crud)
	public OrderExtension crudInstance() {
		OrderExtension bean = new DataBuilder()
				.fixture(FixtureType.crud)
				.optional(true, true)
				.required(true, true)
				.factoryBuild(Order.MODULE_NAME, Order.DOCUMENT_NAME);
		bean.setDescription(null);
		bean.setSelectedTab(0);
		return bean;
	}
}
