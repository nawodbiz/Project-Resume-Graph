package com.example.Project.Resume.Graph.errors;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
@Data

@NoArgsConstructor
public class ApiException {
    private Boolean success;
    private String message;
    private HttpStatus httpStatus;
    private JSONObject jsonObject = new JSONObject();
    public ApiException(Boolean success, String message, HttpStatus httpStatus) {
        this.success = success;
        this.message = message;
        this.httpStatus = httpStatus;
        jsonObject.put("success",success);
        jsonObject.put("message",message);
        jsonObject.put("status",httpStatus);
    }
}
