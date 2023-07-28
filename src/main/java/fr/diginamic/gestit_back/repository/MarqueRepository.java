package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarqueRepository extends JpaRepository<Marque, Integer> {
}