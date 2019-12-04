package pl.pjwstk.jaz.allezon.auctions;

import pl.pjwstk.jaz.allezon.entities.auctions.AuctionParameterEntity;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class AuctionParametersRequest implements Serializable {

    private List<String> parameterValues = new ArrayList<>();
    private List<String> parameterNames = new ArrayList<>();

    AuctionParametersRequest() {
        for(int i = 1; i <= 10; i++) {
            parameterValues.add("");
            parameterNames.add("");
        }
    }


    public List<String> getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(List<String> parameterValues) {
        this.parameterValues = parameterValues;
    }

    public List<String> getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(List<String> parameterNames) {
        this.parameterNames = parameterNames;
    }
}
