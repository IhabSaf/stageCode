package nl.indi.returns.service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import nl.indi.icm.service.Customer;
import nl.indi.icm.service.ICMService;
import nl.indi.order_management.database.beans.RMA;
import nl.indi.order_management.database.beans.RMAItems;
import nl.indi.return_service.overview.beans.APIRMAItems;
import nl.indi.return_service.overview.beans.APIReturnRequest;
import nl.indi.returns.INDIReturnsOverview;

@QuarkusTest
@Tag("integration")
@TestProfile(QuarkusTestProfileNoStartupEvents.class)
class ReturnsOverviewServiceTest {
    @RegisterExtension
    JUnit5Mockery mockery = new JUnit5Mockery() {
        {
            setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
            setThreadingPolicy(new Synchroniser());
        }
    };

    @Test
    @SuppressWarnings({ "unchecked", "boxing" })
    void testGetReturnRequest() throws Exception {
        String testCustomerNumber = "3311208-" + Math.random();

        RMAItems testRMAitem1 = RMAItems.builder()//
                .position(1)//
                .sku("SKU001")//
                .quantity(BigDecimal.TEN)//
                .articleName("Article 1")//
                .imageURL("https://assets.indi.nl/images/2XS/v6e.jpg")//
                .build();//
        List<RMAItems> testRMAitems = new ArrayList<>();
        testRMAitems.add(testRMAitem1);

        RMA testRMA1 = RMA.builder()//
                .rmaNum("RMA12345")//
                .orderNumber("ORD98765")//
                .emailAddress("testEmailAddress@indi.nl")//
                .comment("damaged")//
                .returnRequestDate("2024-04-09 09:14:31.675")//
                .items(testRMAitems)//
                .build();//
        List<RMA> testAPIReturnRequests = new ArrayList<>();
        testAPIReturnRequests.add(testRMA1);

        APIRMAItems apiRMAItems = new APIRMAItems(//
                1, //
                "SKU001", //
                BigDecimal.TEN, //
                "Article 1", //
                "https://assets.indi.nl/images/2XS/v6e.jpg"//
        );
        List<APIRMAItems> testListOfapiRMAItems = new ArrayList<>();
        testListOfapiRMAItems.add(apiRMAItems);

        APIReturnRequest apiReturnRequest = new APIReturnRequest(//
                "RMA12345", //
                "initialized", //
                "ORD98765", //
                "testEmailAddress@indi.nl", //
                "damaged", //
                "2024-04-09 09:14:31.675", //
                testListOfapiRMAItems);
        List<APIReturnRequest> testListOfAPIReturnRequests = new ArrayList<>();
        testListOfAPIReturnRequests.add(apiReturnRequest);

        ICMService mockICMService = mockery.mock(ICMService.class);
        INDIReturnsOverview mockINDIReturnsOverview = mockery.mock(INDIReturnsOverview.class);

        mockery.checking(new Expectations() {
            {
                oneOf(mockICMService).getCustomer(with(any(Map.class)));
                Customer testCustomer = new Customer(null, testCustomerNumber, null, false, null);
                will(returnValue(testCustomer));

                oneOf(mockINDIReturnsOverview).getAPIReturnRequestsForCustomer(testCustomerNumber);
                will(returnValue(testAPIReturnRequests));
            }
        });
        QuarkusMock.installMockForType(mockICMService, ICMService.class);
        QuarkusMock.installMockForType(mockINDIReturnsOverview, INDIReturnsOverview.class);

        given() //
                .when() //
                .get("/returns") //
                .then() //
                .log().all() //
                .statusCode(HttpStatus.SC_OK) //
                .body("apiReturnRequests[0].rmaNum", is(apiReturnRequest.getRmaNum())) //
                .and()//
                .body("apiReturnRequests[0].status", is(apiReturnRequest.getStatus()))//
                .and() //
                .body("apiReturnRequests[0].orderNumber", is(apiReturnRequest.getOrderNumber())) //
                .and() //
                .body("apiReturnRequests[0].emailAddress", is(apiReturnRequest.getEmailAddress()))//
                .and() //
                .body("apiReturnRequests[0].comment", is(apiReturnRequest.getComment()))//
                .and() //
                .body("apiReturnRequests[0].returnRequestDate", is(apiReturnRequest.getReturnRequestDate()))//
                .and() //
                .body("apiReturnRequests[0].rmaItems[0].position",
                        is(apiReturnRequest.getRmaItems().get(0).getPosition()))//
                .and() //
                .body("apiReturnRequests[0].rmaItems[0].sku", //
                        is(apiReturnRequest.getRmaItems().get(0).getSku()))//
                .and() //
                .body("apiReturnRequests[0].rmaItems[0].quantity.toString()",
                        is(apiReturnRequest.getRmaItems().get(0).getQuantity().toString()))//
                .and() //
                .body("apiReturnRequests[0].rmaItems[0].articleName",
                        is(apiReturnRequest.getRmaItems().get(0).getArticleName()))//
                .and() //
                .body("apiReturnRequests[0].rmaItems[0].imageURL",
                        is(apiReturnRequest.getRmaItems().get(0).getImageURL()))//
        ;
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGetReturnRequest_SQLException_Yield500() throws Exception {

        ICMService mockICMService = mockery.mock(ICMService.class);
        INDIReturnsOverview mockINDIReturnsOverview = mockery.mock(INDIReturnsOverview.class);
        String testCustomerNumber = "3311208-" + Math.random();

        mockery.checking(new Expectations() {
            {
                oneOf(mockICMService).getCustomer(with(any(Map.class)));
                Customer testCustomer = new Customer(null, testCustomerNumber, null, false, null);
                will(returnValue(testCustomer));

                oneOf(mockINDIReturnsOverview).getAPIReturnRequestsForCustomer(testCustomerNumber);
                will(throwException(new SQLException("On Purpose For Testing")));
            }
        });
        QuarkusMock.installMockForType(mockICMService, ICMService.class);
        QuarkusMock.installMockForType(mockINDIReturnsOverview, INDIReturnsOverview.class);

        given() //
                .when() //
                .get("/returns") //
                .then() //
                .log().all() //
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR) //
        ;
    }
}
