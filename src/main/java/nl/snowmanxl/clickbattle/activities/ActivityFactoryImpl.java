package nl.snowmanxl.clickbattle.activities;

import nl.snowmanxl.clickbattle.game.ActivityType;
import nl.snowmanxl.clickbattle.game.ClickRace;
import org.springframework.stereotype.Service;

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
