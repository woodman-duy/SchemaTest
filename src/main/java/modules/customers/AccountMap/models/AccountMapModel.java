package modules.customers.AccountMap.models;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.skyve.CORE;
import org.skyve.metadata.view.model.map.MapFeature;
import org.skyve.metadata.view.model.map.MapItem;
import org.skyve.metadata.view.model.map.MapModel;
import org.skyve.metadata.view.model.map.MapResult;
import org.skyve.persistence.DocumentQuery;
import org.skyve.persistence.Persistence;

import modules.admin.ModulesUtil;
import modules.customers.domain.Account;
import modules.customers.domain.AccountMap;

public class AccountMapModel extends MapModel<AccountMap> {

	private static final long serialVersionUID = -2579086300708483513L;

	@Override
	public MapResult getResult(Geometry mapBounds) throws Exception {
		Persistence persistence = CORE.getPersistence();
		DocumentQuery qMyAccounts = persistence.newDocumentQuery(Account.MODULE_NAME, Account.DOCUMENT_NAME);
		qMyAccounts.getFilter().addEquals(Account.accountManagerPropertyName, ModulesUtil.currentAdminUser().getUserName());
		List<Account> accounts = qMyAccounts.beanResults();
		List<MapItem> items = new ArrayList<>();
		
		for(Account account : accounts) {
			MapItem item = new MapItem();
			item.setBizId(account.getBizId());
			item.setModuleName(account.getBizModule());
			item.setDocumentName(account.getBizDocument());
			
			MapFeature location = new MapFeature();
			location.setGeometry(account.getLocation());
			item.getFeatures().add(location);
			
			
			StringBuilder markup = new StringBuilder();
			markup.append("<div><h2>").append(account.getAccountName()).append("</h2>");
			if (account.getWebsite() != null) {
				markup.append("<span><b>Website: </b><i><a href=//").append(account.getWebsite()).append(">").append(account.getWebsite()).append("</a></i></br></span>");
			}			
			markup.append("<span><b>Primary Contact: </b>").append(account.getPrimaryContact().getFirstName()).append(" ")
					.append(account.getPrimaryContact().getLastName()).append("</br></span>");
			if (account.getEmail() != null) {
				markup.append("<span><b>Email: </b>").append(account.getEmail()).append("</br></span>");
			}
			if (account.getPhone() != null) {
				markup.append("<span><b>Phone: </b>").append(account.getPhone()).append("</br></span>");
			}
			if (account.getRelationshipType() != null) {
				markup.append("<span><b>Relationship Type: </b>").append(account.getRelationshipType()).append("</br></span>");
			}
			
			markup.append("</span></div>");
			item.setInfoMarkup(markup.toString());
			
			
			
			items.add(item);
		}
		
		// TODO: info markup
		
		
		
		return new MapResult(items, null);
	}

}
