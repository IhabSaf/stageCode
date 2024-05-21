package nl.indi.return_service.overview.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class APIReturnRequest {

    @JsonProperty("rmaNum")
    private String rmaNum;

    @JsonProperty("status")
    private String status = "initialized";

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("emailAddress")
    private String emailAddress;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("returnRequestDate")
    private String returnRequestDate;

    @JsonProperty("rmaItems")
    private List<APIRMAItems> rmaItems;

    @JsonCreator
    public APIReturnRequest(//
            @JsonProperty("rmaNum") String rmaNum, //
            @JsonProperty("status") String status, //
            @JsonProperty("orderNumber") String orderNumber, //
            @JsonProperty("emailAddress") String emailAddress, //
            @JsonProperty("comment") String comment, //
            @JsonProperty("returnRequestDate") String returnRequestDate, //
            @JsonProperty("rmaItems") List<APIRMAItems> rmaItems) {

        this.rmaNum = rmaNum;
        this.status = status;
        this.orderNumber = orderNumber;
        this.emailAddress = emailAddress;
        this.comment = comment;
        this.returnRequestDate = returnRequestDate;
        this.rmaItems = rmaItems;
    }

    public String getRmaNum() {
        return rmaNum;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getComment() {
        return comment;
    }

    public String getReturnRequestDate() {
        return returnRequestDate;
    }

    public List<APIRMAItems> getRmaItems() {
        return rmaItems;
    }

    @Override
    public String toString() {
        return "APIReturnRequest [rmaNum=" + rmaNum + ", status=" + status + ", orderNumber=" + orderNumber
                + ", emailAddress=" + emailAddress + ", comment=" + comment + ", returnRequestDate=" + returnRequestDate
                + ", rmaItems=" + rmaItems + "]";
    }
}
