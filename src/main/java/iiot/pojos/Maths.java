package iiot.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document(collection = "maths")
public class Maths {

    @Id
    int id;
    Date timestamp;
    Double media, mediana;
    List<Double> moda;
    Double desviacion_tipica, valor_maximo, valor_minimo;
    ArrayList<Double> cuartiles;

    // Constructor para crear el JSON.
    @JsonCreator
    public Maths(@JsonProperty("id") int id,
                 @JsonProperty("timestamp") Date timestamp,
                 @JsonProperty("media") Double media,
                 @JsonProperty("mediana") Double mediana,
                 @JsonProperty("moda") List<Double> moda,
                 @JsonProperty("desviacion_tipica") Double desviacion_tipica,
                 @JsonProperty("cuartiles") ArrayList<Double> cuartiles,
                 @JsonProperty("maximo") Double valor_maximo,
                 @JsonProperty("minimo") Double valor_minimo) {
        this.id = id;
        this.timestamp = timestamp;
        this.media = media;
        this.mediana = mediana;
        this.moda = moda;
        this.desviacion_tipica = desviacion_tipica;
        this.cuartiles = cuartiles;
        this.valor_maximo = valor_maximo;
        this.valor_minimo = valor_minimo;
    }

    // Constructor para deserializar.
    public Maths() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    public Double getMediana() {
        return mediana;
    }

    public void setMediana(Double mediana) {
        this.mediana = mediana;
    }

    public List<Double> getModa() {
        return moda;
    }

    public void setModa(List<Double> moda) {
        this.moda = moda;
    }

    public Double getDesviacion_tipica() {
        return desviacion_tipica;
    }

    public void setDesviacion_tipica(Double desviacion_tipica) {
        this.desviacion_tipica = desviacion_tipica;
    }

    public ArrayList<Double> getCuartiles() {
        return cuartiles;
    }

    public void setCuartiles(ArrayList<Double> cuartiles) {
        this.cuartiles = cuartiles;
    }

    public Double getValor_maximo() {
        return valor_maximo;
    }

    public void setValor_maximo(Double valor_maximo) {
        this.valor_maximo = valor_maximo;
    }

    public Double getValor_minimo() {
        return valor_minimo;
    }

    public void setValor_minimo(Double valor_minimo) {
        this.valor_minimo = valor_minimo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maths maths = (Maths) o;
        return getId() == maths.getId() && Objects.equals(getTimestamp(), maths.getTimestamp()) && Objects.equals(getMedia(), maths.getMedia()) && Objects.equals(getMediana(), maths.getMediana()) && Objects.equals(getModa(), maths.getModa()) && Objects.equals(getDesviacion_tipica(), maths.getDesviacion_tipica()) && Objects.equals(getValor_maximo(), maths.getValor_maximo()) && Objects.equals(getValor_minimo(), maths.getValor_minimo()) && Objects.equals(getCuartiles(), maths.getCuartiles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTimestamp(), getMedia(), getMediana(), getModa(), getDesviacion_tipica(), getValor_maximo(), getValor_minimo(), getCuartiles());
    }

    @Override
    public String toString() {
        return "Maths{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", media=" + media +
                ", mediana=" + mediana +
                ", moda=" + moda +
                ", desviacion_tipica=" + desviacion_tipica +
                ", valor_maximo=" + valor_maximo +
                ", valor_minimo=" + valor_minimo +
                ", cuartiles=" + cuartiles +
                '}';
    }
}
