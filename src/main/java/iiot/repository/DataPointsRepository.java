package iiot.repository;

import iiot.pojos.DataPoints;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Repositorios sencillos para sacar los metodos establecidos en MongoRepository.

@Repository
public interface DataPointsRepository extends MongoRepository<DataPoints, Double> {

}
