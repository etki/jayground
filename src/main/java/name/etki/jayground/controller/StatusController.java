package name.etki.jayground.controller;

import liquibase.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
@RestController
@RequestMapping(value = "/api/v1")
public class StatusController {
    @Autowired
    private Environment environment;
    @Autowired
    private DataSource dataSource;
    
    @RequestMapping(value = "status", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, String>> getStatus()
    {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("active-profile", StringUtils.join(environment.getActiveProfiles(), ", "));
        data.put("default-profile", StringUtils.join(environment.getDefaultProfiles(), ", "));
        ArrayList<String> urls = new ArrayList<String>(); 
        for (URL url : ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs()) {
            urls.add(url.toString());
        }
        data.put("classpath", StringUtils.join(urls, ";"));
        try {
            data.put("datasource-jdbc-url", dataSource.getConnection().getMetaData().getURL());
        } catch (SQLException e) {
            data.put("datasource-jdbc-url", String.format("Unknown (retrieval aborted due to exception: `%s`)", e.toString()));
        }
        return new ResponseEntity<HashMap<String, String>>(data, HttpStatus.OK);
    }
    
    @RequestMapping(value = "property/{name}", method = RequestMethod.GET)
    public ResponseEntity<String> getProperty(@PathVariable String name) {
        return new ResponseEntity<String>(environment.getProperty(name), HttpStatus.OK);
    }
}
