package io.github.nprcz;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties("product")
public class ProductConfigurationProperties {
    private Template template;
    public Template getTemplate() {
        return template;
    }
    public void setTemplate(Template template) {
        this.template = template;
    }
    public static class Template{
       private boolean allowMultipleProducts;
        public boolean isAllowMultipleProducts() {
            return allowMultipleProducts;
        }
        public void setAllowMultipleProducts(boolean allowMultipleProducts) {
            this.allowMultipleProducts = allowMultipleProducts;
        }
    }


}
