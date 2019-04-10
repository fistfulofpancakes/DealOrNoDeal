package com.dealornodeal.java;

import java.util.List;

public class Banker {
    private int offer;
    private int prevOffer;

    public Banker() {

    }

    public void offer(List<Case> cases) {
        int caseNum = 0;
        int valTotal = 0;
        for (Case brief : cases) {
            if (!brief.invalid() || brief.isMyCase()) {
                caseNum++;
                valTotal += brief.getVal();
            }
        }
        offer = valTotal/caseNum;
        System.out.println("Your offer is: " + offer);
        System.out.println("Your previous offer was: " + prevOffer);
        prevOffer = offer;
    }

    public int getOffer() {
        return offer;
    }
}
