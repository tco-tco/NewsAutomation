import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

abstract class NewsManager {
    
    public String selectedIl;
    WebDriver driver = new ChromeDriver();
    DataBaseManager dataBaseManager = new DataBaseManager();
    DBInfo dbInfo = new DBInfo();
    News news = new News();
    
    /** This method opens the url of the news page directly, by using @param selectedIl.
     * Then clicks the news urls one by one, then changes the pages.
     * @param selectedIl city name from the list of cities indicated as "ilListKismi" list
     */

    public void pageChanger(WebDriver driver, String selectedIl) throws InterruptedException{
            driver.get("https://"+ selectedIl +".tarimorman.gov.tr/HaberArsivi");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
            
            while (true){
                String nextPageGroup = ">>";
                String previousPageGroup = "<<";
                List<WebElement> buttons=driver.findElements(By.xpath("//span[@class='btn-group pager-buttons']//input"));     
                dataScraper(driver, selectedIl);  
                
                
                for (int pageNum=1; pageNum<buttons.size()+1; pageNum++){
                    WebElement pageButton = driver.findElement(By.xpath("//span[@class='btn-group pager-buttons']//input[" + pageNum + "]"));
                    String pageButtonValue = pageButton.getAttribute("value");
                    if (pageButtonValue.equals(previousPageGroup)){
                        System.out.println("<< var");  
                        continue;                                      
                    }

                    else if (pageButtonValue.equals(nextPageGroup)){
                        System.out.println(">> var");    
                        continue;                                  
                    }

                    else{
                        pageButton.click();
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
                        Thread.sleep(1000);
                        System.out.println("sayfa değişti");
                        dataScraper(driver, selectedIl);                                       
                    }
                } 
                List<WebElement> buttons2=driver.findElements(By.xpath("//span[@class='btn-group pager-buttons']//input"));
                ArrayList <String> pageList = new ArrayList<>();

                for (WebElement button2:buttons2){
                        pageList.add(button2.getAttribute("value"));
                }

                String pageGroup=pageList.get(pageList.size()-1);
                
                if (pageGroup.equals(nextPageGroup)){
                        driver.findElement(By.xpath("//span[@class='btn-group pager-buttons']//input["+ buttons.size()+"]")).click();
                        Thread.sleep(600);
                        System.out.println("sayfa grubu değişti");
                        
                }
                else{
                        break;
                }
            }
    }


    public abstract void dataScraper(WebDriver driver, String selectedIl)throws InterruptedException;
        

    
    /** 
     * Takes the url of the news and filters the number of the news embedded in the url.
     * @param url   url of the news 
     * @param news.haberUrlNo   output of the filtering operations
     * 
     */
    public void urlNofilter(String url, News news) throws InterruptedException{
        
        ArrayList <String> urlSplitList = new ArrayList<> (Arrays.asList(url.split("/")));

        int indexHaber = urlSplitList.indexOf("Haber");
        String urlNoString = urlSplitList.get(indexHaber+1);
        news.haberUrlNo = Integer.parseInt(urlNoString);
        //System.out.println(news.haberUrlNo);       
    }


    
    /** Checks whether url is working without an error by controlling the "page-title" elements visibility.
     * If NoSuchElementException error occurs it means that url is broken.
     * @param url   url of the news
     * @param news.hata "sayfa hata verdi" error note can be seen in the database, in order to notify for broken url
     */
    public void haberBaslıgıexists(String url, WebDriver driverTab, News news){
        news.hata = null;
        try {
            driverTab.findElement(By.xpath("//h1[@class='page-title black']")).getText();

        }catch(NoSuchElementException NSEE){
            System.out.println(NSEE);
            news.hata = "sayfa hata verdi";
            System.out.println(news.hata);
        }
    }


}
