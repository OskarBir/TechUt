package ug.obir.jdbcdemo.service;

import ug.obir.jdbcdemo.domain.Sock;

import java.sql.*;
import java.util.List;

public interface SockService {

	void addSock(Sock sock) throws SQLException;
	void deleteSock(Sock sock) throws SQLException;
	Connection getConnection();
	void clearSocks();
	List<Sock> getAllSocks() throws SQLException;
	void addAllSocks(List<Sock> sockList);
	Sock findSockById(long id);
	Sock findSockByBrand(String Brand);
	Sock removeSock(Sock sock);
	List<Sock> getAllCottonSocks(String sortingColumn);
	List<Sock> getAllBrandSocks(String sortBy, String brand);
	List<Sock> getAllSocksWithValueBetween(String price1, String price2) ;


}
