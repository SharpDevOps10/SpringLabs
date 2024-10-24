package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.entities.ContactInformation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInformationRepository extends CrudRepository<ContactInformation, Long> {
    // @Query
    @Query("SELECT ci FROM ContactInformation ci WHERE ci.firstName = :firstName")
    List<ContactInformation> findByFirstName(String firstName);

    // @NamedQuery
    List<ContactInformation> findByLastName(String lastName);

    // Auto
    List<ContactInformation> findByEmailContaining(String emailPart);
}