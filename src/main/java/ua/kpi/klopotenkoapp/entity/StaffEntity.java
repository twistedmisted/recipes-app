package ua.kpi.klopotenkoapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ua.kpi.klopotenkoapp.contract.util.StaffAccessLevel;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "staffs")
@Setter
@Getter
public class StaffEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "access_level", nullable = false)
    private StaffAccessLevel accessLevel;
}
