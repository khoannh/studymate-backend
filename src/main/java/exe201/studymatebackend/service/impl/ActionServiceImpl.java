package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.request.action.UpdateActionTokenRequest;
import exe201.studymatebackend.dto.response.action.UpdateActionTokenResponse;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.Action;
import exe201.studymatebackend.repository.ActionRepository;
import exe201.studymatebackend.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    private ActionRepository actionRepository;
//
//    @Override
//    @Transactional
//    public CreateNewActionResponse createNewAction(CreateNewActionRequest request) {
//        Action newAction = new Action();
//        if (actionRepository.findByActionName(request.getActionName()) != null) {
//            throw new AppException(ErrorCode.ACTION_ALREADY_EXIST);
//        }
//        newAction.setActionName(request.getActionName());
//        newAction.setActionToken(request.getActionToken());
//        actionRepository.save(newAction);
//        return CreateNewActionResponse.builder()
//                .actionID(newAction.getActionID())
//                .actionName(newAction.getActionName())
//                .actionToken(newAction.getActionToken())
//                .build();
//    }

    @Override
    @Transactional
    public UpdateActionTokenResponse updateActionToken(Integer actionID, UpdateActionTokenRequest updateActionTokenRequest) {
        if (!actionRepository.existsByActionID(actionID)) {
            throw new AppException(ErrorCode.ACTION_DOES_NOT_EXIST);
        }
        Action action = actionRepository.findByActionID(actionID);
        action.setActionToken(updateActionTokenRequest.getActionToken());
        return UpdateActionTokenResponse.builder()
                .actionID(action.getActionID())
                .actionToken(action.getActionToken())
                .build();
    }


}
