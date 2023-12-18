package ua.kpi.klopotenkoapp.service;

import ua.kpi.klopotenkoapp.contract.dto.UserDTO;

public interface UserService {

    UserDTO getUserByEmail(String email);

    String getUserEmailByRecipeId(long recipeId);
}
