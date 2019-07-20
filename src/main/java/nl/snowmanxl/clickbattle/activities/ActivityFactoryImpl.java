package nl.snowmanxl.clickbattle.activities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityFactoryImpl implements ActivityFactory {

    private final List<ActivityProvider<?>> providers;

    @Autowired
    public ActivityFactoryImpl(List<ActivityProvider<?>> providers) {
        this.providers = providers;
    }

    @Override
    public Activity createNewActivity(Activity type) {
        return providers.stream()
                .filter(provider -> provider.getTypeOfProvider().equals(type.getClass()))
                .map(ActivityProvider::getNewActivity)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
