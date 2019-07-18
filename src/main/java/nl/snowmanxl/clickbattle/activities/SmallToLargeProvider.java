package nl.snowmanxl.clickbattle.activities;

import org.springframework.stereotype.Component;

@Component
public class SmallToLargeProvider implements ActivityProvider<SmallToLarge> {

    @Override
    public Class<SmallToLarge> getTypeOfProvider() {
        return SmallToLarge.class;
    }

    @Override
    public SmallToLarge getNewActivity() {
        return new SmallToLarge();
    }
}
