package co.bk.task.jackson.jsonwithlist;

// IMPORTANT: add getter/setter for each field; constructor NOT required!!!

/*
 * {
        "result_code": 0,
        "state": {
            "state_name": "Quotation created",
            "actions": [
                {
                    "action_name": "submit_quotation",
                    "meta": {
                        "permission": "supplier1:can submit quotations in own tenant"
                    }
                },
                {
                    "action_name": "delete_quotation",
                    "meta": {
                        "permission": "supplier1:can delete own quotations in own tenant"
                    }
                }
            ]
        }
    }
 */
public class JsonWithList {

    private String result_code;

    private State state;

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


}
