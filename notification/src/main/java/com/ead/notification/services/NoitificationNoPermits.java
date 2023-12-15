package com.ead.notification.services;

import com.ead.notification.models.NotificationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
//public class NoitificationNoPermits implements NotificationService{
public final class NoitificationNoPermits implements NotificationService{

    @Override
    public NotificationModel saveNotification(NotificationModel notificationModel) {
        return null;
    }

    @Override
    public Page<NotificationModel> findAllNotificationsByUser(UUID userId, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<NotificationModel> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return Optional.empty();
    }
}
