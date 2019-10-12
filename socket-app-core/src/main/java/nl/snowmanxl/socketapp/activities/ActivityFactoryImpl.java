package nl.snowmanxl.socketapp.activities;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivityFactoryImpl implements ActivityFactory {

    private final ApplicationContext context;

    public ActivityFactoryImpl(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Activity createNewActivity(String typeName) {
        Object bean = context.getBean(typeName);

        return Optional.ofNullable(bean)
                .filter(foundBean -> Activity.class.isAssignableFrom(foundBean.getClass()))
                .map(foundBean -> (Activity) foundBean)
                .orElseThrow(() -> new IllegalArgumentException("Type " + typeName + " is not supported "));
    }

}
