package iiot.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Objects;

@Document(collection = "datastream")
public class DataStream {

    @Id
    private String id;
    private ArrayList<DataPoints> dataPoints;

    // Constructor para crear JSON.
    @JsonCreator
    public DataStream(
            @JsonProperty("id") String id,
            @JsonProperty("datapoints") ArrayList<DataPoints> dataPoints) {
        this.id = id;
        this.dataPoints = dataPoints;
    }

    // Constructor para deserializar.
    public DataStream() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<DataPoints> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(ArrayList<DataPoints> dataPoints) {
        this.dataPoints = dataPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataStream that = (DataStream) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getDataPoints(), that.getDataPoints());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDataPoints());
    }

    @Override
    public String toString() {
        return "DataStream{" +
                "id='" + id + '\'' +
                ", dataPoints=" + dataPoints +
                '}';
    }
}
