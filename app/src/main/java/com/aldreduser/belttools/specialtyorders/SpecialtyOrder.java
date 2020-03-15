package com.aldreduser.belttools.specialtyorders;

class SpecialtyOrder {
    // these are the names of the shared preferences
    String orderNumStorage;
    String infoStorage;
    String noteStorage;

    SpecialtyOrder() {
    }

    String getOrderNumStorage() {
        return orderNumStorage;
    }
    void setOrderNum() {
        this.orderNumStorage = orderNumStorage;
    }

    String getInfoStorage() {
        return infoStorage;
    }
    void setInfo() {
        this.infoStorage = infoStorage;
    }

    String getNoteStorage() {
        return noteStorage;
    }
    void setNote() {
        this.noteStorage = orderNumStorage;
    }
}
