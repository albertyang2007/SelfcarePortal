package org.github.albertyang2007.selfcareportal.mvc.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.github.albertyang2007.selfcareportal.mvc.service.IProductInfoService;
import org.github.albertyang2007.selfcareportal.mvc.service.impl.RetrieveProductInfoServiceImpl;
import org.github.albertyang2007.selfcareportal.util.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:selfcarePortalDispatcher-servlet.xml" })
public class RetrieveProductInfoServiceImplTest {

	@Autowired
	private AnnotationMethodHandlerAdapter handlerAdapter;

	@Autowired
	private RetrieveProductInfoServiceImpl rpiService;

	@Autowired
	private IProductInfoService mockRESTService;

	private static Product[] expectedProducts = new Product[] {
			new Product("1001", "iphone4", 4444.0, true),
			new Product("1002", "iphone5", 5555.0, true),
			new Product("1003", "iphone6", 6666.0, true),
			new Product("1004", "ipad", 4444.0, true),
			new Product("1005", "ipad2", 5555.0, true),
			new Product("1006", "ipad3", 6666.0, true),
			new Product("1007", "ipad4", 6666.0, true) };
	private static Integer totalProduct = 300;

	@Before
	public void before() {
		mockRESTService = EasyMock.createMock(IProductInfoService.class);
	}

	@Test
	public void testGetTotalPage() {
		EasyMock.expect(mockRESTService.getProductCount()).andReturn(
				totalProduct);
		EasyMock.replay(mockRESTService);

		ReflectionTestUtils.setField(rpiService, "productInfoService",
				mockRESTService, IProductInfoService.class);

		Integer totalPage = rpiService.getTotalPage();
		assertThat(totalPage, is(30));
	}

	@Test
	public void testRetrieveProductPerPage() {
		EasyMock.expect(mockRESTService.retrieveProductsInfo(20, 29))
				.andReturn(expectedProducts);
		EasyMock.replay(mockRESTService);

		ReflectionTestUtils.setField(rpiService, "productInfoService",
				mockRESTService, IProductInfoService.class);

		Product[] products = rpiService.retrieveProductInfoPerPage(3);
		assertThat(products, notNullValue());
		assertThat(products, equalTo(expectedProducts));
	}

	@Test
	public void testRetrieveAllProduct() {
		EasyMock.expect(mockRESTService.retrieveAllProductInfo()).andReturn(
				expectedProducts);
		EasyMock.replay(mockRESTService);

		ReflectionTestUtils.setField(rpiService, "productInfoService",
				mockRESTService, IProductInfoService.class);

		Product[] products = rpiService.retrieveProductInfo();
		assertThat(products, notNullValue());
		assertThat(products, equalTo(expectedProducts));
	}

}
