package big.in.imageupload;

/**
 * Created by sonug on 3/28/2018.
 */

public class ImgData {


    public String name;
    public String url;

    public ImgData()
    {

    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ImgData(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
