package com.ttpsc.signingintheair.parcel.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class CompletedDeliveryRequest {

    private UUID pid;

    private StringBuilder signeePhoto;

    private StringBuilder signeeSignaturePhoto;
}
