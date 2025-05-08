package Broken_Linkcheck;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class CheckBrokenLink {
	public static void main(String[] args) throws IOException {
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.get("https://www.jumia.com.ng");
		driver.manage().window().maximize();

		// capture all links for the website
		List<WebElement> links = driver.findElements(By.tagName("a"));
		int noofBrokenlinks = 0;
		for (WebElement LinkElement : links) {
			String hrefattVlue = LinkElement.getDomAttribute("href");

			if (hrefattVlue == null || hrefattVlue.isEmpty()) {
				System.out.println(" href atribute value is null or empty. so not possible to check ");
				continue;
			}
			// fix: skip non-http(s) links to avoid MalformedURLException
			if (!hrefattVlue.startsWith("http")) {
				System.out.println(" skipping non-http link: " + hrefattVlue);
				continue;
			}
			// hit Url to the server
			try {
				URL linkurl = new URL(hrefattVlue); // convert href value from string to URL format
				HttpURLConnection conn = (HttpURLConnection) linkurl.openConnection(); // open connection to server
				conn.connect(); // connect to server and send request
				if (conn.getResponseCode() >= 400) {
					System.out.println(hrefattVlue + " it is Broken Link :- " + conn.getResponseCode());
					noofBrokenlinks++;
				} else {
					System.out.println(hrefattVlue + " it is not Broken Link :-" + conn.getResponseCode());
				}
			} catch (IOException e) {
				System.out.println(" Exception for: " + hrefattVlue + " => " + e.getMessage());
			}
			System.out.println("Number of the brokenlink " + noofBrokenlinks);

		}

	}
}
