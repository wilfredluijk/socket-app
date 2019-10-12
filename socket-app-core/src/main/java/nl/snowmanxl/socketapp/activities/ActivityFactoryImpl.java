package nl.snowmanxl.socketapp.activities;

import nl.snowmanxl.socketapp.game.ActivityType;
import nl.snowmanxl.socketapp.game.ClickRace;
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
