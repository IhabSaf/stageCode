package nl.indi.return_service.overview.beans;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import nl.indi.returns.service.iom.beans.QuantitySerializer;

@Builder
public class APIRMAItems {
    @JsonProperty("position")
    private int position;
    @JsonProperty("sku")
    private String sku;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @JsonProperty("articleName")
    private String articleName;
    @JsonProperty("imageURL")
    private String imageURL;

    @JsonCreator
    public APIRMAItems( //
            @JsonProperty("position") int position, //
            @JsonProperty("sku") String sku, //
            @JsonProperty("quantity") BigDecimal quantity, //
            @JsonProperty("articleName") String articleName, //
            @JsonProperty("imageURL") String imageURL) {
        this.position = position;
        this.sku = sku;
        this.quantity = quantity;
        this.articleName = articleName;
        this.imageURL = imageURL;
    }

    public int getPosition() {
        return position;
    }

    public String getSku() {
        return sku;
    }

    @JsonSerialize(using = QuantitySerializer.class)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return "ReturnRequestItem [position=" + position + ", sku=" + sku + ", quantity=" + quantity + ", articleName="
                + articleName + ", imageURL=" + imageURL + "]";
    }

}
