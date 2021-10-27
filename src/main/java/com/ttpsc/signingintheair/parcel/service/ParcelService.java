package com.ttpsc.signingintheair.parcel.service;

import com.ttpsc.signingintheair.parcel.entity.CompletedDeliveryRequest;
import com.ttpsc.signingintheair.parcel.entity.Parcel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface ParcelService {

    Parcel getParcel(UUID pid);

    List<Parcel> getAllNotDeliveredParcels();

    List<Parcel> getOutForDeliveryParcels();

    List<Parcel> getAllParcel();

    Parcel addParcel(Parcel parcel);

    Parcel deliverParcel(UUID pid);

    Parcel completeDelivery(CompletedDeliveryRequest completedDeliveryRequest);

    Page<Parcel> getAll(Specification<Parcel> specification, Pageable pageable);
}
