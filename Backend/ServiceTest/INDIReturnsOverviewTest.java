package nl.indi.returns;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import nl.indi.order_management.database.OmsDao;
import nl.indi.order_management.database.beans.RMA;
import nl.indi.order_management.database.beans.RMAItems;

class INDIReturnsOverviewTest {

    @RegisterExtension
    JUnit5Mockery mockery = new JUnit5Mockery() {
        {
            setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
        }
    };

    @Test
    void testGetReturnRequestsForCustomer() throws SQLException {
        List<RMAItems> testRMAitems = new ArrayList<>();
        List<RMA> testAPIReturnRequests = new ArrayList<>();
        String testCustomerNumber = "3311208-" + Math.random();

        RMAItems testRMAitem1 = RMAItems.builder()//
                .position(1)//
                .sku("SKU001")//
                .quantity(BigDecimal.TEN)//
                .articleName("Article 1")//
                .build();//
        testRMAitems.add(testRMAitem1);

        RMA testRMA1 = RMA.builder()//
                .rmaNum("RMA12345")//
                .orderNumber("ORD98765")//
                .emailAddress("testEmailAddress@indi.nl")//
                .comment("damaged")//
                .items(testRMAitems)//
                .build();//
        testAPIReturnRequests.add(testRMA1);

        OmsDao mockOmsDao = mockery.mock(OmsDao.class);
        mockery.checking(new Expectations() {
            {
                allowing(mockOmsDao).getAllReturnRequestsByCustomerNumber(testCustomerNumber);
                will(returnValue(testAPIReturnRequests));

            }
        });
        INDIReturnsOverview indiReturnsOverview = new INDIReturnsOverview(null);
        indiReturnsOverview.dao = mockOmsDao;

        List<RMA> testAPIReturnRequets = indiReturnsOverview.getAPIReturnRequestsForCustomer(testCustomerNumber);
        assertNotNull(testAPIReturnRequets);
    }

    @Test
    void testGetReturnRequestsForCustomerEmptyRequest() throws SQLException {
        String testCustomerNumber = "3311208-" + Math.random();

        OmsDao mockOmsDao = mockery.mock(OmsDao.class);
        mockery.checking(new Expectations() {
            {
                allowing(mockOmsDao).getAllReturnRequestsByCustomerNumber(testCustomerNumber);
                will(returnValue(null));

            }
        });
        INDIReturnsOverview indiReturnsOverview = new INDIReturnsOverview(null);
        indiReturnsOverview.dao = mockOmsDao;
        List<RMA> testAPIReturnRequets = indiReturnsOverview.getAPIReturnRequestsForCustomer(testCustomerNumber);

        assertNull(testAPIReturnRequets);
    }
}
