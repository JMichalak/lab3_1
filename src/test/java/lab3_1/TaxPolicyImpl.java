package lab3_1;

import pl.com.bottega.ecommerce.sales.domain.invoicing.Tax;
import pl.com.bottega.ecommerce.sales.domain.invoicing.TaxPolicy;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class TaxPolicyImpl implements TaxPolicy{
	public TaxPolicyImpl(){
		
	}
	
	@Override
	public Tax calculateTax(ProductType productType, Money net) {
		
		return new Tax(new Money(40), "opis");
	}
}
