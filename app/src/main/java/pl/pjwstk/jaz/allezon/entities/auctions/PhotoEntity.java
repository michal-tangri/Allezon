package pl.pjwstk.jaz.allezon.entities.auctions;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private AuctionEntity auction;

    public PhotoEntity(String filePath, AuctionEntity auction) {
        this.filePath = filePath;
        this.auction = auction;
    }

    public PhotoEntity() {

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
