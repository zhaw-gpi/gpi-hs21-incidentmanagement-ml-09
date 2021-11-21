package ch.zhaw.gpi.notificationetc;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientConfigurationRepository extends JpaRepository<RecipientConfiguration, String> {
    
}
