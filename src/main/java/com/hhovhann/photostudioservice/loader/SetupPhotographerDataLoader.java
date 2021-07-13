package com.hhovhann.photostudioservice.loader;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.Photographer;
import com.hhovhann.photostudioservice.repository.PhotographerRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SetupPhotographerDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private PhotographerRepository photographerRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup)
            return;

        Photographer firstPhotographer = new Photographer();
        ContactData firstContactData = new ContactData();
        firstContactData.setFirstName("First Photographer First Name");
        firstContactData.setLastName("First Photographer Last Name");
        firstContactData.setEmail("fisrt_photographer@mail.com");
        firstContactData.setPhone("+391234567890");
        firstPhotographer.setContactData(firstContactData);

        photographerRepository.save(firstPhotographer);

        Photographer twoPhotographer = new Photographer();
        ContactData secondPhotographer = new ContactData();
        secondPhotographer.setFirstName("Second Photographer First Name");
        secondPhotographer.setLastName("Second Photographer Last Name");
        secondPhotographer.setEmail("second_photographer@mail.com");
        secondPhotographer.setPhone("+391234567890");
        firstPhotographer.setContactData(secondPhotographer);

        photographerRepository.save(twoPhotographer);


        alreadySetup = true;
    }
}
