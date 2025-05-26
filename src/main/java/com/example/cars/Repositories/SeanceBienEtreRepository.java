package com.example.cars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.cars.entities.SeanceBienEtre;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SeanceBienEtreRepository extends JpaRepository<SeanceBienEtre, Long> {

    // Méthodes de base héritées de JpaRepository :
    // - findAll()
    // - findById(Long id)
    // - save(SeanceBienEtre entity)
    // - deleteById(Long id)
    // - existsById(Long id)

    // Méthodes personnalisées utiles pour votre application

    /**
     * Trouver toutes les séances par type
     */
    List<SeanceBienEtre> findByTypeOrderByDateAsc(String type);

    /**
     * Trouver les séances disponibles (avec des places libres)
     */
    List<SeanceBienEtre> findByPlacesDisponiblesGreaterThanOrderByDateAsc(int places);

    /**
     * Trouver les séances dans une plage de dates
     */
    @Query("SELECT s FROM SeanceBienEtre s WHERE s.date BETWEEN :startDate AND :endDate ORDER BY s.date ASC")
    List<SeanceBienEtre> findSeancesBetweenDates(@Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);

    /**
     * Trouver les séances d'un instructeur spécifique
     */
    List<SeanceBienEtre> findByInstructeurOrderByDateAsc(String instructeur);

    /**
     * Trouver les séances futures (à partir de maintenant)
     */
    @Query("SELECT s FROM SeanceBienEtre s WHERE s.date >= :now ORDER BY s.date ASC")
    List<SeanceBienEtre> findFutureSeances(@Param("now") LocalDateTime now);

    /**
     * Trouver les séances du jour
     */
    @Query("SELECT s FROM SeanceBienEtre s WHERE DATE(s.date) = DATE(:date) ORDER BY s.date ASC")
    List<SeanceBienEtre> findSeancesByDate(@Param("date") LocalDateTime date);

    /**
     * Compter les séances par type
     */
    long countByType(String type);

    /**
     * Trouver les séances par niveau
     */
    List<SeanceBienEtre> findByNiveauOrderByDateAsc(String niveau);

    /**
     * Trouver les séances avec une durée spécifique
     */
    List<SeanceBienEtre> findByDureeOrderByDateAsc(int duree);

    /**
     * Vérifier s'il existe des conflits d'horaire pour un instructeur
     */
    @Query("SELECT COUNT(s) > 0 FROM SeanceBienEtre s WHERE s.instructeur = :instructeur " +
            "AND s.date BETWEEN :startTime AND :endTime AND s.id != :excludeId")
    boolean existsConflictForInstructor(@Param("instructeur") String instructeur,
                                        @Param("startTime") LocalDateTime startTime,
                                        @Param("endTime") LocalDateTime endTime,
                                        @Param("excludeId") Long excludeId);

    /**
     * Trouver les séances les plus populaires (peu de places disponibles)
     */
    @Query("SELECT s FROM SeanceBienEtre s WHERE (s.placesDisponibles * 1.0 / s.capacite) < :threshold ORDER BY s.date ASC")
    List<SeanceBienEtre> findPopularSeances(@Param("threshold") double threshold);
}