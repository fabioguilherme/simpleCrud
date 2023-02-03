package com.portofolio.demo.infrastructure.notification;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.notification.Notification;
import com.portofolio.demo.domain.notification.NotificationType;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.infrastructure.user.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationRepositoryIntegrationTest extends IntegrationBaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository repository;

    @Test
    @Sql(statements = {"INSERT INTO simple_crud_test.simple_crud_user (id, name, email)\tVALUES (1, \"fake-name\",\"fake-name@email.com\");"})
    public void canPersist() {
        // Given
        User user = userRepository.findById(1L).get();
        String message = "this is a fake message";
        int quantity = 3;

        Notification notificationToStore = Notification.Builder.with().user(user).message(message).build();

        // When
        Notification stored = repository.save(notificationToStore);

        // Then
        assertThat(stored).isNotNull();

        assertThat(stored.getUser().getId()).isEqualTo(user.getId());
        assertThat(stored.getMessage()).isEqualTo(message);
        assertThat(stored.getCreationDate()).isNotNull();
        assertThat(stored.getType()).isEqualTo(NotificationType.EMAIL);
    }

    @Test
    @Sql(statements = {"INSERT INTO simple_crud_test.simple_crud_user (id, name, email)\tVALUES (1, \"fake-name\",\"fake-name@email.com\");" +
            "INSERT INTO simple_crud_test.notification (id,user_id,`type`,creation_date,message)\tVALUES (1,1,'EMAIL',CURRENT_TIMESTAMP,'fake-message');"})
    public void canFindById() {
        // Given
        long id = 1L;

        // When
        Optional<Notification> storedOptional = repository.findById(id);

        // Then
        assertThat(storedOptional).isPresent();
    }

    @Test
    @Sql(statements = {"INSERT INTO simple_crud_test.simple_crud_user (id, name, email)\tVALUES (1, \"fake-name\",\"fake-name@email.com\");" +
            "INSERT INTO simple_crud_test.notification (id,user_id,`type`,creation_date,message)\tVALUES (1,1,'EMAIL',CURRENT_TIMESTAMP,'fake-message');"})
    public void canDeleteById() {
        // Given
        long id = 1L;

        // When
        repository.deleteById(id);

        // Then
        Optional<Notification> storedOptional = repository.findById(id);

        assertThat(storedOptional).isEmpty();
    }

}
