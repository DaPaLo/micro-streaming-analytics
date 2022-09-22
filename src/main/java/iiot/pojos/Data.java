package iiot.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Objects;



@Document(collection = "data")
public class Data {
    @Id
    private int id;
    private String device;
    private String version;
    private ArrayList<DataStream> dataStreams;

    // Consturctor para poder montar los JSON.
    @JsonCreator
    public Data(
            @JsonProperty("id") int id,
            @JsonProperty("device") String device,
            @JsonProperty("version") String version,
            @JsonProperty("datastreams") ArrayList<DataStream> dataStreams) {
        this.id = id;
        this.version = version;
        this.device = device;
        this.dataStreams = dataStreams;
    }
    // Constructor para deserializar.
    public Data() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ArrayList<DataStream> getDataStreams() {
        return dataStreams;
    }

    public void setDataStreams(ArrayList<DataStream> dataStreams) {
        this.dataStreams = dataStreams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return getId() == data.getId() && Objects.equals(getDevice(), data.getDevice()) && Objects.equals(getVersion(), data.getVersion()) && Objects.equals(getDataStreams(), data.getDataStreams());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDevice(), getVersion(), getDataStreams());
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", device='" + device + '\'' +
                ", version='" + version + '\'' +
                ", dataStreams=" + dataStreams +
                '}';
    }
}
