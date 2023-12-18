package ua.kpi.klopotenkoapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.CascadeType.ALL;

@Entity

@Table(name = "videos")
@Setter
@Getter
public class VideoEntity extends MediaEntity {

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private RecipeEntity recipe;
}
