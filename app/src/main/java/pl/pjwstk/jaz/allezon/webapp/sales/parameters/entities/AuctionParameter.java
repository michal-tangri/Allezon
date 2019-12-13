package pl.pjwstk.jaz.allezon.webapp.sales.parameters.entities;

import pl.pjwstk.jaz.allezon.webapp.sales.auctions.entities.Auction;

import javax.persistence.*;
import java.io.Serializable;

//@MapsId()
//@Column
//@JoinColumn

@Entity
@Table(name = "auction_parameter")
public class AuctionParameter implements Serializable {

    @EmbeddedId
    private AuctionParameterId id;

    @MapsId("parameterId")
    @JoinColumn(name = "parameter_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Parameter parameter;

    @MapsId("auctionId")
    @JoinColumn(name = "auction_id")
    @ManyToOne
    private Auction auction;

    @Column(name = "value")
    private String value;

    public AuctionParameter() {
    }

    public AuctionParameter(AuctionParameterId id, Parameter parameter, Auction auction, String value) {
        this.id = id;
        this.parameter = parameter;
        this.auction = auction;
        this.value = value;
    }

    //Getters and setters
    public void setAuction(Auction auction) {
        this.auction = auction;
        this.id.setAuctionId(auction.getId());
    }

    public Auction getAuction() {
        return auction;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
        this.id.setParameterId(parameter.getId());
    }

    public Parameter getParameter() {
        return parameter;
    }

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
}