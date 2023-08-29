package modules.customers.ContactDetail;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.DataMap;
import org.skyve.util.test.SkyveFactory;
import org.skyve.util.test.SkyveFixture;
import org.skyve.util.test.SkyveFixture.FixtureType;

import modules.customers.domain.ContactDetail;

@SkyveFactory(testAction = false, value = {
		@DataMap(attributeName = ContactDetail.mobileNumberPropertyName, fileName = "mobile.txt"),
		@DataMap(attributeName = ContactDetail.businessNumberPropertyName, fileName = "phone.txt"),
		@DataMap(attributeName = ContactDetail.faxPropertyName, fileName = "phone.txt"),
		@DataMap(attributeName = ContactDetail.line1PropertyName, fileName = "addressLine1.txt")
})
public class ContactDetailFactory {
	
	@SkyveFixture(types = FixtureType.crud)
	public ContactDetailExtension crudInstance() {
		ContactDetailExtension bean = new DataBuilder()
				.fixture(FixtureType.crud)
				.optional(true, false)
				.required(true, false)
				.factoryBuild(ContactDetail.MODULE_NAME, ContactDetail.DOCUMENT_NAME);
		bean.setSelectedTab(0);
		bean.setLine2(null);
		return bean;
	}
}
