package lab3_1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.invoicing.BookKeeper;
import pl.com.bottega.ecommerce.sales.domain.invoicing.Invoice;
import pl.com.bottega.ecommerce.sales.domain.invoicing.InvoiceFactory;
import pl.com.bottega.ecommerce.sales.domain.invoicing.InvoiceRequest;
import pl.com.bottega.ecommerce.sales.domain.invoicing.RequestItem;
import pl.com.bottega.ecommerce.sales.domain.invoicing.Tax;
import pl.com.bottega.ecommerce.sales.domain.invoicing.TaxPolicy;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class BookKeeperTest {

	@Test
	public void GivenOneItemInvoiceRequest_WhenIssuanceInovked_ThenOneItemInInvoice() {
		ClientData klient = new ClientData(new Id("1"),"ktos");
		InvoiceFactory invoiceF = new InvoiceFactory();
		
		InvoiceRequest ir = new InvoiceRequest(klient);
		TaxPolicy tp = new TaxPolicyImpl();
		ir.add(new RequestItem(new ProductData(new Id("1"),new Money(20), "cos", ProductType.DRUG, null),20,new Money(40)));
		BookKeeper bk = new BookKeeper(invoiceF);
		
		int result = bk.issuance(ir, tp).getItems().size();
		int expected = 1;
		assertEquals(result,expected);
		
	}
	
	
	
	
	
	
	@Test
	public void GivenTwoItemsInvoiceRequest_WhenIssuanceInovked_ThenTwoTaxCalculateInvoke() {
		InvoiceFactory invoiceF = new InvoiceFactory();
		BookKeeper bk = new BookKeeper(invoiceF);
		ClientData klient = new ClientData(new Id("1"),"ktos");
		InvoiceRequest ir = new InvoiceRequest(klient);
		ir.add(new RequestItem(new ProductData(new Id("1"),new Money(20), "cos", ProductType.DRUG, null),20,new Money(20)));
		ir.add(new RequestItem(new ProductData(new Id("2"),new Money(20), "cos", ProductType.DRUG, null),20,new Money(20)));
		TaxPolicy tp = Mockito.mock(TaxPolicy.class);
		Mockito.when(tp.calculateTax(ProductType.DRUG, new Money(20))).thenReturn(new Tax(new Money(20),"powod"));
		int result = bk.issuance(ir, tp).getItems().size();
		Mockito.verify(tp, Mockito.times(2)).calculateTax(ProductType.DRUG, new Money(20));
	}

}
