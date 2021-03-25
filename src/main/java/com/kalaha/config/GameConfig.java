package com.kalaha.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class represents game settings:
 * 1. Base url
 * 2. Ordinary pit number
 * 3. Number of stones at start
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "game")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameConfig {
	String url;
	int ordinaryPitsNumber;
	int stones;

	@Bean
	public GameConfig config() {
		return new GameConfig();
	}
}
