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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mondrian.olap.*;
import mondrian.rolap.*;
import org.omg.PortableInterceptor.ServerRequestInfo;


import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class MondrianConnector {

    public MondrianConnector(){

    }

    public ArrayList<LinkedHashMap<String, String>> ExecuteQuery(Query queryObject) throws Exception{
        System.setProperty("mondrian.olap.SsasCompatibleNaming", "true");
        String connectionString = getConnectionString(queryObject);
        RolapConnection connection = (RolapConnection) DriverManager.getConnection(connectionString, null);
        mondrian.olap.Query query = connection.parseQuery(queryObject.getMdxQuery());
        Result result = connection.execute(query);
        ArrayList<LinkedHashMap<String, String>> data = new ArrayList<LinkedHashMap<String, String>>();
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
        if (result.getAxes().length == 1) {
            //Only One Axis has come so
            ArrayList<String> measures = new ArrayList<String>();
            for (Position p : result.getAxes()[0].getPositions()) {
                measures.add(p.get(0).getUniqueName().toString());
            }
            LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
            for (int i = 0; i < measures.size(); i++) {

                Object value = result.getCell(new int[]{i}).getValue();
                if (value == null){
                    row.put(measures.get(i), null);
                } else if (value instanceof Integer) {
                    row.put(measures.get(i), ((Integer) value).toString());
                } else if (value instanceof Double) {
                    row.put(measures.get(i), df.format(value));
                } else {
                    row.put(measures.get(i), value.toString());
                }
            }
            data.add(row);
        } else if (result.getAxes().length == 2) {
            ArrayList<String> measures = new ArrayList<String>();
            for (Position p : result.getAxes()[0].getPositions()) {
                measures.add(p.get(0).getUniqueName().toString());
            }
            ArrayList<ArrayList<DimensionItem>> dimensionItems = new ArrayList<ArrayList<DimensionItem>>();
            for (Position p : result.getAxes()[1].getPositions()) {
                ArrayList<DimensionItem> itemsAtRow = new ArrayList<DimensionItem>();
                for (Object item : p.toArray()) {
                    RolapMemberBase member = (RolapMemberBase) item;
                    itemsAtRow.add(new DimensionItem(member.getLevel().getHierarchy().toString(), member.getCaption().toString()));
                }
                dimensionItems.add(itemsAtRow);
            }
            for (int ix = 0; ix < dimensionItems.size(); ix++) {
                LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
                for (DimensionItem item : dimensionItems.get(ix)) {
                    row.put(item.getLevel(), item.getCaption());
                }
                for (int i = 0; i < measures.size(); i++) {
                    Object value = result.getCell(new int[]{i, ix}).getValue();
                    if (value == null) {
                        row.put(measures.get(i), "0");
                    } else {
                        if (value instanceof Integer) {
                            row.put(measures.get(i), ((Integer) value).toString());
                        } else if (value instanceof Double) {
                            row.put(measures.get(i), df.format(value));
                        } else {
                            row.put(measures.get(i), value.toString());
                        }
                    }
                }
                data.add(row);
            }
        }
        return data;
    }


    public ArrayList<ArrayList<String>> ExecuteQuery2(Query queryObject) throws Exception{
        System.setProperty("mondrian.olap.SsasCompatibleNaming", "true");
        String connectionString = getConnectionString(queryObject);
        RolapConnection connection = (RolapConnection) DriverManager.getConnection(connectionString, null);
        mondrian.olap.Query query = connection.parseQuery(queryObject.getMdxQuery());
        Result result = connection.execute(query);
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
        if (result.getAxes().length == 1) {
            //Only One Axis has come so
            ArrayList<String> measures = new ArrayList<String>();
            for (Position p : result.getAxes()[0].getPositions()) {
                measures.add(p.get(0).getUniqueName().toString());
            }
            data.add(measures);

            ArrayList<String> row = new ArrayList<String>();
            for (int i = 0; i < measures.size(); i++) {

                Object value = result.getCell(new int[]{i}).getValue();
                if (value == null){
                    row.add(null);
                } else if (value instanceof Integer) {
                    row.add(((Integer) value).toString());
                } else if (value instanceof Double) {
                    row.add(df.format(value));
                } else {
                    row.add(value.toString());
                }
            }
            data.add(row);
        } else if (result.getAxes().length == 2) {
            ArrayList<String> measures = new ArrayList<String>();
            for (Position p : result.getAxes()[0].getPositions()) {
                measures.add(p.get(0).getUniqueName().toString());
            }
            ArrayList<String> headers = new ArrayList<String>();
            ArrayList<ArrayList<DimensionItem>> dimensionItems = new ArrayList<ArrayList<DimensionItem>>();
            for (Position p : result.getAxes()[1].getPositions()) {
                ArrayList<DimensionItem> itemsAtRow = new ArrayList<DimensionItem>();
                for (Object item : p.toArray()) {
                    RolapMemberBase member = (RolapMemberBase) item;
                    itemsAtRow.add(new DimensionItem(member.getLevel().getHierarchy().toString(), member.getCaption().toString()));
                }
                dimensionItems.add(itemsAtRow);
            }
            for (int ix = 0; ix < dimensionItems.size(); ix++) {
                ArrayList<String> row = new ArrayList<String>();
                for (DimensionItem item : dimensionItems.get(ix)) {
                    if (ix == 0 ) headers.add(item.getLevel());
                    row.add(item.getCaption());
                }
                for (int i = 0; i < measures.size(); i++) {
                    if (ix ==0) headers.add(measures.get(i));
                    Object value = result.getCell(new int[]{i, ix}).getValue();
                    if (value == null) {
                        row.add("0");
                    } else {
                        if (value instanceof Integer) {
                            row.add(((Integer) value).toString());
                        } else if (value instanceof Double) {
                            row.add(df.format(value));
                        } else {
                            row.add(value.toString());
                        }
                    }
                }
                if (ix == 0) data.add(headers);
                data.add(row);
            }
        }
        return data;
    }

    private String getConnectionString(Query queryObject){
        if (queryObject.getDatabaseType().equals("mssql")) {
            return String.format("Provider=mondrian;Jdbc=jdbc:%s://%s:%s;databaseName=%s;JdbcUser=%s;JdbcPassword=%s;Catalog=%s;JdbcDrivers=%s",
                    "sqlserver",queryObject.getServer(),queryObject.getPort(),queryObject.getDatabase(),queryObject.getUsername(),queryObject.getPassword(),
                    queryObject.getSchema(),"com.microsoft.sqlserver.jdbc.SQLServerDriver");

        } else if(queryObject.getDatabaseType().equals("mysql")){
            return String.format("Provider=mondrian;Jdbc=jdbc:%s://%s:%s/%s;JdbcUser=%s;JdbcPassword=%s;Catalog=%s;JdbcDrivers=%s",
                    "mysql",queryObject.getServer(),queryObject.getPort(),queryObject.getDatabase(),queryObject.getUsername(),queryObject.getPassword(),
                    queryObject.getSchema(),"com.mysql.jdbc.Driver");
        } else if(queryObject.getDatabaseType().equals("postgres") || queryObject.getDatabaseType().equals("pgsql")){
            return String.format("Provider=mondrian;Jdbc=jdbc:%s://%s:%s/%s;JdbcUser=%s;JdbcPassword=%s;Catalog=%s;JdbcDrivers=%s",
                    "postgresql",queryObject.getServer(),queryObject.getPort(),queryObject.getDatabase(),queryObject.getUsername(),queryObject.getPassword(),
                    queryObject.getSchema(),"org.postgresql.Driver");
        } else if(queryObject.getDatabaseType().equals("sqlite")){
            return String.format("Provider=mondrian;Jdbc=jdbc:%s://%s/%s.db;Catalog=%s;JdbcDrivers=%s",
                    "sqlite",queryObject.getServer(),queryObject.getDatabase(),
                    queryObject.getSchema(),"org.sqlite.JDBC");
        }
        return "";

    }

    public ArrayList<MeasureInfo> GetMeasures(Query queryObject) throws Exception{
        String connectionString = getConnectionString(queryObject);
        RolapConnection connection = (RolapConnection)DriverManager.getConnection(connectionString, null);
        Schema schema = connection.getSchema();
        RolapCube cube = (RolapCube) schema.lookupCube(queryObject.getCube(),false);
        if (cube == null){
            throw new Exception("Cube Not Found");
        }
        ArrayList<MeasureInfo> measureInfo = new ArrayList<MeasureInfo>();
        for (RolapMember measure: cube.getMeasuresMembers()){
            if (measure.isVisible())
                measureInfo.add(new MeasureInfo(measure.getCaption(), measure.getUniqueName()));
        }
        for(Member measure:cube.getSchemaReader().getCalculatedMembers()){
            if (measure.isVisible())
                measureInfo.add(new MeasureInfo(measure.getCaption(),measure.getUniqueName()));
        }
        return measureInfo;
    }

    public ArrayList<DimensionInfo> GetDimensions(Query queryObject) throws Exception{
        String connectionString = getConnectionString(queryObject);
        RolapConnection connection = (RolapConnection)DriverManager.getConnection(connectionString, null);
        Schema schema = connection.getSchema();
        RolapCube cube = (RolapCube) schema.lookupCube(queryObject.getCube(),false);
        if (cube == null) throw new Exception("Cube Not Found");
        ArrayList<DimensionInfo> dimensionInfo = new ArrayList<DimensionInfo>();
        for(RolapHierarchy hierarchy :  cube.getHierarchyList()) {
            for(RolapLevel level: hierarchy.getLevelList()){
                if (!level.getCaption().equals("(All)") && !level.getDimension().getUniqueName().equals("[Measures]") && level.isVisible() && level.getDimension().isVisible()) {
                    String dimensionType = level.getDimension().getDimensionType().name();
                    boolean isDate = (dimensionType == "TIME") ? true : false;
                    dimensionInfo.add(new DimensionInfo(level.getDimension().getUniqueName(), level.getHierarchy().getUniqueName(), level.getCaption(), level.getHierarchy().getUniqueName(), isDate));
                }
            }
        }
        return dimensionInfo;
    }

    public ArrayList<String> GetCubes(Query queryObject) throws Exception{
        String connectionString = getConnectionString(queryObject);
        RolapConnection connection = (RolapConnection)DriverManager.getConnection(connectionString, null);
        Schema schema = connection.getSchema();
        ArrayList<String> cubes = new ArrayList<String>();
        for (Cube cube : schema.getCubes()){
            cubes.add(cube.getName());
        }
        return cubes;

    }

    public String CleanCubeCache(Query queryObject) throws Exception{
        String connectionString = getConnectionString(queryObject);
        RolapConnection connection = (RolapConnection)DriverManager.getConnection(connectionString, null);
        Schema schema = connection.getSchema();
        RolapCube cube = (RolapCube) schema.lookupCube(queryObject.getCube(),false);
        if (cube == null) throw new Exception("Cube Not Found");
        CacheControl cacheControl = connection.getCacheControl(null);
        cacheControl.flush(cacheControl.createMeasuresRegion(cube));
        cacheControl.flushSchemaCache();
        return "Cache Cleaned Successfully";
    }
}
