package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.ReglePret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReglePretRepository extends JpaRepository<ReglePret, Integer> {
    
    Optional<ReglePret> findFirstByProfilIdAndTypePretId(Integer idProfil, Integer idTypePret);
    List<ReglePret> findByProfilId(Integer idProfil);
    List<ReglePret> findByTypePretId(Integer idTypePret);
}
