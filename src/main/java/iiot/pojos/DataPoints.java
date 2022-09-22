package iiot.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "data_point")
public class DataPoints {

    @Id
    private Double value;

    // Consturctor para poder montar los JSON.
    @JsonCreator
    public DataPoints(
            @JsonProperty("value") Double value) {
        this.value = value;
    }

    // Constructor para deserializar.
    public DataPoints() {
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataPoints that = (DataPoints) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return "DataPoints{" +
                "value=" + value +
                '}';
    }
}
