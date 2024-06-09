public class Main {
    public static void main(String[] args) throws Exception {

        // ilListTam (81 il) & ilListKismi (selected ones) options will be applied here.
        IlList ilList = new IlList();
        System.out.println(ilList.ilListKismi);




        for (String selectedIl: ilList.ilListKismi){
            System.out.println(selectedIl);

            
            denetimNewsOps(selectedIl);

        }

  
       
    }    

    public static void denetimNewsOps(String selectedIl ) throws InterruptedException{
            DataBaseManager databaseManager = new DataBaseManager();
            DBInfo dbinfo = new DBInfo();
            DenetimNewsManager denetimNewsManager = new DenetimNewsManager();

            databaseManager.databaseUrl = dbinfo.getDbUrlWithDbName() + selectedIl;
            String tableName = selectedIl + "_"+ (denetimNewsManager.typeOfNews);
            System.out.println(tableName);
            databaseManager.createDB(dbinfo, selectedIl);
            DataBaseManager.createTablePartial(dbinfo, databaseManager.databaseUrl, tableName);
            
            denetimNewsManager.pageChanger(denetimNewsManager.driver, selectedIl);
    }
    public static void allNewsOps(String selectedIl ) throws InterruptedException{
            DataBaseManager databaseManager = new DataBaseManager();
            DBInfo dbinfo = new DBInfo();
            AllNewsManager allNewsManager = new AllNewsManager();

            databaseManager.databaseUrl = dbinfo.getDbUrlWithDbName() + selectedIl;
            String tableName = selectedIl + "_"+ (allNewsManager.typeOfNews);
            System.out.println(tableName);
            databaseManager.createDB(dbinfo, selectedIl);
            DataBaseManager.createTableAll(dbinfo, databaseManager.databaseUrl, tableName);
            
            allNewsManager.pageChanger(allNewsManager.driver, selectedIl);
    }
}
