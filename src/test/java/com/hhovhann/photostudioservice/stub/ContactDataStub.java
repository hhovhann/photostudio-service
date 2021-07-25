package com.hhovhann.photostudioservice.stub;

import com.hhovhann.photostudioservice.domain.data.ContactData;

public class ContactDataStub {

    public static ContactData createOrderRequestDto(long sequence) {
        ContactData contactData = new ContactData();
        contactData.setName("Order name " + sequence);
        contactData.setSurname("Order surname");
        contactData.setEmail("order" + sequence + "@email.com");
        contactData.setCellNumber("01234567890");

        return contactData;
    }
}
