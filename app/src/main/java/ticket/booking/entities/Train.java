package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import ticket.booking.util.LocalTimeDeserializer;

import java.time.LocalTime;
import java.util.List;
import java.sql.Time;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Train {
    private String trainId;
    private String trainNo;
    private List<List<Boolean>> seats;
    @JsonDeserialize(using= LocalTimeDeserializer.class)
    private Map<String, LocalTime> stationTimes;
    private List<String>stations;

    public Train(){}

    public Train(String trainId, String trainNo, List<List<Boolean>> seats, Map<String, LocalTime> stationTimes, List<String>stations) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public List<List<Boolean>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Boolean>> seats) {
        this.seats = seats;
    }

    public Map<String, LocalTime> getStationTime() {
        return stationTimes;
    }

    public void setStationTime(Map<String, LocalTime> stationTimes) {
        this.stationTimes = stationTimes;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

}
