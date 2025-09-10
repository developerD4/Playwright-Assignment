package assignment;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import org.testng.annotations.BeforeSuite;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class MainClass {

private Playwright play;
private Browser brows;
private Page page;
final String userID = "mngr633419";
final String password = "tyrYhar";

@BeforeClass
	public void setupClass() throws IOException{
		play = Playwright.create();
		brows = play.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)); 
		CsvUtility.init("test-report.csv");
	}

@BeforeMethod
	public void setupTest() {
		page = brows.newPage();
		page.navigate("https://demo.guru99.com/");
	}

@Test
	public void testPageTitle() throws IOException {
		String className = this.getClass().getSimpleName();
		//checks title
		String title = page.title();
		Assert.assertTrue(title.contains("Guru99 Bank Home Page"), "Title mismatched!");
		
		try {
		//click Bank project
			Locator link = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Bank Project"));
			Assert.assertTrue(link.isVisible(), "Not found");
			link.click();
			System.out.println("Button Clicked");
			CsvUtility.logResult(className, "Button Visible", true, "Button Clicked");
		}catch(AssertionError e) {
			CsvUtility.logResult(className, "Button Visible", false, e.getMessage());
		}
		
		//invalid login credentials
		Locator mail = page.locator("input[type = 'text']");
		mail.fill("asdf");
		Locator secretCode = page.locator("input[type = 'password']");
		secretCode.fill("fdsa");
	
		//click login button
		Locator logResultin = page.locator("input[name = 'btnLogin']");
		logResultin.click();
		
		//handle alerts window
		page.onDialog(dialogResult -> {
			System.out.println("Alert message: " + dialogResult.message());
			dialogResult.accept();
		}); 
		
		//fill login credentials
		Locator email = page.locator("input[type = 'text']");
		email.fill(userID);
		Locator pwd = page.locator("input[type = 'password']");
		pwd.fill(password);
		//	 page.waitForTimeout(2000);
		
		//click logtin button
		Locator newlogResultin = page.locator("input[name = 'btnLogin']");
		newlogResultin.click();
		
		//select new customer
		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("New Customer")).click();
		
        // Customer Name
        try {
            Locator custName = page.locator("input[name='name']");
            custName.fill("Tester");
            Assert.assertEquals(custName.inputValue(), "Tester");
            CsvUtility.logResult(className, "Customer Name", true, "Entered correctly");
        } catch (AssertionError e) {
            CsvUtility.logResult(className, "Customer Name", false, e.getMessage());
        }

        // Gender
        try {
            Locator gender = page.getByRole(AriaRole.RADIO).first();
            Assert.assertTrue(gender.isChecked());
            CsvUtility.logResult(className, "Gender", true, "Selected correctly");
        } catch (AssertionError e) {
            CsvUtility.logResult(className, "Gender", false, e.getMessage());
        }

        // DOB
        try {
            Locator dob = page.locator("input[name='dob']");
            dob.fill("2025-09-10");
            Assert.assertEquals(dob.inputValue(), "2025-09-10");
            CsvUtility.logResult(className, "DOB", true, "Entered correctly");
        } catch (AssertionError e) {
            CsvUtility.logResult(className, "DOB", false, e.getMessage());
        }

        // Address
        try {
            Locator address = page.locator("textarea[name='addr']");
            address.fill("winvinaya foundations, Bangalore");
            Assert.assertTrue(address.inputValue().contains("winvinaya foundations"));
            CsvUtility.logResult(className, "Address", true, "Entered correctly");
        } catch (AssertionError e) {
            CsvUtility.logResult(className, "Address", false, e.getMessage());
        }

        // City
        try {
            Locator city = page.locator("input[name='city']");
            city.fill("Bengaluru");
            Assert.assertNotNull(city.inputValue());
            CsvUtility.logResult(className, "City", true, "Entered correctly");
        } catch (AssertionError e) {
            CsvUtility.logResult(className, "City", false, e.getMessage());
        }

        // State
        try {
            Locator state = page.locator("input[name='state']");
            state.fill("Karnataka");
            Assert.assertEquals(state.inputValue(), "Karnataka");
            CsvUtility.logResult(className, "State", true, "Entered correctly");
        } catch (AssertionError e) {
            CsvUtility.logResult(className, "State", false, e.getMessage());
        }

        // Pincode
        try {
            Locator pincode = page.locator("input[name='pinno']");
            pincode.fill("123456");
            Assert.assertEquals(pincode.inputValue(), "123456");
            CsvUtility.logResult(className, "Pincode", true, "Entered correctly");
        } catch (AssertionError e) {
            CsvUtility.logResult(className, "Pincode", false, e.getMessage());
        }

        // Phone
        try {
            Locator phone = page.locator("input[name='telephoneno']");
            phone.fill("1234567890");
            Assert.assertEquals(phone.inputValue(), "1234567890");
            CsvUtility.logResult(className, "Phone", true, "Entered correctly");
        } catch (AssertionError e) {
            CsvUtility.logResult(className, "Phone", false, e.getMessage());
        }

        // Email
        try {
            Locator emailaddr = page.locator("input[name='emailid']");
            emailaddr.fill("abc@mail.com");
            Assert.assertEquals(emailaddr.inputValue(), "abc@mail.com");
            CsvUtility.logResult(className, "Email", true, "Entered correctly");
        } catch (AssertionError e) {
            CsvUtility.logResult(className, "Email", false, e.getMessage());
        }
		
		page.waitForTimeout(5000);
}
@AfterMethod
	public void tearDownTest() {
		page.close();
	}

@AfterClass
	public void tearDownClass() throws IOException {
		brows.close();
		play.close();
		CsvUtility.close();
}
}
