package nl.snowmanxl.clickbattle.activities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityFactoryImpl implements ActivityFactory {

    @Override
    public Activity createNewActivity(ActivityType type) {
        if (type == ActivityType.CLICK_RACE) {
            return new ClickRace();
        }
        throw new UnsupportedOperationException("Not implemented!");
    }

}
