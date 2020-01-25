package pl.pjwstk.jaz.allezon.webapp.sales.carts;

import com.fasterxml.jackson.annotation.JsonBackReference;
import pl.pjwstk.jaz.allezon.webapp.sales.auctions.entities.Auction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cart_products")
public class CartProduct {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    public CartProduct() {}

    public CartProduct(Auction auction, Cart cart, Long amount) {
        this.auction = auction;
        this.cart = cart;
        this.amount = amount;
    }

    //Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
