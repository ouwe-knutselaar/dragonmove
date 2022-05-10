import dragonmove.config.Config;

public class ConfigRead {

    public static Config readConfig(){
        String os = System.getProperty("os.name");
        String configfile = "/opt/dragonmove/src/test/resources/testconfig.yaml";
        if(os.startsWith("Windows")){
            configfile= "D:\\erwin\\dragonmove\\src\\test\\resources\\testconfig.yaml";
        }
        return new Config(configfile);
    }

}
