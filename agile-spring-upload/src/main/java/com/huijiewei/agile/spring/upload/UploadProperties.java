package com.huijiewei.agile.spring.upload;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = UploadProperties.PREFIX)
public class UploadProperties {
    public static final String PREFIX = "agile.spring.upload";

    private String driverName;
}
