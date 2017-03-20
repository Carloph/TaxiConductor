package com.taxiconductor.DirectionMaps;

import java.util.List;

/**
 * Created by carlos on 22/02/17.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}