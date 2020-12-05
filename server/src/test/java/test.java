import com.google.gson.Gson;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {

    @Test
    public void tests() {
        Gson gson = new Gson();
//        Student s = new Student("asd","asd");
        Student s = gson.fromJson("{'name' : 'asdasd' , 'password' : 'asd'}", Student.class);
        System.out.println(s.name);
    }
}
