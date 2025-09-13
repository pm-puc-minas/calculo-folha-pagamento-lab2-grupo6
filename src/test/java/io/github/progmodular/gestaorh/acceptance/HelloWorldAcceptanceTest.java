package io.github.progmodular.gestaorh.acceptance;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HelloWorldAcceptanceTest extends BaseTest {

    @Test
    public void testHelloWorldTextExistsInBody() {
        // Agora use a variável serverPort para a URL
        driver.get("http://localhost:" + serverPort + "/hello-world");

        WebElement helloWorldText = driver.findElement(By.xpath("//body//*[contains(text(), 'Hello World')]"));

        assertNotNull(helloWorldText, "O texto 'Hello World' não foi encontrado no corpo da página.");
    }
}