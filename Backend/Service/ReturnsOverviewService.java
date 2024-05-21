package nl.indi.returns.service;

import java.sql.SQLException;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.spi.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.indi.icm.service.Customer;
import nl.indi.icm.service.ICMService;
import nl.indi.return_service.overview_adapter.APIReturnRequestAdapter;
import nl.indi.returns.INDIReturnsOverview;

@Path("/returns")
public class ReturnsOverviewService {
    private static final Logger LOG = LoggerFactory.getLogger(ReturnsOverviewService.class);

    private ICMService icmService;
    private INDIReturnsOverview indiReturnsOverview;
    private APIReturnRequestAdapter apiReturnRequestAdapter;

    @Inject
    public ReturnsOverviewService(//
            ICMService icmService, //
            INDIReturnsOverview indiReturnsOverview, //
            APIReturnRequestAdapter apiReturnRequestAdapter//
    ) {
        this.indiReturnsOverview = indiReturnsOverview;
        this.icmService = icmService;
        this.apiReturnRequestAdapter = apiReturnRequestAdapter;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReturnRequests( //
            @Context HttpRequest httpRequest) throws SQLException {
        try {
            Customer customer = authorise(httpRequest);

            return Response.ok() //
                    .entity(apiReturnRequestAdapter.mapRMAsToAPIReturnRequests(//
                            indiReturnsOverview.getAPIReturnRequestsForCustomer(customer.getCustomerNo())))//
                    .build();
        } catch (NotAuthorizedException naex) {
            return naex.getResponse();
        }
    }

    private Customer authorise(HttpRequest httpRequest) throws NotAuthorizedException {
        Customer customer = icmService.getCustomer(httpRequest.getHttpHeaders().getCookies());
        if (customer == null) {
            LOG.info("Couldn't get customer from cookies, sending: {}", Integer.valueOf(HttpStatus.SC_UNAUTHORIZED));
            throw new NotAuthorizedException(Response.status(HttpStatus.SC_UNAUTHORIZED) //
                    .build());
        }
        return customer;
    }
}
