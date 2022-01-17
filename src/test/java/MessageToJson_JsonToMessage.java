import com.kikichante.utils.MapperMessage;
import com.kikichante.utils.Message;

public class MessageToJson_JsonToMessage {

    public static void main(String[] args) {
        MapperMessage mapperMessage = new MapperMessage();
        Message message = new Message("test", 1, "test");

        String objString = mapperMessage.objectToJson(message);
        System.out.println("objString = " + objString);

        Message message1 = mapperMessage.jsonToObject(objString);
        System.out.println(message1.toString());
    }
}
