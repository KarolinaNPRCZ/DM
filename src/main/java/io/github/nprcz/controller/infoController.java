package io.github.nprcz.controller;

import io.github.nprcz.ProductConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class infoController {

    private DataSourceProperties dataSourceProperties;

    private ProductConfigurationProperties myProp;

    public infoController(DataSourceProperties dataSourceProperties, ProductConfigurationProperties myProp) {
        this.dataSourceProperties = dataSourceProperties;
        this.myProp = myProp;
    }

    @GetMapping("/info/url")
    String url() {
        return dataSourceProperties.getUrl();
    }

    @GetMapping("/info/prop")
    boolean myProp() {
        return myProp.getTemplate().isAllowMultipleProducts();
    }
}