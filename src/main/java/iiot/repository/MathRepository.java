package iiot.repository;

import iiot.pojos.Maths;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.sql.Timestamp;
// Repositorios sencillos para sacar los metodos establecidos en MongoRepository.
public interface MathRepository extends MongoRepository<Maths, Timestamp> {
}
