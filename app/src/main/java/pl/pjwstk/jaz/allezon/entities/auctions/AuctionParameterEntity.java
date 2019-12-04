package pl.pjwstk.jaz.allezon.entities.auctions;

import javax.persistence.*;

//@MapsId()
//@Column
//@JoinColumn

@Entity
@Table(name = "auction_parameter")
public class AuctionParameterEntity {

    @EmbeddedId
    private AuctionParameterId id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("parameterId")
    @JoinColumn(name = "parameter_id")
    private ParameterEntity parameter;

    @ManyToOne
    @MapsId("auctionId")
    @JoinColumn(name = "auction_id")
    private AuctionEntity auction;

    @Column(name = "value")
    private String value;

    public AuctionParameterId getId() {
        return id;
    }

    public void setId(AuctionParameterId id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ParameterEntity getParameter() {
        return parameter;
    }

    public void setParameter(ParameterEntity parameter) {
        this.parameter = parameter;
    }

    public AuctionEntity getAuction() {
        return auction;
    }

    public void setAuction(AuctionEntity auction) {
        this.auction = auction;
    }
}