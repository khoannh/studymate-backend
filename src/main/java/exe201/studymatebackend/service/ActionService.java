package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.action.UpdateActionTokenRequest;
import exe201.studymatebackend.dto.response.action.UpdateActionTokenResponse;

public interface ActionService {
//    CreateNewActionResponse createNewAction(CreateNewActionRequest createNewActionRequest);

    UpdateActionTokenResponse updateActionToken(Integer actionID, UpdateActionTokenRequest updateActionTokenRequest);

}
