package ua.kpi.klopotenkoapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ratings")
@Setter
@Getter
public class RatingEntity {

    @Id
    @Column(name = "recipe_id")
    private Long id;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "votes_number", nullable = false)
    private Integer votesNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;
}
