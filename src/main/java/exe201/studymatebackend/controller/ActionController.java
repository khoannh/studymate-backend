package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.action.UpdateActionCoinRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.action.UpdateActionCoinResponse;
import exe201.studymatebackend.service.ActionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action-management")
public class ActionController {


    @Autowired
    private ActionService actionService;

//    @PostMapping("/action")
//    public ApiResponse<CreateNewActionResponse> createAction(@RequestBody @Valid CreateNewActionRequest createNewActionRequest) {
//        CreateNewActionResponse result = actionService.createNewAction(createNewActionRequest);
//        return ApiResponse.<CreateNewActionResponse>builder()
//                .code(HttpStatus.CREATED.value())
//                .message("Action created successfully")
//                .resutl(result)
//                .build();
//    }

    @PutMapping("/action/{actionID}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<UpdateActionCoinResponse> updateActionCoin(@RequestBody @Valid UpdateActionCoinRequest updateActionCoinRequest, @PathVariable Integer actionID) {
        UpdateActionCoinResponse result = actionService.updateActionCoin(actionID, updateActionCoinRequest);
        return ApiResponse.<UpdateActionCoinResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Action updated successfully")
                .result(result)
                .build();
    }
}
