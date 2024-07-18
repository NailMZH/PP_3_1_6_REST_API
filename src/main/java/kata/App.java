package kata;

import kata.config.MyConfig;
import kata.controller.RestTemplateController;
import kata.models.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Arrays;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        RestTemplateController controller = context.getBean("restTemplateController", RestTemplateController.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        headers = controller.getSession_id(headers); // сдесь прикрепляется cookie c session_id
        System.out.println("Cookie после добавления User:\n" + headers.get("Cookie"));

        //создаем нового юзера
        HttpEntity<User> entity = new HttpEntity<>(new User(3L, "James", "Brown", (byte) 40), headers);
        //записываем нового юзера
        controller.saveUser(entity);

        //создаем юзера для замены
        HttpEntity<User> entityUpdate = new HttpEntity<>(new User(3L, "Thomas", "Shelby", (byte) 35), headers);
        //меняем юзера
        controller.updateUser(entityUpdate);

        //удаляем юзера

        controller.deleteUser(entityUpdate);
        //печатаем общий результат
        System.out.println("Ресультат:\n" + controller.joinAnswer.toString());
    }
}
