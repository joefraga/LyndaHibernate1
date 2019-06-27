package ejemplo;
import java.io.Serializable;

public class Message implements Serializable{
	
	public Message() {
		this.message = "";
	}
	
	public Message(String message) {
		this.message = message;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private Integer id;
	private String message;
	

}
