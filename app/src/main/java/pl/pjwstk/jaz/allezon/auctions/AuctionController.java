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
import java.util.NoSuchElementException;

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

    private boolean parametersRepeated = false;
    private boolean wrongFileFormat = false;

    private boolean auctionDoesNotExist = false;
    private boolean wrongAuctionOwner = false;
    private boolean wrongParameterInLink = false;

    public void save() {
        Auction auction = auctionRequest.toAuction();

        Long auctionId = auction.getId();

        //Dodanie kategorii i użytkownika
        auction.setCategory(auctionRepository.findCategoryByName(auctionRequest.getCategoryName()));
        auction.setProfile(auctionRepository.findUserByName(session.getUsername()));

        //Pobranie listy parametrów zaznaczonych przez użytkownika
        List<String> parametersNames = auctionRequest.getParametersNames();

        //Lista na parametry, które już wystąpiły żeby sprawdzić czy się nie powtarzają
        List<String> parametersInQueue = new ArrayList<>();

        List<AuctionParameter> parameters = new ArrayList<>();

        for (int i = 0; i < parametersNames.size(); i++) {
            String parameterName = parametersNames.get(i);
            String parameterValue = auctionRequest.getParametersValues().get(i);

            //Jeżeli parametr nie ma wartości, to go nie dodawaj
            if (parameterValue.equals("")) continue;

            //Jeżeli parametr już raz wystąpił, to przerwij dodawanie
            if (parametersInQueue.contains(parameterName)) {
                parametersRepeated = true;
                return;
            }

            Parameter parameter = auctionRepository.findParameterByName(parameterName);

            AuctionParameter auctionParameter = auctionRepository.findAuctionParameterById(parameter.getId(), auctionId);

            //Jeżeli dodajemy nową aukcję do bazy lub nowy parametr do aukcji w bazie
            if (auctionId == null || auctionParameter == null) {
                parameters.add(new AuctionParameter(new AuctionParameterId(), parameter, auction, parameterValue));
            }
            else {
                auctionParameter.setParameter(parameter);
                auctionParameter.setValue(parameterValue);
                parameters.add(auctionParameter);
            }

            parametersInQueue.add(parameterName);
        }

        auction.setParameters(parameters);

        //Dodawanie zdjęć
        List<Photo> photos;
        Part[] photosParts = auctionRequest.getPhotos();
        String fileName = auctionRequest.getTitle() + session.getUsername();

        if(auctionId == null) {
            photos = new ArrayList<>();
            for (Part file : photosParts) {
                if (file == null)
                    continue;

                try {
                    photos.add(new Photo(utils.saveImageToFile(file, fileName), auction));
                } catch (IllegalArgumentException err0) {
                    wrongFileFormat = true;
                    return;
                }
            }
        }
        else {
            photos = auctionRepository.findAllPhotosByAuctionId(auctionId);
            for(int i = 0; i < photosParts.length; i++) {
                Part file = photosParts[i];
                if (file == null)
                    continue;

                try {
                    if(photos.size() < i+1)
                        photos.add(new Photo(utils.saveImageToFile(file, fileName), auction));
                    else
                        photos.get(i).setFilePath(utils.saveImageToFile(file, fileName));
                } catch (IllegalArgumentException err0) {
                    wrongFileFormat = true;
                    return;
                }

            }
        }
        auction.setPhotos(photos);


        //Zapis do bazy
        auctionRepository.save(auction);
        if(auctionId == null)
            utils.redirectToPage("/userAuctionsList.xhtml");
        else
            utils.redirectToPage("/auction.xhtml?auctionId=" + auctionId);
    }

    private AuctionRequest createAuctionRequest() {
        if (utils.linkContains("auctionId")) {
            var auctionId = utils.getLongFromLink("auctionId");

            if (auctionId == null) {
                wrongParameterInLink = true;
                return new AuctionRequest();
            }

            var auction = new Auction();
            try {
                auction = auctionRepository.findAuctionById(auctionId).orElseThrow();
            } catch (NoSuchElementException err0) {
                auctionDoesNotExist = true;
                return new AuctionRequest();
            }

            if (!auction.getProfile().getUsername().equals(session.getUsername())) {
                wrongAuctionOwner = true;
                return new AuctionRequest();
            }

            return new AuctionRequest(auction);
        }
        return new AuctionRequest();
    }

    public List<Parameter> getAllParameters() {
        return auctionRepository.findAllParameters();
    }

    public List<Category> getAllCategories() {
        return auctionRepository.findAllCategories();
    }

    public List<Photo> getAllPhotosByAuctionId() {
        var auctionId = utils.getLongFromLink("auctionId");
        if(auctionId == null)
            return null;
        return auctionRepository.findAllPhotosByAuctionId(auctionId);
    }

    //Getters and setters
    public AuctionRequest getAuctionRequest() {
        if (auctionRequest == null)
            auctionRequest = createAuctionRequest();
        return auctionRequest;
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
