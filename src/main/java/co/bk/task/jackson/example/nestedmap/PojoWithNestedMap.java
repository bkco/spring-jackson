package co.bk.task.jackson.example.nestedmap;

import java.util.Map;

// IMPORTANT: add getter/setter for each field; constructor NOT required!!!
/**
 * {
 *     "test_key": "helloWorld",
 *     "channelToActivityStatus": {
 *         "name":"JavaInterviewPoint",
 *         "department":"IT"
 *     }
 * }
 *
 */
public class PojoWithNestedMap {

    public String getTest_key() {
        return test_key;
    }

    public void setTest_key(String test_key) {
        this.test_key = test_key;
    }

    private String test_key;



    //Map<String, Map> channelToActivityStatus;
    Map<String, String> channelToActivityStatus;

    // GOAL
    //Map<String, Map<String,<Map<String,Integer>>>> channelActivityStatusLookup = new HashMap();

//    //public PojoWithNestedMap(Map<String, Map> channelToActivityStatus) {
//    public PojoWithNestedMap(Map<String, String> channelToActivityStatus) {
//        this.channelToActivityStatus = channelToActivityStatus;
//    }

    public Map<String, String> getChannelToActivityStatus() {
        return channelToActivityStatus;
    }

    public void setChannelToActivityStatus(Map<String, String> channelToActivityStatus) {
        this.channelToActivityStatus = channelToActivityStatus;
    }

}
