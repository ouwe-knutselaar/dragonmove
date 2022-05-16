import dragonmove.config.Config;

public class ConfigRead {

    public static Config readConfig(){
        String os = System.getProperty("os.name");
        String configfile = "/opt/dragonmove/src/test/resources/testconfig.yaml";
        if(os.startsWith("Windows")){
            System.out.println("It is a windows system");
            configfile= "D:\\erwin\\dragonmove\\src\\test\\resources\\testconfig.yaml";
        }
        return new Config(configfile);
    }

    public static String readServoConfig(){
        String os = System.getProperty("os.name");
        String configfile = "/opt/dragonmove/src/test/resources/servotestconf.yaml";
        if(os.startsWith("Windows")){
            System.out.println("It is a windows system");
            configfile= "D:\\erwin\\dragonmove\\src\\test\\resources\\servotestconf.yaml";
        }
        return configfile;
    }

}
