package nl.indi.order_management.database.beans;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RMA {
    private final String rmaNum;
    @Builder.Default
    private final String status = "initialized";
    private final String orderNumber;
    private final String emailAddress;
    private final String comment;
    private final String returnRequestDate;
    private List<RMAItems> items;
}
