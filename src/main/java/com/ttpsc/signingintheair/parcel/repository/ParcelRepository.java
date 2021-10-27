package com.ttpsc.signingintheair.parcel.repository;

import com.ttpsc.signingintheair.parcel.entity.Parcel;
import com.ttpsc.signingintheair.parcel.entity.ParcelStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParcelRepository extends JpaRepository<Parcel, UUID>, JpaSpecificationExecutor<Parcel> {

    List<Parcel> getAllByStatusNotIn(List<ParcelStatus> status);

    List<Parcel> getAllByStatusIn(List<ParcelStatus> status);

    @Query("SELECT p FROM Parcel p WHERE p.pid = :pid AND p.status = 'OUT_FOR_DELIVERY'")
    Optional<Parcel> getOutForDeliveryParcelByPid(@Param("pid") UUID pid);
}
