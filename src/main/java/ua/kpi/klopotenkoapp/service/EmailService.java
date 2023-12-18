package ua.kpi.klopotenkoapp.service;

import ua.kpi.klopotenkoapp.UnsuccessfulMessageInfo;
import ua.kpi.klopotenkoapp.contract.dto.SuccessfulMessageInfo;

public interface EmailService {

    void sendRecipeAcceptedMessage(SuccessfulMessageInfo messageInfo);
    void sendRecipeRejectedMessage(UnsuccessfulMessageInfo messageInfo);
}
