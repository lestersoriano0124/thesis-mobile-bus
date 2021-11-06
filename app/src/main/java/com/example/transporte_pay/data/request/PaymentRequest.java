package com.example.transporte_pay.data.request;

public class PaymentRequest {

    private boolean status;
    private String remarks;
    private String itemId;
    private String gcash_number;
    private String referenceId;

    public boolean isStatus(){
        return status;
    }
    public String getRemarks(){
        return remarks;
    }



    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getGcash() {
        return gcash_number;
    }

    public void setGcash(String gcash_number) {
        this.gcash_number = gcash_number;
    }
    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }


}
