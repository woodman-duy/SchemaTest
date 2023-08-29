package modules.customers.Account;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.DataMap;
import org.skyve.util.test.SkyveFactory;
import org.skyve.util.test.SkyveFixture;
import org.skyve.util.test.SkyveFixture.FixtureType;

import modules.admin.ModulesUtil;
import modules.customers.domain.Account;

@SkyveFactory(testAction = false, value = {
		@DataMap(attributeName = Account.faxPropertyName, fileName = "phone.txt"),
		@DataMap(attributeName = Account.line1PropertyName, fileName = "addressLine1.txt"),
		@DataMap(attributeName = Account.websitePropertyName, fileName = "url.txt")
})
public class AccountFactory {
	
	@SkyveFixture(types = FixtureType.crud)
	public AccountExtension crudInstance() {
		AccountExtension bean = new DataBuilder()
				.fixture(FixtureType.crud)
				.name(Account.currentUserPropertyName, false)
				.optional(true, true)
				.required(true, true)
				.factoryBuild(Account.MODULE_NAME, Account.DOCUMENT_NAME);
		bean.setAccountManager(ModulesUtil.currentAdminUser().getUserName());
		bean.setSelectedTab(0);
		bean.setAccountName(bean.getPrimaryContact().getLastName());
		bean.setLine2(null);
		bean.setCurrentUser(ModulesUtil.currentAdminUser());
		return bean;
	}
}
