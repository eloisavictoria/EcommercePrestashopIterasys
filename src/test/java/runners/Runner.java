package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src\\test\\resources\\features\\compra_produtos.feature", //onde estao os passos
        glue = "steps", //onde é a classe com os passos implementados e validados
        tags = "@fluxopadrao", //qual cenario quero executar
        plugin = {"pretty", "html:target/cucumber.html", "json:target/cucumber.json", "junit:target/cucumber.xml"}, //como será mostrado os dados após a execução, relatório de execução
        //gera relatorio em html, json e junit
        monochrome = true //mostrar a execução sem ser colorida
)
public class Runner {

}
