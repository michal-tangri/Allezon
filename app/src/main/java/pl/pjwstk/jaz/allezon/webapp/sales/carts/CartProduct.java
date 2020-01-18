package pl.pjwstk.jaz.allezon.webapp.sales.carts;

import com.fasterxml.jackson.annotation.JsonBackReference;
import pl.pjwstk.jaz.allezon.webapp.sales.auctions.entities.Auction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "cart_products")
public class CartProduct {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "added_at")
    @NotNull
    private LocalDate addedAt;

    @Column(name = "amount")
    private Long amount;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    public CartProduct() {}

    public CartProduct(LocalDate addedAt, Auction auction, Cart cart, Long amount) {
        this.addedAt = addedAt;
        this.auction = auction;
        this.cart = cart;
        this.amount = amount;
    }

    public CartProduct(LocalDate addedAt, Auction auction, Cart cart) {
        this.addedAt = addedAt;
        this.auction = auction;
        this.cart = cart;
        this.amount = 1L;
    }

    //Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDate addedAt) {
        this.addedAt = addedAt;
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
