package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;
import java.util.List;

public interface RideMatchingStrategy {
    /** @throws com.airtribe.ridewise.exception.NoDriverAvailableException if no driver fits */
    Driver findDriver(Rider rider, List<Driver> drivers);
}
