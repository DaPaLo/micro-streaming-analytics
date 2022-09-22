package iiot.repository;

import iiot.pojos.DataStream;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
// Repositorios sencillos para sacar los metodos establecidos en MongoRepository.
@Repository
public interface DataStreamRepository extends MongoRepository<DataStream, String> {

}
