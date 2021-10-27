package com.ttpsc.signingintheair.parcel.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "parcel")
public class Parcel implements Serializable {

    private static final long serialVersionUID = -7552766144802474807L;

    @Id
    @Column(name = "pid", nullable = false)
    private UUID pid;

    @Column(name = "from_address", nullable = false)
    private String fromAddress;

    @Column(name = "to_address", nullable = false)
    private String toAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParcelStatus status;

    @Column(name = "receiver", nullable = false)
    private String receiver;

    @Column(name = "signee_photo")
    private String signeePhoto;

    @Column(name = "signee_signature_photo")
    private String signeeSignaturePhoto;
}
