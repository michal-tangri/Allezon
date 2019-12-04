package pl.pjwstk.jaz.allezon.entities.auctions;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AuctionParameterId implements Serializable {

    @Column(name = "parameter_id")
    private Long parameterId;

    @Column(name = "auction_id")
    private Long auctionId;

    public AuctionParameterId(Long parameterId, Long auctionId) {
        this.parameterId = parameterId;
        this.auctionId = auctionId;
    }

    public AuctionParameterId() {

    }

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
