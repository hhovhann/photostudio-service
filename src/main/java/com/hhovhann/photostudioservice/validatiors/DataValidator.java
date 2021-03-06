package com.hhovhann.photostudioservice.validatiors;

import com.hhovhann.photostudioservice.domain.data.OrderStatus;
import com.hhovhann.photostudioservice.exception.PhotoStudioValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.time.ZonedDateTime;
import java.util.Objects;

@Component
public class DataValidator {
    /***
     * Checks business hours matching
     * @param localDateTime - order creation local date time
     */
    public void validateBusinessHours(ZonedDateTime localDateTime) {
        if (!isTheTimeBetweenHours(localDateTime)) {
            throw new PhotoStudioValidationException("The date not within the business hours (8:00-20:00)");
        }
    }

    /***
     * Checks zip file content against some business logic and accept it, otherwise validation exception about bad content
     * @param file - photos in zipped file
     * */
    public void validateFile(MultipartFile file) {
        boolean endsWith = file.getOriginalFilename().endsWith("zip");
        if (!endsWith) {
            throw new PhotoStudioValidationException("The uploaded file should be zipped");
        }
    }

    /***
     * Checks zip file content against some business logic and accept it, otherwise validation exception about bad content
     * @param imageURL - photo url where store our zip file
     */
    public void validatePhotoContent(String imageURL) {
        // TODO Call third party resource with this photo identifier and verify the content accept if it's ok, otherwise exception
        if (imageURL.isEmpty()) {
            throw new PhotoStudioValidationException("The uploaded file identifier is empty");
        }
    }

    /***
     * Compare source and target order statuses
     * @param sourceStatus current instance order status
     * @param targetStatus the order status to check against
     */
    public void validateOrderStatuses(OrderStatus sourceStatus, OrderStatus targetStatus) {
        if (!Objects.equals(sourceStatus, targetStatus)) {
            throw new PhotoStudioValidationException("The current order status is different then target order status");
        }
    }

    /***
     * Checks that the time hours within the business hours (8:00-20:00)
     * @param localDateTime - order creation local date time
     */
    private boolean isTheTimeBetweenHours(ZonedDateTime localDateTime) {
        return localDateTime.getHour() >= 8 && localDateTime.getHour() < 20;
    }
}
