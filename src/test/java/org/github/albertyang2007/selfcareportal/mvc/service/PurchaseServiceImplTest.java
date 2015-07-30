package org.github.albertyang2007.selfcareportal.mvc.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.github.albertyang2007.selfcareportal.mvc.service.IProductInfoService;
import org.github.albertyang2007.selfcareportal.mvc.service.impl.PurchaseServiceImpl;
import org.github.albertyang2007.selfcareportal.repository.entity.MyOrder;
import org.github.albertyang2007.selfcareportal.repository.entity.User;
import org.github.albertyang2007.selfcareportal.repository.service.DataAccessService;
import org.github.albertyang2007.selfcareportal.util.Product;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:selfcarePortalDispatcher-servlet.xml"})
public class PurchaseServiceImplTest {
	@Autowired	
	private AnnotationMethodHandlerAdapter handlerAdapter;	
	
	@Autowired
	private PurchaseServiceImpl purchaseServiceImpl;
	
	// Product info
	private static List<Product> hardCodeProductList = null;
	private static Product[] hardCodeProducts = null;
	private static String[] hardCodeProductIDs = new String[] {"123456", "123457", "123458"};
	private static String[] hardCodeProductDescs = new String[] {"iPhone 2", "iPhone 3", "iPhone 4"};
	private static double[] hardCodePrices = new double[] {1999.0d, 2999.0d, 3999.0d};
	private static final int hardCodeOderID = 999;
	
	// Order info
	private static MyOrder hardCodeMyOrder = null;
	private static User hardCodeUser = null;
	private static String hardCodeUserName = "Erica";

	@BeforeClass
	public static void beforeClass() {
		hardCodeProductList = new ArrayList<Product>(hardCodeProductIDs.length);
		hardCodeProducts = new Product[hardCodeProductIDs.length];
						
		for (int i = 0; i < hardCodeProductIDs.length; i++) {
			hardCodeProducts[i] = new Product(hardCodeProductIDs[i],
		  									  hardCodeProductDescs[i],
		  									  hardCodePrices[i],
		  									  true);
						
			hardCodeProductList.add(hardCodeProducts[i]);
		}
		
		hardCodeUser = new User();
		hardCodeUser.setUserId(hardCodeUserName);
		
		hardCodeMyOrder = new MyOrder();		
		hardCodeMyOrder.setOrderId(hardCodeOderID);
	}
	
	@Test
	public void testMakeOrderSuccess() {		
		IMocksControl mockControl = EasyMock.createControl();
		
		// mock the DAO service
		DataAccessService mockDAO = mockControl.createMock(DataAccessService.class);
		EasyMock.expect(mockDAO.addOrder(hardCodeUserName, hardCodeProductList)).andReturn(hardCodeMyOrder);
		
		// mock the Product REST Service
		IProductInfoService mockProductInfoService = mockControl.createMock(IProductInfoService.class);
		EasyMock.expect(mockProductInfoService.retrieveAllProductInfo()).andReturn(hardCodeProducts);
			
		// replay
		mockControl.replay();

		// inject mockDAO to daoService
		ReflectionTestUtils.setField(purchaseServiceImpl, "daoService", 
				mockDAO, DataAccessService.class);
		
		// inject mockProductRESTService to productRESTService
		ReflectionTestUtils.setField(purchaseServiceImpl, "productInfoService", 
				mockProductInfoService, IProductInfoService.class);
								
		// do the test
		MyOrder myOrder = purchaseServiceImpl.makeOrder(hardCodeUserName, hardCodeProductIDs);
				
		// verify the result
		assertThat(myOrder.getOrderId(), is(hardCodeOderID));
		mockControl.verify();
	}
	
	@Test
	public void testMakeOrderFialureWithNullOrderReturn() {		
		IMocksControl mockControl = EasyMock.createControl();
		
		// mock the DAO service
		DataAccessService mockDAO = mockControl.createMock(DataAccessService.class);
		EasyMock.expect(mockDAO.addOrder(hardCodeUserName, hardCodeProductList)).andReturn(null);
		
		// mock the Product REST Service
		IProductInfoService mockProductInfoService = mockControl.createMock(IProductInfoService.class);
		EasyMock.expect(mockProductInfoService.retrieveAllProductInfo()).andReturn(hardCodeProducts);
			

		// replay
		mockControl.replay();

		// inject mockDAO to daoService
		ReflectionTestUtils.setField(purchaseServiceImpl, "daoService", 
				mockDAO, DataAccessService.class);
		
		// inject mockProductRESTService to productRESTService
		ReflectionTestUtils.setField(purchaseServiceImpl, "productInfoService", 
				mockProductInfoService, IProductInfoService.class);
								
		// do the test
		MyOrder myOrder = purchaseServiceImpl.makeOrder(hardCodeUserName, hardCodeProductIDs);
				
		// verify the result
		assertThat(myOrder, nullValue());
		mockControl.verify();
	}
	
	@Test
	public void testMakeOrderFialureWithNullProductReturnByREST() {					
		// mock the Product REST Service
		IProductInfoService mockProductInfoService = EasyMock.createMock(IProductInfoService.class);
		EasyMock.expect(mockProductInfoService.retrieveAllProductInfo()).andReturn(null);
			

		// replay
		EasyMock.replay(mockProductInfoService);
		
		// inject mockProductRESTService to productRESTService
		ReflectionTestUtils.setField(purchaseServiceImpl, "productInfoService", 
				mockProductInfoService, IProductInfoService.class);
								
		// do the test
		MyOrder myOrder = purchaseServiceImpl.makeOrder(hardCodeUserName, hardCodeProductIDs);
				
		// verify the result
		assertThat(myOrder, nullValue());
		EasyMock.verify(mockProductInfoService);
	}
}
