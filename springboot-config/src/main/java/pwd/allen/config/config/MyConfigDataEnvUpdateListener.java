package pwd.allen.config.config;

import org.springframework.boot.context.config.ConfigDataEnvironmentUpdateListener;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.Profiles;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

/**
 * TODO 没效果
 * @author pwdan
 * @create 2024-07-22 14:10
 **/
@Component
public class MyConfigDataEnvUpdateListener implements ConfigDataEnvironmentUpdateListener {
    @Override
    public void onPropertySourceAdded(PropertySource<?> propertySource, ConfigDataLocation location, ConfigDataResource resource) {
        ConfigDataEnvironmentUpdateListener.super.onPropertySourceAdded(propertySource, location, resource);
    }

    @Override
    public void onSetProfiles(Profiles profiles) {
        ConfigDataEnvironmentUpdateListener.super.onSetProfiles(profiles);
    }
}
