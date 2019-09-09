package co.bk.task.jackson;


public class MyPojo {

  private String id;

  private MyPojoNested data;

  private String reply;

  private String socket;

  private String type;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public MyPojoNested getData() {
    return data;
  }

  public void setData(MyPojoNested data) {
    this.data = data;
  }

  public String getReply() {
    return reply;
  }

  public void setReply(String reply) {
    this.reply = reply;
  }

  public String getSocket() {
    return socket;
  }

  public void setSocket(String socket) {
    this.socket = socket;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
