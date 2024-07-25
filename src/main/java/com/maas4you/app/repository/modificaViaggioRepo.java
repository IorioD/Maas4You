package com.maas4you.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maas4you.app.model.modificaViaggio;
import com.maas4you.app.model.viaggio;

@Repository
public interface modificaViaggioRepo extends JpaRepository<modificaViaggio,Long>{
    boolean existsByVecchioViaggio(viaggio viaggio);
}