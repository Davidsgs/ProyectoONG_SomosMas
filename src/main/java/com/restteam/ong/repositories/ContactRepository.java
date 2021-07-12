package com.restteam.ong.repositories;

import com.restteam.ong.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact,Long> {

    Optional<Contact> findByName(String name);
}
