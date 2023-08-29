package modules.customers.Interaction;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFactory;
import org.skyve.util.test.SkyveFixture;
import org.skyve.util.test.SkyveFixture.FixtureType;

import modules.admin.ModulesUtil;
import modules.customers.domain.Interaction;

@SkyveFactory(testAction = false)
public class InteractionFactory {
	@SkyveFixture(types = FixtureType.crud)
	public InteractionExtension crudInstance() {
		InteractionExtension bean = new DataBuilder()
				.fixture(FixtureType.crud)
				.optional(true, true)
				.required(true, true)
				.factoryBuild(Interaction.MODULE_NAME, Interaction.DOCUMENT_NAME);
		bean.setDescription(null);
		bean.setUser(ModulesUtil.currentAdminUser());
		return bean;
	}
}
