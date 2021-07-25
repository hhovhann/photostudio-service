package com.hhovhann.photostudioservice.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.hhovhann.photostudioservice.domain.data.OrderStatus.UNSCHEDULED;
import static com.hhovhann.photostudioservice.domain.data.PhotoType.REAL_ESTATE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DBRider
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DataSet(cleanBefore = true, transactional = true)
    @ExpectedDataSet("order/order.yml")
    @DisplayName("Should properly create the order")
    public void shouldCreateOrder() {
        assertThat(orderRepository).isNotNull();
        assertThat(orderRepository.count()).isEqualTo(0);

        OrderEntity orderEntity = new OrderEntity();
        ContactData contactData = new ContactData();
        contactData.setName("First Order Name");
        contactData.setSurname("First Order Surname");
        contactData.setEmail("first_order@gmail.com");
        contactData.setCellNumber("1231241312");

        orderEntity.setContactData(contactData);
        orderEntity.setPhotoType(REAL_ESTATE);
        orderEntity.setOrderStatus(UNSCHEDULED);
        orderEntity.setTitle("REAL_ESTATE title");
        orderEntity.setLogisticInfo("REAL_ESTATE logistic info");
        orderEntity.setImageUrl("some_url_here");

        orderRepository.save(orderEntity);

        assertThat(orderRepository.count()).isEqualTo(1); //assertion is made by @ExpectedDataset
    }

    @Test
    @DataSet(value = {"order/orders.yml"}, cleanBefore = true)
    @DisplayName("Should properly delete the order created initially")
    public void shouldDeleteOrder() {
        assertThat(orderRepository).isNotNull();
        assertThat(orderRepository.count()).isEqualTo(2);
        orderRepository.findById(2L).ifPresent(orderRepository::delete);
    }
}