package com.soda.phonebook.config;

import java.util.Optional;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditConfiguration {
	@Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.of(new Random().nextLong());
    }
}
