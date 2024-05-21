package nl.indi.order_management.database.beans;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RMAItems {
    private final int position;
    private final String sku;
    private final BigDecimal quantity;
    private final String articleName;
    private final String imageURL;
}
