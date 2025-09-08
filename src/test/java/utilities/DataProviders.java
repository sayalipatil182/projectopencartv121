package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() throws IOException {

        // relative path - works on any machine
        String path = System.getProperty("user.dir") + "\\testData\\opencartLogin.xlsx";
        System.out.println("Excel Path Used: " + path);

        ExcelUtils xlutil = new ExcelUtils(path);
        int rownum = xlutil.getRowCount("Sheet1");
        int colcount = xlutil.getCellCount("Sheet1", 1);

        Object logindata[][] = new Object[rownum][colcount];

        for (int i = 1; i <= rownum; i++) {
            for (int j = 0; j < colcount; j++) {
                logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j); 
            }
        }

        return logindata;
    }
}
