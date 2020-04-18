package co.bk.task.jackson.example.nestedmap;

import java.util.Map;

public class PojoWithNestedMapLevelOne {

    //Map<String, Object> channelActivityStatusLookup = new HashMap();
    //Map<String, Object> activityStatusToTasks = new HashMap();
    Map<String, Map> activityStatusToTasks;

    //Map<String, Map<String,<Map<String,Integer>>>> channelActivityStatusLookup = new HashMap();
    //Map<String, Map<String,<Map<String,Integer>>>> channelActivityStatusLookup = new HashMap();

    public PojoWithNestedMapLevelOne(Map<String, Map> activityStatusToTasks) {
        this.activityStatusToTasks = activityStatusToTasks;
    }

}
