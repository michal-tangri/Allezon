package pl.pjwstk.jaz.allezon.webapp.sales.auctions.management;

import pl.pjwstk.jaz.allezon.webapp.categorization.categories.entities.Category;
import pl.pjwstk.jaz.allezon.webapp.sales.auctions.entities.Auction;
import pl.pjwstk.jaz.allezon.webapp.sales.auctions.services.AuctionManagerService;
import pl.pjwstk.jaz.allezon.webapp.sales.parameters.entities.AuctionParameter;
import pl.pjwstk.jaz.allezon.webapp.sales.parameters.entities.AuctionParameterId;
import pl.pjwstk.jaz.allezon.webapp.sales.parameters.entities.Parameter;
import pl.pjwstk.jaz.allezon.webapp.sales.photos.entities.Photo;
import pl.pjwstk.jaz.allezon.webapp.utilities.AllezonUtils;

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
    private AuctionManagerService auctionManagerService;

    @Inject
    private AllezonUtils utils;

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
        auction.setCategory(auctionManagerService.getCategoryById(auctionRequest.getCategoryId()));
        auction.setProfile(auctionManagerService.getUserByName(auctionManagerService.getUsername()));

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

            Parameter parameter = auctionManagerService.getParameterByName(parameterName);

            AuctionParameter auctionParameter = auctionManagerService.getAuctionParameterById(parameter.getId(), auctionId);

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
        String fileName = auctionRequest.getTitle() + auctionManagerService.getUsername();

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
            photos = auctionManagerService.getAllPhotosByAuctionId(auctionId);
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
        auctionManagerService.saveAuction(auction);
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
                auction = auctionManagerService.getAuctionById(auctionId).orElseThrow();
            } catch (NoSuchElementException err0) {
                auctionDoesNotExist = true;
                return new AuctionRequest();
            }

            if (!auction.getProfile().getUsername().equals(auctionManagerService.getUsername())) {
                wrongAuctionOwner = true;
                return new AuctionRequest();
            }

            return new AuctionRequest(auction);
        }
        return new AuctionRequest();
    }

    public List<Parameter> getAllParameters() {
        return auctionManagerService.getAllParameters();
    }

    public List<Category> getAllCategories() {
        return auctionManagerService.getAllCategories();
    }

    public List<Photo> getAllPhotosByAuctionId() {
        var auctionId = utils.getLongFromLink("auctionId");
        if(auctionId == null)
            return null;
        return auctionManagerService.getAllPhotosByAuctionId(auctionId);
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
