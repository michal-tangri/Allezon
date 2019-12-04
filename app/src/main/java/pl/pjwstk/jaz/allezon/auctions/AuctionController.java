package pl.pjwstk.jaz.allezon.auctions;

import pl.pjwstk.jaz.allezon.entities.ProfileEntity;
import pl.pjwstk.jaz.allezon.entities.auctions.*;
import pl.pjwstk.jaz.allezon.entities.sections.Category;
import pl.pjwstk.jaz.allezon.repositories.AuctionRepository;
import pl.pjwstk.jaz.allezon.repositories.CategoryRepository;
import pl.pjwstk.jaz.allezon.repositories.ProfileRepository;
import pl.pjwstk.jaz.allezon.webapp.CurrentSession;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class AuctionController {

    @Inject
    AddAuctionRequest addRequest;

    @Inject
    AuctionParametersRequest parametersData;

    @Inject
    AuctionRepository database;

    @Inject
    CategoryRepository categoryDatabase;

    @Inject
    ProfileRepository profileDatabase;

    @Inject
    CurrentSession session;

    private boolean parametersRepeated = false;

    //TODO editAuction

    public void add() {
        String title = addRequest.getTitle();
        String description = addRequest.getDescription();
        Double price = Double.parseDouble(addRequest.getPrice().replace(',', '.'));
        Category category = categoryDatabase.getCategory(addRequest.getCategoryName());
        ProfileEntity owner = profileDatabase.getUser(session.getUsername());

        AuctionEntity auction = new AuctionEntity();

        String miniatureFilePath = saveFile(addRequest.getMiniature());
        String firstPhotoFilePath = saveFile(addRequest.getPhoto1());
        String secondPhotoFilePath = saveFile(addRequest.getPhoto2());
        String thirdPhotoFilePath = saveFile(addRequest.getPhoto3());

        List<PhotoEntity> photos = new ArrayList<>();
        photos.add(new PhotoEntity(miniatureFilePath, auction));
        photos.add(new PhotoEntity(firstPhotoFilePath, auction));
        photos.add(new PhotoEntity(secondPhotoFilePath, auction));
        photos.add(new PhotoEntity(thirdPhotoFilePath, auction));

        List<String> parameterNames = parametersData.getParameterNames();
        List<String> parameterValues = parametersData.getParameterValues();
        List<AuctionParameterEntity> parameters = new ArrayList<>();
        List<String> parametersInQueue = new ArrayList<>();

        for(int i = 0; i < parameterValues.size(); i++) {
            if(parameterValues.get(i).equals(""))
                continue;

            if(!parametersInQueue.contains(parameterNames.get(i))) {
                AuctionParameterEntity parameter = new AuctionParameterEntity();
                parameter.setId(new AuctionParameterId());
                parameter.setValue(parameterValues.get(i));
                parameter.setParameter(database.getParameter(parameterNames.get(i)));
                parameter.setAuction(auction);
                parameters.add(parameter);
                parametersInQueue.add(parameterNames.get(i));
            }
            else {
                parametersRepeated = true;
                return;
            }
        }

        auction.setTitle(title);
        auction.setDescription(description);
        auction.setCategory(category);
        auction.setPrice(price);
        auction.setProfile(owner);
        auction.setPhotos(photos);
        auction.setParameters(parameters);

        database.addAuction(auction);

    }

    //Zmienna środowiskowa albo informacja o folderze

    /*
        Jeżeli nie ładują się zdjęcia po dodaniu
        WebServlet z linkiem do zdjęcia
        doGet
        nagłówki content type

     */
    private String saveFile(Part file) {
        String fileName = (addRequest.getTitle() + session.getUsername() + System.nanoTime() + file.getSubmittedFileName())
                .replaceAll("\\s","");

        try (InputStream input = file.getInputStream()) {
            File newFile = new File("/home/michaltangri/auction_photos", fileName);
            Files.copy(input, newFile.toPath());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public boolean isParametersRepeated() {
        return parametersRepeated;
    }
}
