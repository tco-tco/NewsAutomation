import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DenetimNewsManager extends NewsManager{
    DenetimNews denetimNews = new DenetimNews();
    public String typeOfNews = "denetim";

    DBInfo dbinfo = new DBInfo();
    
    
    /** 
     * Overrides the abstract class. Scrapes the information on the news page like title, views, date, etc... according the information required.
     * Formats the scraped information and inserts in the database.
     * Additionally scrapes only news that contains "denetim" word in the news text.
     * @param selectedIl database name and related database url uses city name  
     * @param tableName comprises of the city name and the news type (all, denetim etc..)
     * @param denetimNews.haberText news text 
     */
    @Override
    public void dataScraper(WebDriver driver, String selectedIl) throws InterruptedException{  
        String tableName = selectedIl + "_"+ (typeOfNews);
        String databaseUrl = dbinfo.getDbUrlWithDbName() + selectedIl;
        System.out.println(dbinfo.getDbUrlWithDbName());
        System.out.println("database url:"+databaseUrl);
        System.out.println("database table:"+tableName);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
        List<WebElement> arsivList = driver.findElements(By.className("arsivDetayLink"));
        ArrayList <String> urlList = new ArrayList<>();
        for (WebElement arsiv:arsivList){
                urlList.add(arsiv.getAttribute("href"));
        }
        System.out.println(urlList);

        for (String url:urlList){

            WebDriver driverTab = new ChromeDriver();
            
            
            news.haberUrl = url;
            driverTab.get(news.haberUrl );
            driverTab.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
            Thread.sleep(1000);
            
            urlNofilter(news.haberUrl , news);
            haberBaslıgıexists(news.haberUrl , driverTab, news);

            if (news.hata == "sayfa hata verdi"){  
                ArrayList <?> rowList = new ArrayList<>(Arrays.asList(null, null, null, null, news.haberUrl, news.haberUrlNo, null, news.hata));
                System.out.println(rowList); 
            }else if(driverTab.findElement(By.xpath("//h1[@class='page-title black']")).getText().equals("")){
                System.out.println("başlık açılmadı");   
                driverTab.get(news.haberUrl);
                driverTab.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
                Thread.sleep(10000);
            }else{
                String title = driverTab.findElement(By.xpath("//h1[@class='page-title black']")).getText();
                news.haberBaslıgı = title;
                System.out.println(news.haberBaslıgı);

                String date = driverTab.findElement(By.xpath("//span[@class='itemDateCreated']")).getText();
                news.haberTarihi = date;

                String pageView = driverTab.findElement(By.xpath("//span[@class='itemAuthor']")).getText();
                System.out.println(pageView);
                pageView = pageView.replace("/", "");
                String[] viewList = pageView.split(":");
                pageView = viewList[1].strip();
                news.gosterimSayısı = Integer.parseInt(pageView);
                System.out.println(news.gosterimSayısı);

                String text = driverTab.findElement(By.cssSelector("[class*=\"ExternalClass\"]")).getText();
                text=text.strip();
                denetimNews.haberText = text;

                WebElement image = driverTab.findElement(By.xpath("//span[@class='hoverBorderWrapper']/img"));
                String imageUrl = image.getAttribute("src");
                
                news.imgUrl = imageUrl;

                //ArrayList <?> rowList = new ArrayList<>(Arrays.asList(news.id, news.haberBaslıgı, news.haberTarihi, news.gosterimSayısı, denetimNews.haberText, news.haberUrl, news.haberUrlNo, news.imgUrl, null));  
                //System.out.println(rowList);
                if (denetimNews.haberText.contains("denetim")){
                   wordCounter(text);
                   //denetimNews.finalList.add(rowList); 
                   
                    DataBaseManager.insertValuePartial(dbinfo, databaseUrl, tableName, news.haberBaslıgı, news.haberTarihi, news.gosterimSayısı, denetimNews.haberText, news.haberUrl, news.haberUrlNo, news.imgUrl, denetimNews.countOfDenetimWords, news.hata );

                }
            }

            driverTab.quit(); 
        }

    }
    
    
    /** 
     * Counts words in the news text,
     * @param haberText
     */
    public void wordCounter(String haberText){
        ArrayList <String> denetimWordList = new ArrayList<>();
        String[] haberWordList = haberText.split(" ");
        for (String haber:haberWordList){
            if (haber.contains("denetim")){
                denetimWordList.add(haber);
            }
        }
        denetimNews.countOfDenetimWords = denetimWordList.size();
        //return countOfDenetimWords;
        System.out.println(denetimNews.countOfDenetimWords);
        


        

    }
}