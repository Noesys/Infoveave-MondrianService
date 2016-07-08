/* Copyright © 2015-2016 Noesys Software Pvt.Ltd. - All Rights Reserved
 * -------------
 * This file is part of Infoveave.
 * Infoveave is dual licensed under Infoveave Commercial License and AGPL v3
 * -------------
 * You should have received a copy of the GNU Affero General Public License v3
 * along with this program (Infoveave)
 * You can be released from the requirements of the license by purchasing
 * a commercial license. Buying such a license is mandatory as soon as you
 * develop commercial activities involving the Infoveave without
 * disclosing the source code of your own applications.
 * -------------
 * Authors: Naresh Jois <naresh@noesyssoftware.com>, et al.
 */
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Filter;

import java.io.*;
import java.util.Properties;
import java.net.*;

import static spark.Spark.*;


public class MondrianService {
    public static  void main(String[] arg){
        System.out.println("Starting Mondrian Connector for Infoveave");
        Properties prop = new Properties();
        InputStream input = null;
        int listenPort = 9998;
        int threads = 100;
        try{
            System.out.println("Staring from :" + getSettingsPath());
            input = new FileInputStream(getSettingsPath() + File.separator + "MondrianService.properties");
            prop.load(input);
            listenPort = Integer.parseInt(prop.getProperty("port"));
            threads = Integer.parseInt(prop.getProperty("maxThreads"));
            if (input != null){
                input.close();
            }
            System.out.println("Found MondrianService.Properties");
        }catch (FileNotFoundException e){
        }catch (IOException e){
        }

        port(listenPort);
        threadPool(threads);
        enableCORS("*","*","*");

        ObjectMapper mapper = new ObjectMapper();
        MondrianConnector connector = new MondrianConnector();
        get("/ping", (request,response) -> {
            response.type("application/json");
            return "\"Here\"";
        });

        post("/cubes",(request,response)->{
            response.type("application/json");
            try{
                Query queryObject = mapper.readValue(request.body(),Query.class);
                response.status(200);
                return mapper.writeValueAsString(connector.GetCubes(queryObject));
            }catch (JsonParseException ex){
                response.status(400);
                return mapper.writeValueAsString(new Error(ex.getMessage()));
            }
            catch (Exception ex){
                response.status(400);
                return mapper.writeValueAsString(new Error(ex.getMessage()));
            }
        });

        post("/measures",(request,response)->{
            response.type("application/json");
            try{
                Query queryObject = mapper.readValue(request.body(),Query.class);
                response.status(200);
                return mapper.writeValueAsString(connector.GetMeasures(queryObject));
            }catch (JsonParseException ex){
                response.status(400);
                return mapper.writeValueAsString(new Error(ex.getMessage()));
            }
            catch (Exception ex){
                response.status(400);
                return mapper.writeValueAsString(new Error(ex.getMessage()));
            }
        });

        post("/dimensions",(request,response)->{
            response.type("application/json");
            try{
                Query queryObject = mapper.readValue(request.body(),Query.class);
                response.status(200);
                return mapper.writeValueAsString(connector.GetDimensions(queryObject));
            }catch (JsonParseException ex){
                response.status(400);
                return mapper.writeValueAsString(new Error(ex.getMessage()));
            }
            catch (Exception ex){
                response.status(400);
                return mapper.writeValueAsString(new Error(ex.getMessage()));
            }
        });

        post("/cleanCache",(request,response)->{
            response.type("application/json");
            try{
                Query queryObject = mapper.readValue(request.body(),Query.class);
                response.status(200);
                return mapper.writeValueAsString(connector.CleanCubeCache(queryObject));
            }catch (JsonParseException ex){
                response.status(400);
                return mapper.writeValueAsString(new Error(ex.getMessage()));
            }
            catch (Exception ex){
                response.status(400);
                return mapper.writeValueAsString(new Error(ex.getMessage()));
            }
        });

        post("/executeQuery", (request, response) -> {
            response.type("application/json");
            try {
                Query queryObject = mapper.readValue(request.body(), Query.class);
                response.status(200);
                String content = mapper.writeValueAsString(connector.ExecuteQuery2(queryObject));
                return content;
            } catch (JsonParseException ex) {
                response.status(400);
                return mapper.writeValueAsString(new Error(ex.getMessage()));
            } catch (Exception ex) {
                response.status(400);
                return mapper.writeValueAsString(new Error(ex.getMessage()));
            }
        });
    }

    private static String getSettingsPath(){
        try {
            // Get current location jar path
            String jar = MondrianService.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            // Get jar parent folder
            String path = new File(jar).getParentFile().getAbsolutePath();
            // Return path to settings file
            return path;
        } catch(URISyntaxException ex){
            ex.printStackTrace();
            return null;
        }
    }
    private static void enableCORS(final String origin, final String methods, final String headers) {
        before(new Filter() {
            @Override
            public void handle(spark.Request request, spark.Response response) {
                response.header("Access-Control-Allow-Origin", origin);
                response.header("Access-Control-Request-Method", methods);
                response.header("Access-Control-Allow-Headers", headers);
            }
        });
    }
}
