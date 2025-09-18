package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.action.UpdateActionTokenRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.action.UpdateActionTokenResponse;
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
    public ApiResponse<UpdateActionTokenResponse> updateActionToken(@RequestBody @Valid UpdateActionTokenRequest updateActionTokenRequest, @PathVariable Integer actionID) {
        UpdateActionTokenResponse result = actionService.updateActionToken(actionID, updateActionTokenRequest);
        return ApiResponse.<UpdateActionTokenResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Action updated successfully")
                .resutl(result)
                .build();
    }
}
