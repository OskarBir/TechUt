package ug.obir.jdbcdemo;

import ug.obir.jdbcdemo.domain.Sock;
import ug.obir.jdbcdemo.service.SockServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {

        SockServiceImpl sockServiceImpl = new SockServiceImpl();

        List<Sock> sockList = new ArrayList<>();

        sockServiceImpl.clearSocks();
        //Przykładowe dane do prezentacji działania programu
        for (int i = 0; i < 10; i++) {
            sockList.add(new Sock("Nike", i + 15, i % 2 == 0, 13.1f + i * 3));
        }
        for (int i = 0; i < 15; i++) {
            sockList.add(new Sock("Adidas", i + 30, i % 3 == 0, 5.1f + i * 2));
        }
        for (int i = 0; i < 20; i++) {
            sockList.add(new Sock("Puma", i + 23, i % 4 == 0, 12.5f + i * 5));
        }

        sockServiceImpl.addAllSocks(sockList);
        System.out.println("Wszystkie skarpetki:");
        Object[] all = sockServiceImpl.getAllSocks().toArray();
        for (Object x : all)
            System.out.println(x);


        System.out.println("Wszystkie skarpetki bawełniane:");
        Object[] cotton = sockServiceImpl.getAllCottonSocks("id").toArray();
        for (Object x : cotton)
            System.out.println(x);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj jakiej marki skarpetek szukasz:");
        String brand = scanner.nextLine();

        System.out.println("Wszystkie skarpetki wybranej marki:");
        Object[] socksBrand = sockServiceImpl.getAllBrandSocks("id", brand).toArray();
        for (Object x : socksBrand)
            System.out.println(x);

        System.out.println("Podaj ceny między jakimi szukasz skarpetek");
        String price1 = scanner.nextLine();
        String price2 = scanner.nextLine();
        System.out.println("Wszystkie skarpetki z ceną pomiędzy " + price1 + " a " + price2 + ":");
        Object[] socksPrice = sockServiceImpl.getAllSocksWithValueBetween(price1, price2).toArray();
        for (Object x : socksPrice)
            System.out.println(x);

    }
}

