package nl.indi.return_service.overview_adapter;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nl.indi.order_management.database.beans.RMA;
import nl.indi.order_management.database.beans.RMAItems;
import nl.indi.return_service.overview.beans.APIRMAItems;
import nl.indi.return_service.overview.beans.APIReturnRequest;
import nl.indi.return_service.overview.beans.APIReturnRequests;

class APIReturnRequestAdapterTest {

    @Test
    void testMapRMAsToAPIReturnRequests() {
        RMAItems testRMAitem1 = RMAItems.builder()//
                .position(1)//
                .sku("SKU001")//
                .quantity(BigDecimal.TEN)//
                .articleName("Article 1")//
                .imageURL("https://assets.indi.nl/images/2XS/v6e.jpg")//
                .build();//
        List<RMAItems> testRMAitems = new ArrayList<>();
        testRMAitems.add(testRMAitem1);
        System.out.print(testRMAitems);

        RMA testRMA1 = RMA.builder()//
                .rmaNum("RMA12345")//
                .orderNumber("ORD98765")//
                .emailAddress("testEmailAddress@indi.nl")//
                .comment("damaged")//
                .returnRequestDate("2024-04-09 09:14:31.675")//
                .items(testRMAitems)//
                .build();//
        List<RMA> testRMAs = new ArrayList<>();
        testRMAs.add(testRMA1);

        var adapter = new APIReturnRequestAdapterImpl();
        APIReturnRequests testAPIReturnRequets = adapter.mapRMAsToAPIReturnRequests(testRMAs);
        APIReturnRequest actualAPIReturnRequest = testAPIReturnRequets.getApiReturnRequests().get(0);
        APIRMAItems actualRMAItem = actualAPIReturnRequest.getRmaItems().get(0);

        assertEquals(testRMA1.getRmaNum(), actualAPIReturnRequest.getRmaNum());
        assertEquals(testRMA1.getOrderNumber(), actualAPIReturnRequest.getOrderNumber());
        assertEquals(testRMA1.getEmailAddress(), actualAPIReturnRequest.getEmailAddress());
        assertEquals(testRMA1.getComment(), actualAPIReturnRequest.getComment());
        assertEquals(testRMA1.getReturnRequestDate(), actualAPIReturnRequest.getReturnRequestDate());
        assertEquals(testRMAitem1.getPosition(), actualRMAItem.getPosition());
        assertEquals(testRMAitem1.getSku(), actualRMAItem.getSku());
        assertEquals(testRMAitem1.getQuantity(), actualRMAItem.getQuantity());
        assertEquals(testRMAitem1.getArticleName(), actualRMAItem.getArticleName());
        assertEquals(testRMAitem1.getImageURL(), actualRMAItem.getImageURL());

    }

}
