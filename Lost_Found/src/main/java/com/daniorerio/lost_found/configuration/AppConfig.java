package com.daniorerio.lost_found.configuration;

import com.daniorerio.lost_found.repositories.ContactInformationRepository;
import com.daniorerio.lost_found.repositories.LocationRepository;
import com.daniorerio.lost_found.repositories.LostItemRepository;
import com.daniorerio.lost_found.services.ContactInformationService;
import com.daniorerio.lost_found.services.LostItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    public ContactInformationService contactInformationService(ContactInformationRepository contactInformationRepository) {
        return new ContactInformationService(contactInformationRepository);
    }

    @Bean
    @Scope("prototype")
    public LostItemService lostItemService(LostItemRepository lostItemRepository) {
        LostItemService lostItemService = new LostItemService();
        lostItemService.setLostItemService(lostItemRepository);

        return lostItemService;
    }

    @Bean
    public ContactInformationRepository contactInformationRepository() {
        return new ContactInformationRepository();
    }

    @Bean
    public LostItemRepository lostItemRepository() {
        return new LostItemRepository();
    }

    @Bean
    public LocationRepository locationRepository() {
        return new LocationRepository();
    }
}
