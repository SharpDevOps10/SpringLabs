package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.entities.ContactInformation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInformationRepository extends CrudRepository<ContactInformation, Long> {
    // 5.1.1 Пошук за допомогою @Query (JPQL)
    @Query("SELECT ci FROM ContactInformation ci WHERE ci.firstName = :firstName")
    List<ContactInformation> findByFirstName(String firstName);

    // 5.1.2 Пошук за допомогою @NamedQuery (визначений у @Entity)
    List<ContactInformation> findByLastName(String lastName);

    // 5.2 Автоматично згенеровані методи на основі імені
    List<ContactInformation> findByPhoneNumber(String phoneNumber);

    List<ContactInformation> findByEmailContaining(String emailPart);
}