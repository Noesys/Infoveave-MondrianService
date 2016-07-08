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
public class Query {
    private String schema;
    private String databaseType;
    private String server;
    private int port;


    private String database;
    private String username;
    private String password;
    private String cube;
    private String mdxQuery;

    public String getSchema() {
        return schema;
    }

    public void setMdxQuery(String mdxQuery) {
        this.mdxQuery = mdxQuery;
    }
    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public String getCube() {
        return cube;
    }

    public void setCube(String cube) {
        this.cube = cube;
    }

    public String getMdxQuery() {
        return mdxQuery;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Query(String schema, String databaseType, String server, int port, String database, String username, String password, String cube, String mdxQuery) {
        this.schema = schema;
        this.databaseType = databaseType;
        this.server = server;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.cube = cube;
        this.mdxQuery = mdxQuery;
    }

    public Query(){

    }



}
