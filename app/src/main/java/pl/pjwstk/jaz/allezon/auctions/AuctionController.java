package pl.pjwstk.jaz.allezon.auctions;

import pl.pjwstk.jaz.allezon.entities.auctions.*;
import pl.pjwstk.jaz.allezon.entities.sections.Category;
import pl.pjwstk.jaz.allezon.webapp.AllezonUtils;
import pl.pjwstk.jaz.allezon.webapp.CurrentSession;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class AuctionController {

    @Inject
    private AuctionRepository auctionRepository;

    @Inject
    private AllezonUtils utils;

    @Inject
    private CurrentSession session;

    private AuctionRequest auctionRequest;

    private boolean changingAuctionSuccessful = false;
    private boolean parametersRepeated = false;
    private boolean wrongFileFormat = false;

    private boolean auctionDoesNotExist = false;
    private boolean wrongAuctionOwner = false;
    private boolean wrongParameterInLink = false;

    //TODO editAuction

    public void save() {
        Auction auction = auctionRequest.toAuction();

        //Dodawanie kategorii i użytkownika
        auction.setCategory(auctionRepository.findCategoryByName(auctionRequest.getCategoryName()));
        auction.setProfile(auctionRepository.findUserByName(session.getUsername()));

        //Dodawanie parametrów
        List<String> parametersNames = auctionRequest.getParametersNames();
        List<AuctionParameter> parameters = new ArrayList<>();
        List<String> parametersInQueue = new ArrayList<>();

        for(int i = 0; i < parametersNames.size(); i++) {
            String parameterName = parametersNames.get(i);
            String parameterValue = auctionRequest.getParametersValues().get(i);

            if(parameterValue.equals("")) continue;

            if(parametersInQueue.contains(parametersNames.get(i))) {
                parametersRepeated = true;
                return;
            }

            Parameter parameter = auctionRepository.findParameterByName(parameterName);
            parameters.add(new AuctionParameter(new AuctionParameterId(), parameter, auction, parameterValue));
            parametersInQueue.add(parametersNames.get(i));
        }

        auction.setParameters(parameters);

        //Dodawanie zdjęć
        List<Photo> photos = new ArrayList<>();
        Part[] photosParts = auctionRequest.getPhotos();

        for(Part file: photosParts) {
            if(file == null) continue;
            String fileName = auctionRequest.getTitle() + session.getUsername();

            try {
                photos.add(new Photo(utils.saveImageToFile(file, fileName), auction));
            }
            catch (IllegalArgumentException err0) {
                wrongFileFormat = true;
                return;
            }
        }

        auction.setPhotos(photos);

        //Zapis do bazy
        auctionRepository.save(auction);
        changingAuctionSuccessful = true;
    }

    private AuctionRequest createAuctionRequest() {
        if(utils.linkContains("auctionId")) {

        }
        return new AuctionRequest();
    }

    public List<Parameter> getAllParameters() {
        return auctionRepository.findAllParameters();
    }

    public List<Category> getAllCategories() {
        return auctionRepository.findAllCategories();
    }

    //Getters and setters
    public AuctionRequest getAuctionRequest() {
        if(auctionRequest == null)
            auctionRequest = createAuctionRequest();
        return auctionRequest;
    }

    public boolean isChangingAuctionSuccessful() {
        return changingAuctionSuccessful;
    }

    public boolean isWrongFileFormat() {
        return wrongFileFormat;
    }

    public boolean isParametersRepeated() {
        return parametersRepeated;
    }

    public boolean isAuctionDoesNotExist() {
        return auctionDoesNotExist;
    }

    public boolean isWrongAuctionOwner() {
        return wrongAuctionOwner;
    }

    public boolean isWrongParameterInLink() {
        return wrongParameterInLink;
    }
}
