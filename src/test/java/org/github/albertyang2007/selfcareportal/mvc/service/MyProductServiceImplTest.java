package org.github.albertyang2007.selfcareportal.mvc.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.github.albertyang2007.selfcareportal.mvc.service.impl.MyProductService;
import org.github.albertyang2007.selfcareportal.repository.entity.Item;
import org.github.albertyang2007.selfcareportal.repository.entity.MyOrder;
import org.github.albertyang2007.selfcareportal.repository.service.DataAccessService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:selfcarePortalDispatcher-servlet.xml" })
public class MyProductServiceImplTest {

	private List<MyOrder> expectedproduct, actorderlist;
	private String UserName = "Erica";
	private int start = 0;
	private int page = 1;
	private long expectordernum;
	private int actpagenum;

	@Autowired
	private MyProductService productservice;

	@Before
	public void before() {

	}

	@Test
	public void testgetpurchaselistsuc() {
		expectedproduct = builddata();

		page = 2;

		start = (page - 1) * 5;

		DataAccessService dataservice = EasyMock
				.createMock(DataAccessService.class);

		EasyMock.expect(dataservice.findOrdersbypage(UserName, start, 5))
				.andReturn(expectedproduct);

		// replay
		EasyMock.replay(dataservice);

		// inject the @autowired field mockMyProductService to
		// MyProductController
		ReflectionTestUtils.setField(productservice, "service", dataservice,
				DataAccessService.class);

		actorderlist = productservice.getpurchaselist(UserName, page, 5);

		assertThat(actorderlist, notNullValue());
		assertThat(actorderlist.get(0).getOrderId(), is(10001));
		EasyMock.verify(dataservice);
	}
	
	@Test
	public void testgetgetpagenumsuc() {
		expectordernum = 10l;

		DataAccessService dataservice = EasyMock
				.createMock(DataAccessService.class);

		EasyMock.expect(dataservice.findOrdersNum(UserName))
				.andReturn(expectordernum);

		// replay
		EasyMock.replay(dataservice);

		// inject the @autowired field mockMyProductService to
		// MyProductController
		ReflectionTestUtils.setField(productservice, "service", dataservice,
				DataAccessService.class);

		actpagenum = productservice.getpagenum(UserName, 5);

		assertThat(actpagenum, is(2));
		EasyMock.verify(dataservice);
	}

	private List<MyOrder> builddata() {

		List<MyOrder> myorderlist = new ArrayList<MyOrder>();

		MyOrder oneoder = new MyOrder();

		List<Item> productlist = new ArrayList<Item>();

		Item oneproduct = new Item();

		oneproduct.setDescription("iPhone5s");
		oneproduct.setPrice(5888.00);
		oneproduct.setProductId(1);
		oneproduct.setProductNo("1001");
		productlist.add(oneproduct);

		Item twoproduct = new Item();
		twoproduct.setDescription("iPhone4s");
		twoproduct.setPrice(4888.00);
		twoproduct.setProductId(2);
		twoproduct.setProductNo("1002");
		productlist.add(twoproduct);

		oneoder.setCreateDate(new Date());
		oneoder.setOrderId(10001);
		oneoder.setProducts(productlist);

		myorderlist.add(oneoder);

		return myorderlist;
	}
}
