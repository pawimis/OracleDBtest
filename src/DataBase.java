import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.util.ArrayList;

class DataBase {
    private Connection conn;

    DataBase() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL("xxx");
        conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        int t = 5;
        DatabaseMetaData metaData = conn.getMetaData();
        System.out.println("Driver version: " + metaData.getDriverVersion());
    }

    void insertToEmployeeDataBase(String name, String position, int agency) throws SQLException {

        String insert = "INSERT INTO EMPLOYEE VALUES (?,?,?,?)";
        Savepoint sp = conn.setSavepoint();

        try (PreparedStatement statement = conn.prepareStatement(insert)) {
            String id = getIDFromDataBase(Vars.EMPLOYEE_DB);
            if (id == null)
                id = "1";
        

            statement.setString(1, id);
            statement.setInt(2, agency);
            statement.setString(3, name);
            statement.setString(4, position);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            conn.rollback(sp);
        } finally {
            conn.commit();
        }

    }

    

    void insertToVariationDataBase(String security, int insurance, String eq) throws SQLException {

        String insert = "INSERT INTO VARIATION VALUES(?,?,?,?)";
        Savepoint sp = conn.setSavepoint();

        try (PreparedStatement statement = conn.prepareStatement(insert)) {
            String id = getIDFromDataBase(Vars.VARIATION_DB);
            if (id == null)
                id = "1";
            statement.setString(1, id);
            statement.setString(2, security);
            statement.setInt(3, insurance);
            statement.setString(4, eq);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            conn.rollback(sp);
        } finally {
            conn.commit();
        }
    }
void insertToClientDataBase(String name) throws SQLException {

        String insert = "INSERT INTO CLIENT VALUES (?,?,CURRENT_DATE)";
        Savepoint sp = conn.setSavepoint();

        try (PreparedStatement statement = conn.prepareStatement(insert)) {
            String id = getIDFromDataBase(Vars.CLIENT_DB);
            if (id == null)
                id = "1";
            statement.setString(1, id);
            statement.setString(2, name);
            //statement.setString(3, date);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            conn.rollback(sp);
        } finally {
            conn.commit();

        }

    }
    void insertToCarDataBase(int agency, String car) throws SQLException {

        String insert = "INSERT INTO CAR VALUES (?,?,?,?)";
        Savepoint sp = conn.setSavepoint();

        try (PreparedStatement statement = conn.prepareStatement(insert)) {
            String id = getIDFromDataBase(Vars.CAR_DB);
            if (id == null)
                id = "1";
            statement.setString(1, id);
            statement.setInt(2, agency);
            statement.setString(3, car);
            statement.setString(4, "ON");
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            conn.rollback(sp);
        } finally {
            conn.commit();
        }
    }

    ArrayList<String> selectFromClientDataBase() throws SQLException {
        ArrayList<String> clientList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String query = "SELECT CLIENT_NAME , JOIN_DATE FROM CLIENT";
        Savepoint sp = conn.setSavepoint();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                String date = resultSet.getString(2);
                sb.append(name).append(" joined: ").append(date);
                clientList.add(sb.toString());
                sb.setLength(0);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            conn.rollback(sp);
        } finally {
            conn.commit();

        }
        return clientList;
    }

    ArrayList<String> selectFromEmployeeDataBase() throws SQLException {
        ArrayList<String> employeeList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String query = "SELECT EMPLOYEE_NAME , EMPLOYE_POSITION ,(SELECT AGENCY_LOCATION FROM AGENCY WHERE AGENCY.AGENCY_ID = EMPLOYEE.AGENCY_ID) FROM EMPLOYEE";
        Savepoint sp = conn.setSavepoint();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                String position = resultSet.getString(2);
                String agency = resultSet.getString(3);
                sb.append(name).append(" is ").append(position).append(" from ").append(agency);
                employeeList.add(sb.toString());
                sb.setLength(0);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            conn.rollback(sp);
        } finally {
            conn.commit();

        }
        return employeeList;
    }

    

    ArrayList<String> selectFromVariationDataBase() throws SQLException {
        ArrayList<String> variationList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String query = "SELECT VARIATION_SECURITY,VARIATION_INSURANCE,VARIATION_EQUIPMENT FROM VARIATION";
        Savepoint sp = conn.setSavepoint();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String security = resultSet.getString(1);
                String insurance = resultSet.getString(2);
                String equipment = resultSet.getString(3);
                sb.append("Insurance with secutity = ").append(security).append(" insurance = ").append(insurance).append(" and equipment = ").append(equipment);
                variationList.add(sb.toString());
                sb.setLength(0);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            conn.rollback(sp);
        } finally {
            conn.commit();
        }
        return variationList;
    }

    private String getIDFromDataBase(String database) throws SQLException {
        String query = "SELECT MAX(" + database + "_ID) FROM " + database;
        Savepoint sp = conn.setSavepoint();
        String id = null;
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            id = resultSet.getString(1);
            if (id != null)
                id = String.valueOf(Integer.parseInt(id) + 1);

        } catch (SQLException ex) {
            ex.printStackTrace();
            conn.rollback(sp);
        } finally {
            conn.commit();
        }
        return id != null ? id : "1";
    }
    ArrayList<String> selectFromCarDataBase() throws SQLException {
        ArrayList<String> carList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String query = "SELECT (SELECT AGENCY_LOCATION FROM AGENCY WHERE AGENCY.AGENCY_ID = CAR.AGENCY_ID),CAR_MODEL,STATUS JOIN_DATE FROM CAR";
        Savepoint sp = conn.setSavepoint();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String agency = resultSet.getString(1);
                String car = resultSet.getString(2);
                String status = resultSet.getString(3);
                sb.append("from ").append(agency).append(" model: ").append(car).append(" status :").append(status);
                carList.add(sb.toString());
                sb.setLength(0);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            conn.rollback(sp);
        } finally {
            conn.commit();

        }
        return carList;
    }
}
