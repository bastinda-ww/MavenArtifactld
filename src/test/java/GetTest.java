import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.json.JSONException;
import ru.sbt.qa.model.CalcResponse;
import ru.sbt.qa.model.FromXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;

public class GetTest {
//пример теста
    /*
    @Test
    public void getRequestFindCapital() throws JSONException {
        // выполняем запрос get для доступа ко всем параметрам ответа
        Response resp = RestAssured.get("http://localhost:8888/calc?operand1=2&operation=plus&operand2=2");
        JSONObject jsonResponse = new JSONObject(resp.asString());
        // получение параметра result
        double capital = jsonResponse.getDouble("result");
        // проверка, na sootvetstvie parametrov
        AssertJUnit.assertEquals(capital, 4.0);
    }
*/
//пытался создать класс, но видимо что-то не так делаю
public static FromXML get (String str) throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(FromXML.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    return (FromXML) jaxbUnmarshaller.unmarshal(new StringReader(str));
}
    @Test
    public void minusTest() throws JSONException {
        Response resp = RestAssured.get("http://localhost:8888/calc?operand1=5&operation=minus&operand2=7");
        JSONObject jsonResponse = new JSONObject(resp.asString());
        double capital = jsonResponse.getDouble("result");
        Assert.assertEquals(capital, -2.0);
    }
    @Test
    public void multiTest() throws JSONException {
        Response resp = RestAssured.get("http://localhost:8888/calc?operand1=5&operation=mult&operand2=7");
        JSONObject jsonResponse = new JSONObject(resp.asString());
        double capital = jsonResponse.getDouble("result");
        Assert.assertEquals(capital, 35.0);
    }

    @Test
    public void divTest() throws JSONException {
        Response resp = RestAssured.get("http://localhost:8888/calc?operand1=5&operation=div&operand2=7");
        JSONObject jsonResponse = new JSONObject(resp.asString());
        double capital = jsonResponse.getDouble("result");
        Assert.assertEquals(capital, 0.7142857142857143);
    }

    @Test
    public void plusTest() throws JSONException {
        Response resp = RestAssured.get("http://localhost:8888/calc?operand1=5&operation=plus&operand2=-7");
        JSONObject jsonResponse = new JSONObject(resp.asString());
        double capital = jsonResponse.getDouble("result");
        Assert.assertEquals(capital, -2);
    }

//неверно задан операнд
//ошибка обрабатывается на уровне калькулятора, который при данных аргументах возвращает null
    @Test
    public void letterTest() throws JSONException {
        Response resp = RestAssured.get("http://localhost:8888/calc?operand1=d&operation=div&operand2=7");
        JSONObject jsonResponse = new JSONObject(resp.asString());
        Object capital = jsonResponse.get("result");
        Assert.assertEquals(capital, null);
    }

//такой же запрос как и в предыдущем тесте, но проверяем поле errorDescription
    @Test
    public void letterTest2() throws JSONException {
        Response resp = RestAssured.get("http://localhost:8888/calc?operand1=d&operation=div&operand2=7");
        JSONObject jsonResponse = new JSONObject(resp.asString());
        Object capital = jsonResponse.get("errorDescription");
        Assert.assertEquals(capital, "Операнд 1 задан неверно. ");
    }

//такой же запрос как и в предыдущем тесте, но проверяем поле success
    @Test
    public void letterTest3() throws JSONException {
        Response resp = RestAssured.get("http://localhost:8888/calc?operand1=d&operation=div&operand2=7");
        JSONObject jsonResponse = new JSONObject(resp.asString());
        Object capital = jsonResponse.get("success");
        Assert.assertEquals(capital, false);
        System.out.println("Error: " +jsonResponse.get("errorDescription"));
    }

    @Test
    public void divByZeroTest() throws JSONException {
        Response resp = RestAssured.get("http://localhost:8888/calc?operand1=7&operation=div&operand2=0");
        JSONObject jsonResponse = new JSONObject(resp.asString());
        Object capital = jsonResponse.get("result");
        Assert.assertEquals(capital, null);
    }

    @Test
    public void divByZeroTest2() throws JSONException {
        Response resp = RestAssured.get("http://localhost:8888/calc?operand1=7&operation=div&operand2=0");
        JSONObject jsonResponse = new JSONObject(resp.asString());
        Object capital = jsonResponse.get("success");
        Assert.assertEquals(capital, false);
        System.out.println("Error: " +jsonResponse.get("errorDescription"));
    }


    @Test
    public  void testJackson() throws IOException {

        Response response  = RestAssured.get("http://localhost:8888/calc?operand1=2&operation=div&operand2=0");

        ObjectMapper objectMapper = new ObjectMapper();

        CalcResponse calcResponse = objectMapper.readValue(response.getBody().asString(), CalcResponse.class);
        boolean res = calcResponse.getSuccess();
        Assert.assertEquals(false, res);
        System.out.println("Error: " +calcResponse.getErrorDescription());
    }

//нерабочий тест
    @Test
    public void newTest () throws IOException, JAXBException {
        Response response  =   RestAssured.given().body("{\"operand1\": \"2\", \"operation\": \"mult\",\"operand2\": \"2\"}").post("http://localhost:8888/calc");
        FromXML forNewTest = get(response.getBody().asString());
        System.out.println(response.getBody().asString());
    }

}