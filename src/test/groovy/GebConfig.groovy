import io.github.bonigarcia.wdm.ChromeDriverManager
import io.github.bonigarcia.wdm.FirefoxDriverManager
import io.github.bonigarcia.wdm.PhantomJsDriverManager
import org.openqa.selenium.Dimension
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver

println "Executing gebconfig..."

/////////////////////////////////////////////////////////////////////////////////////////////////////////
// Standard config loading block
//
File configFile = new File("settings.groovy")
if(!configFile.exists()) {
    configFile = new File("../settings.groovy")
    if(!configFile.exists()) {
        throw new RuntimeException("Failed to find a settings.groovy anywhere")
    }
}

println "Loading config from ${configFile.getAbsolutePath()}"
def config = new ConfigSlurper().parse(configFile.toURI().toURL())
println config

////////////////////////////////////////////////////////////////////////////////////////////////////////

if (!config.containsKey("browserType") || config.browserType == "headless") {
    println "Setting up headless mode with phantomjs path ${config.phantomjsPath}"
    System.setProperty("phantomjs.binary.path", (String)config.phantomjsPath)
    driver = {
        PhantomJsDriverManager.getInstance().setup()
        def pjsDriver = new PhantomJSDriver()
        pjsDriver.manage().window().size = new Dimension(1680, 1050)
        return pjsDriver
    }
} else if(config.browserType == "firefoxLegacy") {
    println "Setting up firefox (<=41) mode"
    driver = {
        new FirefoxDriver()
    }
} else if(config.browserType == "firefox") {
    println "Setting up firefox (41+) mode with geckodriver path ${config.geckoDriverPath}"
    System.setProperty("webdriver.gecko.driver", (String)config.geckoDriverPath)
    driver = {
        FirefoxDriverManager.getInstance().setup()
    }
} else if(config.browserType == "chrome") {
    println "Setting up chrome mode with chromedriver path ${config.chromeDriverPath}"
    System.setProperty("webdriver.chrome.driver", (String)config.chromeDriverPath)
    driver = {
        ChromeDriverManager.getInstance().setup()
        new ChromeDriver()
    }
}

atCheckWaiting = true
baseUrl = "http://localhost:8080/"
reportsDir = "build/geb-reports"
autoClearCookies=true

waiting {
    timeout = 6
    retryInterval = 0.5

    presets {
        slow {
            timeout = 12
        }
        reallySlow {
            timeout = 24
        }
    }
}