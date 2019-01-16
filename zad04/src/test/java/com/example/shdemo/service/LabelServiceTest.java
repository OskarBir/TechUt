package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.example.shdemo.domain.Label;
import com.example.shdemo.domain.Sock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class LabelServiceTest {

    @Autowired
    LabelService labelService;
    @Autowired
    SockService sockService;

    private final String LABEL1 = "Some label";

    private final String SOCK1_NAME = "elastico";
    private final Boolean SOCK1_COTTON = true;
    private final Double SOCK1_PRICE = 51.2;
    private Date SOCK1_DATE;

    {
        try {
            SOCK1_DATE = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2018");
        } catch ( ParseException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        List<Label> labels = labelService.getAllLabels();

        for (Label label : labels) {
            if (label.getLabel().equals(LABEL1))
                labelService.deleteLabel(label);
        }
    }

    @Test
    public void addLabelTest() {

        Label labelToAdd = new Label();
        labelToAdd.setLabel(LABEL1);
        labelService.addLabel(labelToAdd);
        Label addedLabel = labelService.getLabelByLabel(LABEL1);

        Assert.assertEquals(LABEL1, addedLabel.getLabel());
    }

    @Test
    public void updateLabelTest() {
        Label labelToAdd = new Label();
        labelToAdd.setLabel(LABEL1);
        labelService.addLabel(labelToAdd);

        labelToAdd.setLabel("New label");
        labelService.updateLabel(labelToAdd);
        Label addedLabel = labelService.getLabelByLabel("New label");

        assertEquals("New label", addedLabel.getLabel());
    }

    @Test
    public void addLabelToSockTest() {
        Sock sockToAdd = new Sock();
        sockToAdd.setName(SOCK1_NAME);
        sockToAdd.setCotton(SOCK1_COTTON);
        sockToAdd.setPrice(SOCK1_PRICE);
        sockToAdd.setDateOfProduction(SOCK1_DATE);

        Label labelToAdd = new Label();
        labelToAdd.setLabel(LABEL1);
        labelToAdd.setSock(sockToAdd);
        sockToAdd.setLabel(labelToAdd);

        sockService.addSock(sockToAdd);
        labelService.addLabel(labelToAdd);

        Sock addedSock = sockService.getSockByName(SOCK1_NAME);
        Label socksLabel = addedSock.getLabel();

        assertEquals(SOCK1_NAME,addedSock.getName());
        assertEquals(SOCK1_PRICE,addedSock.getPrice());
        assertEquals(SOCK1_COTTON,addedSock.getCotton());
        assertEquals(SOCK1_DATE,addedSock.getDateOfProduction());
        assertEquals(LABEL1,socksLabel.getLabel());
        assertEquals(addedSock,socksLabel.getSock());
    }

    @Test
    public void deleteLabelTest(){
        Label labelToRemove = new Label();
        labelToRemove.setLabel(LABEL1);

        Sock sockToAdd = new Sock();
        sockToAdd.setName(SOCK1_NAME);
        sockToAdd.setCotton(SOCK1_COTTON);
        sockToAdd.setPrice(SOCK1_PRICE);
        sockToAdd.setLabel(labelToRemove);
        sockToAdd.setDateOfProduction(SOCK1_DATE);
        labelToRemove.setSock(sockToAdd);

        sockService.addSock(sockToAdd);
        labelService.addLabel(labelToRemove);

        labelService.deleteLabel(labelService.getLabelByLabel(LABEL1));

        Sock addedSock = sockService.getSockByName(SOCK1_NAME);

        assertNull(addedSock.getLabel());

    }
}
