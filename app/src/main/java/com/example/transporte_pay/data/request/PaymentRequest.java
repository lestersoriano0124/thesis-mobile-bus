package com.example.transporte_pay.data.request;

public class PaymentRequest {

    private boolean status;
    private String remarks;
    private String itemId;
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

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }


}
