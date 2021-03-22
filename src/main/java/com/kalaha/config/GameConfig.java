package com.kalaha.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "game")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameConfig {
	String url;
	int ordinaryPitsSize;
	int stones;

	@Bean
	public GameConfig config() {
		return new GameConfig();
	}
}
