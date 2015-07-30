package org.github.albertyang2007.selfcareportal.mvc.service;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import org.easymock.EasyMock;
import org.github.albertyang2007.selfcareportal.mvc.service.IProductInfoService;
import org.github.albertyang2007.selfcareportal.util.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:selfcarePortalDispatcher-servlet.xml" })
public class ProductRESTServiceTest {
	
	@Autowired
	IProductInfoService productRESTService;
	
	@Autowired
	private RestTemplate mockRestTemplate;
	
	private static String serverUrl = "http://localhost:8080/ProductCatalog/";
	private static String allProductsAction = "/api/products";
	private static String multipleProductsAction = "/api/products/";
	private static String productCountAction = "/api/products/count";
	private static Integer expectedProductCount = 1000;
	
	private static Product[] expectedProducts = new Product[] {
		new Product("1001", "iphone4", 4444.0, true),
		new Product("1002", "iphone5", 5555.0, true),
		new Product("1003", "iphone6", 6666.0, true),
		new Product("1004", "ipad", 4444.0, true),
		new Product("1005", "ipad2", 5555.0, true),
		new Product("1006", "ipad3", 6666.0, true),
		new Product("1007", "ipad4", 6666.0, true) };
	
	@Before
	public void before() {
		mockRestTemplate = EasyMock.createMock(RestTemplate.class);
	}
	
	@Test
	public void testGetProductCount(){
		EasyMock.expect(mockRestTemplate.getForObject(serverUrl+productCountAction,Integer.class)).andReturn(expectedProductCount);
		EasyMock.replay(mockRestTemplate);

		ReflectionTestUtils.setField(productRESTService, "restTemplate",
				mockRestTemplate, RestTemplate.class);
		Integer count = productRESTService.getProductCount();
		assertThat(count,is(expectedProductCount));
	}
	
	@Test
	public void testRetrieveAllProductInfo(){
		EasyMock.expect(mockRestTemplate.getForObject(serverUrl + allProductsAction,Product[].class)).andReturn(expectedProducts);
		EasyMock.replay(mockRestTemplate);

		ReflectionTestUtils.setField(productRESTService, "restTemplate",
				mockRestTemplate, RestTemplate.class);
		Product[] products = productRESTService.retrieveAllProductInfo();		 		 
		assertThat(products,equalTo(expectedProducts));
	}
	
	@Test
	public void testRetrieveProductsInfoByIndex(){
		EasyMock.expect(mockRestTemplate.getForObject(serverUrl + multipleProductsAction+"?begin=5&end=100",
				Product[].class)).andReturn(expectedProducts);
		EasyMock.replay(mockRestTemplate);

		ReflectionTestUtils.setField(productRESTService, "restTemplate",
				mockRestTemplate, RestTemplate.class);
		Product[] products = productRESTService.retrieveProductsInfo(5, 100);	 		 
		assertThat(products,equalTo(expectedProducts));
	}
	
	@Test
	public void testRetrieveProductsInfoByIDs(){
		String[] productID = {"999","888","777"};
		EasyMock.expect(mockRestTemplate.getForObject(serverUrl + multipleProductsAction+"?productNo=999,888,777",
				Product[].class)).andReturn(expectedProducts);
		EasyMock.replay(mockRestTemplate);

		ReflectionTestUtils.setField(productRESTService, "restTemplate",
				mockRestTemplate, RestTemplate.class);
		Product[] products = productRESTService.retrieveProductsInfo(productID);		 		 
		assertThat(products,equalTo(expectedProducts));
	}
}
