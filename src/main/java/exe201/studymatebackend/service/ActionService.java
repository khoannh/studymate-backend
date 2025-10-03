package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.action.UpdateActionCoinRequest;
import exe201.studymatebackend.dto.response.action.UpdateActionCoinResponse;

public interface ActionService {
//    CreateNewActionResponse createNewAction(CreateNewActionRequest createNewActionRequest);

    UpdateActionCoinResponse updateActionCoin(Integer actionID, UpdateActionCoinRequest updateActionCoinRequest);

}
