package modules.products.ProductInfo;

import org.skyve.CORE;
import org.skyve.metadata.model.document.Bizlet;

import modules.products.domain.ProductInfo;

public class ProductInfoBizlet extends Bizlet<ProductInfo> {

	@Override
	public ProductInfo newInstance(ProductInfo bean) throws Exception {
		// 
		bean.setProductId(CORE.getNumberGenerator().next("PRO", ProductInfo.MODULE_NAME, ProductInfo.DOCUMENT_NAME,
				ProductInfo.productIdPropertyName, 8));
		return super.newInstance(bean);
	}

}
