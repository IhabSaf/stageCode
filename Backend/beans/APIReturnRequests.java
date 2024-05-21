package nl.indi.return_service.overview.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class APIReturnRequests {
    @JsonProperty("apiReturnRequests")
    private List<APIReturnRequest> apiReturnRequests;

    @JsonCreator
    public APIReturnRequests(@JsonProperty("apiReturnRequests") List<APIReturnRequest> apiReturnRequests) {
        this.apiReturnRequests = apiReturnRequests;
    }

    public List<APIReturnRequest> getApiReturnRequests() {
        return apiReturnRequests;
    }

    @Override
    public String toString() {
        return "APIReturnRequests{" + "apiReturnRequests=" + apiReturnRequests + '}';
    }
}
