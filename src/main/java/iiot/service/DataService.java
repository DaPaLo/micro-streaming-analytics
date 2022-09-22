package iiot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iiot.exceptions.DeviceAlreadySaved;
import iiot.exceptions.DeviceNotFound;
import iiot.pojos.Data;
import iiot.pojos.DataPoints;
import iiot.pojos.DataStream;
import iiot.pojos.Maths;
import iiot.repository.DataRepository;
import iiot.repository.MathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private MathRepository mathRepository;


    public DataService() {
    }

    // Insertar en la base de datos el dato.
    public void insert(Data data) {
        if (dataRepository.existsById(data.getId())) {
            try {
                throw new DeviceAlreadySaved();
            } catch (DeviceAlreadySaved e) {
                e.printStackTrace();
            }
        } else {
            dataRepository.insert(data);
        }
    }
    // Listar todas las entidades en la base de datos.
    public List<Data> list() {
        return dataRepository.findAll();
    }

    // Conseguir el objeto dado el id.
    public Data getData(int id) {
        if (!dataRepository.existsById(id)) {
            try {
                throw new DeviceNotFound();
            } catch (DeviceNotFound e) {
                e.printStackTrace();
            }
        }
        return dataRepository.findById(id).get();
    }

    // Devuelve el numero de valores de temperatura que tiene un dispositivo.
    public int countValuesFromDevice(int id) {
        Data data = getData(id);
        int sum = 0;
        ArrayList<DataStream> dataStreams = getDataStreams(data);
        for (int i = 0; i < dataStreams.size(); i++) {
            ArrayList<DataPoints> dataPoints = getDataPoints(dataStreams.get(i));
            int number = dataPoints.size();
            sum += number;
        }
        return sum;
    }

    // Devuelve una lista de todos los valores de temperatura de un dispositivo.
    public List<Double> getValuesToList(int id) {
        Data data = getData(id);
        List<Double> sum = new ArrayList<>();
        ArrayList<DataStream> dataStreams = getDataStreams(data);
        for (int i = 0; i < dataStreams.size(); i++) {
            ArrayList<DataPoints> dataPoints = getDataPoints(dataStreams.get(i));
            for (int j = 0; j < dataPoints.size(); j++) {
                Double value = dataPoints.get(j).getValue();
                sum.add(value);
            }
        }
        return sum;
    }
    // Devuelve un double la suma de todos los valores de temperatura de un dispositivo.
    public Double getValuesFromDevice(int id) {
        Data data = getData(id);
        Double sum = 0.0;
        ArrayList<DataStream> dataStreams = getDataStreams(data);
        for (int i = 0; i < dataStreams.size(); i++) {
            ArrayList<DataPoints> dataPoints = getDataPoints(dataStreams.get(i));
            for (int j = 0; j < dataPoints.size(); j++) {
                Double value = dataPoints.get(j).getValue();
                sum = sum + value;
            }
        }
        return sum;
    }

    // Devuelve la suma de todos los valores de temperatura de la basde de datos.
    public Double sumValues() {
        List<Data> data = list();
        Double sum = 0.0;
        for (int i = 0; i < data.size(); i++) {
            Data dataF = data.get(i);
            Double valueDevice = getValuesFromDevice(dataF.getId());
            sum = sum + valueDevice;
        }
        return sum;
    }

    // Devuelve el numero de valores que hay en total en la base de datos.
    public int countValues() {
        List<Data> data = list();
        int sum = 0;
        for (int i = 0; i < data.size(); i++) {
            Data dataF = data.get(i);
            int count = countValuesFromDevice(dataF.getId());
            sum = sum + count;
        }
        return sum;
    }

    // Devuelve la lista de valores de temperatura de la basde de datos.
    public List<Double> getValues() {
        List<Data> data = list();
        List<Double> sum = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Data dataF = data.get(i);
            List<Double> valueDevice = getValuesToList(dataF.getId());
            sum.addAll(valueDevice);
        }
        return sum;
    }

    // Devuelve el valor de la lista de valores de la basde de datos dado un numero de lista.
    public Double getValue(int number) {
        List<Double> data = getValues();
        return data.get(number);
    }

    // Devuelve los datastreams.
    public ArrayList<DataStream> getDataStreams(Data data) {
        return data.getDataStreams();
    }

    // Devuelve los datapoints.
    public ArrayList<DataPoints> getDataPoints(DataStream dataStream) {
        return dataStream.getDataPoints();
    }

    //-----------------------------Pasamos a las operaciones------------------------------------------


    // Creamos un objeto de clase Maths.
    public Maths createMaths() {
        Maths math = new Maths(
                mathRepository.findAll().size() + 1,
                setTimestamp(),
                makeMedia(),
                makeMediana(),
                makeModa(),
                makeDesviacionT(),
                makeQuartils(),
                takeMaximum(),
                takeMinimum()
        );
        return math;
    }

    // Insertamos el objeto en la base de datos.
    public void insertMaths(Maths math) {
        mathRepository.insert(math);
    }

    // Creamos la marca de tiempo.
    public Date setTimestamp() {
        Date date = new Date();
        return date;
    }

    // Operacion para sacar la media.
    public Double makeMedia() {
        int count = countValues();
        Double values = sumValues();
        Double media = values / count;
        return media;
    }

    // Operacion para sacar la mediana.
    public Double makeMediana() {
        int count = countValues();
        List<Double> values = getValues();
        Collections.sort(values);
        int half = count / 2;
        if (count > 2) {
            if (count % 2 == 0) {
                return (((values.get(half - 1)) + (values.get(half + 1))) / 2);
            } else {
                return getValue(half);
            }
        } else if (count == 2) {
            return (((values.get(0)) + (values.get(1))) / 2);
        } else if (count == 1) {
            return getValues().get(0);
        } else {
            return null;
        }
    }

    // Operacion para sacar la moda. Aunque no he conseguido que saque los repetidos.
    public List<Double> makeModa() {
        List<Double> values = getValues();
        Collections.sort(values);
        List<Double> moda = new ArrayList<>();
        Double modaNum = 0.0;
        int maxRepes = 0;
        HashMap<Double, Integer> map = new HashMap<>();
        for (int i = 0; i < values.size(); i++) {
            Double number = values.get(i);
            if (map.containsKey(number)) {
                map.put(number, map.get(number) + 1);
            } else {
                map.put(number, 1);
            }
        }
        for (HashMap.Entry<Double, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= maxRepes) {
                maxRepes = entry.getValue();
                modaNum = entry.getKey();

            }
        }
        moda.add(modaNum);
        return moda;
    }

    // Operacion para sacar la desviacion tipica.
    public Double makeDesviacionT() {
        List<Double> values = getValues();
        Collections.sort(values);
        double sum = 0.0;
        double desviacion = 0.0;
        int length = values.size();
        for (double num : values) {
            sum += num;
        }
        double mean = sum / length;

        for (double num : values) {
            desviacion += Math.pow(num - mean, 2);
        }
        return Math.sqrt(desviacion / length);
    }

    // Operacion para sacar el valor minimo.
    public Double takeMinimum() {
        List<Double> values = getValues();
        Double min = Collections.min(values);
        return min;
    }

    // Operacion para sacar el valor máximo.
    public Double takeMaximum() {
        List<Double> values = getValues();
        Double max = Collections.max(values);
        return max;
    }

    // Juntamos los resultados de los 3 cuartiles y los metemos en un array.
    public ArrayList<Double> makeQuartils() {
        ArrayList<Double> quartils = new ArrayList<>();
        quartils.add(firstQuartile());
        quartils.add(secondQuartile());
        quartils.add(thirdQuartile());
        return quartils;
    }

    // Operacion para sacar el primer cuartil.
    public Double firstQuartile() {
        int count = countValues();
        Double quartile = Double.valueOf((count + 1) / 4);
        return quartile;
    }

    // Operacion para sacar el segundo cuartil.
    public Double secondQuartile() {
        int count = countValues();
        Double quartile = Double.valueOf((count + 1) / 2);
        return quartile;
    }

    // Operacion para sacar el tercer cuartil.
    public Double thirdQuartile() {
        int count = countValues();
        Double quartile = Double.valueOf(3 * (count + 1) / 4);
        return quartile;
    }
    //-------------------Creacion de el dato para hacer la cola continua-------------------------------------------

    // Numero aletorio del 0 al 9. Para luego crear la version y el valor.
    public int number0to9() {
        Random rnd = new Random();
        return rnd.nextInt(9);
    }

    // Creamos el valor aletorio.
    public Double value() {
        Random rnd = new Random();
        int number1 = number0to9();
        int number2 = number0to9();
        int number3 = number0to9();
        String number = number1 + "" + number2 + "." + number3;
        return Double.parseDouble(number);
    }

    // Creamos el JSON con los datos.
    public String jsonConstructor() {
        String json = "{" +
                "_id : " + ((dataRepository.findAll().size()) + 1) + "," +
                " version : " + number0to9() + "." + number0to9() + "." + number0to9() + "," +
                " device : device-id," +
                " datastreams : [ " +
                "{" +
                " id : temperature," +
                " datapoints: [ { value : " + value() + " } ]" +
                " }" +
                " ]" +
                " }";
        return json;
    }

    // Creamos el Objeto desde el JSON.
    public Data createData() {
        int id = (dataRepository.findAll().size()) + 1;
        String version = number0to9() + "." + number0to9() + "." + number0to9();
        Double value = value();
        DataPoints dataPoint = new DataPoints(value);
        ArrayList<DataPoints> dataPoints = new ArrayList<>();
        dataPoints.add(dataPoint);
        DataStream dataStream = new DataStream("temperature", dataPoints);
        ArrayList<DataStream> dataStreams = new ArrayList<>();
        dataStreams.add(dataStream);
        Data data = new Data(id, "my-device", version, dataStreams);
        return data;
    }

}
