package ufc.quixada.npi.contest.model;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";
    // retorna o caminho do arquivo
    public String getLocation() {
        return location;
    }
    // define o caminho do arquivo
    public void setLocation(String location) {
        this.location = location;
    }

}
