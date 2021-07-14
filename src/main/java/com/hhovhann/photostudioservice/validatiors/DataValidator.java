package com.hhovhann.photostudioservice.validatiors;

import com.hhovhann.photostudioservice.domain.data.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class DataValidator {
    /***
     * Checks business hours matching
     * @param localDateTime - order creation local date time
     */
    public void validateBusinessHours(LocalDateTime localDateTime) {
        if (!isTheTimeBetweenHours(localDateTime) || !isTheTimeBetweenMinutes(localDateTime))
            throw new ValidationException("The date not within  the business hours (8:00-20:00)");
    }

    /***
     * Checks zip file content against some business logic and accept it, otherwise validation exception about bad content
     * @param file - photos in zipped file
     * */
    public void validateFile(MultipartFile file) {
        boolean endsWith = file.getOriginalFilename().endsWith("zip");
        if (!endsWith) {
            throw new ValidationException("The uploaded file should be zipped");
        }
    }

    /***
     * Checks zip file content against some business logic and accept it, otherwise validation exception about bad content
     * @param imageURL - photo url where store our zip file
     */
    public void validatePhotoContent(String imageURL) {
        // TODO Call third party resource with this photo identifier and verify the content accept if it's ok, otherwise exception
        if(imageURL.isEmpty()){
            throw new ValidationException("The uploaded file identifier is empty");
        }
    }

    /***
     * Compare source and target order statuses
     * @param sourceStatus current instance order status
     * @param targetStatus the order status to check against
     */
    public void validateOrderStatuses(OrderStatus sourceStatus, OrderStatus targetStatus) {
        if (!Objects.equals(sourceStatus, targetStatus)) {
            throw new ValidationException("The current order status is different then target order status");
        }
    }

    /***
     * Checks that the time hours within the business hours (8:00-20:00)
     * @param localDateTime - order creation local date time
     */
    private boolean isTheTimeBetweenHours(LocalDateTime localDateTime) {
        return localDateTime.getHour() >= 8 && localDateTime.getHour() < 24;
    }

    /***
     * Checks that the time minutes within the business hours (8:00-20:00)
     * @param localDateTime - order creation local date time
     */
    private boolean isTheTimeBetweenMinutes(LocalDateTime localDateTime) {
        return localDateTime.getMinute() >= 0 && localDateTime.getMinute() < 60;
    }

}
