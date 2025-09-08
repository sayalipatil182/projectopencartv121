package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {
     
    public  static WebDriver driver;
    public Logger logger;   // logger declaration
    public Properties p;
    @BeforeClass(groups= {"master","sanity","regression"})
    @Parameters({"os", "browser"})
    public void setup(@Optional("windows") String os, @Optional("chrome") String br) throws IOException {
    	
    	        // Load properties file	
    	        p = new Properties();
    	       FileReader file = new FileReader(".//src/test/resources/config.properties");
    	      p.load(file);
    
        logger = LogManager.getLogger(this.getClass());
        
           if(p.getProperty("execution_env").equalsIgnoreCase("remote")){
        	   
        	   DesiredCapabilities cap= new DesiredCapabilities(); 
        	   
				if (os.equalsIgnoreCase("windows")) {
					cap.setPlatform(Platform.WIN11);
				} else if (os.equalsIgnoreCase("mac")) {
					cap.setPlatform(Platform.MAC);
				} else if (os.equalsIgnoreCase("linux")) {
					cap.setPlatform(Platform.LINUX);
				}
				
				else {
					
					System.out.println("no matching os");
				}
				
				if (br.equalsIgnoreCase("chrome")) {
					cap.setBrowserName("chrome");
				} else if (br.equalsIgnoreCase("firefox")) {
					cap.setBrowserName("firefox");
				} else if (br.equalsIgnoreCase("edge")) {
					cap.setBrowserName("edge");
				} else {
					System.out.println("no matching browser");
				}
				
        	   
        	  driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
        	   
           }
           
			if (p.getProperty("execution_env").equalsIgnoreCase("local")) {

				if (br.equalsIgnoreCase("chrome")) {
					driver = new ChromeDriver();
					logger.info("Launching Chrome browser");
				} else if (br.equalsIgnoreCase("firefox")) {
					driver = new FirefoxDriver();
					logger.info("Launching Firefox browser");
				} else if (br.equalsIgnoreCase("edge")) {
					driver = new EdgeDriver();
					logger.info("Launching Edge browser");
				} else {
					System.out.println("No matching browser found, launching Chrome as default.");
					driver = new ChromeDriver();
				}
			}
           
        
        // Initialize WebDriver
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        
        // Open your AUT URL
        driver.get(p.getProperty("appURL"));
        logger.info("Launched application URL: http://localhost/opencart/");
    }

    @AfterClass(groups= {"master","sanity","regression"})
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed successfully.");
        }
    }

    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumber() {
        return RandomStringUtils.randomNumeric(8);
    }
    
	public String captureScreen(String tname) throws IOException {
		String timeStamp= new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts= (TakesScreenshot) driver;
		
		File sourceFile= ts.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
		
		String targetFilePath= System.getProperty("user.dir")+ "\\screenshots\\"+tname+"_"+timeStamp+".png";
		File targetFile= new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}
}
