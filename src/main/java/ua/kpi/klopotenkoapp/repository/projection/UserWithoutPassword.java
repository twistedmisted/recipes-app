package ua.kpi.klopotenkoapp.repository.projection;

public interface UserWithoutPassword {

    Long getId();

    String getName();

    String getSurname();

    String getUsername();

    String getEmail();
}
