package com.example.shdemo.service;

import com.example.shdemo.domain.Label;

import java.util.List;

public interface LabelService {

    List getAllLabels();
    void addLabelToSock(Long labelId, Long sockId);
    Long addLabel(Label label);
    Label getLabelById(Long labelId);
    Label getLabelByLabel(String label);
    void updateLabel(Label label);
    void deleteLabel(Label label);
}
