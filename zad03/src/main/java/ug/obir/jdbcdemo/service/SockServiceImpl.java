package ug.obir.jdbcdemo.service;

import ug.obir.jdbcdemo.domain.Sock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SockServiceImpl implements SockService {

    private Connection connection;
    private String url = "jdbc:hsqldb:hsql://localhost/workdb";
    private String createTableSock = "CREATE TABLE Sock(id bigint GENERATED BY DEFAULT AS IDENTITY, brand varchar(20), size integer, cotton boolean, price float(8))";
    private Statement statement;
    private PreparedStatement getAllSocksStmt;
    private PreparedStatement addSockStmt;
    private PreparedStatement deleteAllSocksStmt;
    private PreparedStatement deleteSockStmt;

    public SockServiceImpl() {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            ResultSet resultSet = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (resultSet.next()) {
                if ("Sock".equalsIgnoreCase(resultSet.getString("table_name"))) {
                    tableExists = true;
                    break;
                }
            }
            if (!tableExists) {
                statement.executeUpdate(createTableSock);
            }

            addSockStmt = connection.prepareStatement("INSERT INTO Sock(brand, size, cotton, price) VALUES (?, ?, ?, ?)");
            deleteAllSocksStmt = connection.prepareStatement("DELETE FROM Sock");
            getAllSocksStmt = connection.prepareStatement("SELECT id, brand, size, cotton, price FROM Sock");
            deleteSockStmt = connection.prepareStatement("DELETE FROM Sock WHERE id = ?");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSock(Sock sock) throws SQLException {
        addSockStmt.setString(1, sock.getBrand());
        addSockStmt.setInt(2, sock.getSize());
        addSockStmt.setBoolean(3, sock.getCotton());
        addSockStmt.setFloat(4, sock.getPrice());
        addSockStmt.executeUpdate();
    }


    @Override
    public void clearSocks() {
        try {
            deleteAllSocksStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Sock> getAllSocks() throws SQLException {
        List<Sock> socks = new ArrayList<>();
        ResultSet socksRs = getAllSocksStmt.executeQuery();
        while (socksRs.next()) {
            Sock sock = new Sock(socksRs.getLong(1), socksRs.getString("brand"),
                    socksRs.getInt("size"),
                    socksRs.getBoolean("cotton"),
                    socksRs.getFloat("price"));
            socks.add(sock);

        }
        return socks;

    }

    @Override
    public void addAllSocks(List<Sock> sockList) {

        try {
            connection.setAutoCommit(false);
            for (Sock sock : sockList) {
                addSockStmt.setString(1, sock.getBrand());
                addSockStmt.setInt(2, sock.getSize());
                addSockStmt.setBoolean(3, sock.getCotton());
                addSockStmt.setFloat(4, sock.getPrice());
                addSockStmt.executeUpdate();
            }
            connection.commit();

        } catch (SQLException exception) {

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Sock findSockById(long id) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Sock WHERE id=" + id);
            if (resultSet.next())
                return getSockById(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Sock getSockById(ResultSet socksRs) throws SQLException {
        return new Sock(socksRs.getLong(1), socksRs.getString(2),
                socksRs.getInt(3), socksRs.getBoolean(4),
                socksRs.getFloat(5));

    }

    @Override
    public Sock removeSock(Sock sock) {
        try {
            deleteSockStmt.setString(1, String.valueOf(sock.getId()));
            deleteSockStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sock;
    }

    @Override
    public List<Sock> getAllCottonSocks(String sortBy) {
        List<Sock> cottonSocks = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Sock WHERE cotton = true ORDER BY " + sortBy + " ASC");
            while (resultSet.next()) {
                cottonSocks.add(getSockById(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cottonSocks;
    }

    @Override
    public List<Sock> getAllBrandSocks(String sortBy, String brand) {
        List<Sock> brandSocks = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Sock WHERE brand ='" + brand + "' ORDER BY " + sortBy + " ASC");
            while (resultSet.next()) {
                brandSocks.add(getSockById(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandSocks;
    }

    @Override
    public List<Sock> getAllSocksWithValueBetween(String price1, String price2) {
        List<Sock> socks = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Sock WHERE price BETWEEN " + price1 + " AND " + price2);
            while (resultSet.next()) {
                socks.add(getSockById(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return socks;
    }

}
