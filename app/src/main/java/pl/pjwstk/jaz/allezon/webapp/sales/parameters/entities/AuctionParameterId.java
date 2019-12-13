package pl.pjwstk.jaz.allezon.webapp.sales.parameters.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AuctionParameterId implements Serializable {

    private Long parameterId;
    private Long auctionId;

    public AuctionParameterId() {
    }

    public AuctionParameterId(Long parameterId, Long auctionId) {
        this.parameterId = parameterId;
        this.auctionId = auctionId;
    }

    //Getters and setters
    public Long getParameterId() {
        return parameterId;
    }

    public void setParameterId(Long parameterId) {
        this.parameterId = parameterId;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }
}
