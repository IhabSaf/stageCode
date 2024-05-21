package nl.indi.return_service.overview_adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import nl.indi.order_management.database.beans.RMA;
import nl.indi.return_service.overview.beans.APIReturnRequest;
import nl.indi.return_service.overview.beans.APIReturnRequests;

@Mapper( //
        componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, //
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class APIReturnRequestAdapter {

    @Mapping(target = "rmaItems", source = "items")
    public abstract APIReturnRequest mapRMAtoAPIReturnRequest(RMA rma);

    public APIReturnRequests mapRMAsToAPIReturnRequests(List<RMA> rmas) {
        if (rmas == null || rmas.isEmpty()) {
            return null;
        }
        List<APIReturnRequest> apiReturnRequests = rmas//
                .stream()//
                .map(this::mapRMAtoAPIReturnRequest)//
                .collect(Collectors.toList());

        return new APIReturnRequests(apiReturnRequests);
    }
}
