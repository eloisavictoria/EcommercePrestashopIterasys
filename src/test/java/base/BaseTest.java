package base;

import com.google.common.io.Files;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import pages.HomePage;

import java.io.File;
import java.io.IOException;


public class BaseTest {

    private static WebDriver driver;//faz interação com o navegarodr
    protected HomePage homePage;

    @BeforeAll//JUnit 5
    public static void inicializar(){
        System.setProperty("webdriver.opera.driver", "C:\\webdriver\\operadriver\\81\\operadriver.exe");
        driver = new OperaDriver();

    }
    @BeforeEach
    public void carregarPaginaInicial(){
        driver.get("https://marcelodebittencourt.com/demoprestashop/");
        homePage= new HomePage(driver);

    }

    public void capturaTela(String nomeTeste, String resultado){
        TakesScreenshot camera = (TakesScreenshot) driver;//TakesScreenshot vem do selenium
        File capturaDeTela = camera.getScreenshotAs(OutputType.FILE);
        try {
            Files.move(capturaDeTela, new File("resources/screenshots/"+ nomeTeste + "_" + resultado + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AfterAll
    public static void finalizar(){

        driver.quit();
    }
}
