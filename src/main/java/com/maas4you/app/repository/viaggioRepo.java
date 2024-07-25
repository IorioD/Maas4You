package com.maas4you.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maas4you.app.model.viaggio;


@Repository
public interface viaggioRepo extends JpaRepository<viaggio,Long> {
    List<viaggio> findAllByUsername(String username);
}