package com.rainingsince.admin.autoconfigure;

import com.rainingsince.admin.ScanConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ScanConfiguration.class)
public class AdminAutoConfiguration {

}
