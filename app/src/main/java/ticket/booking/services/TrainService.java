package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {
    public List<Train>trainList;
    ObjectMapper objM = new ObjectMapper();
    private final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/trains.json";


    public TrainService() throws IOException {
        readTrainListFromFile();
    }

    private void readTrainListFromFile() throws IOException, MismatchedInputException {
        File trains = new File(USERS_PATH);
        // At runtime, Using TypeReference to retain the Generic information
        this.trainList = objM.readValue(trains, new TypeReference<List<Train>>() {});
    }

    public List<Train> searchTrains(String source, String destination){
        // get the list of stations for each train in the list
        // check if source and destination exist for each of the train

        return trainList.stream()
                .filter(train -> validTrains(train,source,destination))
                .collect(Collectors.toList());
    }

    // stations are valid if their orders are okay
    public Boolean validTrains(Train train, String source, String destination){
        int sourceIndex = train.getStations().indexOf(source);
        int destinationIndex = train.getStations().indexOf(destination);

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }

}
