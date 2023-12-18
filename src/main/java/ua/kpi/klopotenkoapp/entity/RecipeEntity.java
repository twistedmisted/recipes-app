package ua.kpi.klopotenkoapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ua.kpi.klopotenkoapp.contract.util.Complexity;
import ua.kpi.klopotenkoapp.contract.util.RecipeStatus;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "recipes")
@Setter
@Getter
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ElementCollection
    private List<String> ingredients = new ArrayList<>();

    @ElementCollection
    private List<String> steps = new ArrayList<>();

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RecipeStatus status;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private RegionEntity region;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "hours", nullable = false)
    private Integer hours;

    @Column(name = "minutes", nullable = false)
    private Integer minutes;

    @Column(name = "complexity", nullable = false)
    private Complexity complexity;

    @OneToOne(mappedBy = "recipe", cascade = ALL)
    @PrimaryKeyJoinColumn
    private RatingEntity rating;

    @OneToOne(mappedBy = "recipe", cascade = ALL)
    private PhotoEntity photo;

    @OneToOne(mappedBy = "recipe", cascade = ALL)
    private VideoEntity video;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
