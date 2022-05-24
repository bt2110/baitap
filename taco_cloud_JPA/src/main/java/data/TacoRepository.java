package data;

import org.springframework.data.repository.CrudRepository;

import Taco_cloud_JPA.Taco;

public interface TacoRepository
        extends CrudRepository<Taco, Long> {

}