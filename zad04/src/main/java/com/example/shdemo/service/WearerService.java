package com.example.shdemo.service;

import com.example.shdemo.domain.Wearer;

import java.util.List;

public interface WearerService {

    Long addWearer(Wearer wearer);
    List getAllWearers();
    Wearer getWearerByName(String name);
    void updateWearer(Wearer wearer);
    void deleteWearer(Wearer wearer);

}