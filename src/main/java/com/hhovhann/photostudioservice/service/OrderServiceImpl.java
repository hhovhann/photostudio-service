package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import com.hhovhann.photostudioservice.dto.OrderRequestDTO;
import com.hhovhann.photostudioservice.mapper.OrderMapper;
import com.hhovhann.photostudioservice.repository.OrderRepository;
import com.hhovhann.photostudioservice.repository.PhotographerRepository;
import com.hhovhann.photostudioservice.validatiors.DataValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.hhovhann.photostudioservice.domain.data.OrderStatus.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DataValidator dataValidator;
    private final PhotographerRepository photographerRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, DataValidator dataValidator, OrderMapper orderMapper, PhotographerRepository photographerRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.dataValidator = dataValidator;
        this.photographerRepository = photographerRepository;
    }

    @Override
    @Transactional
    public Long create(OrderRequestDTO orderRequestDTO) {
        OrderEntity orderEntity = orderMapper.toEntity(orderRequestDTO);
        LocalDateTime localDateTime = orderRequestDTO.getLocalDateTime();
        if (Objects.isNull(localDateTime)) {
            orderEntity.setOrderStatus(UNSCHEDULED);
        } else {
            dataValidator.validateBusinessHours(localDateTime);
            orderEntity.setOrderStatus(PENDING);
        }
        orderRepository.save(orderEntity);
        return orderEntity.getId();
    }

    @Override
    @Transactional
    public void update(Long orderId, LocalDateTime localDateTime) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("No order found with specified Id"));
        dataValidator.validateBusinessHours(localDateTime);
        orderEntity.setCreationDateTime(localDateTime);
        orderEntity.setOrderStatus(PENDING);
        orderRepository.save(orderEntity);
    }


    @Override
    @Transactional
    public void assign(Long orderId, Long photographerId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("No order found with specified Id"));
        dataValidator.validateOrderStatuses(orderEntity.getOrderStatus(), PENDING);
        PhotographerEntity photographerEntity = photographerRepository.findById(photographerId).orElseThrow(() -> new RuntimeException("No order found with specified Id"));
        orderEntity.addPhotographer(photographerEntity);
        orderEntity.setOrderStatus(ASSIGNED);
        orderRepository.save(orderEntity);
    }

    @Override
    @Transactional
    public void uploadPhoto(Long orderId, MultipartFile zipFIle) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("No order found with specified Id"));
        dataValidator.validateOrderStatuses(orderEntity.getOrderStatus(), ASSIGNED);
        dataValidator.validateFile(zipFIle);
        // TODO upload file to photo storage, take URL and store in database the image URL, now I just filename and store it in the database
        orderEntity.setImageURL(zipFIle.getResource().getFilename());
        orderEntity.setOrderStatus(UPLOADED);
        orderRepository.save(orderEntity);
    }

    @Override
    @Transactional
    public void verifyContent(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("No order found with specified Id"));
        dataValidator.validateOrderStatuses(orderEntity.getOrderStatus(), UPLOADED);
        // now change the status to COMPLETED, in future logic should be added where real content verification will happen
        dataValidator.validatePhotoContent(orderEntity.getImageURL());
        orderEntity.setOrderStatus(COMPLETED);
        orderRepository.save(orderEntity);
    }


    @Override
    @Transactional
    public void cancel(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("No order found with specified Id"));
        if (!orderEntity.getPhotographers().isEmpty()) {
            // remove all related photographers, hopefully without ConcurrentModificationException :)
            List<PhotographerEntity> photographers = orderEntity.getPhotographers();
            for (Iterator<PhotographerEntity> iterator = photographers.iterator(); iterator.hasNext(); ) {
                PhotographerEntity photographer = iterator.next();
                orderEntity.removePhotographer(photographer);
            }
        }
        orderRepository.delete(orderEntity);
    }
}
