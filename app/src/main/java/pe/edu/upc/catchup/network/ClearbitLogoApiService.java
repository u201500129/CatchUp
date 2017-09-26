package pe.edu.upc.catchup.network;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Proyecto on 16/09/2017.
 */

public class ClearbitLogoApiService {
    public static String LOGO_BASE_URL = "https://logo.clearbit.com/";

    public static String getUrlToLogo(String url) {
        try {
            return LOGO_BASE_URL + new URI(url).getHost();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return LOGO_BASE_URL + url;
        }
    }

}
