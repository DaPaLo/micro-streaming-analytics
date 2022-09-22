package iiot.repository;

import iiot.pojos.Data;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
// Repositorios sencillos para sacar los metodos establecidos en MongoRepository.
@Repository
public interface DataRepository extends MongoRepository<Data, Integer> {

}
