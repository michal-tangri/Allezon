package pl.pjwstk.jaz.allezon.entities.auctions;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    public Photo() {
    }

    public Photo(String filePath, Auction auction) {
        this.filePath = filePath;
        this.auction = auction;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
