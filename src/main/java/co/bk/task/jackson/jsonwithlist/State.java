package co.bk.task.jackson.jsonwithlist;


import java.util.List;

public class State {

    private String state_name;

    private List<Action> actions;

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
