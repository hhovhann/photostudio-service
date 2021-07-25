package com.hhovhann.photostudioservice.stub;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.data.PhotoType;

public class ContactDataStub {

    public static ContactData createOrderRequestDto(long sequence, PhotoType photoType) {
        ContactData contactData = new ContactData();
        contactData.setName("Order name " + sequence);
        contactData.setSurname("Order surname");
        contactData.setEmail("order" + sequence + "@email.com");
        contactData.setCellNumber("01234567890");

        return contactData;
    }
}
